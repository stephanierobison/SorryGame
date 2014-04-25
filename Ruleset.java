import java.util.*;
/**
   The Ruleset describes the interactions between game elements.
   It is especially concerned with the movement of pawns around 
   the board via both direct movement and special cards.
*/
public abstract class Ruleset{
//*************************************************************************
// CONSTANTS
//*************************************************************************
   // Space Trait Values
   public static final int NO_TRAIT = 0; 
   public static final int START = 1;
   public static final int HOME = 2;
   public static final int START_EXIT = 3;
   public static final int SLIDER_START = 4;

   // Special Move Values
   public static final int SPECIAL_MOVE_MIN = -1000;
   public static final int START_OUT = -1001;
   public static final int SEVEN = -1002;    
   public static final int SORRY_SWAP = -1003;
   public static final int ELEVEN_SWAP = -1004;
   
//*************************************************************************
// PUBLIC METHODS
//*************************************************************************   
//-------------------------------------------------------------------------
   /** Handle method for overloaded getTargets: permits initial call
   function to simply reference the starting number of moves. */
   public ArrayList<Move> getTargets(Board b, Pawn p, Space s, int n){
      return getTargets(b, p, s, n, n);   
   }
   
   /**
      Recursivly determines all possible forwards moves
      given a starting Space and a positive integer 
      number of moves.
      
      @param   Pawn              The Pawn making the move
      @param   Space             A starting space
      @param   int               An integer
      @return  ArrayList<Space>  A list of all Spaces n moves away from
                                 the starting Space.
                                 A postive n indicates FORWARD movement.
                                 A negative n indicates BACKWARD movement.
     
   */
   // NOTE TO SELF: should probably make iterative somehow for speed..
   private ArrayList<Move> getTargets(Board b, Pawn p, Space s, int n, int x){// x is just recording starting n
      ArrayList<Move> result = new ArrayList<Move>();
      ArrayList<Space> neighbors;   // neighbors of s
      ArrayList<Move> neighborsTargets;  // valid targets of neighbors of neighbors of s    
      int cost;                     // cost of movement (depends on direction)
      
      if (n < SPECIAL_MOVE_MIN){ // SPECIAL BASE CASE
                                 // Special Moves have negative values below some MIN
         result = getSpecialTargets(b, p, s, n);
      }
      else if (n == 0){         // BASE CASE
         if (isLandable(s,p)) // ... but it only counts if we can land on the space
            result.add(new Move(s, x));
      }
                                 // INDUCTIVE CASE 
      else if (isPassable(s,p)){   // ...but only bother if we can traverse the current space.               
         if (n < 0){ 
            neighbors = s.getBackwardsNeighbors();
            cost = 1;
         }
         else{
            neighbors = s.getForwardsNeighbors();
            cost = -1;
         }
         for (int i = 0; i < neighbors.size(); i++){   // ...recurse on all neighbors
            neighborsTargets = getTargets(b, p, neighbors.get(i), (n + cost), x ); // don't forget to expend a move
            result.addAll(neighborsTargets);
         }
      }
      // Implied ELSE result.add(NULL)
      return result;
   }// end of getTargetsBack
//-------------------------------------------------------------------------
//*************************************************************************
// ABSTRACT METHODS
//*************************************************************************
/*
   Note that the "passable" and "landable" properties will change based
   on different rulesets and potentially the internal state of a given
   Ruleset, the considered Space, the Space's Board, or the Space's Game.
   Hence the abstraction at the top level of inheritance.
*/
//-------------------------------------------------------------------------
   /**
      @return boolean   TRUE if the space can currently be traversed using
                        the current rules, FALSE otherwise.
   */
   abstract boolean isPassable(Space s, Pawn p);
//-------------------------------------------------------------------------
   /**
      @return boolean   TRUE if the space can currently be landed on using
                        the current rules, FALSE otherwise.
   */
   abstract boolean isLandable(Space s, Pawn p);
//-------------------------------------------------------------------------
//-------------------------------------------------------------------------
   /**
      Handles the mutation required to move a particular pawn to a
      particular space.
   */
   abstract void move(Pawn p, Space s, int move);
//-------------------------------------------------------------------------
//-------------------------------------------------------------------------
   /**
      Determines if a set of moves landing on a single space creates the 
      possibility of an ambiguous outcome. For example, a space could
      potentially be landed on via 
   */
   abstract boolean containsAmbiguousMove(ArrayList<Move> moves, Space s);
//-------------------------------------------------------------------------
   /**
      Handles movement which cannot be described by a recursive walk through
      the Space graph.
   */
   protected abstract ArrayList<Move> getSpecialTargets(Board b, Pawn p, Space s, int n);
//-------------------------------------------------------------------------

}// end of ruleset