import java.util.ArrayList;

abstract class Actor {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private String imagePath;
	
	protected int xSpeed;
	protected int ySpeed;
	
	public Actor(int x, int y,int xSpeed,int ySpeed) {
		this.x = x;
		this.y = y;
		
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	protected initWithImage(String imagePath) {
		this.imagePath = "images/" + imagePath;
		//build the Image based on the URL
		
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