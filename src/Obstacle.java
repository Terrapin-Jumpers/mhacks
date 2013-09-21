public class Obstacle extends Actor {
	
	protected int damage;
	
	public Obstacle(int x, int y, int damage,int xSpeed,int ySpeed){
		super(x,y,xSpeed,ySpeed);
		this.damage = damage;
	}
	
	public boolean isCollision(Actor a) {
		
	}
}
