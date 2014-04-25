import java.util.*;

/**
   AIRules is a static set of variables and methods designed to 
   service decision requests. Its methods are all filters of one form
   of another which narrow down a set of possible move based on the
   AI's personality. This is intended to model some semblence of 
   "Artificial Intelligence".
*/

public class AIRules{
//*************************************************************************
// CONSTANTS
//*************************************************************************
   // Special Values and Descriptions for Humans and Various Types of AI
   public static final int HUMAN = 0;
   public static final int NICE = 1;
   public static final int MEAN = 2;
   public static final int CRAZY = 3;
   public static final String[] DESCRIPTIONS = {"HUMAN", "NICE AI", "MEAN AI", "CRAZY AI"};
   

//*************************************************************************
// METHODS
//*************************************************************************
   /**
      @param   A list of Moves
               A Player
      @return  A single move chosen based on the Player's personality
   */
   public static Move play(Player p, ArrayList<Move> moves){
      
      if (moves.size() <= 0) return null; // GUARD: Should never happen
      
      // find any safe spaces
      ArrayList<Move> safeMoves = new ArrayList<Move>();
      for (int i = 0; i < moves.size(); i++){
         if(moves.get(i).getTarget().isSafeForMe(p))
            safeMoves.add(moves.get(i));
      }
      if (safeMoves.size() > 0)
         moves = safeMoves;   // by default ALL ai's prefer to make their guys safe
      
      // Once the list has been filtered we can send it to a child
      // algorhim for the final selection
      Move result = null;
      int type = p.getAIType();
      if (type == NICE)
         result = playNice(moves);
      else if (type == MEAN)
         result = playMean(moves);
      else if (type == CRAZY)
         result = playCrazy(moves);
      
      return result;
   
   }
//-------------------------------------------------------------------------
   public static Move playCrazy(Player p, ArrayList<Move> moves){
      
            if (moves.size() <= 0) return null; // GUARD: Should never happen
      
      // find any safe spaces
      ArrayList<Move> safeMoves = new ArrayList<Move>();
      for (int i = 0; i < moves.size(); i++){
         if(moves.get(i).getTarget().isSafeForMe(p))
            safeMoves.add(moves.get(i));
      }
      if (safeMoves.size() > 0)
         moves = safeMoves;   // by default ALL ai's prefer to make their guys safe
      
      
      // A crazy AI just picks a random move
      Random r = new Random();
      int i = r.nextInt(moves.size());
      return moves.get(i);
   
   }
//-------------------------------------------------------------------------
   private static Move playCrazy(ArrayList<Move> moves){
      
      // A crazy AI just picks a random move
      Random r = new Random();
      int i = r.nextInt(moves.size());
      return moves.get(i);
   
   }
//-------------------------------------------------------------------------
   /** A handle for play where isNice = true */   
   private static Move playNice(ArrayList<Move> moves){
      return play(moves, true);   
   }
//-------------------------------------------------------------------------
   /** A handle for play where isNice = false */   
   private static Move playMean(ArrayList<Move> moves){
      return play(moves, false);   
   }   
//-------------------------------------------------------------------------
   /**
      Filters the list into subsets of moves which either bump (mean)
      or don't bump (nice). These subsets are then chosen from randomly
      to model the described behaviour.
   */
   private static Move play(ArrayList<Move> moves, boolean isNice){
      ArrayList<Move> niceMoves = new ArrayList<Move>();
      ArrayList<Move> meanMoves = new ArrayList<Move>();
      Move currentMove;
      Move result = null;
      // builds the lists of nice and mean moves
      for (int i = 0; i < moves.size(); i++){
         currentMove = moves.get(i);
         if (currentMove.getTarget().isEmpty())
            niceMoves.add(currentMove);
         else
            meanMoves.add(currentMove);   
      
      }
      
      // Choose based on existance of options and character
      if (isNice && (niceMoves.size() > 0)){
         result = playCrazy(niceMoves); // pick a random nice move
      }
      else if (!isNice && (meanMoves.size() > 0)){
         result = playCrazy(meanMoves); // pick a random mean move
      }

      return result;
      
   }


   
}// end of AIRules