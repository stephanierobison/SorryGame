import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.net.*;
import java.security.*;

/**
   The player panel is a GUI element used to prompt
   a user for enough information to create a Player
   object. To do this we need: a color, a portrait,
   a name, and the player type (AI or Human).
   Provision is also made to answer the meta question:
   "Is this player going to participate in the next game?"
   
   Note that the color is not mutated directly. Multiple
   instances of this object are used by the setup screen
   each with a different color.
*/

public class PlayerPanel extends JPanel {

//*************************************************************************
//  CONSTANTS
//*************************************************************************
   /** Hard coded list of portrait images. */
   public static final String[] PORTRAITS = {"AA.jpg",
                                             "AB.jpg",
                                             "AC.jpg",
                                             "AD.jpg",
                                             "AF.jpg",
                                             "AG.jpg",
                                             "AH.jpg"};
   /** Hard coded sample player names */
   public static final String[] NAMES = {"Namor", "Mer-man", "Aquaman", "Black Manta", "Mermaid Man",
                                         "Barnacle Boy", "Ariel", "Ursula", "Neptune", "Poseiden",
                                         "Charybdis", "Scylla", "Calypso", "Medea", "Kraken", 
                                         "Blackbeard", "Davy Jones", "Jolly Roger", "Jacques Cousteau"};
   
   /** Max player name size */
   public static final int NAME_MAX = 20;

//*************************************************************************
//  INSTANCE VARIABLES
//*************************************************************************   
   /** The unique color representing this player */
   private Color color;
   
   // GUI Elements
   /** GUI Element: "Is this player going to play? Y/N" */
   private JCheckBox isPlayingCheck;
   /** GUI Element: "What is this player's name?" */   
   private JTextField nameField;
   /** GUI Element: "What does this player look like?" */   
   private JLabel portraitDisplay;
      /** GUI Element: Mutates the player's look. */      
      private JButton leftPortraitButton;
      /** GUI Element: Mutates the player's look. */      
      private JButton rightPortraitButton;
      /** A list of all possible player looks */
      private String[] portraitList;
         /** Keeps track of where we are in the portrait list */
         private int portraitListCounter;
      /** The currently selected player look */
      private String portrait;
   
   /** GUI Element: "What kind of player are you? (Human/AI.1/AI.2/AI.3/...) */         
   private JComboBox playerTypeBox;

//*************************************************************************
//  CONSTRUCTOR
//*************************************************************************     
   /** 
      Procedurally builds the GUI element using the porvided color. 
      @param This player's unique color   
   */
   public PlayerPanel(Color c){
      super();
      color = c;
      
      JPanel tempPanel; // don't need to reference everthing
      
      // ARE YOU PLAYING?
      tempPanel = new JPanel();
      isPlayingCheck = new JCheckBox("In Game?");
      isPlayingCheck.setSelected(true);
      tempPanel.add(isPlayingCheck);
      tempPanel.setBackground(color);
      add(tempPanel);  
      
      
      // WHAT DO YOU LOOK LIKE?
      tempPanel = new JPanel();
      leftPortraitButton = new JButton("<");
      leftPortraitButton.addActionListener(new ButtonListener());
      rightPortraitButton = new JButton(">");
      rightPortraitButton.addActionListener(new ButtonListener());
            
      // WHAT COULD YOU LOOK LIKE?
      portraitList = PORTRAITS;
      portraitDisplay = new JLabel("", JLabel.CENTER);
      Random r = new Random();   // More interesting with random starting players
      portraitListCounter = r.nextInt(portraitList.length);
      updatePortrait();   
      // Extensive boilerplate to get the layout to look nice (basically nesting JPanels for spacing)
      tempPanel.setLayout(new BorderLayout());
         JPanel miniPanel = new JPanel();
         miniPanel.setBackground(color);
         miniPanel.setLayout(new GridLayout(0,2));
         miniPanel.add(leftPortraitButton);
         miniPanel.add(rightPortraitButton);
         tempPanel.add(miniPanel, BorderLayout.SOUTH);
      tempPanel.add(portraitDisplay, BorderLayout.CENTER);
      tempPanel.setBackground(color);
      add(tempPanel);  
      
      // WHAT IS YOUR NAME?
      tempPanel = new JPanel();
      nameField = new JTextField(randomName(), NAME_MAX); // Random names are silly & silly = fun
      tempPanel.add(nameField);
      tempPanel.setBackground(color);
      add(tempPanel);      
      
      // ARE YOU A HUMAN OR A COMPUTER?
      tempPanel = new JPanel();
      playerTypeBox = new JComboBox(AIRules.DESCRIPTIONS);
      tempPanel.add(playerTypeBox);
      tempPanel.setBackground(color);
      add(tempPanel);
      
      // every player has a unique color
      setBackground(color);
   
   }// end PlayerPanel
//*************************************************************************
//  METHODS
//*************************************************************************     
   /**
      Changes the displayed IconImage based on where the portrait list
      counter variable is. Only needs to be called if counter has been mutated.
   */
   private void updatePortrait(){
      ImageIcon image = new ImageIcon(portraitList[portraitListCounter]);
      if (image != null) 
         portraitDisplay.setIcon(image);
   }// end updatePortrait
   
//-------------------------------------------------------------------------
   /**
      Polls the GUI elements to create a player, IF valid.
      @return Player or null if IS PLAYING not checked
   */
   public Player generatePlayer(){
      Player p = null;
      if (isPlayingCheck.isSelected()) // If box checked...
         p = new Player(color,         // ... poll GUI to satisfy constructor
                        nameField.getText(), 
                            portraitList[portraitListCounter], 
                            playerTypeBox.getSelectedIndex());
      return p;
   }// end of generatePlayer
//-------------------------------------------------------------------------
   /**
      Grabs a random name from the contant list.
      @return <String> random name
   */
   public static String randomName(){
      Random r = new Random();
      int i = r.nextInt(NAMES.length);
      return NAMES[i];
   }
//*************************************************************************
//  LISTENERS
//************************************************************************* 
//-------------------------------------------------------------------------
   /**
      Powers the buttons which mutate the current portrait.
      This is achieved by mutating the int list counter
      and then updating the displayed ImageIcon based on
      the new counter position.
   */
   private class ButtonListener implements ActionListener{
   
      public void actionPerformed(ActionEvent e){
         String action = e.getActionCommand();
         
         // "Left Arrow" decrements, with loop around if out of bounds
         if (action.equals("<")){
            portraitListCounter--;
            if (portraitListCounter < 0)
               portraitListCounter = portraitList.length - 1;
         }
         // "Right Arrow" increments, with loop around if out of bounds         
         else if (action.equals(">")){
            portraitListCounter++;
            if (portraitListCounter >= portraitList.length)
               portraitListCounter = 0;
         
         }
         updatePortrait(); // always update and redraw
         revalidate();
      }
   }// end of class ButtonListener
//-------------------------------------------------------------------------
//*************************************************************************
//  LISTENERS
//*************************************************************************  
   /**
      The test function simply creates an instance and displays it on an
      arbitrary JFrame.
      Mostly used to test button functionality.
   */
   public static void main(String [ ] args){
      PlayerPanel pp = new PlayerPanel(Color.red);
      JFrame x = new JFrame();
      x.setTitle("Test");  // name the window
		x.setSize(800, 600);  // set size
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		x.add(pp);
		x.setVisible(true);	
   } // end of main
}// end of PlayerPanel