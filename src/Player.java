public class Player extends Actor {
	private boolean alive;
	int score;
	
	public Player(int x, int y) {
		super(x, y);
		initWithImage("Player.png");
		alive = true;
		score = 0;
	}
	
	void move(int m) {
		x += m;
	}
	
	void jump() {
		int vSpeed = 10;
		y += 5;
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