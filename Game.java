import java.util.*;
import java.awt.*;

/**
   Model for Game
*/
public class Game{

   private Board board;
   private Deck deck;
   private Ruleset rules;
   private ArrayList<Player> players;
   private int currentPlayerIndex;
   
   private int[] moves; // will change turn by turn, might be null, decided by controller
   
   public Game(Board b, Ruleset r, ArrayList<Player> p){
      board = b;
      rules = r;
      players = p;
      // hard code for now
      deck = new Deck();
      currentPlayerIndex = -1;
      moves = null;
      nextTurn();
   }
   
   // Mutates state to get next turn
   public void nextTurn(){
      currentPlayerIndex++;
      currentPlayerIndex = currentPlayerIndex % players.size();
      moves = new int[] {-1, 1, 10, -10}; // Replace with call to Deck
      //moves = new int[] {1}; // Replace with call to Deck
      //moves = new int[] {deck.drawCard().getRank()}; // change to use ruleset
      System.out.println(getCurrentPlayer().getColor().toString() + 
                                       "PLAYER'S TURN\n============================");
      System.out.println("They drew a " + Integer.toString(moves[0]));
      if (getMoveablePawns().size() == 0){
         System.out.println("They cannot move.");
         nextTurn();
      }

   }
   
   public Player getCurrentPlayer(){
      return players.get(currentPlayerIndex);
   }
   
   public Board getBoard(){
   
      return board;
   
   }// end of getBoard()
   
   public Deck getDeck(){
      return deck;
   }
   
   public Ruleset getRules(){
      return rules;
   }
   
   public void setMoves(int[] moves){
      this.moves = moves;
   }
   
   public int[] getMoves(){
      return moves;
   }// end of getMoves()

   public ArrayList<Pawn> getPawns(){
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      ArrayList<Token> tokens = board.getTokens();
      for (int i = 0; i < tokens.size(); i++){
         if (tokens.get(i) instanceof Pawn) // check type before casting
            result.add((Pawn)tokens.get(i)); 
      }
      return result; 
   }
   
   
   //REFACTOR FOR PRETTY WITH PREV.
   public ArrayList<Pawn> getPawns(Color c){
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      ArrayList<Token> tokens = board.getTokens();
      Pawn currentPawn;
      for (int i = 0; i < tokens.size(); i++){
         if (tokens.get(i) instanceof Pawn){ // check type before casting
            currentPawn = (Pawn)tokens.get(i);
            if (currentPawn.getColor().toString().equals(c.toString())) // check color before adding
               result.add(currentPawn);
         } 
      }
      return result; 
   }
   
   public ArrayList<Token> getTokens(){
      return board.getTokens();
   }
   
   public ArrayList<Pawn> getMoveablePawns(){
      Color c = players.get(currentPlayerIndex).getColor();
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      ArrayList<Pawn> candidates = getPawns(c);
      ArrayList<Space> s = new ArrayList<Space>();
      for (int i = 0; i < candidates.size(); i++){
         if (moves != null){
            for (int j = 0; j < moves.length; j++){
               s.addAll(rules.getTargets(candidates.get(i), candidates.get(i).whereAmI(), moves[j]));
            }
         }
         if (s.size() > 0)
            result.add(candidates.get(i));
         s = new ArrayList<Space>();
      }

      return result;
   
   }
   
   
   public ArrayList<Space> getAllTargets(Pawn p){
      ArrayList<Space> result = new ArrayList<Space>();
      for (int i = 0; i < moves.length; i++){
         result.addAll(rules.getTargets(p, p.whereAmI(), moves[i]));
      } 
      
      return result;
         
   }



}//end of class