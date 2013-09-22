import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends Actor {
	
	private final static int JUMP_SPEED = -10;
	private boolean alive = true;
	private boolean airbourne = false;
	static float gravity = 0.5f;
	private int score = 0;
	private int playerNumber;
	
	private Rectangle prevRect, rect;
	
	private static int RECT_TIME = 5;
	private int rectTimer = 0;
		
	public Player(int x, int y, int playerNumber) {
		super(x, y);
		initWithImage("Player_"+playerNumber+".png");
		this.playerNumber = playerNumber;
	}

	public void move() {
		if (rectTimer > 0) {
			rectTimer --;
		} else {
			clearRect();
		}
		//Checks if in air, slows ySpeed
		if(airbourne){
			ySpeed+=gravity;
		}
		super.move();
	}
	
	public void moveToContact(Actor other) {
		//if moving on top
		y = other.getY() - height;
		airbourne = false;
		ySpeed = 0;
	}
	
	void fall() {
		airbourne = true;
	}
	
	boolean isAirbourne() {
		return airbourne;
	}

	
	void jump() {
		
		if(!airbourne){
			ySpeed = JUMP_SPEED;
			airbourne =true;
		}
	}
	
	void collide(Obstacle obs) {
		score -= obs.damage;
		x -= 5;
	}

	/*void getCoin(Coin c) {
		score += c.POINTS;
		c.collected(this);
	}*/

	void die() {
		alive = false;
	}
	
	boolean isAlive() {
		return (score <= 0) ? false : true;
	}
	
	int getNumber() {
		return playerNumber;
	}
	
	public int getScore() {
		return score;
	}
	
	/*public Rectangle getPrevRect() {
		return prevRect;
	}
	
	public void setPrevRect(Rectangle rect) {
		prevRect = rect;
	}*/
	
	public void clearRect() {
		prevRect = null;
		rect = null;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setRect(Rectangle rect) {
		rectTimer = RECT_TIME;
		prevRect = this.rect;
		this.rect = rect;
	}
	
	public float getYMotion() {
		return 0.0f;
	}
}