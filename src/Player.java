import java.awt.event.KeyEvent;

public class Player extends Actor {
	
	private final static int JUMP_SPEED = -10;
	private boolean alive = true;
	private boolean airbourne = false;
	static float gravity = 0.5f;
	private int score = 0;
		
	public Player(int x, int y, int playerNumber) {
		super(x, y);
		initWithImage("Player_"+playerNumber+".png");
	}

	public void move() {
		
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
	
	void collectCoin(Coin c) {
		score += c.POINTS;
		c.collected(this);
	}
	
	void collide(Obstacle obs) {
		score -= obs.damage;
		x -= 5;
	}

	void getCoin() {
		score += 10;
	}

	void die() {
		alive = false;
	}
	
	boolean isAlive() {
		return (score <= 0) ? false : true;
	}
}