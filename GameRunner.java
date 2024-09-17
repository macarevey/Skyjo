import java.util.ArrayList;
import java.util.Timer;

public class GameRunner {
	private static ArrayList<Integer> scores;
	private static int players;
	private static DisplayCards display;
	private static ArrayList<Hand> hands;
	
	public static void newRound() {
		ArrayList<Hand> h = new ArrayList<Hand>();
		Deck deck = new Deck();
		deck.shuffleDeck();
		
		for (int i = 0; i < players; i++) {
			ArrayList<Card> hand = deck.getCards(12);
			//for (Card c : hand) {
			//	c.flipCard();
			//}
			if (i == 0) {
				h.add(new Hand(hand, true, 0, 0));
			} else {
				h.add(new Hand(hand, false, 0, 0));
			}
		}
		
		hands = h;
		
		for (Hand ha : hands) {
			scores.add(0);
		}
		Discard discard = new Discard(deck.getCards(1).get(0));
		
		display = new DisplayCards("Skyjo Game", hands, deck, discard, players, scores);
	}
	
	public static void roundEnd(int players, int playerTurn) {
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

		Discard discard = new Discard(deck.getCards(1).get(0));
		
		display.newRound(hands, deck, discard, players, scores, playerTurn);
	}
	
	public static void main(String[] args) {
		players = 8;
		scores = new ArrayList<Integer>();
		
		newRound();
		MouseListenerPanel m = display.getMouseListener();
	}
}
