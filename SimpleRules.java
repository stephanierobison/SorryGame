import java.util.*;



public class SimpleRules extends Ruleset{
  
 
  


//*************************************************************************
// CONSTRUCTORS
//*************************************************************************   

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
   public void move(Pawn p, Space s, int move){
      Pawn p2 = s.getPawn();   
      // Special Cases
      // Eleven swap is only case where you land on someone without
      // bumping them
      if (move == Ruleset.ELEVEN_SWAP){
         p.swapWith(p2);
         //incSwaps();
      }
      else if(!s.isEmpty() && (s.getToken() instanceof Pawn)){ // BUMP!
         p2.goHome();
         //incBumps();
         
      }
      p.moveTo(s); // redundant for eleven move but OK
      
      // BOOK KEEPING
      // If on valid slider then SLIDE
      if ( s.isSlider() && 
           (!s.getTraitColor().toString().equals(p.getColor().toString()))){
           
           // send everyone on slide home
           ArrayList<Space> slideTargets = s.getSlideTargets();
           for (int i = 0; i < slideTargets.size(); i++){ 
               if (!slideTargets.get(i).isEmpty()){
                  slideTargets.get(i).getPawn().goHome();
                  //incBumps();
               }
           }
           // send pawn to end of slide
           p.moveTo(s.getSlideEnd());
         
      }
      
         
   }
   
   
   public ArrayList<Move> getSpecialTargets(Board b, Pawn p, Space s, int n){
      ArrayList<Move> result = new ArrayList<Move>();
      if (n == START_OUT){
         if (p.whereAmI().getTrait() == START){
            ArrayList<Space> allExits = b.getTypeOfSpaces(START_EXIT, p.getColor());
            //result.addAll(b.getTypeOfSpaces(START_EXIT, p.getColor()));
            for (int i = 0; i < allExits.size(); i++){
               Pawn squatter = allExits.get(i).getPawn();
               if ((allExits.get(i).isEmpty()) ||
                   (!squatter.getColor().toString().equals(p.getColor().toString())))
                  result.add(new Move (allExits.get(i), n));
            }
            
         }
      }
      // 77777777777777777777777777777777777777777777777777777777777
      else if (n == SEVEN){
         result.addAll(getTargets(b,p,s,7)); // can always try vanilla 7

         // Find allied pawns
         ArrayList<Pawn> otherAlliedPawns = new ArrayList<Pawn>();
         ArrayList<Token> tokens = b.getTokens();
         Pawn currentPawn;
         for (int i = 0; i < tokens.size(); i++){
            if ((tokens.get(i) instanceof Pawn)&&
                (tokens.get(i) != p)){ // check type before casting
               currentPawn = (Pawn)tokens.get(i);
               if (currentPawn.getColor().toString().equals(p.getColor().toString())) // check color before adding
                  otherAlliedPawns.add(currentPawn);
            } 
         }
         
         // MIGHT be able to add 6 through 1 IF a move would be possible after
         ArrayList<Move> intermediateResult;
         Pawn currentAlly;
         for (int i = 1; i <= 6; i++){
            intermediateResult = new ArrayList<Move>();
            for (int j = 0; j < otherAlliedPawns.size(); j++){
               currentAlly = otherAlliedPawns.get(j);
               intermediateResult.addAll(getTargets(b, currentAlly, currentAlly.whereAmI(), 7 - i));
            }
            if (intermediateResult.size() > 0){
               intermediateResult = getTargets(b,p,s,i);
               for (int k = 0; k < intermediateResult.size(); k++)
                  intermediateResult.get(k).setChain(7 - i); 
               result.addAll(intermediateResult); 
            }
         }
         
         return result;
      }
      else if ((n == SORRY_SWAP) && (p.whereAmI().getTrait() == START)){
         ArrayList<Pawn> victims = b.getUnsafePawns();
         for (int i = 0; i < victims.size(); i++){
            if (!victims.get(i).getColor().toString().equals(p.getColor().toString()))
               result.add(new Move (victims.get(i).whereAmI(), n));
         }
      }
      else if (n == ELEVEN_SWAP){
         ArrayList<Pawn> victims = b.getUnsafePawns();
         if (victims.contains(p)){ // for eleven swap you must be unsafe too
            for (int i = 0; i < victims.size(); i++){
               if (!victims.get(i).getColor().toString().equals(p.getColor().toString()))
                  result.add(new Move(victims.get(i).whereAmI(), n));
            } 
         } 
      }
      
      return result;
   }
   
   
   public boolean containsAmbiguousMove(ArrayList<Move> moves, Space s){
      boolean swap = false;
      boolean move = false;
      Move currentMove;
      // In the basic rules the only possible source of ambiguity comes from the eleven
      // card - a pawn could be bumped by moving eleven spaces OR swapped using the
      // eleven card's swap ability
      // Therefore the simple rule's ambiguity test is to check for an ELEVEN_SWAP AND
      //  an 11 move both targeting the given space WITH a pawn
      
      if (s.isEmpty()) return false; // if the target space is empty then don't bother
      
      for (int i = 0; i < moves.size(); i++){
         currentMove = moves.get(i);
         if (s == currentMove.getTarget()){ // safety check - need to be talking about same space
            if (moves.get(i).getMove() == ELEVEN_SWAP){
               swap = true;
            }
            else if (moves.get(i).getMove() == 11){
               move = true;
            }
         }
      }
      System.out.println("Swap = " + Boolean.toString(swap) + "\nMove = " + Boolean.toString(move));
      return (swap && move);
   }
}// end of ruleset