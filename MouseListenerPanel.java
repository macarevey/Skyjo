import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MouseListenerPanel extends JPanel implements MouseListener {
	private Deck d;
	private ArrayList<Hand> hands;
	private Deck deck;
	private Discard discard;
	private double ellipseW, ellipseH;
	private int players;
	
	// Game state
	private int playerTurn;
	private String gameState;
	private Card deckCardPulled;
	private boolean newRound;
	private ArrayList<Integer> scores;
	// Game state Methods
	public void switchTurn() {
		playerTurn++;
		if (playerTurn > players) {
			playerTurn = 0;
		}
	}
	
	public ArrayList<Integer> calcScores() {
		for (int i = 0; i < players; i++) {
			scores.set(i, ((int)(Math.random() * 100)) + scores.get(i));
		}
		return scores;
	}
	
	// Math for hands position
	public void doEllipse() {
	    int frameWidth = getWidth();
	    int frameHeight = getHeight();

	    // Ellipse radii
	    ellipseW = frameWidth - frameWidth / 3.5;
	    ellipseH = frameHeight - frameHeight / 3.5;
	    double horizontalRadius = ellipseW / 2;
	    double verticalRadius = ellipseH / 2;

	    // Ellipse center coordinates
	    double xCenter = frameWidth / 2;
	    double yCenter = frameHeight / 2;

	    int count = 90; // Start at 90 degrees (for the player's position at the bottom)
	    for (int i = 0; i < hands.size(); i++) {
	        // Calculate coordinates for each hand
	        hands.get(i).calculateCoords(count, horizontalRadius, verticalRadius, xCenter, yCenter, frameWidth, frameHeight);

	        // Adjust angle for the next hand
	        count += 360 / players;
	        if (count > 359) {
	            count -= 360;
	        }
	    }
	    deck.calculateCoords(frameWidth, frameHeight, xCenter, yCenter);
	    discard.calculateCoords(frameWidth, frameHeight, xCenter, yCenter);
	}
	
	public MouseListenerPanel(ArrayList<Hand> h, Deck d, Discard disc, int p, ArrayList<Integer> s) {
		addMouseListener(this);
		hands = h;
		deck = d;
		discard = disc;
		players = p;
		playerTurn = 0;
		gameState = "Normal";
		newRound = false;
		scores = s;
		doEllipse();
	}
	
	public void paint(Graphics g) {
		doEllipse();
		double x = (getWidth() - ellipseW) / 2;
		double y = (getHeight() - ellipseH) / 2;
		g.setColor(Color.blue);
		g.drawOval((int)x, (int)y, (int)ellipseW, (int)ellipseH);
		
		// Drawing scores
		int scoresX = getWidth() / 40;
		int scoresY = getHeight() / 20;
		for (int i = 0; i < scores.size(); i++) {
			int score = scores.get(i);
			if (i == 0) {
				g.drawString("Player:", scoresX, scoresY);
				g.drawString("" + score, scoresX*2, scoresY);
			} else {
				g.drawString("Bot " + i + ":", scoresX, i*scoresY+scoresY);
				g.drawString("" + score, scoresX*2, i*scoresY+scoresY);
			}
		}
		
		// Drawing hands
		int handCount = 1;
		for (Hand h : hands) {
			//h.checkColumns();
			if (h.getIsPlayer()) {
				g.drawString("Player", (int)h.getX(), (int)h.getY());
			} else {
				g.drawString("Bot " + handCount, (int)h.getX(), (int)h.getY());
				handCount++;
			}
			
			for (Card c : h.getHand()) {
				//System.out.println("x: " + c.getX() + ", y: " + c.getY() + ", width: " + c.getWidth() + ", height: " + c.getHeight());
				if (c.isOnBack()) {
					g.drawImage(c.getBack(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
				} else {
					g.drawImage(c.getImage(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
				}
			}
		}
		
		// Drawing deck, discard piles
		int deckSize = deck.getDeck().size();
		int discardSize = discard.getPile().size();
		Card deckCard = deck.getDeck().get(deckSize-1);
		Card discardCard = discard.getPile().get(discardSize-1);
		
		if (discardCard.isOnBack()) {
			g.drawImage(discardCard.getBack(), discard.getX(), discard.getY(), discardCard.getWidth(), discardCard.getHeight(), null);
		} else {
			g.drawImage(discardCard.getImage(), discard.getX(), discard.getY(), discardCard.getWidth(), discardCard.getHeight(), null);
		}
		
		if (deckCard.isOnBack()) {
			g.drawImage(deckCard.getBack(), deck.getX(), deck.getY(), deckCard.getWidth(), deckCard.getHeight(), null);
		} else {
			g.drawImage(deckCard.getImage(), deck.getX(), deck.getY(), deckCard.getWidth(), deckCard.getHeight(), null);
		}
		
		if (deckCardPulled != null) {
			g.drawImage(deckCardPulled.getImage(), deckCardPulled.getX(), deckCardPulled.getY(), deckCardPulled.getWidth(), deckCardPulled.getHeight(), null);
			System.out.println(deckCardPulled.getX() + ", " + deckCardPulled.getY());
		}
		
        g.drawString(".", getWidth()/2, getHeight()/2);
	}
	
	public boolean startNewRound() {
		return newRound;
	}
	
	public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
    	int y = e.getY();
    	if (playerTurn == 0 && e.getButton() == e.BUTTON1) { // If it's the players turn and he left clicked
	    	if (gameState == "Normal") {
		    	int deckSize = deck.getDeck().size();
		    	Card deckCard = deck.getDeck().get(deckSize-1);
		    	if (x >= deck.getX() && x <= (deckCard.getWidth() + deck.getX()) && y >= deck.getY() && y <= (deckCard.getHeight() + deck.getY())) {
		    		//System.out.println("Clicked on the deck, card is " +  deckCard.getNum());
		    		gameState = "Deck";
		    		Card cardPulled = deck.getCards(1).get(0);
		    		cardPulled.setCoords(deckCard.getX(), deckCard.getY()+deckCard.getHeight()+5);
		    		deckCardPulled = cardPulled;
		    	}
		    	
		    	int discardSize = discard.getPile().size();
		    	Card discardCard = discard.getPile().get(discardSize-1);
		    	if (x >= discard.getX() && x <= (discardCard.getWidth() + discard.getX()) && y >= discard.getY() && y <= (discardCard.getHeight() + discard.getY())) {
		    		System.out.println("Clicked on the discard pile, card is " +  discardCard.getNum());
		    		gameState = "Discard";
		    	}
		    	
		    	//int hand = 1; 	
		    	//for (Hand h : hands) {
		    		//System.out.println("Hands (x,y): (" + h.getX() + ", " + h.getY() + ") Hand's width,height: " + h.getWidth() + ", " + h.getHeight());
		    	//	if (x >= h.getX() && x <= (h.getWidth() + h.getX()) && y >= h.getY() && y <= (h.getHeight() + h.getY())) {
		    			//System.out.println("Found hand " + hand);
		    	//		for (Card c : h.getHand()) {
		    	//			if (x >= c.getX() && x <= (c.getWidth() + c.getX()) && y >= c.getY() && y <= (c.getHeight() + c.getY())) {
		    	//				//System.out.println("Clicked on card " + c.getNum() + " in hand " + hand);
		    	///				c.flipCard();
		    	//				gameState = "Normal";
		    	//			}
		    	//		}
		    	//	}
		    	//	hand++;
		    	//}
	    	} else if (gameState == "Deck") {
	    		int discardSize = discard.getPile().size();
		    	Card discardCard = discard.getPile().get(discardSize-1);
		    	
		    	if (x >= discard.getX() && x <= (discardCard.getWidth() + discard.getX()) && y >= discard.getY() && y <= (discardCard.getHeight() + discard.getY())) {
		    		deckCardPulled.setCoords(discardCard.getX(), discardCard.getY());
		    		discard.addCard(deckCardPulled);
		    		gameState = "FlipCard";
		    		deckCardPulled = null;
		    	}
		    	
		    	if (gameState != "Normal") { // Check if the code above did not fire
		    		for (Hand h : hands) {
			    		if (x >= h.getX() && x <= (h.getWidth() + h.getX()) && y >= h.getY() && y <= (h.getHeight() + h.getY())) {
			    			for (Card c : h.getHand()) {
			    				if (x >= c.getX() && x <= (c.getWidth() + c.getX()) && y >= c.getY() && y <= (c.getHeight() + c.getY())) {
			    					h.replaceCard(c, deckCardPulled);
			    					discard.addCard(c);
			    					gameState = "Normal";
			    					deckCardPulled = null;
			    					repaint();
			    				}
			    			}
			    		}
			    	}
		    	}
	    	} else if (gameState == "FlipCard") {
	    		Hand h = hands.get(0);
		    	if (x >= h.getX() && x <= (h.getWidth() + h.getX()) && y >= h.getY() && y <= (h.getHeight() + h.getY())) {
		    		for (Card c : h.getHand()) {
		    			if (x >= c.getX() && x <= (c.getWidth() + c.getX()) && y >= c.getY() && y <= (c.getHeight() + c.getY())) {
		    				c.flipCard();
		    				gameState = "Normal";
		    			}
		    		}
		    	}
		    }
    	}

    	repaint();
    }
}
