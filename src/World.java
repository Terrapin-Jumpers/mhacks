import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public class World extends JComponent {
	public static ArrayList<Actor> actors = new ArrayList<Actor>();
	
	static final int GRID_SIZE = 32;
	
	private ArrayList <Player> players;
	
	
	public World(int width, int height,int numPlayers) {
		
		players = new ArrayList<Player>(numPlayers);
		
		for(int i =1;i<=numPlayers;i++){
			Player x =  new Player(100*i,TerpJump.HEIGHT-100);
			players.add(x);
			this.addChild(x);
		}
		
		Block block = new Block(200,TerpJump.HEIGHT-100,1);
		
		this.addChild(block);
		
		Block floor = new Block(0, HEIGHT - GRID_SIZE*2, WIDTH/GRID_SIZE);
		addChild(floor);
		
	}
	
	public void update() {
		
		for (Actor a : actors){
			a.move();
			if(a instanceof Obstacle){
				a.getCollisionBox();
				for(Player b : players){
					b.willCollideWith(a);
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		for (Actor a : actors)
			a.paint(g);
	}
	
	public void addChild(Actor a) {
		actors.add(a);
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
}
