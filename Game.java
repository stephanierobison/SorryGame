/**
   Model for Game
*/
public class Game{

   private Board board;
   private Ruleset rules;
   
   private int[] moves; // will change turn by turn, might be null, decided by controller
   
   public Game(Board b, Ruleset r){
      board = b;
      rules = r;
      moves = null;
   }
   
   public Board getBoard(){
   
      return board;
   
   }// end of getBoard()
   
   public Ruleset getRules(){
      return rules;
   }
   
   public int[] getMoves(){
      return moves;
   }// end of getMoves()


}//end of class