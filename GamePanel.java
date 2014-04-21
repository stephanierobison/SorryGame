import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel{

   public static final Color BACKGROUND_COLOR = new Color(0,255,255);
   public static final int NUMBER_PLAYERS = 4;
   public static final int BORDER_THICKNESS = 4;
   
   public static final int MESSAGE_WIDTH = 20;
   public static final int MESSAGE_HEIGHT = 10;
   
   public static final String OK_TEXT = "OK";
   public static final String SWAP_TEXT = "SWAP";
   public static final String BUMP_TEXT = "BUMP";
   public static final String WIN_TEXT = "HOORAY!";
   
   public static final String AMBIGUITY_TEXT = "You may either swap your selected pawn " +
                                               "with the opponent pawn or bump the enemy pawn.";

   private Game game;
   
   private JPanel UIPanel;
      private JPanel playerPanel;
         private JLabel[] portraits; 
      
      private JPanel cardPanel;
      
      private JPanel messagePanel;
         private JTextArea messageTextArea;
      
      private JPanel buttonPanel;
         private JButton okButton;
         private JButton swapButton;
         private JButton bumpButton;
         private JButton winButton;
   
   
   public GamePanel(Game g){
      super();
         game = g;
      
         UIPanel = new JPanel();
         UIPanel.setBackground(BACKGROUND_COLOR);
            
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
               
            cardPanel = new JPanel();
            cardPanel.setBackground(BACKGROUND_COLOR);
               image = new ImageIcon("card.jpg");
               JLabel cardLabel = new JLabel("", image, JLabel.CENTER);
               cardPanel.add(cardLabel, BorderLayout.CENTER );
            
            messagePanel = new JPanel();
            messagePanel.setBackground(BACKGROUND_COLOR);
               messageTextArea = new JTextArea("Hello World. Where is task force 47. The world wonders.",
                                                MESSAGE_HEIGHT, MESSAGE_WIDTH);
               messageTextArea.setEditable(false);
               messageTextArea.setLineWrap(true);
               messageTextArea.setWrapStyleWord(true);
               messagePanel.add(messageTextArea);
            
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
            //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
            buttonPanel.setLayout(new GridLayout(2,1));
                     
         UIPanel.add(playerPanel);
         UIPanel.add(cardPanel);
         UIPanel.add(messagePanel);
         UIPanel.add(buttonPanel);
         UIPanel.setLayout(new BoxLayout(UIPanel, BoxLayout.PAGE_AXIS));
      
      
      add(UIPanel);      
      setBackground(BACKGROUND_COLOR);
      setSize(600,600);
      update();
      
   }
   
   public JButton getOkButton(){
      return okButton;
   }
   

   public JButton getSwapButton(){
      return swapButton;
   }   

   public JButton getBumpButton(){
      return bumpButton;
   }

   public JButton getWinButton(){
      return winButton;
   }


   public void gameWon(){
      buttonPanel.removeAll();
      JLabel label = new JLabel("The game is over!");
      label.setFont(new Font("Serif", Font.BOLD, 16));
      buttonPanel.add(label);
      buttonPanel.add(winButton);
      setMessage("The game is over!");
   }

   public void ambiguityQuery(){
      buttonPanel.removeAll();
      buttonPanel.add(swapButton);
      buttonPanel.add(bumpButton);
      appendMessage("You have a choice between swapping or bumping the targeted pawn.");
   }
   
   public void okToProceed(String message){
      buttonPanel.removeAll();
      JLabel label = new JLabel(message);
      label.setFont(new Font("Serif", Font.BOLD, 16));
      buttonPanel.add(label);
      buttonPanel.add(okButton);
         //buttonPanel.add(winButton);
   }

   public void clearMessage(){
      messageTextArea.setText("");
   }
   
   public void setMessage(String s){
      messageTextArea.setText(s);
   }
   
   public void appendMessage(String s){
      String s1 = messageTextArea.getText();
      messageTextArea.setText(s1 + "\n" + s);
   }

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
                            BORDER_THICKNESS));
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
      
     
   }

   //TEST---------------------------------------------------------------------
  
   /*
   public static void main(String [ ] args){
      GameController2 g = new GameController2();
     
      JFrame x = new JFrame();
      x.setTitle("GamePanel Test");  // name the window
		x.setSize(800, 600);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      //BoardPanel bp = new BoardPanel(g.getGame());
         
		x.add(new GamePanel(g.getGame()));
      //x.add(g.getBoardPanel());
		x.setVisible(true);	
      
      
   } // end of main
*/


}