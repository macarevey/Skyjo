import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MouseListenerPanel extends JPanel implements MouseListener {
	private Deck d;
	private ArrayList<Hand> hands;
	private Deck deck;
	private Discard discard;
	private double ellipseW, ellipseH;
	private int players;
	
	public void doEllipse() {
		int frameWidth = getWidth();
		int frameHeight = getHeight();
		ellipseW = frameWidth - frameWidth / 8;
		ellipseH = frameHeight - frameHeight / 8;
		
		int count = 270;
		for (int i = 0; i < hands.size(); i++) {
			if (hands.get(i).getIsPlayer()) {
				hands.get(i).calculateCoords(count, ellipseW/2, ellipseH/2);
			} else {
				count += 360 / players;
				if (count > 359) {
					count = 360 - count;
				}
				hands.get(i).calculateCoords(count, ellipseW/2, ellipseH/2);
			}
		}
	}
	
	public MouseListenerPanel(ArrayList<Hand> h, Deck d, Discard disc, int p) {
		addMouseListener(this);
		hands = h;
		deck = d;
		discard = disc;
		players = p;
		doEllipse();
	}
	
	public void paint(Graphics g) {
		doEllipse();
		double x = (getWidth() - ellipseW) / 2;
		double y = (getHeight() - ellipseH) / 2;
		g.setColor(Color.blue);
		g.drawOval((int)x, (int)y, (int)ellipseW, (int)ellipseH);
	}
	
	public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {System.out.println("Click");}
}
