import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
   This class displays the model's current turn meta data. This includes:
      - The player's portraits with the current player highlighted
      - An image of the drawn card
      - A message telling the user about the current game status
      - (context dependent) buttons to interact with
*/
public class GamePanel extends JPanel{
//*************************************************************************
// CONSTANTS
//************************************************************************* 
   /** a soothing Aqua panel background */
   public static final Color BACKGROUND_COLOR = new Color(0,255,255);
   /** maximum number of players */
   public static final int NUMBER_PLAYERS = 4;
   /** portrait highlighting border thickness */
   public static final int BORDER_THICKNESS = 4;
   
   /** Message text display box width */
   public static final int MESSAGE_WIDTH = 20;
   /** Message text display box height */
   public static final int MESSAGE_HEIGHT = 10;
   
   /** Button message. Used in controller Listeners */
   public static final String OK_TEXT = "OK";
   /** Button message. Used in controller Listeners */
   public static final String SWAP_TEXT = "SWAP";
   /** Button message. Used in controller Listeners */
   public static final String BUMP_TEXT = "BUMP";
   /** Button message. Used in controller Listeners */
   public static final String WIN_TEXT = "HOORAY!";
   
   /** Prompt for user to resolve a move ambiguity*/
   public static final String AMBIGUITY_TEXT = "You may either swap your selected pawn " +
                                               "with the opponent pawn or bump the enemy pawn.";
   
   /** a convenience reference to the game being modelled. NOT to be used to mutate. ONLY for READING */
   private Game game;
   
//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************    
   /** top level container*/
   private JPanel UIPanel;
      /** shows the playter portraits */
      private JPanel playerPanel;
         private JLabel[] portraits; 
      
      /** Holds the card image */
      private JPanel cardPanel;
      
      /** Panel for messages and prompts */
      private JPanel messagePanel;
         private JTextArea messageTextArea;
      
      /** Panel to organize buttons */
      private JPanel buttonPanel;
         private JButton okButton;
         private JButton swapButton;
         private JButton bumpButton;
         private JButton winButton;
   
//*************************************************************************
// CONSTRUCTOR
//*************************************************************************   
      public GamePanel(Game g){
         super();
         game = g; // store modelled game
      
         UIPanel = new JPanel();// top level container
         UIPanel.setBackground(BACKGROUND_COLOR);
            
            // display all players
            playerPanel = new JPanel();
            playerPanel.setBackground(BACKGROUND_COLOR);
               ImageIcon image;
               portraits = new JLabel[game.getPlayers().size()];
               for (int i = 0; i < game.getPlayers().size(); i++){
                  image = new ImageIcon(game.getPlayers().get(i).getPortrait());
                  portraits[i] = new JLabel("", image, JLabel.CENTER);
                  playerPanel.add(portraits[i]);
               }
               portraits[0].setBorder(BorderFactory.createLineBorder(Color.black));
               
            // first display default card image
            cardPanel = new JPanel();
            cardPanel.setBackground(BACKGROUND_COLOR);
               image = new ImageIcon("card.jpg");
               JLabel cardLabel = new JLabel("", image, JLabel.CENTER);
               cardPanel.add(cardLabel, BorderLayout.CENTER );
            
           // first display default message
            messagePanel = new JPanel();
            messagePanel.setBackground(BACKGROUND_COLOR);
               messageTextArea = new JTextArea("Hello World. Where is task force 47. The world wonders.",
                                                MESSAGE_HEIGHT, MESSAGE_WIDTH);
               messageTextArea.setEditable(false);
               messageTextArea.setLineWrap(true);
               messageTextArea.setWrapStyleWord(true);
               messagePanel.add(messageTextArea);  //  Cannot edit the tules
            
           
            // Sets up button panel and add buttons
            buttonPanel = new JPanel();
            buttonPanel.setBackground(BACKGROUND_COLOR);
               okButton = new JButton(OK_TEXT);
               swapButton = new JButton(SWAP_TEXT);
               bumpButton = new JButton(BUMP_TEXT);
               winButton = new JButton(WIN_TEXT);
            buttonPanel.add(okButton);
            buttonPanel.add(bumpButton);                    
            buttonPanel.add(swapButton);
            buttonPanel.add(winButton);
            buttonPanel.setLayout(new GridLayout(2,1));
                     
         // add all generated elements to top level container
         UIPanel.add(playerPanel);
         UIPanel.add(cardPanel);
         UIPanel.add(messagePanel);
         UIPanel.add(buttonPanel);
         UIPanel.setLayout(new BoxLayout(UIPanel, BoxLayout.PAGE_AXIS));
      
      //JPanel boilerplate
      add(UIPanel);      
      setBackground(BACKGROUND_COLOR);
      setSize(600,600);
      update();
      
   }
//*************************************************************************
// BASIC GETTERS
//*************************************************************************   
   public JButton getOkButton(){
      return okButton;
   }
//-------------------------------------------------------------------------
   public JButton getSwapButton(){
      return swapButton;
   }   
//-------------------------------------------------------------------------
   public JButton getBumpButton(){
      return bumpButton;
   }
//-------------------------------------------------------------------------
   public JButton getWinButton(){
      return winButton;
   }
//*************************************************************************
// METHODS
//*************************************************************************
   /**
      Checks for a victor by seeing if any player's pawns are
      all in their home position
      
      @return true if game is won, false otheriwse
   */
   public void gameWon(){
      buttonPanel.removeAll();
      JLabel label = new JLabel("The game is over!");
      label.setFont(new Font("Serif", Font.BOLD, 16));
      buttonPanel.add(label);
      buttonPanel.add(winButton);
      setMessage("The game is over!");
   }
//-------------------------------------------------------------------------
   /**
      Replaces all buttons with the SWAP & BUMP buttons.
   */   
   public void ambiguityQuery(){
      buttonPanel.removeAll();
      buttonPanel.add(swapButton);
      buttonPanel.add(bumpButton);
      appendMessage("You have a choice between swapping or bumping the targeted pawn.");
   }
//-------------------------------------------------------------------------
   /**
      Replaces all buttons with the OK button.
   */   
   public void okToProceed(String message){
      buttonPanel.removeAll();
      JLabel label = new JLabel(message);
      label.setFont(new Font("Serif", Font.BOLD, 16));
      buttonPanel.add(label);
      buttonPanel.add(okButton);
   }
//-------------------------------------------------------------------------
   /**
      Changes the message in the 
      message JtextField to the 
      provided String
      
      @param new message String
   */   
   public void setMessage(String s){
      messageTextArea.setText(s);
   }
//-------------------------------------------------------------------------
   /**
      As setMessage except for s = ""
   */
   public void clearMessage(){
      messageTextArea.setText("");
   }
//-------------------------------------------------------------------------
   /**
      Appends a String to the extant message in the 
      message JtextField
      
      @param String to add to game message
   */
   public void appendMessage(String s){
      String s1 = messageTextArea.getText();
      messageTextArea.setText(s1 + "\n" + s);
   }
//-------------------------------------------------------------------------
   /**
      Refreshes all GUI relements based on the MDOEL's internal state.
      Meant only to be called on if the model has been mutated.
   */
   public void update(){
   
      // Show Players and
      // Highlight current player
      ImageIcon image;
      JLabel label;
      playerPanel.removeAll();
      ArrayList<Player> players = game.getPlayers();
      for (int i = 0; i < players.size(); i++){
         image = new ImageIcon(players.get(i).getPortrait());
         label = new JLabel("", image, JLabel.CENTER);
         
         if (game.isCurrentPlayer(players.get(i))){
            label.setBorder(BorderFactory.createLineBorder(players.get(i).getColor(), 
                            BORDER_THICKNESS)); // highlight
         }
         playerPanel.add(label);
      }
      
      // show correct card
      int card = game.getCurrentCard();
      String cardFileName = "card" + Integer.toString(card) + ".jpg";
      image = new ImageIcon(cardFileName);
      cardPanel.removeAll();
      JLabel cardLabel = new JLabel("", image, JLabel.CENTER);
      cardPanel.add(cardLabel, BorderLayout.CENTER );
      
      // show current message prompt
      clearMessage();
      appendMessage(game.getCurrentPlayer().getName() + "'s turn");
      appendMessage("They drew a " + Deck.CARD_TEXT[card]);
      
      // clear buttons (buttons placed by GameController)
      buttonPanel.removeAll();
   }// end of update

}// end GamePanel