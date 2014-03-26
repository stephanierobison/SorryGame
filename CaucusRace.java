import java.util.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
// move a single pawn around the board

// NEEDS TO BE REFACTORED TO EXTEND JPANEL INSTEAD

// REPRESENTS A BOARD

public class CaucusRace extends JFrame{
   private final static int NUMBER_COLUMNS = SimpleBoard.COLUMNS;
   private final static int NUMBER_ROWS = SimpleBoard.ROWS;
      
   private final static double FILL_SCALE = 0.75; // MUST BE > 0 and < 1

   private final static Color DEFAULT_TOKEN_COLOR = Color.pink;
   private final static Color VALID_TARGET_COLOR = Color.orange;

   
   
   private final static Color BACKGROUND = Color.black;
	private final static Color BOX = Color.green;
	private final static Color X = Color.white;
	
	private Color backgroundColor;
	private Color boxColor;
	private Color metaColor;
	
	private myCanvas animCanvas;
	

	
	private Random rand;

   // IMPORTANT
   private Board board;
   
   private Pawn selectedPawn;
   private ArrayList<Space> targetSpaces; // spaces the selected pawn could be moved to
   
   // NEEDS TO CHANGE
   private int[] moves; // will change turn by turn, might be null, decided by controller

//CONSTRUCTORS-------------------------------------------------------------	
	public CaucusRace(Board board)
	{
		
      this.board = board;
      selectedPawn = null;
      
		rand = new Random();
		
		setTitle("");  // name the window
		setSize(550, 550);  // set size
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		animCanvas = new myCanvas();
		animCanvas.setBackground(backgroundColor);
		
		add(animCanvas);
		animCanvas.repaint();
      animCanvas.addMouseListener(new myListener());

		//setUndecorated(true);
		
      //moves = null;
      moves = new int[] {10, 12, -5, 2, -1};
      
		setVisible(true);	
	}

//PUBLIC METHODS-------------------------------------------------------------------

	public void refresh()
	{
		animCanvas.repaint();
	}


//PRIVATE METHODS--------------------------------------------------------------------
      
   private void updateTargets(){ // update valid move targets for a newly selected pawn
      if ((moves != null) && (selectedPawn != null)){ // only do moves if available and pawn is selected
         Space sourceSpace = selectedPawn.whereAmI();
         ArrayList<Space> targetSpaces = new ArrayList<Space>();
         for (int i = 0; i < moves.length; i++){
            // get all valid move targets
            if(moves[i] > 0){ // move forward
               targetSpaces.addAll(board.getTargetsForward(sourceSpace, moves[i]));
            }
            else if (moves[i] < 0){ // move back (deliberately exclude 0 move)
               targetSpaces.addAll(board.getTargetsBack(sourceSpace, -1 * moves[i])); // don't forget sign adjust
            }
         } // should have list of targets now
         
         if (targetSpaces.size() > 0)
            this.targetSpaces = targetSpaces;
         else
            this.targetSpaces = null;
      
      }
      else
         this.targetSpaces = null;
  }

   private void drawPawns(Graphics g){
      ArrayList<Token> tokens = board.getTokens();
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

//PRIVATE CLASSES-------------------------------------------------------------
	private class myCanvas extends JPanel{
	}// end of myCanvas

//LISTENERS----------------------------------------------------------------

    private class myListener implements MouseListener
    {
       public void mousePressed(MouseEvent e) {
           //System.out.println("Mouse pressed (# of clicks: "
                   //+ e.getClickCount() + ")");
           
       }
        
       public void mouseReleased(MouseEvent e) {
           //System.out.println("Mouse released (# of clicks: "
                  // + e.getClickCount() + ")");
       }
        
       public void mouseEntered(MouseEvent e) {
           //System.out.println("Mouse entered");
       }
        
       public void mouseExited(MouseEvent e) {
           //System.out.println("Mouse exited");
       }
        
       public void mouseClicked(MouseEvent e) {
           /*System.out.println("Mouse clicked (# of clicks: "
                   + e.getClickCount() + ")");
           System.out.println("Mouse relative x,y: "
                   + e.getX() + ", " + e.getY());
           System.out.println("Mouse absolute x,y: "
                   + e.getXOnScreen() + ", " + e.getYOnScreen());
           getGridCoords(e.getX(), e.getY());*/
           
           // Want this no matter what
           int clickedX = getGridX(e.getX());
           int clickedY = getGridY(e.getY());
           Space clickedSpace = board.getSpace(clickedX, clickedY);
           Token clickedToken;
           if ((clickedSpace != null) && (SwingUtilities.isLeftMouseButton(e))){ // LEFT CLICK SELECTS (if valid space)
              System.out.println("X = " + clickedX + ", Y = " + clickedY);
              // TWO UI STATES: Either pawn selected or not
              
              if (selectedPawn == null){ // if not selected could select a pawn
                  //System.out.println("PAWN *NOT* SELECTED -> SELECTS PAWN");
                  clickedToken = clickedSpace.getToken();
                  if (clickedToken != null){
                     selectedPawn = (Pawn)clickedToken;
                     //System.out.println("SELECTED - " + selectedPawn.toString());
                  }
              }
              else{ // if pawn selected could move it
                  //System.out.println("PAWN SELECTED");
                  
                  //MOVE PAWN
                  // only do if selected space is valid target
                  if (targetSpaces.contains(clickedSpace)){
                     selectedPawn.whereAmI().clearToken(); // remove pawn from start space
                     selectedPawn.moveTo(clickedSpace);// change pawn's reference to target space
                     clickedSpace.setToken(selectedPawn);// refer target space to pawn
                     
                     selectedPawn = null; // done with the pawn
                  }
                  
              }
           }
           else if (SwingUtilities.isRightMouseButton(e)){ // right click deselects
               selectedPawn = null;
               
               //System.out.println("RIGHT CLICK DESELECTS");           
           }
         updateTargets();
         repaint();
       }
    }
//GRID STUFF---------------------------------------------------------------

   private void getGridCoords(int x, int y){
      // Zero index grid of boxes divying up panel evenly
      // in NUMBER_COLUMNS & NUMBER_ROWS
      int panelX = animCanvas.getWidth();
      int panelY = animCanvas.getHeight();
      int boxWidth = panelX / NUMBER_COLUMNS;
      int boxHeight = panelY / NUMBER_ROWS;
      int columnNumber = (x / boxWidth);
      int rowNumber = (y / boxHeight);
      System.out.println("Bow Column, Row = " + columnNumber + ", " + rowNumber);
   }
   
   private int getGridX(int x){
      int panelX = animCanvas.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      int columnNumber = (x / boxWidth);
      return columnNumber;
   }
   
   private int getGridY(int y){
      int panelY = animCanvas.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      int rowNumber = (y / boxHeight);
      return rowNumber;
   }
   
   // grid coords -> pixel coords
   private int getLowPixelX(int x){
      int panelX = animCanvas.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      //int columnNumber = (x / boxWidth);
      return boxWidth * x;      
   }

   private int getHighPixelX(int x){
      int panelX = animCanvas.getWidth();
      int boxWidth = panelX / NUMBER_COLUMNS;
      //int columnNumber = (x / boxWidth);
      return boxWidth * (x + 1);      
   }
   
   private int getLowPixelY(int y){
      int panelY = animCanvas.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      //int rowNumber = (y / boxHeight);
      return boxHeight * y;
   }
   
   private int getHighPixelY(int y){
      int panelY = animCanvas.getHeight();
      int boxHeight = panelY / NUMBER_ROWS;
      //int rowNumber = (y / boxHeight);
      return boxHeight * (y + 1);
   }
   
//TEST---------------------------------------------------------------------
  
   
   public static void main(String [ ] args){
      SimpleBoard b = new SimpleBoard();
      Pawn p1 = new Pawn(b.getByIndex(5));
      b.getByIndex(5).setToken(p1);
      Pawn p2 = new Pawn(b.getByIndex(59));
      b.getByIndex(59).setToken(p2);
      CaucusRace x = new CaucusRace(b);
      
      

   } // end of main

}