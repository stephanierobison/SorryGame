import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class SetupPanel extends JPanel {
   
   public static final Color[] COLORS = {Color.red, Color.blue, Color.yellow, Color.green};
   public static final String BUTTON_TEXT = "START";
   
   private ArrayList<PlayerPanel> p;
   private JButton startButton;
   private JPanel UIPanel;
   private JLabel errorLabel;

   public SetupPanel(){
      super();
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      
      p = new ArrayList<PlayerPanel>();
      for (int i = 0; i < COLORS.length; i++){
         p.add(new PlayerPanel(COLORS[i]));
         add(p.get(i));
      }
      UIPanel = new JPanel();
      UIPanel.setBackground(new Color(0, 255, 255));
      JLabel label = new JLabel("Please configure Players and Press Start to Begin.");
      label.setFont(new Font("Serif", Font.BOLD, 16));
      UIPanel.add(label);
      startButton = new JButton(BUTTON_TEXT);
      UIPanel.add(startButton);
      add(UIPanel);
      
      errorLabel = new JLabel("YOU MUST HAVE AT LEAST ONE PLAYER");
      errorLabel.setFont(new Font("Serif", Font.BOLD, 16));
      errorLabel.setForeground(Color.red);
      errorLabel.setVisible(false);
      UIPanel.add(errorLabel);

   }
   
   
   public ArrayList<Player> generatePlayers(){
      ArrayList<Player> result = new ArrayList<Player> ();
      Player p2;
      for (int i = 0; i < p.size(); i++){
         p2 = p.get(i).generatePlayer();
         if (p2 != null)
            result.add(p2);
      }
      return result;
   }
   
   public JButton getStartButton(){
      return startButton;
   }

   public void showError(){
      errorLabel.setVisible(true);
   }

   public void hideError(){
      errorLabel.setVisible(false);
   }
   
   public static void main(String args[]) {

      SetupPanel x = new SetupPanel();
      JFrame go = new JFrame();
      go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      go.setSize(1200, 600);
      go.add(x);
      go.setVisible(true);
      x.showError();
   }
}