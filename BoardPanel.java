import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BoardPanel extends JPanel{
//*************************************************************************
// CONSTANTS
//************************************************************************* 
   private final int NUMBER_COLUMNS;
   private final int NUMBER_ROWS;
      
   private final static double FILL_SCALE = 0.75; // MUST BE > 0 and < 1

   private final static Color DEFAULT_TOKEN_COLOR = Color.pink;
   private final static Color VALID_TARGET_COLOR = Color.orange;

//*************************************************************************
// INSTANCE VARIABLES
//************************************************************************* 
   /** 
      Main internal state variable. When NULL no pawn is selected
      and selectABLE pawns should be shown. Otherwise a Pawn is
      selected and it's valid moves should be shown
   */
   private Pawn selectedPawn;
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
      targetSpaces = new ArrayList<Space>();
      this.game = game;
      NUMBER_COLUMNS = game.getBoard().COLUMNS;
      NUMBER_ROWS = game.getBoard().ROWS;
      
   } 
//-------------------------------------------------------------------------
   public void selectPawn(Pawn p){
      selectedPawn = p;
      updateTargets(); 
   }// end of selectPawn(Pawn)
   
   
   public void deselectPawn(){
      selectedPawn = null;
      updateTargets();
   }

//-------------------------------------------------------------------------
   
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
  }
        
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
		setBackground(Color.black);
		
      // Draw all components in Z-Axis Order
      
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
      	  
		drawPawns(g);
		
		drawMoves(g);
	  
	  
	  //System.out.println("Paint	Component");
		
	}//end of paintcomponent(Graphics)

//-------------------------------------------------------------------------


//-------------------------------------------------------------------------
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
   }// end of drawMoves
//-------------------------------------------------------------------------
   private void drawPawns(Graphics g){
      ArrayList<Token> tokens = game.getBoard().getTokens();
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
         currentToken.drawMe(g, xLow, yLow, xHigh, yHigh, DEFAULT_TOKEN_COLOR, FILL_SCALE);  
      }      
      
   } // end drawPawns
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