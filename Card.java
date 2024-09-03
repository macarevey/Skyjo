import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class Card extends JPanel {
	private int number;
	private BufferedImage image;
	private BufferedImage back;
	private boolean onBack;
	private int x;
	private int y;
	
	public Card(int n) {
		number = n;
		onBack = true;
		try {
			image = ImageIO.read(Card.class.getResource("/Images/" + n + ".png"));
			back = ImageIO.read(Card.class.getResource("/Images/Back.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCoords(int xx, int yy) {
		x = xx;
		y = yy;
	}
	
	//public void paint(Graphics g) {
	//	System.out.println("Painting");
	//	super.paint(g);
	//	if (!onBack) {
	//		g.drawImage(back, x, y, 50, 100, null);
	//	} else {
	//		g.drawImage(image, x, y, 50, 100, null);
	//	}
	//}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void flipCard() {
		onBack = !onBack;
	}
	
	public int getNum() {
		return number;
	}
	
	public boolean isOnBack() {
		return onBack;
	}
	
	public BufferedImage getBack() {
		return back;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
