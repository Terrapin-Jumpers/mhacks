import java.awt.Graphics;


public class Paralax extends Actor {
	private float z;
	private float realX = 0;
	public Paralax(int y, float z, String image) {
		super(0, y);
		this.z = z;
		initWithImage(image);
	}
	
	public void paint(Graphics g) {
		for (int i = 0; i < 4; i++)
			if (x + width*i < WebcamView.GAME_WIDTH)
				g.drawImage(image, x + width*i, y, width, height, null);
	 }
	
	public void move() {
		if (this.x < -width) {
			this.x = 0;
			realX = 0;
		} else {
			realX -= World.SPEED / z;
			this.x = (int) realX;
		}
	}
}
