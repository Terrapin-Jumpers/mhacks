
public class World {
	public static ArrayList<Actor> actors = new ArrayList<Actor>();
	
	public void update() {
		for (Actor a : actors)
			a.update();
	}
	
	public void paint(Graphics g) {
		for (Actor a : actors)
			a.paint(g);
	}
	
	public void addChild(Actor a) {
		actors.add(a);
	}
}
