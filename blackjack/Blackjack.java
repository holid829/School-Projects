/* David Fan CMSC 132
 * Blackjack
 * A card game where you try to draw a high number combination below 21
 */


package blackjack;
import java.util.*;
import java.util.Collections;

public class Blackjack implements BlackjackEngine {
	
	private int account;
	private int betAmount;
	private int deckNum;
	private Random rGen;
	private ArrayList<Card> deck;
	private ArrayList<Card> player;
	private ArrayList<Card> dealer;
	private int gameStat;
	
	/**
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	
	
	public Blackjack(Random randomGenerator, int numberOfDecks) {
		account = 200;
		betAmount = 5;
		deckNum = numberOfDecks;
		rGen = randomGenerator;
		newHands();
	}
	
	public int getNumberOfDecks() {
		return deckNum;
	}
	
	//Creates the game deck
	public void createAndShuffleGameDeck() {
		//Numbers and suits hold all of the card values and suits, and are used to create cards later
		ArrayList<CardValue> numbers = new ArrayList<CardValue>();
		ArrayList<CardSuit> suits = new ArrayList<CardSuit>();
		
		numbers.add(CardValue.Ace);
		numbers.add(CardValue.Two);
		numbers.add(CardValue.Three);
		numbers.add(CardValue.Four);
		numbers.add(CardValue.Five);
		numbers.add(CardValue.Six);
		numbers.add(CardValue.Seven);
		numbers.add(CardValue.Eight);
		numbers.add(CardValue.Nine);
		numbers.add(CardValue.Ten);
		numbers.add(CardValue.Jack);
		numbers.add(CardValue.Queen);
		numbers.add(CardValue.King);
	
		
		suits.add(CardSuit.SPADES);
		suits.add(CardSuit.DIAMONDS);	
		suits.add(CardSuit.HEARTS);		
		suits.add(CardSuit.CLUBS);
		

		
		for(int i = 0; i < deckNum; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 13; k++) {
					deck.add(new Card(numbers.get(k),suits.get(j)));				
				}
			}
		}
		Collections.shuffle(deck,rGen);
	}
	
	public Card[] getGameDeck() {
		return convertArray(deck);
	}
	
	//Shuffles the deck and deals cards
	public void deal() {	

		newHands();
		
		account = account - betAmount;
		
		 
		 createAndShuffleGameDeck();	 
		 gameStat = 8;
		 
		//Adds cards to each players hands
		 for(int i = 0; i < 2; i++) {
			 player.add(deck.remove(0));
			 dealer.add(deck.remove(0));
		 }
		 dealer.get(0).setFaceDown();

	}
		
	public Card[] getDealerCards() {
		return  convertArray(dealer);
	}

	//Gets all possible values of the dealer's cards
	public int[] getDealerCardsTotal() {
		return calculateTotals(dealer);
	}

	//Gets the outcome of the dealer's card
	public int getDealerCardsEvaluation() {
		int[]vals = getDealerCardsTotal();
		return evaluateTotals(vals,dealer.size());
	}
	
	
	public Card[] getPlayerCards() {
		return convertArray(player);
	}
	
	//Gets all possible values of the player's cards
	public int[] getPlayerCardsTotal() {
		return calculateTotals(player);
	}
		
	//Gets the outcome of the player's card
	public int getPlayerCardsEvaluation() {
		int[]vals = getPlayerCardsTotal();
		return evaluateTotals(vals,player.size());
	}
	
	//Adds a card to the players hand
	public void playerHit() {
		player.add(deck.remove(0));
		if(getPlayerCardsEvaluation() == 3) 
		{
			dealer.get(0).setFaceUp();
			gameStat = 6;
		}
	}
	
	//Ends the game
	public void playerStand() {
		
		//pulls cards for the dealer
		dealer.get(0).setFaceUp();
		
		//Draws cards for the dealer until he hits at least 16 or busts
		
			
		int[]cards = getDealerCardsTotal();
		while(cards.length!=0 && cards[cards.length - 1] < 16) {		
			dealer.add(deck.remove(0));		
			cards = getDealerCardsTotal();
		}
		
		
		int dealerState = getDealerCardsEvaluation();
		//Compares the cards of the dealer and player and checks for busts
		if(dealerState == 3) {
			account += betAmount * 2;
			gameStat = 7;
			return;
		}
		
		
		
		int[] playerVals = getPlayerCardsTotal();
		int playerVal = playerVals[playerVals.length-1];
		int dealerVal = cards[cards.length - 1];
		
		if(getPlayerCardsEvaluation() == 4) 
			playerVal = 22;
		if(dealerState == 4)
			dealerVal = 22;

		
		if(dealerVal > playerVal)
			gameStat = 6;
		else if(dealerVal == playerVal) {
			gameStat = 1;
			account += betAmount;
		}
		
		else {
			gameStat = 7;
			account += betAmount * 2;
		}	
	}
	
	public int getGameStatus() {
		return gameStat;
	}
		
	public void setBetAmount(int amount) {
		betAmount = amount;
	}
	
	public int getBetAmount() {
		return betAmount;
	}
	
	public void setAccountAmount(int amount) {	
		account = amount;
	}
	
	public int getAccountAmount() {
		return account;
	}
	
	/* Feel Free to add any private methods you might need */
	
	

	
	
	
	//Calculates the totals for both player and dealer
	private int[] calculateTotals(ArrayList<Card> cardList){
		int[] cards = new int[1];
		cards[0] = 0;
		
		//This variable keeps track of if there is a one
		boolean passedOne = false;
		
		for(int i =0; i < cardList.size(); i ++) {
			int currentCard = cardList.get(i).getValue().getIntValue();
			cards[0] += currentCard;
			if(!passedOne && currentCard == 1)
				passedOne = true;
		}
		
		if(cards[0]>21)
			return new int[0];
		if(!passedOne || cards[0] + 10 > 21)
			return cards;
		
		//The seconds possible value is always the first value + 10
		int[] finCards = {cards[0],cards[0] + 10};
			
		return finCards;
    }
	
	//evaluates totals for both player and dealer
	private int evaluateTotals(int[]vals, int size){
		int count = vals.length - 1;
		while(count > -1 && vals[count] > 21) 
			count--;
		
		//if count is -1 then that means all of the possible values will bust
		if(count == -1) 
			return 3;
			
		else if(vals[count] == 21) {
			//If there's only two cards in the hand and it reaches 21, then it must be a blackjack
			if(size == 2) 
				return 4;
			
			return 5;
		}
		return 2;
		
	
	}
	
	//Creates and resets hands and decks
	private void newHands() {
		deck = new ArrayList<Card>();
		player = new ArrayList<Card>();
		dealer = new ArrayList<Card>();
	}
	
	//Turns a arraylist into a array
	private Card[] convertArray(ArrayList<Card> toConv) {
		Card[] toRet =new Card[toConv.size()];
		for(int i = 0; i < toConv.size(); i ++)
			toRet[i] = toConv.get(i);
		return toRet;
	}
	
}