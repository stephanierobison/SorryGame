import java.util.*;

/**
 * CARD DECK (edited to fit Sorry! criteria from a previous function for cs 110)
 * Programmer: Stephanie Robison
 */

public class Deck {
    public static final int[] CARD_RANK = {0, 1, 2, 3, 4, 5, 7, 8, 10, 11, 12};
    public final int DECK_SIZE = 44;

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

    /**
	* Using an iterator, returns the topcard whilst also removing it from the deck.  Will recursively call makeDeck is list empty.
     * Implements a ListIterator to return the top card from the deck List, and removes the returned card from the deck.
     */
    public Card getCard() {
        ListIterator<Card> deckIterator = deck.listIterator();
        
	if (deckIterator.hasNext()){
            topCard = deckIterator.next();
            deckIterator.remove();
        }//if
        
		else{
            this.createDeck();
            topCard = this.getCard();
        }//else
        return topCard;
    }//getCard

   
	
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
		private int theRank:
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
		public String getDirections() {
			theRank = getRank();
			
			if (theRank == 0){
				direction = "Move any one pawn from Start to a square occupied by any opponent, " +
					"sending that pawn back to its own Start. If there are no pawns on the player's "+
					"Start, or no opponent's pawns on any squares, the turn is lost. "+
					"If an enemy's pawn is swapped while it is in front of your HOME, "+
					"your pawn is switched EXACTLY where your enemy's pawn is, not at your HOME."								
			if( theRank == 1){
				direction = "Move a pawn from Start or move a pawn 1 space forward.";
			}
			if( theRank == 2){
				direction = "Move a pawn from Start or move a pawn 2 spaces forward.";
			}
			if( theRank == 3){
				direction = "Move a pawn 3 spaces forward.";
			}
			if(theRank == 4){
				direction = "Move a pawn 4 spaces backward.";
			}
			if(theRank == 5){
				direction = "Move a pawn 5 spaces forward.";
			}
			if(theRank == 7){
				direction = "Move one pawn 7 spaces forward or split the 7 spaces between two pawns "+
					"(such as four spaces for one pawn and three for another). This makes it possible "+
					"for two pawns to enter Home on the same turn, for example. "+
					"The 7 cannot be split into a 6 and 1 or a 5 and 2 for the purposes of "+
					"moving out of Start. The entire seven spaces must be used one way or the other or the turn is lost."
			}
			if (theRank == 8){
				direction = "Move a pawn 8 spaces forward.";
			}
			
			if(theRank == 10){
				direction = "Move a pawn 10 spaces forward or 1 space backward. If a player cannot go forward ten"
			}
			
			if(theRank == 11){
				direction = "Move 11 spaces forward or switch places with one opposing pawn. "+
					"A player that cannot move 11 spaces is not forced to switch and instead can "+
					"forfeit the turn."
			}
			if(theRank == 12){
				direction = "Move a pawn 12 spaces forward.";
			}

		return direction;
				
			
		}
	        
		public String toString() {
			String cardString = "";
	    	return Integer.toString(this.rank);
		}//toString
	}//CARD
}//DECK
