import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

public class PlayerPanel extends JPanel {

   public static final String PORTRAIT_LIST_FILE = "portraits.txt";
   public static final int NAME_MAX = 20;
   
   private Color color;
   
   private JCheckBox isPlayingCheck;
   
   private JTextField nameField;
   
   private JLabel portraitDisplay;
      private JButton leftPortraitButton;
      private JButton rightPortraitButton;
   private String[] portraitList;
      private int portraitListCounter;
   private String portrait;
   
   private JComboBox playerTypeBox;
   
   public PlayerPanel(Color c){
      super();
      color = c;
      
      JPanel tempPanel;
      
      tempPanel = new JPanel();
      isPlayingCheck = new JCheckBox("In Game?");
      isPlayingCheck.setSelected(true);
      tempPanel.add(isPlayingCheck);
      tempPanel.setBackground(color);
      add(tempPanel);  
      
      
      // portrait display
      tempPanel = new JPanel();
      leftPortraitButton = new JButton("<");
      leftPortraitButton.addActionListener(new ButtonListener());
      rightPortraitButton = new JButton(">");
      rightPortraitButton.addActionListener(new ButtonListener());
            
      ReadFile reader = new ReadFile(PORTRAIT_LIST_FILE);
      portraitListCounter = 0;
      try{
         portraitList = reader.getData();
      }
      catch (IOException e){
         System.out.println("ERROR! Did not read portrait file correctly.");      
     }
      //portraitDisplay = new JLabel("", image, JLabel.CENTER);
      portraitDisplay = new JLabel("", JLabel.CENTER);
      updatePortrait();   
      tempPanel.setLayout(new BorderLayout());
         JPanel miniPanel = new JPanel();
         miniPanel.setBackground(color);
         //miniPanel.setLayout(new BorderLayout());
         miniPanel.setLayout(new GridLayout(0,2));
         miniPanel.add(leftPortraitButton);//, BorderLayout.CENTER);
         //tempPanel.add(miniPanel, BorderLayout.WEST);
         //miniPanel = new JPanel();
         miniPanel.add(rightPortraitButton);//, BorderLayout.CENTER);
         tempPanel.add(miniPanel, BorderLayout.SOUTH);
      
      tempPanel.add(portraitDisplay, BorderLayout.CENTER);
      tempPanel.setBackground(color);
      add(tempPanel);  
      
      tempPanel = new JPanel();
      nameField = new JTextField(AIRules.randomName(), NAME_MAX);
      tempPanel.add(nameField);
      tempPanel.setBackground(color);
      add(tempPanel);      
      
      
      tempPanel = new JPanel();
      playerTypeBox = new JComboBox(AIRules.DESCRIPTIONS);
      tempPanel.add(playerTypeBox);
      tempPanel.setBackground(color);
      add(tempPanel);
      
      setBackground(color);
   
   }
   
   
   private void updatePortrait(){
      
      ImageIcon image = new ImageIcon(portraitList[portraitListCounter]);
      portraitDisplay.setIcon(image);
   }
   
   public Player generatePlayer(){
      Player p = null;
      if (isPlayingCheck.isSelected())
         p = new Player(color, 
                        nameField.getText(), 
                            portraitList[portraitListCounter], 
                            playerTypeBox.getSelectedIndex());
      return p;
   
   }

   private class ButtonListener implements ActionListener{
   
      public void actionPerformed(ActionEvent e){
         String action = e.getActionCommand();
         
         if (action.equals("<")){
            portraitListCounter--;
            if (portraitListCounter < 0)
               portraitListCounter = portraitList.length - 1;
         }
         else if (action.equals(">")){
            portraitListCounter++;
            if (portraitListCounter >= portraitList.length)
               portraitListCounter = 0;
         
         }
         updatePortrait();
         revalidate();
         //System.out.println(action + " # " + portraitList[portraitListCounter]);
      }
   
   
   }// end of class ButtonListener



   //TEST---------------------------------------------------------------------
  
   
   public static void main(String [ ] args){
      PlayerPanel pp = new PlayerPanel(Color.red);
     
      JFrame x = new JFrame();
      x.setTitle("Test");  // name the window
		x.setSize(800, 600);  // set size
		//setLocationRelativeTo(null);
		x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		x.add(pp);

		x.setVisible(true);	
      
      
   } // end of main

}