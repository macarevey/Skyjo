import java.util.ArrayList;

public class Discard {
	private ArrayList<Card> discardPile;
	
	public Discard(Card c) {
		discardPile = new ArrayList<Card>();
		discardPile.add(c);
	}
	
	public Card takeCard() {
		Card c = discardPile.get(discardPile.size() - 1);
		discardPile.remove(discardPile.size() - 1);
		return c;
	}
	
	public ArrayList<Card> getPile() {
		return discardPile;
	}
}
