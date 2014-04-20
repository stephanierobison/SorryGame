import java.awt.*;
import javax.swing.*;

public class LocalTest{
      public static void main(String [ ] args){
      
      GameController2 g = new GameController2();
     
      JFrame x = new JFrame();
      x.setTitle("");  // name the window
		x.setSize(1200, 600);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      //BoardPanel bp = new BoardPanel(g.getGame());
      
      x.setLayout(new GridLayout(0,2));
		x.add(g.getBoardPanel());
      x.add(g.getGamePanel());
		x.setVisible(true);	
      
      
   } // end of main
}