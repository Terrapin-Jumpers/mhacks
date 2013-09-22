import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Actor {
	
	public static final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN};
	public static final String[] names = {"RED", "BLUE", "GREEN"};
	
	public final static int JUMP_SPEED = -9;
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
	private static final double MOTION_CAP = 16f;
	
	private boolean ducking = false;
	private int duckTimer = 0;
	private final static int DUCK_TIME = 30;
	
	private boolean boost = false;
	private int boostTimer = 0;
	private final static int BOOST_TIME = 6;
	
	private int defaultHeight;
	
	public Player(int x, int y, int playerNumber) {
		super(x, y);
		initWithImage("Player_"+playerNumber+".png");
		defaultHeight = height;
		this.playerNumber = playerNumber;
	}

	public void move() {
		if (rectTimer > 0) {
			rectTimer --;
		} else {
			clearRect();
		}
		
		if (ducking) {
			if (duckTimer > 0) duckTimer --;
			else {
				ducking = false;
				height = defaultHeight;
				y -= defaultHeight / 2 + 1;
			}
		}
		if (boost) {
			if (boostTimer > 0) {boostTimer --; ySpeed = JUMP_SPEED;}
			else { boost = false; }
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
		if (!airbourne){
			if (ducking && !boost) {
				boost = true;
				boostTimer = BOOST_TIME;
				ducking = false;
				height = defaultHeight;
				y -= defaultHeight / 2;
			}
			ySpeed = JUMP_SPEED;
			airbourne = true;
			beenHit = false;
		}
	}
	
	void setOnGround() {
		airbourne = false;
	}
	
	void duck() {
		if (!ducking && !airbourne) {
			ducking = true;
			duckTimer = DUCK_TIME;
			height = defaultHeight / 2;
			y += defaultHeight / 2 - 1;
		}
	}
	
	void collideRight(Obstacle obs) {
		if (!beenHit) {
			beenHit = true;
			score -= obs.damage;
		}
		x = obs.x - this.width;
	}
	
	void collideTop(Obstacle obs) {
		y = obs.getY() + obs.getHeight() + 2;
		ySpeed = 0;
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
	}

	/*void getCoin(Coin c) {
		score += c.POINTS;
		c.collected(this);
	}*/

	void die() {
		alive = false;
	}
	
	boolean isAlive() {
		return (score >= 0 && x + width >= 0);
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
	
	public String getName() {
		return names[getNumber() - 1];
	}
}