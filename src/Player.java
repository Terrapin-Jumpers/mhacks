public class Player extends Actor {
	private boolean alive;
	int score;
	
	public Player(String img) {
		alive = true;
		score = 0;
		x = 5;
		y = 5;
	}
	
	void jump() {
		y += 5;
	}
	
	void hitObstacle(Actor obs) {
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