import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

abstract class Actor {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private String imagePath;
	
	protected int xSpeed = 0;
	protected int ySpeed = 0;
	
	public Actor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected void initWithImage(String imagePath) {
		this.imagePath = "images/" + imagePath;
		InputStream in = getClass().getResourceAsStream(this.imagePath);
		BufferedImage img;
		try {
			img = ImageIO.read(in);
			height = img.getHeight();
			width = img.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void move() {
		//changes the x/y coordinate by how much the object is moving in either
		//direction
		x+=xSpeed;
		y+=ySpeed;
	}
	
	public int getXSpeed(){
		return xSpeed;
		
	}
	public int getYSpeed(){
		return ySpeed;
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