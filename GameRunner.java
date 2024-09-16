import java.util.ArrayList;

public class GameRunner {
	private static ArrayList<Integer> scores;
	private static int players;
	
	public static DisplayCards newRound() {
		ArrayList<Hand> hands = new ArrayList<Hand>();
		Deck deck = new Deck();
		deck.shuffleDeck();
		
		for (int i = 0; i < players; i++) {
			ArrayList<Card> hand = deck.getCards(12);
			//for (Card c : hand) {
			//	c.flipCard();
			//}
			if (i == 0) {
				hands.add(new Hand(hand, true, 0, 0));
			} else {
				hands.add(new Hand(hand, false, 0, 0));
			}
		}
		
		for (Hand h : hands) {
			scores.add(h.getValue());
		}
		Discard discard = new Discard(deck.getCards(1).get(0));
		
		DisplayCards display = new DisplayCards("Skyjo Game", hands, deck, discard, players, scores);
		return display;
	}
	
	public static void main(String[] args) {
		players = 3;
		scores = new ArrayList<Integer>();
		
		DisplayCards display = newRound();
		MouseListenerPanel m = display.getMouseListener();
		while (m != null) {
			if (m.startNewRound()) {
				ArrayList<Integer> newScores = m.calcScores();
				for (int score : newScores) {
					scores.add(score);
				}
				
				display = newRound();
				m = display.getMouseListener();
			}
		}
	}
}
