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
	
	public void checkColumns() { // Doesnt work
		for (int col = 0; col < 4; col++) {
            // Get the card numbers in the column (row 0, row 1, row 2)
            int firstCardNum = hand.get(col).getNum();
            int secondCardNum = hand.get(col + 4).getNum();
            int thirdCardNum = hand.get(col + 8).getNum();
            
            if (firstCardNum == secondCardNum && secondCardNum == thirdCardNum) {
                hand.remove(col + 8);
                hand.remove(col + 4);
                hand.remove(col);
                System.out.println("Removed all cards in column " + col + " (number " + firstCardNum + ")");
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
        double cardWidth = screenWidth / 35.0; // Adjust the divisor to change overall card size
        double cardHeight = screenHeight / 15.0;

        // Add spacing between the cards
        double cardSpacingX = cardWidth * 0.2; // 20% of card width as spacing
        double cardSpacingY = cardHeight * 0.2; // 20% of card height as spacing

        // Calculate the total width and height of the grid layout
        double handWidth = (cardWidth + cardSpacingX) * 4;  // 4 cards horizontally with spacing
        double handHeight = (cardHeight + cardSpacingY) * 3; // 3 cards vertically with spacing

        // Adjust the hand coordinates to center the grid of cards
        x -= handWidth / 2;
        y -= handHeight / 2;

        // Assign coordinates to each card in the hand with spacing
        for (int i = 0; i < hand.size(); i++) {
            int row = i / 4;
            int col = i % 4;
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
}
