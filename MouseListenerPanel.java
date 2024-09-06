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

	    // Ellipse radii
	    ellipseW = frameWidth - frameWidth / 3.5;
	    ellipseH = frameHeight - frameHeight / 3.5;
	    double horizontalRadius = ellipseW / 2;
	    double verticalRadius = ellipseH / 2;

	    // Ellipse center coordinates
	    double xCenter = frameWidth / 2;
	    double yCenter = frameHeight / 2;

	    int count = 90; // Start at 90 degrees (for the player's position at the bottom)
	    for (int i = 0; i < hands.size(); i++) {
	        // Calculate coordinates for each hand
	        hands.get(i).calculateCoords(count, horizontalRadius, verticalRadius, xCenter, yCenter, frameWidth, frameHeight);

	        // Adjust angle for the next hand
	        count += 360 / players;
	        if (count > 359) {
	            count -= 360;
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
		//g.drawOval((int)x, (int)y, (int)ellipseW, (int)ellipseH);
		
		for (Hand h : hands) {
			//h.checkColumns();
			if (h.getIsPlayer()) {
				g.drawString("Player", (int)h.getX(), (int)h.getY());
			} else {
				g.drawString("Bot", (int)h.getX(), (int)h.getY());
			}
			
			for (Card c : h.getHand()) {
				//System.out.println("x: " + c.getX() + ", y: " + c.getY() + ", width: " + c.getWidth() + ", height: " + c.getHeight());
				g.drawImage(c.getImage(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
			}
		}
		
		//g.drawString("Player", (int)hands.get(0).getX(), (int)hands.get(0).getY());
		//g.drawString("Bot", (int)hands.get(1).getX(), (int)hands.get(1).getY());
	}
	
	public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {System.out.println("Click");}
}
