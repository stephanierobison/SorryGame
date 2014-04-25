import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
   The setup panel is used to permit a human to
   indirectly create the ArrayList<Player> that
   the GameController2 object needs to run.
   
   It it primarily comprised of 4 player panels,
   one for each of the 4 colors/players on a Sorry 
   board. A button is provided for the calling
   top level SorryGame listener to attach to and
   some user prompts are displayed.
*/
public class SetupPanel extends JPanel {
//*************************************************************************
//  CONSTANTS
//*************************************************************************   
   /** The four colors on a Sorry board: Red, Blue, Yellow, and Green */
   public static final Color[] COLORS = {Color.red, Color.blue, Color.yellow, Color.green};
   /** Start button text message */
   public static final String BUTTON_TEXT = "START";
   
//*************************************************************************
//  INSTANCE VARIABLES
//*************************************************************************     
   // GUI Elements
   /** Array of the four player panels used to create up to 4 players */
   private ArrayList<PlayerPanel> p;
   /** Button accessed by calling function to close this panel and start game */
   private JButton startButton;
   /** high level access to conatiner with mutable HUI elements */
   private JPanel UIPanel;
   /** Reference to error message - used to display or hide message */
   private JLabel errorLabel;
//*************************************************************************
//  CONSTRUCTORS
//*************************************************************************  
   public SetupPanel(){
      // JPanel boilerplate
      super();
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      JLabel title = new JLabel("GAME SETUP");
      title.setFont(new Font("Serif", Font.BOLD, 22));
      
      // Create 4 player panels, one for each color
      p = new ArrayList<PlayerPanel>();
      for (int i = 0; i < COLORS.length; i++){
         p.add(new PlayerPanel(COLORS[i]));
         add(p.get(i));
      }
      // More JPanel boilerplate with the JLabels for the user
      UIPanel = new JPanel();
      UIPanel.setBackground(new Color(0, 255, 255));
      // Always displayed
      JLabel label = new JLabel("Please configure Players and Press Start to Begin.");
      label.setFont(new Font("Serif", Font.BOLD, 16));
      UIPanel.add(label);
      startButton = new JButton(BUTTON_TEXT);
      UIPanel.add(startButton);
      add(UIPanel);
      // Only displayed if you try to start a game with no players
      // This error checking is handled by the SorryGame object
      errorLabel = new JLabel("YOU MUST HAVE AT LEAST ONE PLAYER");
      errorLabel.setFont(new Font("Serif", Font.BOLD, 16));
      errorLabel.setForeground(Color.red);
      errorLabel.setVisible(false);
      UIPanel.add(errorLabel);

   }// end of SteupPanel
//*************************************************************************
//  METHODS
//*************************************************************************    
   /**
      Generates an array of players. This is achieved by
      simply collating the non null generatePlayer() results
      from each PlayerPanel. These will correspond to players
      whose "IN GAME" boxes have been checked.
      
      @return List of all selected Players, empty list if
              none selected
   */
   public ArrayList<Player> generatePlayers(){
      ArrayList<Player> result = new ArrayList<Player> ();
      Player p2;
      for (int i = 0; i < p.size(); i++){ // step through playerpanels
         p2 = p.get(i).generatePlayer();  
         if (p2 != null)                  // record non null results
            result.add(p2);
      }
      return result;
   }// end of generatePlayers
//-------------------------------------------------------------------------
   /** 
      @return This object's start button.
      Intended to permit top level object to attach a
      listener to it.
   */         
   public JButton getStartButton(){
      return startButton;
   }
//-------------------------------------------------------------------------
   /** Reveals the error message label. */
   public void showError(){
      errorLabel.setVisible(true);
   }
//-------------------------------------------------------------------------
   /** Hides the error message label. */
   public void hideError(){
      errorLabel.setVisible(false);
   }
//-------------------------------------------------------------------------   
//*************************************************************************
//  TEST
//************************************************************************* 
   /**
      Instantiates this Panel in an arbitrary JFrame for testing.
      Note that in this test mode the button and context sensitive
      error message are non-functional.
   */
   public static void main(String args[]) {
      SetupPanel x = new SetupPanel();
      JFrame go = new JFrame();
      go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      go.setSize(1200, 600);
      go.add(x);
      go.setVisible(true);
      x.showError();
   }// end of main
}// end of SetupPanel