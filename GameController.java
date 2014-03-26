import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameController{

   private Game game;
   private BoardPanel boardPanel; 
 
   // NEED TO CHANGE TO ACCEPT INPUT FROM GAME SETUP
   public GameController(){
      ArrayList<Player> p = new ArrayList<Player>();
      p.add(new Player(Color.red));
      p.add(new Player(Color.blue));      
      game = new Game(new SimpleBoard(), 
                      new SimpleRules(), p);
   
      boardPanel = new BoardPanel(game);
   }
   
   //LISTENERS----------------------------------------------------------------

    private class boardListener implements MouseListener{
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
           int clickedX = boardPanel.getGridX(e.getX());
           int clickedY = boardPanel.getGridY(e.getY());
           Space clickedSpace = game.getBoard().getSpace(clickedX, clickedY);
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
       }
    }// end of class boardListener
 
   // TEST
   public static void main(String [ ] args){
   
   
   }


}