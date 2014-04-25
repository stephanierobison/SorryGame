import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
   This class is the topmost object used to play the
   Sorry game. It essentially switches between a
   setup screen and the main game using listeners
   attached to provided buttons in the respective
   GUIs.
   
   Internally it can be thought of as generally having
   two internal states: setup & play. These states
   correspond directly to the screens being displayed.
   
   This functionality is encapsulated in a JFrame
   which merely needs to be instantiated in order 
   to start the entire event driven Sorry game.
   
   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   ! TO PLAY THE GAME RUN THIS CLASS' MAIN FUNCTION !
   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/

public class SorryGame extends JFrame{

//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************      
      /** The game controller class both creates and is the C in the
          MVC for the core game algorithm. */
      private GameController2 g;
      /** The setup panel is a simple form which provides an interface
          for selecting and configuring up to 4 human or computer players.
          This must be done in order to provide the GameController2 object
          with the ArrayList<Player> its constructor requires.
      */
      private SetupPanel s;
      
      /** A temporary placeholder used to store the results of running
          the game setup.
      */
      private ArrayList<Player> p;

//*************************************************************************
// CONSTRUCTORS
//*************************************************************************      
   /** Creates the setup screen and displays it thus forcing
       this object to start in the "Setup Screen" state.
       The GameController (and its listener) will only be created 
       AFTER the setup screen has retrieved valid setup parameters.
   */
   public SorryGame(){
      super();
      // create and show the setup screen
      s = new SetupPanel();
      s.getStartButton().addActionListener(new MetaListener());
	   add(s);     

      // Standard JFrame Boilerplate
      setTitle("Sorry Game");  // name the window
		setSize(1200, 600);  // set size
   	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBackground(new Color(0,255,255)); // Should never see but just in case
      setVisible(true);	
 
   } // end SorryGame()
//*************************************************************************
// METHODS
//*************************************************************************   
//-------------------------------------------------------------------------
   /**
      Switches from the "playing the game" state
      to the "setting up a game" state. Note that the 
      setup screen is persistant (this preserves the
      user's choices) while the game object is purged
      and created anew each iteration.
   */
   public void showSetup(){
      // clean up the old game
      remove(g.getBoardPanel());
      remove(g.getGamePanel());
      g = null; // should trigger garbage collection
      // show the setup screen
      setLayout(new BorderLayout());
      add(s, BorderLayout.CENTER);
      validate();
      repaint();  // Should be redundant, but I'm superstitious...
   }// end of showSetup()
//-------------------------------------------------------------------------
   /**
      Switches from the "setting up a game" state
      to the "playing the game" state. Note that the 
      setup screen is persistant (this preserves the
      user's choices) while the game object is purged
      and created anew each iteration.
   */
   public void newGame(){
      // clean up but do not delete setup
      remove(s);
      // create and display a new game      
      g = new GameController2(p);
      g.getGamePanel().getWinButton().addActionListener(new MetaListener());
      setLayout(new GridLayout(0,2));
		add(g.getBoardPanel());
      add(g.getGamePanel());
      validate();
   }// end of newGame()
//-------------------------------------------------------------------------   
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************
//-------------------------------------------------------------------------
   /**
      Provides button listeners for the two buttons in the
      setup panel and game panel which toggle between the
      setup and play states: the Start Game button and the
      Someone won button respectively.
   */
   private class MetaListener implements ActionListener{
   
      public void actionPerformed(ActionEvent e){
         String action = e.getActionCommand();
         
         // Done setting up, START GAME
         if (action.equals(SetupPanel.BUTTON_TEXT)){
            p = s.generatePlayers();  // save results of setting up
            if (p.size() > 0){        // only transition if results are useful
               s.hideError();             
               newGame();
            }
            else{
               s.showError();    // shows error message if results not valid
            }
            
         }
         // Done playing, SHOW SETUP
         else if (action.equals(GamePanel.WIN_TEXT)){
            showSetup();
         
         }

      }

   }// end of class MetaListener
//-------------------------------------------------------------------------
//*************************************************************************
//  MAIN
//*************************************************************************
   /**
      Simply creates an instance of this object, setting
      off the chain reaction of object creatin which will
      instantiate the Sorry Game.
   */
   public static void main(String [ ] args){
      SorryGame x = new SorryGame();
   }
}// end of SorryGame