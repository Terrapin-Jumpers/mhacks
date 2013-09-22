import java.awt.Graphics;

 public class Block extends Obstacle {
	 
	 protected static final int DAMAGE = 20;
	 
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
		 xSpeed = -World.SPEED;
		 super.move();
	 }
	 
	 public void paint(Graphics g) {
		for (int i = 0; i < times; i++) 
			g.drawImage(image, x + i*initialWidth, y, initialWidth, height, null);
	 }
	 
	 public void setX(int x) {
		 this.x = x;
	 }
}