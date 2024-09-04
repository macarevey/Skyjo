import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DisplayCards extends JFrame {
	private static final int WIDTH = 2000;
	private static final int HEIGHT = 1000;
	//private Card[][] cards;
	private Hand[] hands;
	private MouseListenerPanel m;
	private Deck deck;
	private Discard discard;
	
	public DisplayCards(String title, Hand[] h, Deck d, Discard disc) {
		super(title);
		
		hands = h;
		deck = d;
		discard = disc;
		
		//setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m = new MouseListenerPanel(hands, deck, discard);
		add(m);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
}