import java.util.*;

/**
 * CARD DECK (edited to fit Sorry! criteria from a previous function for cs 110)
 * Programmer: Stephanie Robison
 */

public class Deck {
    public static final int[] CARD_RANK = {0, 1, 2, 3, 4, 5, 7, 8, 10, 11, 12}; // 0 is SORRY
    public final int DECK_SIZE = 44;
    
    public static final int[][] CARD_VALUES = 
                                   {{Ruleset.SORRY_SWAP},   // 0 
                                   {Ruleset.START_OUT, 1},  // 1
                                   {Ruleset.START_OUT, 2},  // 2
                                   {3},                     // 3
                                   {-4},                    // 4
                                   {5},                     // 5
                                   {},                      // 6 (no moves)
                                   {Ruleset.SEVEN},         // SEVEN IS SPECIAL
                                   {8},                     // 8
                                   {},                      // 9 (no moves)
                                   {-1,10},                 // 10
                                   {Ruleset.ELEVEN_SWAP, 11},// 11
                                   {12}                     // 12
                                  };
        public static final String[] CARD_TEXT = {
            "SORRY! Can swap a pawn in your start with an enemy pawn!",
            "ONE. Can either start a pawn out of start or move a pawn forwards one space.", 
            "TWO. Can either start a pawn out of start or move a pawn forwards two spaces.",
            "THREE. Can move a pawn forwards three spaces.",
            "FOUR. Can move a pawn backwards four spaces.", 
            "FIVE. Can move a pawn forwards five spaces.",
            "SIX. ERROR! THERE IS NO SIX CARD!",
            "SEVEN. Can split seven forwards moves between two pawns. Be aware that ALL the" + 
            " moves must ve legally consumed using just the two pawns.",
            "EIGHT. Can move a pawn forwards eight spaces.",
            "NINE. ERROR! THERE IS NO NINE CARD!",
            "TEN. Can move a pawn forwards ten spaces OR back one space.",
            "ELEVEN. Can move a pawn forwards eleven spaces OR swap one of your pawns on the " +
            "track with an enemy pawn on the track.",
            "TWELVE. Can move a pawn forwards twelve spaces."};

    List<Card> deck;
    Card topCard;

    public Deck() {
        createDeck();
    }//Deck

    private void createDeck() {
        deck = new ArrayList<Card>(DECK_SIZE);
        for (int i = 0; i < 4; i++){
            for (int card : CARD_RANK) {
                    deck.add(new Card(card));
            }//for
        }//for
        ((ArrayList) deck).trimToSize();
        shuffle();
    }//createDeck


    public int draw(){
      Card c = drawCard();
      return c.getRank();
    }  
      
    /**
	* Using an iterator, returns the topcard whilst also removing it from the deck.  Will recursively call makeDeck is list empty.
     * Implements a ListIterator to return the top card from the deck List, and removes the returned card from the deck.
     */
    private Card drawCard() {
        ListIterator<Card> deckIterator = deck.listIterator();
        
	if (deckIterator.hasNext()){
            topCard = deckIterator.next();
            deckIterator.remove();
        }//if
        
		else{
            this.createDeck();
            topCard = this.drawCard();
        }//else
        return topCard;
    }//drawCard

   
	
	/**
	* makes a string of the deck by appending each next card.
	*/
	public String toString() {
		StringBuilder deckString = new StringBuilder();
		for (Card theDeck : deck) {
	            String nextCard = theDeck.toString();
	            deckString.append(nextCard).append(" ");
	        }//for
	    return deckString.toString();
	}//toString


		 /**
	     * Uses random number generator to move through the deck and shuffle it.
	     */
	    public void shuffle() {
	        Random random = new Random();
	        for (int i = deck.size(); i > 0; i--) {
	            int movedCardIndex = random.nextInt(i);
	            Card card = deck.get(movedCardIndex);
	            deck.add((i), card);
	            deck.remove(movedCardIndex);
	        }//for
	    }// shuffle

/**
 * CARD (edited to fit Sorry! criteria from a previous function for cs 110)
 * Programmer: Stephanie Robison
 */           
	public static class Card {
	   	private int rank;
		private int theRank;
		private String direction;

		public Card(Integer rank) {
	    		setRank(rank);
		}//Card

		public void setRank(int rank) {
			this.rank = rank;
	    	}//setRank

		public int getRank() {
			return rank;
	    	}//getRank
  
		public String toString() {
			String cardString = "";
	    	return Integer.toString(this.rank);
		}//toString
	}//CARD
   
   
   //TEST
   public static void main(String [ ] args){
      Deck d = new Deck();
      System.out.println(d.toString());
   }
   
   
}//DECK
