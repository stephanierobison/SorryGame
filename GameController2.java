import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GameController2{
   private Game game;
   private BoardPanel boardPanel;
   private GamePanel gamePanel;
   
   private boolean aiThinking;
   
   // Default 4 players, simple rules and board.
   public GameController2(){
      // Make the Players
      ArrayList<Player> p = new ArrayList<Player>();
      p.add(new Player(Color.red, "PLAYER 1", "player1.jpg", true));
      p.add(new Player(Color.blue, "PLAYER 2", "player2.jpg", true));
      //p.add(new Player(Color.yellow));
      //p.add(new Player(Color.green));      
      
      SimpleBoard b = new SimpleBoard();
    
                        
      game = new Game(b , new SimpleRules(), p);
      boardPanel = new BoardPanel(game);
      boardPanel.addMouseListener(new BoardListener());
      
      
      
      gamePanel = new GamePanel(game);
      gamePanel.getOkButton().addActionListener(new ButtonListener());
      gamePanel.getBumpButton().addActionListener(new ButtonListener());
      gamePanel.getSwapButton().addActionListener(new ButtonListener());
      
      
      startTurn();      

      
   }
   
   private void startTurn(){
      
      gamePanel.update();
      
      // set up first move
      if (!game.isCurrentPlayerHuman()){
         aiThinking = true;
         aiTurn();   // AI Turns are driven by methods
      }
      else{
         aiThinking = false; // Human player turns are driven by events...
         if (game.getMoveablePawns().size() == 0){ // ...unless they can't do anything
            gamePanel.appendMessage("You cannot move and must forfeit your turn.");
            gamePanel.okToProceed();
         }
      }
   }
   
   
   private void aiTurn(){
   
   
   }
   
   private void aiMove(){
      
      aiThinking = false;
   }
   
   
   public Game getGame(){
      return game;
   }
   
   public BoardPanel getBoardPanel(){
      return boardPanel;
   }
 
   public GamePanel getGamePanel(){
      return gamePanel;
   } 
   
   public String toString(){
      return game.getBoard().toString();
   }


   

      //LISTENERS----------------------------------------------------------------

    private class BoardListener implements MouseListener{
       // We're not interested in most mouse events but we need to satisfy the interface
       public void mouseClicked(MouseEvent e) {
          //System.out.println("CLICK");
          //System.out.println("Mouse pressed (# of clicks: "
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
        
       public void mousePressed(MouseEvent e) {
            //System.out.println("CLICK");
           // Get grid coords of mouse click and clicked on space if applicable
           int clickedX = boardPanel.getGridX(e.getX());
           int clickedY = boardPanel.getGridY(e.getY());
           Space clickedSpace = game.getBoard().getSpace(clickedX, clickedY);
           Pawn clickedPawn;
           if ((clickedSpace != null) && 
               !boardPanel.isSpaceSelected() && // left mouse ignored with waiting for a button press
               (SwingUtilities.isLeftMouseButton(e))){ // LEFT CLICK SELECTS (if valid space)

              // TWO UI STATES: Either pawn selected or not
              // if no pawn is selected we could select a pawn
              if (!boardPanel.isPawnSelected()){ 
                  clickedPawn = clickedSpace.getPawn();
                  // Can only select a pawn if it's that player's turn and if it can be moved
                  if ((clickedPawn != null) && 
                      (clickedPawn.getColor().toString().equals(game.getCurrentPlayer().getColor().toString())) &&
                      (game.getAllTargets(clickedPawn).size() > 0)){
                     boardPanel.selectPawn(clickedPawn);  // Actual pawn selection op
                  }
              }
              // else if pawn selected we might be able to move it
              else{ 
                  // Find all moves which will take the selected pawn to the clicked space
                  ArrayList<Move> germaneMoves = 
                                       Move.subsetTargeting(game.getAllTargets(boardPanel.getSelectedPawn()),
                                                            clickedSpace);
                  
                  // Only proceed if we CAN move to the selected space
                  if (germaneMoves.size() > 0){
                     // If there are no swap/bump ambiguities we can simply pick any
                     // move from the subset - defaults to first one
                     // It is important to note that a seven move may NEVER result in
                     // this type of ambiguity - a 7 always bumps
                     if (!game.getRules().containsAmbiguousMove(germaneMoves, clickedSpace)){
                           game.getRules().move(boardPanel.getSelectedPawn(),
                                                clickedSpace,
                                                germaneMoves.get(0).getMove()); // actual move op
                         boardPanel.deselectPawn(); // done with pawn
                         
                         if (germaneMoves.get(0).getChain() != null){ // A chained multimove doesn't end the player's turn
                           game.setMoves(new int[] {germaneMoves.get(0).getChain().intValue()});
                         }
                         else{
                           game.nextTurn();  // but most moves simply end the player's turn
                           startTurn();
                         }
                         
                   
   
                     }
                     // If there is an ambiguous move then we need to put boardpanel
                     // into it's special bump or swap query state to handle 
                     // (uses button listeners)
                     else{
                        boardPanel.selectSpace(clickedSpace);
                        gamePanel.ambiguityQuery();
                     }
                  
                  
                  }// end of CAN move to the selected space
                  
                 
                  
              }// end if pawn selected we might be able to move it

           }// END IF LEFT CLICK
           
           // IF RIGHT CLICK
           else if (SwingUtilities.isRightMouseButton(e)){ 
               // right click always deselects one "level" of stuff in boardpanel
               if (boardPanel.isSpaceSelected()){
                     boardPanel.deselectSpace();
                     gamePanel.update();
                  }
               else
                  boardPanel.deselectPawn(); 
           }// END IF RIGHT CLICK
           
           // Always redraw the GUI after a mouse event - something may have changed
           boardPanel.revalidate();
       }
   
    }// end of class boardListener
   
   
   
   private class ButtonListener implements ActionListener{
   
      public void actionPerformed(ActionEvent e){
         String action = e.getActionCommand();
         //System.out.println(action);
         
         if (action.equals(GamePanel.OK_TEXT)){
            if (aiThinking){
            
            }
            else{
               game.nextTurn();
               startTurn();
            }
         
         }
         else if (action.equals(GamePanel.SWAP_TEXT)){
            if (!boardPanel.isSpaceSelected()){
               System.out.println("ERROR! Space not selected.");
            }
            else{
               game.getRules().move(boardPanel.getSelectedPawn(),
                                    boardPanel.getSelectedSpace(),
                                    Ruleset.ELEVEN_SWAP); // use swap move
               boardPanel.deselectPawn(); // done with pawn...
               boardPanel.deselectSpace(); // ...and Space
               game.nextTurn();  // but most moves simply end the player's turn
               startTurn();
            } 
         }
         else if (action.equals(GamePanel.BUMP_TEXT)){
            if (!boardPanel.isSpaceSelected()){
               System.out.println("ERROR! Space not selected.");
            }
            else{
               game.getRules().move(boardPanel.getSelectedPawn(),
                                    boardPanel.getSelectedSpace(),
                                    11); // use regular move
               boardPanel.deselectPawn(); // done with pawn...
               boardPanel.deselectSpace(); // ...and Space
               game.nextTurn();  // but most moves simply end the player's turn
               startTurn();            
            }
         }

         
      }
   
   
   }// end of class ButtonListener
   
   //TEST---------------------------------------------------------------------
  
   
   public static void main(String [ ] args){
      Scanner scanner = new Scanner(System.in);
      
      GameController2 g = new GameController2();
     
      JFrame x = new JFrame();
      x.setTitle("");  // name the window
		x.setSize(600, 600);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      //BoardPanel bp = new BoardPanel(g.getGame());
      
		x.add(g.getBoardPanel());
		x.setVisible(true);	
      
      
   } // end of main


}