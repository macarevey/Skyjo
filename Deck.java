import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;
	
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
		for (int i = 0; i < amt; i++) { // add to return list
			cards.add(deck.get(i));
		}
		
		for (int i = amt-1; i > -1; i--) { // remove cards taken
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
}
