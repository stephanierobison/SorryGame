import java.util.*;


public class AIRules{


   public static final int HUMAN = 0;
   public static final int NICE = 1;
   public static final int MEAN = 2;
   public static final int CRAZY = 3;
   public static final String[] DESCRIPTIONS = {"HUMAN", "NICE AI", "MEAN AI", "CRAZY AI"};
   
   public static final String[] NAMES = {"Namor", "Mer-man", "Aquaman", "Black Manta", "Mermaid Man",
                                         "Barnacle Boy", "Ariel", "Ursula", "Neptune", "Poseiden",
                                         "Charybdis", "Scylla", "Calypso", "Medea", "Kraken", 
                                         "Blackbeard", "Davy Jones", "Jolly Roger", "Jacques Cousteau"};
   
   
   public static String randomName(){
      Random r = new Random();
      int i = r.nextInt(NAMES.length);
      return NAMES[i];
   }

   // picks a move from a list
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


   private static Move playCrazy(ArrayList<Move> moves){
      
      // A crazy AI just picks a random move
      Random r = new Random();
      int i = r.nextInt(moves.size());
      return moves.get(i);
   
   }
   
   private static Move playNice(ArrayList<Move> moves){
      return play(moves, true);   
   }
   
   private static Move playMean(ArrayList<Move> moves){
      return play(moves, false);   
   }   
   
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
         result = playCrazy(niceMoves);
      }
      else if (!isNice && (meanMoves.size() > 0)){
         result = playCrazy(meanMoves);
      }
      /* else
         result = playCrazy(moves);   */
     
      return result;
      
   }


   
}