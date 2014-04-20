
import java.util.*;

public class Move{
   
   public Space target;
   public int cost;
   public Integer chain; 
   
   public Move(Space s, int m){
      target = s;
      cost = m;
      chain = null;
   }
   
   public Move(Space s, int m, int c){
      target = s;
      cost = m;
      chain = new Integer(c);
   }
   
   public Integer getChain(){
      return chain;
   }
   
   public void setChain(int i){
      chain = new Integer(i);
   }
   
   public Space getTarget(){
      return target;
   }
   
   public int getMove(){
      return cost;
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