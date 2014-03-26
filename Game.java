import java.util.*;
import java.awt.*;

/**
   Model for Game
*/
public class Game{

   private Board board;
   private Ruleset rules;
   private ArrayList<Player> players;
   private int currentPlayerIndex;
   
   private int[] moves; // will change turn by turn, might be null, decided by controller
   
   public Game(Board b, Ruleset r, ArrayList<Player> p){
      board = b;
      rules = r;
      players = p;
      // hard code for now
      currentPlayerIndex = 0;
      moves = null;
   }
   
   // Mutates state to get next turn
   public void nextTurn(){
      currentPlayerIndex++;
      currentPlayerIndex = currentPlayerIndex % players.size();
      moves = new int[] {-2, 4, 10}; // Replace with call to Deck
   }
   
   public Player getCurrentPlayer(){
      return players.get(currentPlayerIndex);
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