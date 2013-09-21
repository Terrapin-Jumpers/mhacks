public class Player extends Actor {
	private boolean alive;
	int score;
	String pic;
	
	public Player(String img) {
		alive = true;
		pic = img;
		score = 0;
		x = 5;
		y = 5;
	}
	
	void move(int m) {
		x += m;
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