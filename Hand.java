import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> hand;
	private boolean isPlayer;
	private double x,y;
	private String gameState;
	private boolean isMyTurn;
	
	public Hand(ArrayList<Card> h, boolean p, int xx, int yy) { // handle the giving of cards from deck in game runner
		hand = h;
		isPlayer = p;
		x = xx;
		y = yy;
		if (p) {
			isMyTurn = true;
		} else {
			isMyTurn = false;
		}
		
		// For flipping two random cards
		
		int c1 = (int)(Math.random() * hand.size());
		int c2 = 0;
		boolean hasCardTwo = false;
		while (!hasCardTwo) {
			c2 = (int)(Math.random() * hand.size());
			if (c2 != c1) {
				hasCardTwo = true;
			}
		}
		
		hand.get(c1).flipCard();
		hand.get(c2).flipCard();
	}
	
	public boolean allFlipped() {
		boolean check = true;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).isOnBack()) {
				check = false;
			}
		}
		return check;
	}
	
	public void checkColumns() {
	    boolean noMatches = false;
	    
	    while (!noMatches) {
	    	int numColumns = hand.size() / 3;
		    int numRows = hand.size() / numColumns;
		    for (int col = 0; col < numColumns; col++) {
		    	Card firstCard = hand.get(col * numRows); // First row in the column
		        Card secondCard = hand.get(col * numRows + 1); // Second row in the column
		        Card thirdCard = hand.get(col * numRows + 2); // Third row in the column
		        
		        //System.out.println("Checking " + firstCard.getNum() + ", " + secondCard.getNum() + ", " + thirdCard.getNum() + " in: ");
		        
		        
		        if (!firstCard.isOnBack() && !secondCard.isOnBack() && !thirdCard.isOnBack() &&
		        		firstCard.getNum() == secondCard.getNum() && secondCard.getNum() == thirdCard.getNum()) {
		                
		        	hand.remove(col * numRows + 2); // Third card
		            hand.remove(col * numRows + 1);     // Second card
		            hand.remove(col * numRows);                 // First card
		                
		            //System.out.println("Removed all cards in column " + col + " (number " + firstCard.getNum() + ")");
		            noMatches = false;
		            break;
		        } else {
		        	noMatches = true;
		        }
		    }
	    }
	}
	
	public void cardClicked(Card c) { // do stuff based off game state, and card clicked
		if (isMyTurn) {
			return;
		}
		return;
	}
	
	public void updateGameState(String gs) {
		gameState = gs;
	}
	
	public void calculateCoords(double angle, double horizontalRadius, double verticalRadius, double xCenter, double yCenter, int screenWidth, int screenHeight) {
        // Convert angle to radians
        double theta = Math.toRadians(angle);

        // Calculate the hand's (x, y) center based on ellipse parametric equations
        x = horizontalRadius * Math.cos(theta) + xCenter;
        y = verticalRadius * Math.sin(theta) + yCenter;

        // Set card size based purely on screen size
        double cardWidth = screenWidth / 35.0;
        double cardHeight = screenHeight / 15.0;

        // Add spacing between the cards
        double cardSpacingX = cardWidth * 0.2; // 20% of card width as spacing
        double cardSpacingY = cardHeight * 0.2; // 20% of card height as spacing

        // Calculate the total width and height of the grid layout
        double handWidth = (cardWidth + cardSpacingX) * (hand.size() / 3);  // 4 cards horizontally with spacing
        double handHeight = (cardHeight + cardSpacingY) * (hand.size() / 4); // 3 cards vertically with spacing

        // Adjust the hand coordinates to center the grid of cards
        x -= handWidth / 2;
        y -= handHeight / 2;

        // Assign coordinates to each card in the hand with spacing
        for (int i = 0; i < hand.size(); i++) {
            int row = i % 3;
            int col = i / 3;
            //System.out.println(row + ", " + col + " for " + hand.get(i).getNum());
            int cardX = (int) (x + col * (cardWidth + cardSpacingX));
            int cardY = (int) (y + row * (cardHeight + cardSpacingY));
            hand.get(i).setCoords(cardX, cardY);
            hand.get(i).setWidth((int)cardWidth);
            hand.get(i).setHeight((int)cardHeight);
            //System.out.println("set width/height : " + (int)cardWidth + ", " + (int)cardHeight + " as compared to Card: " + hand.get(i).getWidth() + ", " + hand.get(i).getHeight());
        }

        //System.out.println("Calculated hand position (" + x + ", " + y + ")");
    }

	
	public boolean getIsPlayer() {
		return isPlayer;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public int getWidth() {
		int cardWidth = hand.get(0).getWidth();
		double cardSpacing = cardWidth * 0.2;
		double width = 0;
		for (int i = 0; i < 4; i++) {
			if (i != 3) {
				width += (cardWidth + cardSpacing);
			} else {
				width += cardWidth;
			}
		}
		//System.out.println("Card size: " + hand.get(0).getWidth());
		//System.out.println(width);
		return (int)width;
	}
	
	public int getHeight() {
		int cardHeight = hand.get(0).getHeight();
		double cardSpacing = cardHeight * 0.2;
		double height = 0;
		for (int i = 0; i < 4; i++) {
			if (i != 3) {
				height += (cardHeight + cardSpacing);
			} else {
				height += cardHeight;
			}
		}
		//System.out.println("Card size: " + hand.get(0).getWidth());
		//System.out.println(width);
		return (int)height;
	}
	
	public void replaceCard(Card c, Card newCard) {
		for (int i = 0; i < hand.size(); i++) {
			Card card = hand.get(i);
			if (c.getNum() == card.getNum() && c.getX() == card.getX() && c.getY() == card.getY()) {
				//System.out.println("Found the card, replacing (" + c.getNum() + ")");
				newCard.setCoords(card.getX(), card.getY());
				if (newCard.isOnBack()) {
					newCard.flipCard();
				}
				hand.set(i, newCard);
			}
		}
	}
	
	public int getValue() {
		int v = 0;
		for (Card c : hand) {
			v += c.getNum();
		}
		return v;
	}
}
