import java.util.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;

/*
	Experimental moving box stimuli prototype
	An animation window showing a box moving in
	a random cardinal direction interspersed with
	rest periods just showing an x
*/

public class GridClick extends JFrame{
		
	private final static int NUMBER_COLUMNS = 16;
   private final static int NUMBER_ROWS = 16;
   
   
   private final static Color BACKGROUND = Color.black;
	private final static Color BOX = Color.green;
	private final static Color X = Color.white;
	
	private Color backgroundColor;
	private Color boxColor;
	private Color metaColor;
	
	private myCanvas animCanvas;
	

	
	private Random rand;

	
	
//CONSTRUCTORS-------------------------------------------------------------	
	public GridClick()//actionTime
	{
		
		rand = new Random();
		
		setTitle("");  // name the window
		setSize(600, 600);  // set size
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		animCanvas = new myCanvas();
		animCanvas.setBackground(backgroundColor);
		
		add(animCanvas);
		animCanvas.repaint();
      animCanvas.addMouseListener(new myListener());
	

		//setUndecorated(true);
		

		setVisible(true);	
	}

//PUBLIC METHODS-------------------------------------------------------------------

	public void refresh()
	{
		animCanvas.repaint();
	}


//PRIVATE METHODS--------------------------------------------------------------------

//PRIVATE CLASSES-------------------------------------------------------------
	private class myCanvas extends JPanel{
		public void paintComponent(Graphics g){

         
         
         super.paintComponent(g);
         
         
//         int panelX = animCanvas.getWidth();
//         int panelY = animCanvas.getHeight();
         int panelX = getWidth();
         int panelY = getHeight();   
         int boxWidth = panelX / NUMBER_COLUMNS;
         int boxHeight = panelY / NUMBER_ROWS;
         setSize((boxWidth*NUMBER_COLUMNS)+1, (boxHeight * NUMBER_ROWS)+1);
         setBackground(Color.black);
			
         //draw column dividers
         for(int i = 0; i <= NUMBER_COLUMNS; i++){
            g.setColor(Color.blue);
            g.drawLine(i * boxWidth, 0,
                        i * boxWidth, panelY);
         }
			
         //draw row dividers
         for(int i = 0; i <= NUMBER_ROWS; i++){
            g.setColor(Color.green);
            g.drawLine(0, i * boxHeight,
                        panelX, i * boxHeight);
         }
        
        System.out.println("Paint Component");
         
	   }	
	}// end of myCanvas

//LISTENERS----------------------------------------------------------------

    private class myListener implements MouseListener
    {
       public void mousePressed(MouseEvent e) {
           System.out.println("Mouse pressed (# of clicks: "
                   + e.getClickCount() + ")");
           
       }
        
       public void mouseReleased(MouseEvent e) {
           System.out.println("Mouse released (# of clicks: "
                   + e.getClickCount() + ")");
       }
        
       public void mouseEntered(MouseEvent e) {
           System.out.println("Mouse entered");
       }
        
       public void mouseExited(MouseEvent e) {
           System.out.println("Mouse exited");
       }
        
       public void mouseClicked(MouseEvent e) {
           System.out.println("Mouse clicked (# of clicks: "
                   + e.getClickCount() + ")");
           System.out.println("Mouse relative x,y: "
                   + e.getX() + ", " + e.getY());
           System.out.println("Mouse absolute x,y: "
                   + e.getXOnScreen() + ", " + e.getYOnScreen());
           getGridCoords(e.getX(), e.getY());
       }
    }
//GRID STUFF---------------------------------------------------------------

   private void getGridCoords(int x, int y){
      // Zero index grid of boxes divying up panel evenly
      // in NUMBER_COLUMNS & NUMBER_ROWS
      int panelX = animCanvas.getWidth();
      int panelY = animCanvas.getHeight();
      int boxWidth = panelX / NUMBER_COLUMNS;
      int boxHeight = panelY / NUMBER_ROWS;
      int columnNumber = (x / boxWidth);
      int rowNumber = (y / boxHeight);
      System.out.println("Bow Column, Row = " + columnNumber + ", " + rowNumber);
   }
   
//TEST---------------------------------------------------------------------
	public static void main(String[] args)
	{
		GridClick x = new GridClick();
	}

}// end of AnimWindow