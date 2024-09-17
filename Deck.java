import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;
	private int x,y;
	
	public Deck() {
		deck = new ArrayList<Card>();
		for (int i = -2; i <= 12; i++) {
			if (i == -2) {
				for (int j = 0; j < 5; j++) {
					deck.add(new Card(i));
				}
			} else if (i == 0) {
				for (int j = 0; j < 15; j++) {
					deck.add(new Card(i));
				}
			} else {
				for (int j = 0; j < 10; j++) {
					deck.add(new Card(i));
				}
			}
		}
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Card> getCards(int amt) {
		ArrayList<Card> cards = new ArrayList<Card>();
		int first = deck.size() - amt - 1;
		int last = deck.size() - 1;
		
		for (int i = last; i > first; i--) { // add to return list
			cards.add(deck.get(i));
		}
		
		for (int i = last; i > first; i--) { // remove cards taken
			deck.remove(i);
		}
		
		return cards;
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	public String toString() {
		String str = deck.get(0).getNum() + " ";
		for (int i = 1; i < deck.size(); i++) {
			if (deck.get(i).getNum() != deck.get(i-1).getNum()) {
				str += ("\n");
			}
			str += (deck.get(i).getNum() + " ");
		}
		return str;
	}
	
	public void calculateCoords(int frameWidth, int frameHeight, double xCenter, double yCenter) {
		double cardWidth = frameWidth / 25.0;
        double cardHeight = frameHeight / 10.0;
        double cardSpacingX = cardWidth * 0.2; // 20%
        double cardSpacingY = cardHeight * 0.2;
        
        x = (int)(xCenter - cardSpacingX - cardWidth);
        y = (int)(yCenter - cardHeight / 2);
        for (Card c : deck) {
        	c.setWidth((int)cardWidth);
        	c.setHeight((int)cardHeight);
        	c.setCoords(x, y);
        }
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void addCard(Card c) {
		deck.add(c);
	}
	
	public void flipCards() {
		for (Card c : deck) {
			if (!c.isOnBack()) {
				c.flipCard();
			}
		}
	}
}
