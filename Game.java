import java.util.*;
import java.awt.*;

/**
   Model for Game
*/
public class Game{

   private Board board;
   private Deck deck;
      private int currentCard;
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
      nextTurn(); // set up the first turn
   }
   
   // Mutates state to get next turn
   public void nextTurn(){
      rules.incTurns();
      currentPlayerIndex++;
      currentPlayerIndex = currentPlayerIndex % players.size();
      
      currentCard = deck.draw();
      moves = Deck.CARD_VALUES[currentCard]; // !!!!!!!!!!!!!! REPLACE !!!!!!!!!!!!!!!!!!
      //moves = new int[] {1, -1, -11, 11, Ruleset.ELEVEN_SWAP, Ruleset.START_OUT}; // Replace with call to Deck
//      moves = new int[] {Ruleset.SEVEN, Ruleset.START_OUT}; // Replace with call to Deck
      // moves = new int[] {-4, -1, 1, 2, 5, 10, 20, Ruleset.START_OUT};
      /*
      System.out.println(getCurrentPlayer().getColor().toString() + 
                                       "PLAYER'S TURN\n============================");
      System.out.println("They drew a " + Integer.toString(currentCard));
      if (getMoveablePawns().size() == 0){
         System.out.println("They cannot move.");
         nextTurn();          // MOVE TO GAME CONTROLLER!!!!
      }*/

   }
   
   public Color winner(){
      Color result = null;
      Color currentColor;
      boolean didWin;
      ArrayList<Pawn> currentPawns;
      for (int i = 0; i < players.size(); i++){
         currentColor = players.get(i).getColor();
         currentPawns = board.getPawns(currentColor);
         didWin = true;
         for (int j = 0; j < currentPawns.size(); j++){
            didWin = didWin && currentPawns.get(j).whereAmI().isAHome();
         }
         if (didWin){
            result = currentColor;
            System.out.println("WE HAVE A WINNER! - Color: " + currentColor.toString());
         }
      }
      return result;
   }
   
   public boolean canCurrentPlayerMove(){
      boolean result = false;
         if (getMoveablePawns().size() > 0)
            result = true;
      return result;   
   }
   
   public boolean isCurrentPlayerHuman(){
      return getCurrentPlayer().isHuman();
   }
   
   public int getCurrentCard(){
      return currentCard;
   }
   
   public Player getCurrentPlayer(){
      return players.get(currentPlayerIndex);
   }
   
   public ArrayList<Player> getPlayers(){
      return players;
   }
   
   public boolean isCurrentPlayer(Player p){
      boolean result = false;
      if (p == players.get(currentPlayerIndex))
         result = true;
      
      return result;
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
      ArrayList<Move> s = new ArrayList<Move>();
      for (int i = 0; i < candidates.size(); i++){
         if (moves != null){
            for (int j = 0; j < moves.length; j++){
               s.addAll(rules.getTargets(board, candidates.get(i), candidates.get(i).whereAmI(), moves[j]));
            }
         }
         if (s.size() > 0)
            result.add(candidates.get(i));
         s = new ArrayList<Move>();
      }

      return result;
   
   }
   
   
   public ArrayList<Move> getAllTargets(Pawn p){
      ArrayList<Move> result = new ArrayList<Move>();
      for (int i = 0; i < moves.length; i++){
         result.addAll(rules.getTargets(board, p, p.whereAmI(), moves[i]));
      } 
      
      return result;
         
   }


}//end of class