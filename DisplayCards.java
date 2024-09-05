import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DisplayCards extends JFrame {
	private static final int WIDTH = 2000;
	private static final int HEIGHT = 1000;
	//private Card[][] cards;
	private ArrayList<Hand> hands;
	private MouseListenerPanel m;
	private Deck deck;
	private Discard discard;
	
	public DisplayCards(String title, ArrayList<Hand> h, Deck d, Discard disc, int playerNum) {
		super(title);
		
		hands = h;
		deck = d;
		discard = disc;
		
		//Ellipse magic
		setSize(1800, 900);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m = new MouseListenerPanel(hands, deck, discard, playerNum);
		add(m);
		//setSize(1800, 900);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
}