import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DisplayCards extends JFrame {
	private static final int WIDTH = 2000;
	private static final int HEIGHT = 1000;
	private Card[][] cards;
	
	public DisplayCards(String title) {
		super(title);
		
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MouseListenerPanel m = new MouseListenerPanel();
		add(m);
		setVisible(true);
	}
}