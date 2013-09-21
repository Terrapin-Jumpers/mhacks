import java.awt.Graphics;

 public class Block extends Obstacle {
	 
	 protected static final int DAMAGE = 100;
	 
	 private int times;
	 private int initialWidth;
	 
	 public Block(int x, int y, int timesRepeated) {
		 super(x, y, DAMAGE);
		 initWithImage("Block.png");
		 times = timesRepeated;
		 initialWidth = width;
		 width = width*timesRepeated;
	 }
	 
	 public void move(){
		 x -= World.SPEED;
	 }
	 
	 public void paint(Graphics g) {
		for (int i = 0; i < times; i++) 
			g.drawImage(image, x + i*initialWidth, y, initialWidth, height, null);
	 }
}