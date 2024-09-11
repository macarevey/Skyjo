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
	private int height;
	private int width;
	
	public Card(int n) {
		number = n;
		onBack = false;
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
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
