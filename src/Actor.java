import java.util.ArrayList;
import java.awt.image.BufferedImage;

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
		BufferedImage img = ImageIO.read(this.imagePath);
		height = img.getHeight();
		width = img.getWidth();
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