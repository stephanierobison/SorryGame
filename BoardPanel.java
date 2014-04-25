import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

/**
   The BoardPanel is a visual representation of the internal state of
   the Board object. It is comprised of a XY grid each square of which
   is a clickable hot spot which can represent a single Space (or none).
   The various Pawns are drawn directly above the Space which they
   occupy.
   
*/


public class BoardPanel extends JPanel{
//*************************************************************************
// CONSTANTS
//************************************************************************* 
   /** Board width */
   private final int NUMBER_COLUMNS;
   /** Board height */
   private final int NUMBER_ROWS;
      
   /** Scaling factor for objects drawn in Spaces. ie Pawns, Highlighting,
   etc. will be FILL_SCALE * 100% of the size of a space */
   private final static double FILL_SCALE = 0.75; // MUST BE > 0 and < 1

   /** The default color of space highlighting */
   private final static Color VALID_TARGET_COLOR = Color.orange;



//*************************************************************************
// INSTANCE VARIABLES
//************************************************************************* 
   /** The board background image */
   private BufferedImage bgImg;

   /** 
      Main internal state variable. When NULL no pawn is selected
      and selectABLE pawns should be shown. Otherwise a Pawn is
      selected and it's valid moves should be shown
   */
   private Pawn selectedPawn;
   
   /**
      Subsidiary internal state variable. Only used to keep track of
      a Space where there is a move ambiguity.
   */
   private Space selectedSpace;
   
      /**
         Spaces the selected pawn could be moved to. Updated when
         a pawn is selected.
      */
      private ArrayList<Space> targetSpaces; 

   
   /*
      The game whose board this view displays.
   */
   private Game game;
		
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************      
   /**
      @param Game game The Game MODEL this will be a VIEW for
      
      On instantiation the BoardPanel has nothing selected
      and populates itself based on the size of provided game.
   */   
   public BoardPanel(Game game){
      super();
      selectedPawn = null; // nothing selected
      selectedSpace = null;
      targetSpaces = new ArrayList<Space>(); // empty to start
      this.game = game;
      NUMBER_COLUMNS = game.getBoard().COLUMNS; // set by game
      NUMBER_ROWS = game.getBoard().ROWS;
      
      bgImg = null;
      try{
         bgImg = ImageIO.read(new File("board.jpg")); // load background file
      }
      catch (IOException e){
         System.out.println("COULD NOT READ BOARD PANEL IMAGES!");
      }
      
   } 
//-------------------------------------------------------------------------
   /**
      @param Pawn p
      
      Selects the indicated pawn. While a pawn is selected it may be
      possible to move the selected pawn.
   */
   public void selectPawn(Pawn p){
      selectedPawn = p;
   }// end of selectPawn(Pawn)
//-------------------------------------------------------------------------
   /**
      Deselects pawn. Can no longer move but might be able to select
      another pawn.
   */   
   public void deselectPawn(){
      selectedPawn = null;
      //updateTargets();
   }
//-------------------------------------------------------------------------
   /**
      @return the currently selected Pawn, may be null if nothing selected
   */
   public Pawn getSelectedPawn(){
      return selectedPawn;
   }
//-------------------------------------------------------------------------
   /**
      @return true if selectedPawn != null, false otherwise
   */
   public boolean isPawnSelected(){
      boolean result = false;
      if (selectedPawn != null)
         result = true;
      return result;
   }
//-------------------------------------------------------------------------
   /**
      @param Space s
      
      Selects the indicated space. A selected space indicates that an
      ambiguity about how to move to that space has been detected.
   */
   public void selectSpace(Space s){
      selectedSpace = s;
   }   
//-------------------------------------------------------------------------
   /**
      Deselects the selected space making it possible to move the selected
      pawn elsewhere.
   */   
   public void deselectSpace(){
      selectedSpace = null;
   }
//-------------------------------------------------------------------------
   /**
      @return the currently selected space
   */
   public Space getSelectedSpace(){
      return selectedSpace;
   }
//-------------------------------------------------------------------------
   /**
      @return true if selectedSpace != null, false otherwise
    */
   public boolean isSpaceSelected(){
      boolean result = false;
      if (selectedSpace != null)
         result = true;
      return result;
   }
//-------------------------------------------------------------------------
  /**
      @param Graphics g
  
      JPanel's paint method has been overidden to permit this view
      to draw the various Pawns and highlighting that need to be 
      visible above the drawn board background.
  */
   public void	paintComponent(Graphics	g){
		super.paintComponent(g);
		
		//	get current	size
		int panelX = getWidth();
		int panelY = getHeight();	 
		int boxWidth =	panelX /	NUMBER_COLUMNS;
		int boxHeight = panelY / NUMBER_ROWS;
		//	resize to nearest	multiple	of	box widths & heights
		setSize((boxWidth*NUMBER_COLUMNS)+1, (boxHeight	* NUMBER_ROWS)+1);
		
      g.drawImage(bgImg,   // make board image background
                0,
                0,
                panelX,
                panelY,
                null);

      // If both a pawn and a space are selected then highlight them both
      if ((selectedPawn != null) && (selectedSpace != null)){
         highlightSpace(g, selectedPawn.whereAmI());
         highlightSpace(g, selectedSpace); 
      }
		// If just a pawn is selected then highlight it and it's target move spaces
      else if (selectedPawn != null){
         highlightSpace(g, selectedPawn.whereAmI());
         ArrayList<Move> targets = game.getAllTargets(selectedPawn);
         for (int i = 0; i < targets.size(); i++){
            highlightSpace(g, targets.get(i).getTarget());
         }
      }
      // otherwise highlight all selectable pawns
      else{
         ArrayList<Pawn> moveablePawns = game.getMoveablePawns();
         for (int i = 0; i < moveablePawns.size(); i++){
            highlightSpace(g, moveablePawns.get(i).whereAmI());
         }         
      
      }
  		drawTokens(g);       // PAWNS GO ON TOP
	  
		
	}//end of paintcomponent(Graphics)
//-------------------------------------------------------------------------
   /**
      Cycles through all tokens on the board,
      calling their drawme methods in the region
      where their space resides.
   */
   private void drawTokens(Graphics g){
      ArrayList<Token> tokens = game.getTokens();
      Token currentToken;
      int gridX, xLow, xHigh;
      int gridY, yLow, yHigh;
      for (int i = 0; i < tokens.size(); i++){
         currentToken = tokens.get(i);
         gridX = currentToken.whereAmI().getX(); // Grid Coords => Pixel Coords
         gridY = currentToken.whereAmI().getY();
         xLow = getLowPixelX(gridX);
         xHigh = getHighPixelX(gridX);  
         yLow = getLowPixelY(gridY);
         yHigh = getHighPixelY(gridY);
         currentToken.drawMe(g, xLow, yLow, xHigh, yHigh, FILL_SCALE);  
      }      
      
   } // end drawTokens
//-------------------------------------------------------------------------
   /** 
      @param Graphics g
      @param Space s
   
      Highlights Space s by calculating its grid coordinates and calling
      drawme(Graphics, int, int)
   */
   private void highlightSpace(Graphics g, Space s){
      highlightSpace(g, s.getX(), s.getY());
   }
//-------------------------------------------------------------------------
   /**
      @param Graphics g
      @param int x
      @param int y
      
      Highlights a space by drawing a boldly colored square
      inside it's grid X,Y
   */   
   private void highlightSpace(Graphics g, int x, int y){
      int xLow = getLowPixelX(x);
      int xHigh = getHighPixelX(x);       // Grid XY => Pixel XY
      int yLow = getLowPixelY(y);
      int yHigh = getHighPixelY(y);
      g.setColor(VALID_TARGET_COLOR);                  
      Graphics2D g2 = (Graphics2D) g;
      int k = 6;                       // set fat pen
      g2.setStroke(new BasicStroke(k));
      g2.drawRect(xLow + k ,yLow + k,xHigh - xLow - (2*k),yHigh - yLow - (2*k));   // actual draw op    
      
   }
//*************************************************************************
// UTILITY FUNCTIONS
//*************************************************************************
/* The following functions are used to automate several straightforward
   mappings of Grid XY => Pixel XY. */
//*************************************************************************
   public void getGridCoords(int x, int y){
      // Zero index grid of boxes divying up panel evenly
      // in NUMBER_COLUMNS & NUMBER_ROWS
      int panelX = this.getWidth();
      int panelY = this.getHeight();
      int boxWidth = panelX / NUMBER_COLUMNS;
      int boxHeight = panelY / NUMBER_ROWS;
      int columnNumber = (x / boxWidth);
      int rowNumber = (y / boxHeight);
      System.out.println("Bow Column, Row = " + columnNumber + ", " + rowNumber);
   }
//-------------------------------------------------------------------------
   public int getGridX(int x){
      int panelX = this.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      int columnNumber = (x / boxWidth);
      return columnNumber;
   }
//-------------------------------------------------------------------------
   public int getGridY(int y){
      int panelY = this.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      int rowNumber = (y / boxHeight);
      return rowNumber;
   }
//-------------------------------------------------------------------------
   // grid coords -> pixel coords
   public int getLowPixelX(int x){
      int panelX = this.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      //int columnNumber = (x / boxWidth);
      return boxWidth * x;      
   }
//-------------------------------------------------------------------------
   public int getHighPixelX(int x){
      int panelX = this.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      //int columnNumber = (x / boxWidth);
      return boxWidth * (x + 1);      
   }
//-------------------------------------------------------------------------
   public int getLowPixelY(int y){
      int panelY = this.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      //int rowNumber = (y / boxHeight);
      return boxHeight * y;
   }
//-------------------------------------------------------------------------
   public int getHighPixelY(int y){
      int panelY = this.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      //int rowNumber = (y / boxHeight);
      return boxHeight * (y + 1);
   }
   

}// end of class