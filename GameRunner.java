import java.util.ArrayList;

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
		players = 3;
		scores = new ArrayList<Integer>();
		
		newRound();
		MouseListenerPanel m = display.getMouseListener();
		while (m != null) {
			m.repaint();
			if (m.getIsLastTurns() && m.getLastTurnIndex() == m.getPlayerTurn()) {
				System.out.println("Back at original index of " + m.getPlayerTurn());
				ArrayList<Integer> newScores = m.calcScores();
				for (int i = 0; i < newScores.size(); i++) {
					int score = newScores.get(i);
					scores.set(i, score + scores.get(i));
				}
				
				for (int i = 0; i < hands.size(); i++) {
					hands.get(i).flipAllCards();
				}
				
				int lowestScore = Integer.MAX_VALUE;
				int lowestScoreIndex = 0;
				
				for (int i = 0; i < scores.size(); i++) {
					if (scores.get(i) < lowestScore) {
						lowestScore = scores.get(i);
						lowestScoreIndex = i;
					}
				}
				
				if (m.getLastTurnIndex() != lowestScoreIndex) {
					scores.set(m.getLastTurnIndex(), (scores.get(m.getLastTurnIndex()) * 2));
					
				}
				
				boolean gameEnd = false;
				for (int i = 0; i < scores.size(); i++) {
					if (scores.get(i) >= 100) {
						gameEnd = true;
					}
				}
				
				if (gameEnd) {
					String winner = "";
					if (lowestScoreIndex == 0) {
						winner = "Player";
					} else {
						winner = m.getRobots()[lowestScoreIndex-1].getName();
					}
					m.makeGameEnd(winner);
				} else {
					roundEnd(players, m.getPlayerTurn());
					m = display.getMouseListener();
				}
			}
		}
	}
}
