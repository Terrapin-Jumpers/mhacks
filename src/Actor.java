import java.util.ArrayList;
import java.awt.Image;

abstract class Actor {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private String imagePath;
	
	public Actor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected initWithImage(String imagePath) {
		this.imagePath = "images/" + imagePath;
		//build the Image based on the URL
		InputStream in = getClass().getResourceAsStream(imagePath);
		ImageIcon img = new ImageIcon(ImageIO.read(in));
		height = img.getIconHeight();
		width = img.getIconWidth();
	}
	
	public void move() {
		
	}
	
	public void paint(Graphics g) {
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}