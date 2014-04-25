import java.util.*;

/**
   A Move is basically a Struct permitting us to move a target Space,
   the move that got a Pawn there, and the moves remaining to the
   player after the move (if any) as a single entity.
*/

public class Move{
//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************
   /** the landed on Space */
   public Space target;
   /** The move value that got us to the target */
   public int cost;
   /** Leftover moves from a 7 (if any) */
   public Integer chain; 
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************   
   public Move(Space s, int m){
      target = s;
      cost = m;
      chain = null;
   }
//-------------------------------------------------------------------------
   public Move(Space s, int m, int c){
      target = s;
      cost = m;
      chain = new Integer(c);
   }
//*************************************************************************
// BASIC SETTERS/GETTERS
//*************************************************************************   
   public Integer getChain(){
      return chain;
   }
//-------------------------------------------------------------------------
   public void setChain(int i){
      chain = new Integer(i);
   }
//-------------------------------------------------------------------------
   public Space getTarget(){
      return target;
   }
//-------------------------------------------------------------------------
   public int getMove(){
      return cost;
   }
//-------------------------------------------------------------------------
   /**
      @param ArrayList<Move> moves  An array of Moves
      @param Space           s      a space
      @return                moves with all Move elements that do not target s removed
   */
   public static ArrayList<Move> subsetTargeting(ArrayList<Move> moves, Space s){
      ArrayList<Move> result = new ArrayList<Move> ();
      for (int i = 0; i < moves.size(); i++){   // Linear Scan
         if (moves.get(i).target == s){
            result.add(moves.get(i));
         }      
      }
      return result;
   }// end of subsetTargeting

}// end of Move