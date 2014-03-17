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
}//DECK
