
import java.util.*;

public class Move{
   
   public Space target;
   public int cost;
   
   public Move(Space s, int m){
      target = s;
      cost = m;
   }
   
   public static ArrayList<Move> subsetTargeting(ArrayList<Move> moves, Space s){
      ArrayList<Move> result = new ArrayList<Move> ();
      for (int i = 0; i < moves.size(); i++){
         if (moves.get(i).target == s){
            result.add(moves.get(i));
         }      
      }
      return result;
   }

}