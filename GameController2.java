import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GameController2{
   private Game game;
   private BoardPanel boardPanel;
   
   // Default 4 players, simple rules and board.
   public GameController2(){
      // Make the Players
      ArrayList<Player> p = new ArrayList<Player>();
      p.add(new Player(Color.red));
      p.add(new Player(Color.blue));
      p.add(new Player(Color.green));
      p.add(new Player(Color.yellow));
      
      
      Pawn p11 = new Pawn(Color.red);
      Pawn p12 = new Pawn(Color.red);
      Pawn p13 = new Pawn(Color.red);
      Pawn p14 = new Pawn(Color.red);
      
      Pawn p21 = new Pawn(Color.blue);
      Pawn p22 = new Pawn(Color.blue);
      Pawn p23 = new Pawn(Color.blue);
      Pawn p24 = new Pawn(Color.blue);
      
      SimpleBoard b = new SimpleBoard();
      
      p11.moveTo(b.getSpace("60"));
      p12.moveTo(b.getSpace("61"));
      p13.moveTo(b.getSpace("62"));
      p14.moveTo(b.getSpace("63"));

      p21.moveTo(b.getSpace("73"));
      p22.moveTo(b.getSpace("74"));
      p23.moveTo(b.getSpace("75"));
      p24.moveTo(b.getSpace("76"));     
                        
      game = new Game(b , new SimpleRules(), p);
      boardPanel = new BoardPanel(game);
      boardPanel.addMouseListener(new BoardListener());
   }
   
   public Game getGame(){
      return game;
   }
   
   public BoardPanel getBoardPanel(){
      return boardPanel;
   }
   
   public String toString(){
      return game.getBoard().toString();
   }
      //LISTENERS----------------------------------------------------------------

    private class BoardListener implements MouseListener{
       public void mousePressed(MouseEvent e) {
          // System.out.println("Mouse pressed (# of clicks: "
                 //  + e.getClickCount() + ")");
           
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
           //System.out.println("X = " + e.getX() + ", Y = " + e.getY());
           // Want this no matter what
           int clickedX = boardPanel.getGridX(e.getX());
           int clickedY = boardPanel.getGridY(e.getY());
           Space clickedSpace = game.getBoard().getSpace(clickedX, clickedY);
           Pawn clickedPawn;
           if ((clickedSpace != null) && (SwingUtilities.isLeftMouseButton(e))){ // LEFT CLICK SELECTS (if valid space)
              //System.out.println("X = " + clickedX + ", Y = " + clickedY);
              // TWO UI STATES: Either pawn selected or not
              
              if (!boardPanel.isPawnSelected()){ // if not selected could select a pawn
                  System.out.println("Selected the Pawn at x = " + clickedX + ", y = " + clickedY);
                  clickedPawn = (Pawn)clickedSpace.getToken();
                  if ((clickedPawn != null) && (game.getAllTargets(clickedPawn).size() > 0)){
                     boardPanel.selectPawn(clickedPawn);
                     //System.out.println("SELECTED - " + selectedPawn.toString());
                  }
              }
              else{ // if pawn selected could move it
                  //System.out.println("PAWN SELECTED");
                  
                  //MOVE PAWN
                  // only do if selected space is valid target
                  if (game.getAllTargets(boardPanel.getSelectedPawn()).contains(clickedSpace)){
                     System.out.println("Moving selected pawn to space at x = " + clickedSpace.getX() 
                                                                     + ", y = " + clickedSpace.getY());
                     //(targetSpaces.contains(clickedSpace)){
                     boardPanel.getSelectedPawn().moveTo(clickedSpace);
                     boardPanel.deselectPawn(); // done with the pawn
                     // NEEDS TO BE DIFFERENT TO ACCOUNT FOR THE 7
                     game.setMoves(null);
                     game.nextTurn();
                  }
                  
              }
           }// END IF LEFT CLICK
           else if (SwingUtilities.isRightMouseButton(e)){ // right click deselects
               //System.out.println("RIGHT CLICK");
               boardPanel.deselectPawn();
               
               //System.out.println("RIGHT CLICK DESELECTS");           
           }// END IF RIGHT CLICK
           boardPanel.revalidate();
       }
    }// end of class boardListener
   //TEST---------------------------------------------------------------------
  
   
   public static void main(String [ ] args){
      Scanner scanner = new Scanner(System.in);
      
      GameController2 g = new GameController2();
     
      JFrame x = new JFrame();
      x.setTitle("");  // name the window
		x.setSize(550, 550);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      //BoardPanel bp = new BoardPanel(g.getGame());
      
		x.add(g.getBoardPanel());
		x.setVisible(true);	
      
      
   } // end of main


}