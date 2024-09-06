import java.util.ArrayList;

public class GameRunner {
	public static DisplayCards newRound() {
		int players = 5;
		ArrayList<Hand> hands = new ArrayList<Hand>();
		Deck deck = new Deck();
		deck.shuffleDeck();
		
		for (int i = 0; i < players; i++) {
			ArrayList<Card> hand = deck.getCards(12);
			for (Card c : hand) {
				c.flipCard();
			}
			if (i == 0) {
				hands.add(new Hand(hand, true, 0, 0));
			} else {
				hands.add(new Hand(hand, false, 0, 0));
			}
		}
		Discard discard = new Discard(deck.getCards(1).get(0));
		
		DisplayCards display = new DisplayCards("Skyjo Game", hands, deck, discard, players);
		return display;
	}
	
	public static void main(String[] args) {
		boolean createNewRound = true;
		DisplayCards display;
		while (true) {
			if (createNewRound) {
				display = newRound();
				createNewRound = false;
			}
		}
	}
}
