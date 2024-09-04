import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> hand;
	private boolean isPlayer;
	private int x,y;
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
	
	public void checkColumns() {
		int columnNum = hand.size() / 3;
		
		Card[][] h = new Card[3][4];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				h[i][j] = hand.get(i+j);
			}
		}
		
		for (int j = h[0].length; j > -1; j--) {
			if (h[0][j].getNum() == h[1][j].getNum() && h[1][j].getNum() == h[2][j].getNum()) {
				// do stuff
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
}
