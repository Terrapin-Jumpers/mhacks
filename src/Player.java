public class Player extends Actor {
	private static int jumpSpeed = -10;
	private int PLAYER_X, PLAYER_Y = 5;
	private boolean alive = true;
	private boolean airbourne = false;
	static float gravity = 0.5f;
	private int score = 0;
	
	
	public Player(int x, int y) {

		super(x, y);
		initWithImage("Player.png");
	}

	public void move() {
		x ++;
		//Checks if in air, slows ySpeed
		if(airbourne){
			ySpeed+=gravity;
		}
		super.move();
	}

	void jump() {
		if(!airbourne){
			ySpeed = jumpSpeed;
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