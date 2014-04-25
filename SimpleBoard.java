import java.util.*; 
import java.awt.*;

/**
   SimpleBoard is the standard SORRY
   16 x 16 board with branches to enter
   start and safe areas for up to four
   players. It is intended to be used
   with the SimpleRules extension of
   the Ruleset object.
   
   THE CURRENT MAKE METHOD IS INSANE.
   BUT IT HAS BEEN THOUROUGHLY TESTED
   AND WORKS. IT SHOULD BE REFACTORED
   BUT AT THE MOMENT IT IS A LOWer PRIORITY
   THAN OTHER THINGS.
*/

public class SimpleBoard extends Board{
//*************************************************************************
// CONSTANTS
//*************************************************************************   

//*************************************************************************
// CONSTRUCTOR
//*************************************************************************   
   public SimpleBoard(){
      super();
      rules = new SimpleRules();
    }

   public SimpleBoard(int r, int c){
      super(r,c);
      rules = new SimpleRules();      
   }
 
//*************************************************************************
// METHODS
//*************************************************************************   
   /*
     Makes the adjacency list representing the board.
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
      
      Pawn p;   
      // RED PLAYERS SPECIAL SPACES   
      // make red player's start
      for (int i = 60; i <= 63; i++){
         spaces.add(new Space(Integer.toString(i)));
         //spaces.get(i).addForwardsNeighbor(spaces.get(4));
         spaces.get(i).setColor(Color.red);
         spaces.get(i).setTrait(Ruleset.START);
         spaces.get(i).setXY(((i - 60) % 2) + 4, (int)Math.floor(((double)(i) - 60.0)/2.0) + 2);
         p = new Pawn(spaces.get(i), spaces.get(i).getColor());
      }
      spaces.get(4).setTrait(Ruleset.START_EXIT);
      spaces.get(4).setTraitColor(Color.red);
      
      // make red player's safe
      for (int i = 64; i <= 68; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).setColor(Color.red);
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
         spaces.get(i).setColor(Color.red);
         spaces.get(i).setTrait(Ruleset.HOME);
         spaces.get(i).setXY(3 - (i % 2), (int)Math.floor(((double)(i) - 69.0)/2.0) + 6);
      }
      
      // BLUE PLAYERS SPECIAL SPACES   
      // make blue player's start
      for (int i = 73; i <= 76; i++){
         spaces.add(new Space(Integer.toString(i)));
         //spaces.get(i).addForwardsNeighbor(spaces.get(19));
         spaces.get(i).setTrait(Ruleset.START);
         spaces.get(i).setColor(Color.blue);
         spaces.get(i).setXY(13 - (i % 2), (int)Math.floor(((double)(i) - 73.0)/2.0) + 4);
         p = new Pawn(spaces.get(i), spaces.get(i).getColor());
      }
      spaces.get(19).setTrait(Ruleset.START_EXIT);
      spaces.get(19).setTraitColor(Color.blue);
      
      // make blue player's safe
      for (int i = 77; i <= 81; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).setColor(Color.blue);
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
         spaces.get(i).setColor(Color.blue);
         spaces.get(i).setTrait(Ruleset.HOME);
         spaces.get(i).setXY(8 + (i % 2), (int)Math.floor(((double)(i) - 82.0)/2.0) + 2);
      }
      
      // YELLOW PLAYERS SPECIAL SPACES   
      // make yellow player's start
      for (int i = 86; i <= 89; i++){
         spaces.add(new Space(Integer.toString(i)));
         //spaces.get(i).addForwardsNeighbor(spaces.get(34));
         spaces.get(i).setColor(Color.yellow);
         spaces.get(i).setTrait(Ruleset.START);
         spaces.get(i).setXY(10 + (i % 2), (int)Math.floor(((double)(i) - 86.0)/2.0) + 12);
         p = new Pawn(spaces.get(i), spaces.get(i).getColor());

      }
      spaces.get(34).setTrait(Ruleset.START_EXIT);
      spaces.get(34).setTraitColor(Color.yellow);
      
      // make yellow player's safe
      for (int i = 90; i <= 94; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).setColor(Color.yellow);
         spaces.get(i).setXY(13, 14 - (i - 90));
      }
      for (int i = 90; i <= 93; i++){
         spaces.get(i).addForwardsNeighbor(spaces.get(i + 1));
      }
      spaces.get(90).addBackwardsNeighbor(spaces.get(32));
      spaces.get(32).addForwardsNeighbor(spaces.get(90));
      for (int i = 91; i <= 94; i++){
         spaces.get(i).addBackwardsNeighbor(spaces.get(i - 1));
      }
      
      // make yellow player's home      
      for (int i = 95; i <= 98; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(94).addForwardsNeighbor(spaces.get(i));
         spaces.get(i).setColor(Color.yellow);
         spaces.get(i).setTrait(Ruleset.HOME);
         spaces.get(i).setXY(13 - (i % 2), (int)Math.floor(((double)(i) - 95.0)/2.0) + 8);
      }
      
      // GREEN PLAYERS SPECIAL SPACES   
      // make green player's start
      for (int i = 99; i <= 102; i++){
         spaces.add(new Space(Integer.toString(i)));
         //spaces.get(i).addForwardsNeighbor(spaces.get(49));
         spaces.get(i).setColor(Color.green);
         spaces.get(i).setTrait(Ruleset.START);
         spaces.get(i).setXY(3 - (i % 2), (int)Math.floor(((double)(i) - 99.0)/2.0) + 10);
         p = new Pawn(spaces.get(i), Color.green);
      }
      spaces.get(49).setTrait(Ruleset.START_EXIT);
      spaces.get(49).setTraitColor(Color.green);
      
      // make green player's safe
      for (int i = 103; i <= 107; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(i).setColor(Color.green);
         spaces.get(i).setXY(i - 102, 13);
      }
      for (int i = 103; i <= 106; i++){
         spaces.get(i).addForwardsNeighbor(spaces.get(i + 1));
      }
      spaces.get(103).addBackwardsNeighbor(spaces.get(47));
      spaces.get(47).addForwardsNeighbor(spaces.get(103));
      for (int i = 104; i <= 107; i++){
         spaces.get(i).addBackwardsNeighbor(spaces.get(i - 1));
      }
      
      // make green player's home      
      for (int i = 108; i <= 111; i++){
         spaces.add(new Space(Integer.toString(i)));
         spaces.get(107).addForwardsNeighbor(spaces.get(i));
         spaces.get(i).setColor(Color.green);
         spaces.get(i).setTrait(Ruleset.HOME);
         spaces.get(i).setXY(6 + (i % 2), (int)Math.floor(((double)(i) - 108.0)/2.0) + 12);
      } 
      
      
      // SET SLIDERS
      //red
      spaces.get(1).setTraitColor(Color.red);
         spaces.get(1).addSlideTarget(spaces.get(2));
         spaces.get(1).addSlideTarget(spaces.get(3));      
         spaces.get(1).addSlideTarget(spaces.get(4));
      
      spaces.get(9).setTraitColor(Color.red);
         spaces.get(9).addSlideTarget(spaces.get(10));
         spaces.get(9).addSlideTarget(spaces.get(11));      
         spaces.get(9).addSlideTarget(spaces.get(12));
         spaces.get(9).addSlideTarget(spaces.get(13));
      
      //blue
      spaces.get(16).setTraitColor(Color.blue);
         spaces.get(16).addSlideTarget(spaces.get(17));
         spaces.get(16).addSlideTarget(spaces.get(18));      
         spaces.get(16).addSlideTarget(spaces.get(19));
      
      spaces.get(24).setTraitColor(Color.blue);
         spaces.get(24).addSlideTarget(spaces.get(25));
         spaces.get(24).addSlideTarget(spaces.get(26));      
         spaces.get(24).addSlideTarget(spaces.get(27));
         spaces.get(24).addSlideTarget(spaces.get(28));
         
      // yellow
      spaces.get(31).setTraitColor(Color.yellow);
         spaces.get(31).addSlideTarget(spaces.get(32));
         spaces.get(31).addSlideTarget(spaces.get(33));      
         spaces.get(31).addSlideTarget(spaces.get(34));
      
      spaces.get(39).setTraitColor(Color.yellow);
         spaces.get(39).addSlideTarget(spaces.get(40));
         spaces.get(39).addSlideTarget(spaces.get(41));      
         spaces.get(39).addSlideTarget(spaces.get(42));
         spaces.get(39).addSlideTarget(spaces.get(43));
         
      //green
      spaces.get(46).setTraitColor(Color.green);
         spaces.get(46).addSlideTarget(spaces.get(47));
         spaces.get(46).addSlideTarget(spaces.get(48));      
         spaces.get(46).addSlideTarget(spaces.get(49));
      
      spaces.get(54).setTraitColor(Color.green);
         spaces.get(54).addSlideTarget(spaces.get(55));
         spaces.get(54).addSlideTarget(spaces.get(56));      
         spaces.get(54).addSlideTarget(spaces.get(57));
         spaces.get(54).addSlideTarget(spaces.get(58));


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
