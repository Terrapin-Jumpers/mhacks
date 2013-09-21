 public class Block extends Obstacle {
	 
	 protected static final int DAMAGE = 100;
	 
	 public Block(int x, int y, int timesRepeated) {
		 super(x, y, DAMAGE);
		 initWithImage("Block.png");
		 width = width*timesRepeated;
	 }
	 public void move(){
		 x--;
	 }
}