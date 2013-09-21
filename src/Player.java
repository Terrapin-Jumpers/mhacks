public class Player extends Actor {
	
	private final static int JUMP_SPEED = -10;
	private boolean alive = true;
	private boolean airbourne = false;
	static float gravity = 0.5f;
	private int score = 0;
	
	
	public Player(int x, int y) {

		super(x, y);
		initWithImage("Player.png");
	}

	public void move() {
		
		//Checks if in air, slows ySpeed
		if(airbourne){
			ySpeed+=gravity;
		}
		super.move();
	}

	void jump() {
		
		if(!airbourne){
			ySpeed = JUMP_SPEED;
			airbourne =true;
		}
		
	}

	void hitObstacle(Obstacle obs) {
		score -= obs.damage;
		x -= 5;
	}

	void getCoin() {
		score += 10;
	}

	void die() {
		alive = false;
	}
}