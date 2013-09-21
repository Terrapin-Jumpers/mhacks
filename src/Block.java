 public class Block extends Obstacle {
	 
	 protected static final int DAMAGE = 100;
	 
	 public Block(int x, int y) {
		 super(x, y, DAMAGE);
		 initWithImage("Block.png");
	 }
}