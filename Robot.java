import java.util.ArrayList;
import javax.swing.JPanel;

public class Robot {
	private String name;
	
	private String gameState;
	private Hand hand;
	private Deck deck;
	private Discard discard;
	private JPanel gamePanel;
	
	private Card deckCardPulled;
	private Card discardCardPulled;
	
	private boolean doneWithTurn;
	
	public Robot(String n) {
		name = n;
		gameState = "Normal";
		doneWithTurn = false;
	}
	
	public void setDoneWithTurn(boolean d) {
		doneWithTurn = d;
	}
	
	public boolean getDoneWithTurn() {
		return doneWithTurn;
	}
	
	public String getGameState() {
		return gameState;
	}
	
	public void setGameState(String g) {
		gameState = g;
	}
	
	public void updateInfo(String g, Hand h, Deck de, Discard di, JPanel j) {
		gameState = g;
		hand = h;
		deck = de;
		discard = di;
		gamePanel = j;
	}
	
	public void makeDecision() {
		int deckSize = deck.getDeck().size();
	    int discardSize = discard.getPile().size();
	    Card deckCard = null;
	    Card discardCard = null;
	    try {
	        deckCard = deck.getDeck().get(deckSize-1);
	        discardCard = discard.getPile().get(discardSize-1);
	    } catch (Exception e) {}
		
			if (gameState.equals("Normal")) {
				System.out.println("Normal");
				if (discardCard != null && discardCard.getNum() <= 2) { // Auto pull from discard
					gameState = "Discard";
		    		Card cardPulled = discard.takeCard();
		    		cardPulled.setCoords(cardPulled.getX(), cardPulled.getY() + cardPulled.getHeight() + 5);
		    		discardCardPulled = cardPulled;
		    		gamePanel.repaint();
				} else if (discardCard != null && discardCard.getNum() <= 6) {
					int chance = (int)(Math.random() * 11) + 1;
					if (chance >= 3) { // Pull from the deck
						gameState = "Deck";
			    		Card cardPulled = deck.getCards(1).get(0);
			    		cardPulled.setCoords(deckCard.getX(), deckCard.getY()+deckCard.getHeight()+5);
			    		deckCardPulled = cardPulled;
					} else if (discardCard != null) { // Pull from the discard
						gameState = "Discard";
			    		Card cardPulled = discard.takeCard();
			    		cardPulled.setCoords(cardPulled.getX(), cardPulled.getY() + cardPulled.getHeight() + 5);
			    		discardCardPulled = cardPulled;
					}
				} else { // Auto pull from deck
					gameState = "Deck";
		    		Card cardPulled = deck.getCards(1).get(0);
		    		cardPulled.setCoords(deckCard.getX(), deckCard.getY()+deckCard.getHeight()+5);
		    		deckCardPulled = cardPulled;
				}
			} else if (gameState.equals("Deck")) {
				System.out.println("Deck");
				int highestCardIndex = 0;
				int highestCard = -2;
				
				for (int i = 0; i < hand.getHand().size(); i++) { // Find the highest card inside robots hand, only ones that are turned up
					if (hand.getHand().get(i).getNum() > highestCard && !hand.getHand().get(i).isOnBack()) {
						highestCard = hand.getHand().get(i).getNum();
						highestCardIndex = i;
					}
				}
				
				if (deckCardPulled.getNum() < highestCard) { // Deck card is less than the highest
					if ((highestCard >= 7 && highestCard <= 9 && deckCardPulled.getNum() <= 4) || 
							(highestCard <= 5 && highestCard >= 2 && deckCardPulled.getNum() <= 0) || (deckCardPulled.getNum() >= 4 && highestCard >= 10)) {
						hand.replaceCard(hand.getHand().get(highestCardIndex), deckCardPulled);
	    				discard.addCard(hand.getHand().get(highestCardIndex));
	    				gameState = "Normal";
	    				deckCardPulled = null;
	    				doneWithTurn = true;
					} else if (deckCardPulled.getNum() >= 5) {
						deckCardPulled.setCoords(discardCard.getX(), discardCard.getY());
			    		discard.addCard(deckCardPulled);
			    		gameState = "FlipCard";
					} else { // Pick a random card to flip
						ArrayList<Integer> indexes = new ArrayList<Integer>();
						for (int i = 0; i < hand.getHand().size(); i++) {
							Card c = hand.getHand().get(i);
							
							if (c.isOnBack()) {
								indexes.add(i);
							}
						}
						int num = (int)(Math.random() * indexes.size());
						Card cardToReplace = hand.getHand().get(indexes.get(num));
						
						hand.replaceCard(cardToReplace, deckCardPulled);
	    				discard.addCard(cardToReplace);
	    				gameState = "Normal";
	    				doneWithTurn = true;
	    				deckCardPulled = null;
					}
				} else if (deckCardPulled.getNum() >= highestCard) {
					if (highestCard <= 3 && deckCardPulled.getNum() <= 5) {
						ArrayList<Integer> indexes = new ArrayList<Integer>();
						for (int i = 0; i < hand.getHand().size(); i++) {
							Card c = hand.getHand().get(i);
							
							if (c.isOnBack()) {
								indexes.add(i);
							}
						}
						int num = (int)(Math.random() * indexes.size());
						Card cardToReplace = hand.getHand().get(indexes.get(num));
						
						hand.replaceCard(cardToReplace, deckCardPulled);
	    				discard.addCard(cardToReplace);
	    				gameState = "Normal";
	    				doneWithTurn = true;
	    				deckCardPulled = null;
					} else {
						deckCardPulled.setCoords(discardCard.getX(), discardCard.getY());
			    		discard.addCard(deckCardPulled);
			    		gameState = "FlipCard";
					}
				}
			} else if (gameState.equals("FlipCard")) {
				System.out.println("FlipCard");
				ArrayList<Integer> indexes = new ArrayList<Integer>();
				for (int i = 0; i < hand.getHand().size(); i++) {
					Card c = hand.getHand().get(i);
					
					if (c.isOnBack()) {
						indexes.add(i);
					}
				}
				int num = (int)(Math.random() * indexes.size());
				Card cardToFlip = hand.getHand().get(indexes.get(num));
				
				cardToFlip.flipCard();
				doneWithTurn = true;
			} else if (gameState.equals("Discard")) {
				System.out.println("Discard");
				int highestCardIndex = 0;
				int highestCard = -2;
				
				for (int i = 0; i < hand.getHand().size(); i++) { // Find the highest card inside robots hand, only ones that are turned up
					if (hand.getHand().get(i).getNum() > highestCard && !hand.getHand().get(i).isOnBack()) {
						highestCard = hand.getHand().get(i).getNum();
						highestCardIndex = i;
					}
				}
				
				if (discardCardPulled.getNum() < highestCard) { // Deck card is less than the highest
					if ((highestCard >= 7 && highestCard <= 9 && discardCardPulled.getNum() <= 4) || 
							(highestCard <= 5 && highestCard >= 2 && discardCardPulled.getNum() <= 0) || (discardCardPulled.getNum() >= 4 && highestCard >= 10)) {
						hand.replaceCard(hand.getHand().get(highestCardIndex), discardCardPulled);
	    				discard.addCard(hand.getHand().get(highestCardIndex));
	    				gameState = "Normal";
	    				doneWithTurn = true;
	    				discardCardPulled = null;
					} else { // Pick a random card to flip
						ArrayList<Integer> indexes = new ArrayList<Integer>();
						for (int i = 0; i < hand.getHand().size(); i++) {
							Card c = hand.getHand().get(i);
							
							if (c.isOnBack()) {
								indexes.add(i);
							}
						}
						int num = (int)(Math.random() * indexes.size());
						Card cardToReplace = hand.getHand().get(indexes.get(num));
						
						hand.replaceCard(cardToReplace, discardCardPulled);
	    				discard.addCard(cardToReplace);
	    				gameState = "Normal";
	    				doneWithTurn = true;
	    				discardCardPulled = null;
					}
				} else if (discardCardPulled.getNum() >= highestCard) {
					if (highestCard <= 3 && discardCardPulled.getNum() <= 5) {
						ArrayList<Integer> indexes = new ArrayList<Integer>();
						for (int i = 0; i < hand.getHand().size(); i++) {
							Card c = hand.getHand().get(i);
							
							if (c.isOnBack()) {
								indexes.add(i);
							}
						}
						int num = (int)(Math.random() * indexes.size());
						Card cardToReplace = hand.getHand().get(indexes.get(num));
						
						hand.replaceCard(cardToReplace, discardCardPulled);
	    				discard.addCard(cardToReplace);
	    				gameState = "Normal";
	    				doneWithTurn = true;
	    				discardCardPulled = null;
					} else {
						ArrayList<Integer> indexes = new ArrayList<Integer>();
						for (int i = 0; i < hand.getHand().size(); i++) {
							Card c = hand.getHand().get(i);
							
							if (c.isOnBack()) {
								indexes.add(i);
							}
						}
						int num = (int)(Math.random() * indexes.size());
						Card cardToReplace = hand.getHand().get(indexes.get(num));
						
						hand.replaceCard(cardToReplace, discardCardPulled);
	    				discard.addCard(cardToReplace);
	    				gameState = "Normal";
	    				doneWithTurn = true;
	    				discardCardPulled = null;
				}
			}
		}
	}
}
