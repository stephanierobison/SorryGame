import java.util.*; 

/**
   The board class is a multiply linked list of Space
   objects. The actual objects are stored in an 
   ArrayList to permit immediate references. (Not sure
   how that will work out..)
   
   For now each different configuration will be done 
   manually using a make() method. I hope to later
   change this to:
   1. Read from file 
   2. Use GUI editor
*/

public class SimpleBoard extends Board{
   
   public static final int ROWS = 16;
   public static final int COLUMNS = 16;
   public static final int N = (2 * ROWS) + (2 * COLUMNS) - 4;
   //private ArrayList<Space> spaces;
   
   public SimpleBoard(){
      spaces = new ArrayList<Space>();
      make();
    }
   
   public void make(){
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
   

   
   // TEST
   public static void main(String [ ] args){
      SimpleBoard b = new SimpleBoard();
      System.out.println(b.toString());
   
   }

}



// JUNK
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
