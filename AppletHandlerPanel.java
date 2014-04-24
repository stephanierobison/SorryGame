import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class AppletHandlerPanel extends JPanel{
      
         ArrayList<Player> p;
         GameController2 g;
         SetupPanel s;
         
         public AppletHandlerPanel(){
            super();
            
 /*           p = new ArrayList<Player>();
            p.add(new Player(Color.red, "PLAYER 1", "player1.jpg", AIRules.HUMAN));
            p.add(new Player(Color.blue, "PLAYER 2", "player2.jpg", AIRules.HUMAN));
   */         
            s = new SetupPanel();
            s.getStartButton().addActionListener(new MetaListener());
           
      	
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

   public static void main(String [ ] args){
      AppletHandlerPanel a = new AppletHandlerPanel();
      JFrame x = new JFrame();
      x.setTitle("Local Test");  // name the window
		x.setSize(1200, 600);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      x.setBackground(new Color(0,255,255));
      x.add(a);
      x.setVisible(true);	      
	
   }

}