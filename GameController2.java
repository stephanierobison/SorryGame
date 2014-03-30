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
      
      
      Pawn p1 = new Pawn(Color.red);
      Pawn p11 = new Pawn(Color.red);
      Pawn p22 = new Pawn(Color.blue);
      Pawn p33 = new Pawn(Color.green);
      Pawn p44 = new Pawn(Color.yellow);
      Pawn p2 = new Pawn(Color.blue);
      Pawn p3 = new Pawn(Color.green);
      Pawn p4 = new Pawn(Color.yellow);
      
      SimpleBoard b = new SimpleBoard();
      
      p1.moveTo(b.getSpace(0,0));
      p2.moveTo(b.getSpace(15,0));
      p3.moveTo(b.getSpace(0,15));
      p4.moveTo(b.getSpace(15,15));

      p11.moveTo(b.getSpace(0,1));
      p22.moveTo(b.getSpace(15,1));
      p33.moveTo(b.getSpace(0,14));
      p44.moveTo(b.getSpace(15,14));      
                        
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
                  //System.out.println("PAWN *NOT* SELECTED -> SELECTS PAWN");
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
                     //(targetSpaces.contains(clickedSpace)){
                     boardPanel.getSelectedPawn().moveTo(clickedSpace);
                     boardPanel.deselectPawn(); // done with the pawn
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
      GameController2 g = new GameController2();
      System.out.println(g.toString());
      System.out.println(g.getGame().getPawns());
      System.out.println(g.getGame().getMoveablePawns());
      g.getGame().nextTurn();
      System.out.println(g.getGame().getMoveablePawns());
      g.getGame().nextTurn();
      System.out.println(g.getGame().getMoveablePawns());
      g.getGame().nextTurn();
      System.out.println(g.getGame().getMoveablePawns());
      g.getGame().nextTurn();
      System.out.println(g.getGame().getMoveablePawns());
      g.getGame().nextTurn();
      System.out.println(g.getGame().getMoveablePawns());
      
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