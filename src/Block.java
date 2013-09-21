 public class Block extends Obstacle {
	 
	 protected static final int DAMAGE = 100;
	 
	 public Block(int x, int y,int xSpeed,int ySpeed) {
		 
		 super(x, y, DAMAGE,xSpeed,ySpeed);
		 
		 initWithImage("Block.png");
	 }
}