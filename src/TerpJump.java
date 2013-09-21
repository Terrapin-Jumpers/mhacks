import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TerpJump extends JFrame implements Runnable {
	World world;
	int height; int width;
	static final int FPS = 60;
	static final int REFRESH_RATE = 1000/FPS;
	
	public TerpJump() {
		super("TerpJump");
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = ((int) tk.getScreenSize().getWidth());
		int height = ((int) tk.getScreenSize().getHeight());
		this.setVisible(true);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		world = new World();

		this.add(world);
		Player J = new Player(0,0);
	
		world.addChild(J);
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