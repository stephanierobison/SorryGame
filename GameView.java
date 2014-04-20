import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GameView extends JPanel{

   public static final Color BACKGROUND_COLOR = Color.pink;
   public static final int NUMBER_PLAYERS = 4;
   
   public static final int MESSAGE_WIDTH = 20;
   public static final int MESSAGE_HEIGHT = 10;
   
   public static final String OK_TEXT = "OK";
   public static final String SWAP_TEXT = "SWAP";
   public static final String BUMP_TEXT = "BUMP";

   private Game game;
   private BoardPanel boardPanel;
   
   private JPanel UIPanel;
      private JPanel playerPanel;
         private JLabel[] portraits; 
      
      private JPanel cardPanel;
         private JLabel cardLabel;
      
      private JPanel messagePanel;
         private JTextArea messageTextArea;
      
      private JPanel buttonPanel;
         private JButton okButton;
         private JButton swapButton;
         private JButton bumpButton;
   
   
   public GameView(Game g, BoardPanel bp, String[] players){
      super();
         game = g;
         boardPanel = bp;
      
         UIPanel = new JPanel();
         UIPanel.setBackground(Color.pink);
            
            playerPanel = new JPanel();
            playerPanel.setBackground(Color.green);
               ImageIcon image;
               portraits = new JLabel[NUMBER_PLAYERS];
               for (int i = 0; i < portraits.length; i++){
                  image = new ImageIcon(players[i]);
                  portraits[i] = new JLabel("", image, JLabel.CENTER);
                  playerPanel.add(portraits[i]);
               }
               portraits[0].setBorder(BorderFactory.createLineBorder(Color.black));
               
            cardPanel = new JPanel();
            cardPanel.setBackground(Color.red);
               image = new ImageIcon("card.jpg");
               cardLabel = new JLabel("", image, JLabel.CENTER);
               cardPanel.add(cardLabel, BorderLayout.CENTER );
            
            messagePanel = new JPanel();
            messagePanel.setBackground(Color.white);
               messageTextArea = new JTextArea("Hello World. Where is task force 47. The world wonders.",
                                                MESSAGE_HEIGHT, MESSAGE_WIDTH);
               messageTextArea.setEditable(false);
               messageTextArea.setLineWrap(true);
               messagePanel.add(messageTextArea);
            
            buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.blue);
               okButton = new JButton(OK_TEXT);
               swapButton = new JButton(SWAP_TEXT);
               bumpButton = new JButton(BUMP_TEXT);
            buttonPanel.add(okButton);
            buttonPanel.add(bumpButton);                    
            buttonPanel.add(swapButton);
         
         UIPanel.add(playerPanel);
         UIPanel.add(cardPanel);
         UIPanel.add(messagePanel);
         UIPanel.add(buttonPanel);
         UIPanel.setLayout(new BoxLayout(UIPanel, BoxLayout.PAGE_AXIS));
      
      System.out.println(boardPanel.getWidth());
      
      add(boardPanel);
      boardPanel.setSize(200,200);
      //add(UIPanel);      
      setBackground(Color.black);
      
   }
   

   //TEST---------------------------------------------------------------------
  
   
   public static void main(String [ ] args){
      GameController2 g = new GameController2();
     
      JFrame x = new JFrame();
      x.setTitle("GameView Test");  // name the window
		x.setSize(800, 600);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      //BoardPanel bp = new BoardPanel(g.getGame());
         
		x.add(new GameView(g.getGame(), g.getBoardPanel(),
                         new String[] {"player1.jpg","player2.jpg","player1.jpg","player2.jpg"}));
      //x.add(g.getBoardPanel());
		x.setVisible(true);	
      
      
   } // end of main



}