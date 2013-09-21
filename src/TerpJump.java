import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TerpJump extends JFrame implements Runnable {
	World world;
	static final int HEIGHT=720; 
	static final int WIDTH=960;
	static final int FPS = 60;
	static final int REFRESH_RATE = 1000/FPS;
	
	public TerpJump() {
		super("TerpJump");
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.setVisible(true);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		world = new World(WIDTH, HEIGHT);
		this.add(world);
	}

	public static void main(String[] args) {
		new TerpJump().run();
	}

	public void run() {
		while (true) {
			world.update();
			world.repaint();
			try {
				Thread.sleep(REFRESH_RATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}