import java.util.*;


public abstract class Ruleset{
//*************************************************************************
// PUBLIC METHODS
//*************************************************************************   
//-------------------------------------------------------------------------
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
   public ArrayList<Space> getTargets(Pawn p, Space s, int n){
      ArrayList<Space> result = new ArrayList<Space>();
      ArrayList<Space> neighbors;   // neighbors of s
      ArrayList<Space> neighbors2;  // neighbors of neighbors of s    
      int cost;                     // cost of movement (depends on direction)
      if (n == 0){         // BASE CASE
         if (isLandable(s,p)) // ... but it only counts if we can land on the space
            result.add(s);
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
            neighbors2 = getTargets(p, neighbors.get(i), (n + cost) ); // don't forget to expend a move
            result.addAll(neighbors2);
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
}// end of ruleset