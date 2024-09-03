import java.util.ArrayList;

public class GameRunner {
	public static void main(String[] args) {
		int players = 2;
		ArrayList<Hand> hands = new ArrayList<Hand>();
		Deck d = new Deck();
		d.shuffleDeck();
		
		for (int i = 0; i < players; i++) {
			ArrayList<Card> hand = d.getCards(12);
			if (i == 0) {
				hands.add(new Hand(hand, true, 0, 0));
			} else {
				hands.add(new Hand(hand, false, 0, 0));
			}
		}
		
		DisplayCards display = new DisplayCards("Skyjo Game");
	}
}
