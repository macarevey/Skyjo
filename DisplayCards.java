import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DisplayCards extends JFrame {
	private static final int WIDTH = 2000;
	private static final int HEIGHT = 1000;
	//private Card[][] cards;
	private ArrayList<Hand> hands;
	private Deck deck;
	private Discard discard;
	private int playerNum;
	
	private MouseListenerPanel m;
	
	public DisplayCards(String title, ArrayList<Hand> h, Deck d, Discard disc, int p, ArrayList<Integer> s) {
		super(title);
		
		hands = h;
		deck = d;
		discard = disc;
		playerNum = p;
		
		setSize(1800, 900);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m = new MouseListenerPanel(hands, deck, discard, playerNum, s, 0);
		add(m);
		//setSize(1800, 900);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
	
	public void newRound(ArrayList<Hand> h, Deck d, Discard disc, int p, ArrayList<Integer> s, int playerTurn) {
		hands = h;
		deck = d;
		discard = disc;
		playerNum = p;
		
		this.getContentPane().removeAll();
		m = new MouseListenerPanel(hands, deck, discard, playerNum, s, playerTurn);
		add(m);
	}
	
	public MouseListenerPanel getMouseListener() {
		return m;
	}
}