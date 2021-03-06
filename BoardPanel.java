import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class BoardPanel extends JPanel{
//*************************************************************************
// CONSTANTS
//************************************************************************* 
   private final int NUMBER_COLUMNS;
   private final int NUMBER_ROWS;
      
   private final static double FILL_SCALE = 0.75; // MUST BE > 0 and < 1

   private final static Color DEFAULT_TOKEN_COLOR = Color.pink;
   private final static Color VALID_TARGET_COLOR = Color.black;



//*************************************************************************
// INSTANCE VARIABLES
//************************************************************************* 

   private BufferedImage bgImg;
    private BufferedImage r;
     private BufferedImage b;
      private BufferedImage y;
       private BufferedImage g;


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
   public BoardPanel(Game game){
      super();
      selectedPawn = null;
      selectedSpace = null;
      targetSpaces = new ArrayList<Space>();
      this.game = game;
      NUMBER_COLUMNS = game.getBoard().COLUMNS;
      NUMBER_ROWS = game.getBoard().ROWS;
      
      bgImg = null;
      try{
         bgImg = ImageIO.read(new File("board.jpg"));
         r = ImageIO.read(new File("red.jpg"));
         b = ImageIO.read(new File("blue.jpg"));
         y = ImageIO.read(new File("yellow.jpg"));
         g = ImageIO.read(new File("green.jpg"));
      }
      catch (IOException e){
         System.out.println("COULD NOT READ BOARD PANEL IMAGES!");
      }
      
   } 
//-------------------------------------------------------------------------
   public void selectPawn(Pawn p){
      selectedPawn = p;
      //updateTargets(); 
   }// end of selectPawn(Pawn)
   
   
   public void deselectPawn(){
      selectedPawn = null;
      //updateTargets();
   }
   
   public Pawn getSelectedPawn(){
      return selectedPawn;
   }
   
   public boolean isPawnSelected(){
      boolean result = false;
      if (selectedPawn != null)
         result = true;
      return result;
   }
   
   //---------------------------
   public void selectSpace(Space s){
      selectedSpace = s;
   }   
   
   public void deselectSpace(){
      selectedSpace = null;
   }
   
   public Space getSelectedSpace(){
      return selectedSpace;
   }
   
   public boolean isSpaceSelected(){
      boolean result = false;
      if (selectedSpace != null)
         result = true;
      return result;
   }
   
   
   //---------------------------
   


//-------------------------------------------------------------------------
   /*
   private void updateTargets(){ // update valid move targets for a newly selected pawn
      int [] moves = game.getMoves();
      Ruleset rules = game.getRules();
      if ((moves != null) && (selectedPawn != null)){ // only do moves if available and pawn is selected
         Space sourceSpace = selectedPawn.whereAmI();
         ArrayList<Space> targetSpaces = new ArrayList<Space>();
         for (int i = 0; i < moves.length; i++){
            // get all valid move targets
            targetSpaces.addAll(rules.getTargets(selectedPawn, sourceSpace, moves[i]));
         } // should have list of targets now
         
         if (targetSpaces.size() > 0)
            this.targetSpaces = targetSpaces;
         else
            this.targetSpaces = null;
      
      }
      else
         this.targetSpaces = null;
  }*/
        
//-------------------------------------------------------------------------
  
   public void	paintComponent(Graphics	g){
		super.paintComponent(g);
		
		//	get current	size
		int panelX = getWidth();
		int panelY = getHeight();	 
		int boxWidth =	panelX /	NUMBER_COLUMNS;
		int boxHeight = panelY / NUMBER_ROWS;
		//	resize to nearest	multiple	of	box widths & heights
		setSize((boxWidth*NUMBER_COLUMNS)+1, (boxHeight	* NUMBER_ROWS)+1);

		//	NEEDS	TO	CHANGE
		setBackground(Color.gray);
		
      g.drawImage(bgImg,
                0,
                0,
                panelX,
                panelY,
                null);

      
      
      // Draw all components in Z-Axis Order
      
      /*
      //drawGrid(g); // TEST ONLY
      //draw column dividers
	   for(int i =	0;	i <= NUMBER_COLUMNS;	i++){
		g.setColor(Color.blue);
		g.drawLine(i *	boxWidth, 0,
						i * boxWidth, panelY);
	   }
	
	   //draw row dividers
	   for(int i =	0;	i <= NUMBER_ROWS;	i++){
   		g.setColor(Color.green);
   		g.drawLine(0, i *	boxHeight,
   						panelX, i *	boxHeight);
      }
      */
      
      //DRAW SPACES ????? TEST ONLY??? SEE HOW IT LOOKS....
/*      ArrayList<Space> allSpaces = game.getBoard().getAllSpaces();
      for (int i = 0; i < allSpaces.size(); i++){
            int gridX = allSpaces.get(i).getX();
            int gridY = allSpaces.get(i).getY();
            int xLow = getLowPixelX(gridX);
            int xHigh = getHighPixelX(gridX);  
            int yLow = getLowPixelY(gridY);
            int yHigh = getHighPixelY(gridY);
            allSpaces.get(i).drawMe(g, xLow, yLow, xHigh, yHigh);         
      }
      
  */    
      // DO USING INSTANCE VARIABLES TO REDUCE ON THE FLY COMPUTATION?
      	  

      
      //highlightSpace(g, 0 ,0); // TEST ONLY
      
      
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
		//drawMoves(g);
  		drawTokens(g);       // PAWNS GO ON TOP
	  
	  //System.out.println("Paint	Component");
		
	}//end of paintcomponent(Graphics)

//-------------------------------------------------------------------------


//-------------------------------------------------------------------------
   
   /*
   private void drawMoves(Graphics g){
      //System.out.println(moves);
      if ((targetSpaces != null) && (selectedPawn != null) ){ // only show moves if available and pawn is selected
         //System.out.println(targetSpaces);
         
         //fill in target spaces
         for (int i = 0; i < targetSpaces.size(); i++){
            int gridX = targetSpaces.get(i).getX();
            int gridY = targetSpaces.get(i).getY();
            int xLow = getLowPixelX(gridX);
            int xHigh = getHighPixelX(gridX);  
            int yLow = getLowPixelY(gridY);
            int yHigh = getHighPixelY(gridY);
            g.setColor(VALID_TARGET_COLOR);
            g.fillRect(xLow,yLow,xHigh - xLow,yHigh - yLow);
         
         }
      }
   }// end of drawMoves*/
//-------------------------------------------------------------------------
   private void drawTokens(Graphics g){
      ArrayList<Token> tokens = game.getTokens();
      Token currentToken;
      int gridX, xLow, xHigh;
      int gridY, yLow, yHigh;
      for (int i = 0; i < tokens.size(); i++){
         currentToken = tokens.get(i);
         gridX = currentToken.whereAmI().getX();
         gridY = currentToken.whereAmI().getY();
         xLow = getLowPixelX(gridX);
         xHigh = getHighPixelX(gridX);  
         yLow = getLowPixelY(gridY);
         yHigh = getHighPixelY(gridY);
         currentToken.drawMe(g, xLow, yLow, xHigh, yHigh, FILL_SCALE);  
      }      
      
   } // end drawPawns
   
   
   private void highlightSpace(Graphics g, Space s){
      highlightSpace(g, s.getX(), s.getY());
   }
   
   private void highlightSpace(Graphics g, int x, int y){
      int xLow = getLowPixelX(x);
      int xHigh = getHighPixelX(x);  
      int yLow = getLowPixelY(y);
      int yHigh = getHighPixelY(y);
      g.setColor(VALID_TARGET_COLOR);                   // NEED TO CHANGE
      Graphics2D g2 = (Graphics2D) g;
      int k = 6;
      g2.setStroke(new BasicStroke(k));
      g2.drawRect(xLow + k ,yLow + k,xHigh - xLow - (2*k),yHigh - yLow - (2*k));      
      
   }
//-------------------------------------------------------------------------
//GRID STUFF---------------------------------------------------------------

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
   
   public int getGridX(int x){
      int panelX = this.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      int columnNumber = (x / boxWidth);
      return columnNumber;
   }
   
   public int getGridY(int y){
      int panelY = this.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      int rowNumber = (y / boxHeight);
      return rowNumber;
   }
   
   // grid coords -> pixel coords
   public int getLowPixelX(int x){
      int panelX = this.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      //int columnNumber = (x / boxWidth);
      return boxWidth * x;      
   }

   public int getHighPixelX(int x){
      int panelX = this.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      //int columnNumber = (x / boxWidth);
      return boxWidth * (x + 1);      
   }
   
   public int getLowPixelY(int y){
      int panelY = this.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      //int rowNumber = (y / boxHeight);
      return boxHeight * y;
   }
   
   public int getHighPixelY(int y){
      int panelY = this.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      //int rowNumber = (y / boxHeight);
      return boxHeight * (y + 1);
   }
   

}// end of class