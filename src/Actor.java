import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

abstract class Actor {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private String imagePath;
	
	protected float xSpeed = 0;
	protected float ySpeed = 0;
	protected BufferedImage image;
	public Actor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	protected void initWithImage(String imagePath) {
		this.imagePath = "images/" + imagePath;
		try {
			image = ImageIO.read(new File(this.imagePath));
			height = image.getHeight();
			width = image.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	 //Finds the area over which the actor will travel
	public Rectangle getCollisionBox() {
	    Rectangle rec = new Rectangle(x, y, (int) xSpeed + width, (int) ySpeed + height + 1);
	    return rec;
	}
	
	//Finds if current actor is within another actors field of motion
	public boolean willCollideWith(Actor other) {
	    return (other.getCollisionBox().intersects(this.getCollisionBox()));
	}
	
	public void moveToContact(Actor other) {
		//if moving on top
		y = other.getY() - height;
	}
	
	public void move() {
		//changes the x/y coordinate by how much the object is moving in either
		//direction
		x+=xSpeed;
		y+=ySpeed;
	}

	public float getXSpeed(){
		return xSpeed;
		
	}
	public float getYSpeed(){
		return ySpeed;
	}
	public void paint(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
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
	
	public boolean isAbove(Actor other) {
		return this.y + this.height <= other.getY();
	}
	
}