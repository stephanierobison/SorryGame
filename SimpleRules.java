import java.util.*;


public class SimpleRules extends Ruleset{
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************   
   public SimpleRules(){
   
   }
//*************************************************************************
// PUBLIC METHODS
//*************************************************************************   
//-------------------------------------------------------------------------
/*
   Note that the "passable" and "landable" properties will change based
   on different rulesets and potentiall the internal state of a given
   Ruleset, the considered Space, the Space's Board, or the Space's Game.
   Hence the abstraction at the top level of inheritance.
*/
//-------------------------------------------------------------------------
   /**
      Until space special properties are implemented all Spaces
      are traversable.
   
      @return boolean   TRUE if the space can currently be traversed using
                        the current rules, FALSE otherwise.
   */
   public boolean isPassable(Space s, Pawn p){
      boolean result = true;
      return result;
   }
//-------------------------------------------------------------------------
   /**
      For now, a Space must be empty to be landed on.
      
      @return boolean   TRUE if the space can currently be landed on using
                        the current rules, FALSE otherwise.
   */
   public boolean isLandable(Space s, Pawn p){
      boolean result = false;
      if (s.isEmpty())
         result = true;
      return result;
   }
//-------------------------------------------------------------------------
}// end of ruleset