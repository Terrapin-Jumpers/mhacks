public class Obstacle extends Actor {
	
	protected int damage;
	
	public Obstacle(int x, int y, int damage){
		super(x, y);
		this.damage = damage;
	}
	public int getDamage(){
		return damage;
	}
}
