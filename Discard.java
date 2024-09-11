import java.util.ArrayList;

public class Discard {
	private ArrayList<Card> discardPile;
	private int x,y;
	
	public Discard(Card c) {
		c.flipCard();
		discardPile = new ArrayList<Card>();
		discardPile.add(c);
	}
	
	public Card takeCard() {
		Card c = discardPile.get(discardPile.size() - 1);
		discardPile.remove(discardPile.size() - 1);
		return c;
	}
	
	public void addCard(Card c) {
		if (c.isOnBack()) {
			c.flipCard();
		}
		c.setCoords(x, y);
		discardPile.add(c);
	}
	
	public ArrayList<Card> getPile() {
		return discardPile;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void calculateCoords(int frameWidth, int frameHeight, double xCenter, double yCenter) {
		double cardWidth = frameWidth / 25.0;
        double cardHeight = frameHeight / 10.0;
        double cardSpacingX = cardWidth * 0.2; // 20%
        double cardSpacingY = cardHeight * 0.2;
        
        x = (int)(xCenter + cardSpacingX);
        y = (int)(yCenter - cardHeight / 2);
        for (Card c : discardPile) {
        	c.setWidth((int)cardWidth);
        	c.setHeight((int)cardHeight);
        	c.setCoords(x, y);
        }
	}
}
