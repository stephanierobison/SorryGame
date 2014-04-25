import java.util.*;
import java.awt.*;

/**
   Model for Game. It contains:
      
      - The Board & its Spaces
      - The card deck
      - the rules
      - the Players
      - the current Player
      
   As turns transpire each of these may mutate in some way.
   This object must handle those mutations in a manner accessible
   to the function owning it (Sorry Game) and to the GameController
   class.
*/
public class Game{
//*************************************************************************
// INSTANCE VARIABLE
//*************************************************************************
   /** represents the board itself as a clickable GUI*/
   private Board board;
   /** The deck, our random number generator*/   
   private Deck deck;
      private int currentCard;
   /** The Rules governing this game */
   private Ruleset rules;
   /** the list of players */
   private ArrayList<Player> players;
      /** a flag indicating the current player */
      private int currentPlayerIndex;
   
   private int[] moves; // will change turn by turn, might be null, decided by controller
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************   
   /** Game requires references to its core objects */
   public Game(Board b, Ruleset r, ArrayList<Player> p){
      board = b;
      rules = r;
      players = p;
      deck = new Deck();
      currentPlayerIndex = -1;
      moves = null;
      nextTurn(); // set up the first turn, will restore counter to range
   }
//*************************************************************************
// METHODS
//*************************************************************************   
   // Mutates state to get next turn
   public void nextTurn(){
      currentPlayerIndex++;
      currentPlayerIndex = currentPlayerIndex % players.size();
      
      currentCard = deck.draw();
      moves = Deck.CARD_VALUES[currentCard]; 
      // TEST MOVESET TO OVERIDDE RANDOM DECK
      //moves = new int[] {1, -1, -11, 11, Ruleset.ELEVEN_SWAP, Ruleset.START_OUT}; // Replace with call to Deck
      //moves = new int[] {Ruleset.SEVEN, Ruleset.START_OUT}; // Replace with call to Deck
      //moves = new int[] {-4, -1, 1, 2, 5, 10, 20, Ruleset.START_OUT};
      
   }// end of nextTurn
//-------------------------------------------------------------------------   
   /**
      @return Color of winning player if someone has won the game, 
              null otherwise
   */
   public Color winner(){
      Color result = null;
      Color currentColor;
      boolean didWin;
      ArrayList<Pawn> currentPawns;
      for (int i = 0; i < players.size(); i++){    // For each player...
         currentColor = players.get(i).getColor();
         currentPawns = board.getPawns(currentColor);
         didWin = true;
         for (int j = 0; j < currentPawns.size(); j++){  // ... are ALL of their pawns home?
            didWin = didWin && currentPawns.get(j).whereAmI().isAHome();
         }
         if (didWin){
            result = currentColor;
            System.out.println("WE HAVE A WINNER! - Color: " + currentColor.toString());
         }
      }
      return result;
   }
//-------------------------------------------------------------------------   
   /**
      @return true if ANY of the current players Pawns have a legal move
              false otherwise
   */
   public boolean canCurrentPlayerMove(){
      boolean result = false;
         if (getMoveablePawns().size() > 0)
            result = true;
      return result;   
   }
//-------------------------------------------------------------------------   
   /**
      @return true if current player is not an AI, false otherwise
   */
   public boolean isCurrentPlayerHuman(){
      return getCurrentPlayer().isHuman();
   }

//-------------------------------------------------------------------------   
   /**
      @return true if p is the current player, false otherwise
   */
   public boolean isCurrentPlayer(Player p){
      boolean result = false;
      if (p == players.get(currentPlayerIndex))
         result = true;
      
      return result;
   }
//-------------------------------------------------------------------------   
   /**
      @return all Pawns on the board
   */   
   public ArrayList<Pawn> getPawns(){
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      ArrayList<Token> tokens = board.getTokens();
      for (int i = 0; i < tokens.size(); i++){ // linear scan of token result
         if (tokens.get(i) instanceof Pawn) // check type before casting
            result.add((Pawn)tokens.get(i)); 
      }
      return result; 
   }
//-------------------------------------------------------------------------   
   /**
      @param Color c the Color of a player
      @return all pawns of color c on the board
   */
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
//-------------------------------------------------------------------------   
   /**
      @return the result of Board's getToken
   */
   public ArrayList<Token> getTokens(){
      return board.getTokens();
   }


//-------------------------------------------------------------------------   
   /**
      @return a list of all Pawns which can move this turn
   */
   public ArrayList<Pawn> getMoveablePawns(){
      Color c = players.get(currentPlayerIndex).getColor();
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      ArrayList<Pawn> candidates = getPawns(c);    // get all pawns of the current player's color
      ArrayList<Move> s = new ArrayList<Move>();
      for (int i = 0; i < candidates.size(); i++){  
         if (moves != null){
            for (int j = 0; j < moves.length; j++){
               s.addAll(rules.getTargets(board, candidates.get(i), candidates.get(i).whereAmI(), moves[j])); //add all space candidate can reach
            }
         }
         if (s.size() > 0) // If the candidate CAN move add them to the final result list
            result.add(candidates.get(i));
         s = new ArrayList<Move>();
      }

      return result;
   
   }
   
 //-------------------------------------------------------------------------   
   /**
      @param Pawn p
      @return A list of moves this pawn can take
   */ 
   public ArrayList<Move> getAllTargets(Pawn p){
      ArrayList<Move> result = new ArrayList<Move>();
      for (int i = 0; i < moves.length; i++){
         result.addAll(rules.getTargets(board, p, p.whereAmI(), moves[i]));// Automatically add valid moves as we go
      } 
      
      return result;
         
   }
//*************************************************************************
// BASIC SETTERS/GETTERS
//*************************************************************************
   public Board getBoard(){
      return board;
   }// end of getBoard()
//-------------------------------------------------------------------------    
   public Deck getDeck(){
      return deck;
   }
//-------------------------------------------------------------------------    
   public Ruleset getRules(){
      return rules;
   }
//-------------------------------------------------------------------------     
   public void setMoves(int[] moves){
      this.moves = moves;
   }
//-------------------------------------------------------------------------     
   public int[] getMoves(){
      return moves;
   }// end of getMoves()
//-------------------------------------------------------------------------   
  
   public int getCurrentCard(){
      return currentCard;
   }
//-------------------------------------------------------------------------   
   
   public Player getCurrentPlayer(){
      return players.get(currentPlayerIndex);
   }
//-------------------------------------------------------------------------   
   
   public ArrayList<Player> getPlayers(){
      return players;
   }   
//-------------------------------------------------------------------------   
   
}//end of class