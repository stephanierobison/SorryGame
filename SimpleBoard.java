import java.util.*; 

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

   public static final int ROWS = 16;
   public static final int COLUMNS = 16;
   public static final int N = (2 * ROWS) + (2 * COLUMNS) - 4;

//*************************************************************************
// CONSTRUCTOR
//*************************************************************************   
   public SimpleBoard(){
      spaces = new ArrayList<Space>();
      make();
    }
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
