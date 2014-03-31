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
      boolean result = false;
      // Cannot enter a space of a different color (not counting default)
      if ((s.getColor().toString().equals(Space.DEFAULT_COLOR.toString())) || 
          (p.getColor().toString().equals(s.getColor().toString())))
         result = true;
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
      Pawn p2 = null;
      // can only posibly land on a traversable space
      if (isPassable(s,p)){
         if (s.getToken() instanceof Pawn)
            p2 = (Pawn)s.getToken();
         // can land if space is either empty or contains an enemy Pawn
         if (s.isEmpty() || (!p.getColor().toString().equals(p2.getColor().toString())))
            result = true;
      }
      return result;
   }
//-------------------------------------------------------------------------
   //handles consequences of moving a pawn somewhere
   public void move(Pawn p, Space s){
      if(!s.isEmpty() && (s.getToken() instanceof Pawn)){ // BUMP!
         Pawn p2 = (Pawn)s.getToken();
         p2.goHome();
      }
      p.moveTo(s);
         
   }
}// end of ruleset