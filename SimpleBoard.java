import java.util.*; 
import java.awt.*;

/**
   SimpleBoard is the standard SORRY
   16 x 16 board with branches to enter
   start and safe areas for up to four
   players. It is intended to be used
   with the SimpleRules extension of
   the Ruleset object.
   
   FOR NOW IS JUST A TRACK AROUND THE
   EDGE OF THE BOARD.
*/

public class SimpleBoard extends Board{
//*************************************************************************
// CONSTANTS
//*************************************************************************   

//*************************************************************************
// CONSTRUCTOR
//*************************************************************************   
  /* public SimpleBoard(){
      ROWS = 16;
      COLUMNS = 16;
      N = (2 * ROWS) + (2 * COLUMNS) - 4;
      spaces = new ArrayList<Space>();
      make();
    }*/
    /*public SimpleBoard(int rows, int columns){
      super(rows, columns);
    }*/
//*************************************************************************
// PRIVATE METHODS
//*************************************************************************   
   /*
     At this point the default board is hard coded manually,
     not generated procedurally. 
   */   
   protected void make(){
      // make spaces and add forwards links
      spaces.add(new Space("0"));
      for (int i = 1; i <= N-1; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i - 1).addForwardsNeighbor(spaces.get(i));
      }   
      spaces.get(N-1).addForwardsNeighbor(spaces.get(0));
      
      // add backwards links
      spaces.get(0).addBackwardsNeighbor(spaces.get(N-1));
      for (int i = 1; i <= N-1; i++){
         spaces.get(i).addBackwardsNeighbor(spaces.get(i-1));
      } 
      
      // add xy coords
      // there has to be a better way to do this...
     
      // top row 
      for (int j = 0; j <= COLUMNS - 1; j++)
         spaces.get(j).setXY(j, 0);

      // right side        
      for (int j = 1;j <= ROWS - 1 ;j++)
         spaces.get(j + COLUMNS - 1).setXY(COLUMNS - 1, j);
      
      // bottom row 
      for (int j = 0; j <= COLUMNS - 1; j++)
         spaces.get(j + COLUMNS - 1 + ROWS - 1).setXY(COLUMNS - j - 1, ROWS - 1);
        
      // right side
      for (int j = 1; j <= ROWS - 2; j++)
         spaces.get(j + (2* COLUMNS) - 2 + ROWS - 1).setXY(0, ROWS - j - 1);
         
      // RED PLAYERS SPECIAL SPACES   
      // make red player's start
      for (int i = 60; i <= 63; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).addForwardsNeighbor(spaces.get(4));
         spaces.get(i).setColor(Color.red.darker());
         spaces.get(i).setXY(((i - 60) % 2) + 4, (int)Math.floor(((double)(i) - 60.0)/2.0) + 2);
      }
      
      // make red player's safe
      for (int i = 64; i <= 68; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).setColor(Color.red.darker());
         spaces.get(i).setXY(2, i - 63);
      }
      for (int i = 64; i <= 67; i++){
         spaces.get(i).addForwardsNeighbor(spaces.get(i + 1));
      }
      spaces.get(64).addBackwardsNeighbor(spaces.get(2));
      spaces.get(2).addForwardsNeighbor(spaces.get(64));
      for (int i = 65; i <= 68; i++){
         spaces.get(i).addBackwardsNeighbor(spaces.get(i - 1));
      }
      // make red player's home      
      for (int i = 69; i <= 72; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(68).addForwardsNeighbor(spaces.get(i));
         spaces.get(i).setColor(Color.red.darker());
         spaces.get(i).setXY(3 - (i % 2), (int)Math.floor(((double)(i) - 69.0)/2.0) + 6);
      }
      
      // BLUE PLAYERS SPECIAL SPACES   
      // make blue player's start
      for (int i = 73; i <= 76; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).addForwardsNeighbor(spaces.get(19));
         spaces.get(i).setColor(Color.blue.darker());
         spaces.get(i).setXY(13 - (i % 2), (int)Math.floor(((double)(i) - 73.0)/2.0) + 4);
      }
      
      
      // make blue player's safe
      for (int i = 77; i <= 81; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).setColor(Color.blue.darker());
         spaces.get(i).setXY(14 - (i - 77), 2);
      }
      for (int i = 77; i <= 80; i++){
         spaces.get(i).addForwardsNeighbor(spaces.get(i + 1));
      }
      spaces.get(77).addBackwardsNeighbor(spaces.get(17));
      spaces.get(17).addForwardsNeighbor(spaces.get(77));
      for (int i = 78; i <= 81; i++){
         spaces.get(i).addBackwardsNeighbor(spaces.get(i - 1));
      }
      
      // make blue player's home      
      for (int i = 82; i <= 85; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(81).addForwardsNeighbor(spaces.get(i));
         spaces.get(i).setColor(Color.blue.darker());
         spaces.get(i).setXY(8 + (i % 2), (int)Math.floor(((double)(i) - 82.0)/2.0) + 2);
      }
      
        
   }// end of make()
   
//*************************************************************************
// TEST
//*************************************************************************
   /* Simply creates and prints toString representation
      for human testing */
   public static void main(String [ ] args){
      SimpleBoard b = new SimpleBoard();
      System.out.println(b.toString());
   
   }

}



// SCRIPS AND SCRAPS
/*
            else if (i == 2)
               spaces.get(index).setXY((N/4) - 1 - j,(N/4) - 1);         
            else if (i == 3)
               spaces.get(index).setXY(0, (N/4) - 1 - j);
*/
      /*spaces.get(0).setXY(0,0); // start in upper LH corner
      for (int i = 0; i < 4; i++)
         for (int j = 1, index ; j < (N / 4); j++){
            // there has to be a better way to do this...
            index = (i * (N/4)) + j;
            if (i == 0)
               spaces.get(index).setXY(j, 0);
            else if (i == 1)
               spaces.get(index).setXY((N/4) - 1, j);
            else if (i == 2)
               spaces.get(index).setXY((N/4) - 1 - j,(N/4) - 1);         
            else if (i == 3)
               spaces.get(index).setXY(0, (N/4) - 1 - j);
                     
            System.out.println("Index: " + index + ",\tXY: " + 
                                       spaces.get(index).getX() + ", " +
                                       spaces.get(index).getY());
            
         } WRONG */
      
      // add shortcut from 5 to 9 and back
      /*spaces.get(5).addForwardsNeighbor(spaces.get(9));
      spaces.get(9).addBackwardsNeighbor(spaces.get(5));*/
      
      // add a dead end
/*      spaces.add(new Space("D")); // should have index 10
      spaces.get(1).addForwardsNeighbor(spaces.get(10));            
      spaces.get(10).addBackwardsNeighbor(spaces.get(1));*/
