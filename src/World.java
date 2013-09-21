import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public class World extends JComponent {
	public static ArrayList<Actor> actors = new ArrayList<Actor>();
	
	static final int GRID_SIZE = 32;
	
	private Player player;
	
	public World(int width, int height) {
		player = new Player(100,400);
		this.addChild(player);
		player.jump();
		Block floor = new Block(0, HEIGHT - GRID_SIZE*2, WIDTH/GRID_SIZE);
		addChild(floor);
	}
	
	public void update() {
		
		for (Actor a : actors)
			a.move();
	}
	
	public void paint(Graphics g) {
		for (Actor a : actors)
			a.paint(g);
	}
	
	public void addChild(Actor a) {
		actors.add(a);
	}
}
