import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class LocalTest extends JFrame{
      
      ArrayList<Player> p;
      GameController2 g;
      SetupPanel s;
      
      public LocalTest(){
      super();
      
      p = new ArrayList<Player>();
      p.add(new Player(Color.red, "PLAYER 1", "player1.jpg", AIRules.HUMAN));
      p.add(new Player(Color.blue, "PLAYER 2", "player2.jpg", AIRules.HUMAN));
      
      s = new SetupPanel();
      s.getStartButton().addActionListener(new MetaListener());
     
      setTitle("Local Test");  // name the window
		setSize(1200, 600);  // set size
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      //BoardPanel bp = new BoardPanel(g.getGame());
      /*
      setLayout(new GridLayout(0,2));
		add(g.getBoardPanel());
      add(g.getGamePanel());*/
      
      add(s);
      
      //showSetup();
      //newGame();
      setBackground(new Color(0,255,255));
      setVisible(true);	
      
      
   } // end of main
   
   
   public void showSetup(){
      remove(g.getBoardPanel());
      remove(g.getGamePanel());
      setLayout(new BorderLayout());
      add(s, BorderLayout.CENTER);
      validate();
      repaint();
   }


   public void newGame(){
      g = new GameController2(p);
      g.getGamePanel().getWinButton().addActionListener(new MetaListener());
      remove(s);
      setLayout(new GridLayout(0,2));
		add(g.getBoardPanel());
      add(g.getGamePanel());
      validate();
   }
      
   private class MetaListener implements ActionListener{
   
      public void actionPerformed(ActionEvent e){
         String action = e.getActionCommand();
         
         if (action.equals(SetupPanel.BUTTON_TEXT)){
            p = s.generatePlayers();
            if (p.size() > 0){
               s.hideError();
               newGame();
            }
            else{
               s.showError();
            }
            
         }
         else if (action.equals(GamePanel.WIN_TEXT)){
            showSetup();
         
         }

      }
   
   
   }// end of class MetaListener


   private class ThinkingPanel extends JPanel{
      
      public ThinkingPanel(){
         super();
         setLayout(new BorderLayout());
         JLabel l = new JLabel("Setting Up Game...");
         l.setFont(new Font("Serif", Font.BOLD, 16));
         add(l, BorderLayout.CENTER);

      }
   
   }

   public static void main(String [ ] args){
      LocalTest x = new LocalTest();
   }
}