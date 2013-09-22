import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends Actor {
	
	private final static int JUMP_SPEED = -10;
	private boolean alive = true;
	private boolean airbourne = false;
	static float gravity = 0.5f;
	private int score = 0;
	private int playerNumber;
	private boolean beenHit = false;
	
	private Rectangle prevRect, rect;
	
	private static int RECT_TIME = 5;
	private int rectTimer = 0;
	
	private static final double MOTION_NORM = 20f;
	private static final double MOTION_CAP = 12f;
		
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
			airbourne = true;
			beenHit = false;
		}
	}
	
	void collideRight(Obstacle obs) {
		if (!beenHit) {
			beenHit = true;
			score -= obs.damage;
		}
		x = obs.x - this.width;
	}

	/*void getCoin(Coin c) {
		score += c.POINTS;
		c.collected(this);
	}*/

	void die() {
		alive = false;
	}
	
	boolean isAlive() {
		return (score > 0 && x + width > 0);
	}
	
	int getNumber() {
		return playerNumber;
	}
	
	public int getScore() {
		return score;
	}

	public void addScore(int i) {
		score += i;
	}
	
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
	
	public double getYMotion() {
		double m = 0f;
		if (rect != null && prevRect != null) {
			m = (rect.getCenterY() - prevRect.getCenterY())/MOTION_NORM;
			if (Math.abs(m) > MOTION_CAP) {
				m = 0;
				clearRect();
			}
		}
		return m;
	}
}