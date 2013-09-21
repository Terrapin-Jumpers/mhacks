import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;

public class World extends JComponent {
	public static ArrayList<Actor> actors = new ArrayList<Actor>();
	
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
