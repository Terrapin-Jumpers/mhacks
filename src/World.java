import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

public class World extends JPanel{

	private static final long serialVersionUID = -13349648902529661L;
	public ArrayList<Actor> actors = new ArrayList<Actor>();
	public ArrayList<Actor> garbage = new ArrayList<Actor>();
	
	//private long starTime = System.nanoTime();
	//private static int DIFF_MULT = 20;
	
	public static float SPEED = 2;
	static final int GRID_SIZE = 32;
	
	public static final float SCALE = 3f;

	private int width;
	private int height;

	private ArrayList<Player> players;
	
	Font font = new Font("Sans-Serif", Font.BOLD, 18);
	Block floor;
	private int score = 0;

	public World(int width, int height,int numPlayers) {
		this.width = width;
		this.height = height;

		players = new ArrayList<Player>(numPlayers);

		for(int i=1;i<=numPlayers;i++){
			Player x =  new Player(100*numPlayers - 100*(numPlayers-i),height-100, i);
			players.add(x);
			this.addChild(x);
		}

		Block block = new Block(500, height-100, 1);

		this.addChild(block);

		floor = new Block(0, height - GRID_SIZE, (width/GRID_SIZE)*2);
		addChild(floor);

		addChild(new Block(400, height - 50, 1));
	}

	public void update() {
		//endless floor
		if (floor.getX() < -floor.getWidth()/2) {
			floor.setX(0);
		}
		
		for (Player p : players) {
			boolean grounded = false;
			for (Actor a : actors) if (a instanceof Obstacle) {
				if (p.willCollideWith(a) && p.isAbove(a)) {
					grounded = true;
					p.moveToContact(a);
				} else if (p.willCollideWith(a)) {
					p.collideRight((Obstacle) a);
				}
			}
			if (!grounded)
				p.fall();
			
			//check for movement
			double m = p.getYMotion();
			if (m < -3f) {
				p.jump();
			} else if (m > 3f) {
				System.out.println("Player " + p.getNumber() + " ducked!");
			}
			
			if (score % 20 == 0)
				p.addScore(10);
		}
		
		//update instances
		for (Actor a : actors) {
			a.move();
			if (a.getX() + a.getWidth() < 0) 
				garbage.add(a);
		}
		
		//garbage collection
		for (Actor a : garbage)
			actors.remove(a);
		garbage.clear();
		
		score ++;
		/*if (SPEED < 5)
			SPEED += 0.01;
		else SPEED = 5;*/
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        AffineTransform oldXform = g2.getTransform();
        g2.scale(SCALE, SCALE);
        g2.setBackground(Color.DARK_GRAY);
		g.clearRect(0, 0, width, height);
		
		for (Actor a : actors)
			a.paint(g);
		
		g2.setColor(Color.red);
		g2.setFont(font);
		int i = 0;
		for (Player p : getPlayers()) {
			Rectangle r = p.getRect();
			if (r != null)
				g2.drawRect((int)(r.x/SCALE), (int)(r.y/SCALE), (int)(r.width/SCALE), (int)(r.height/SCALE));
			int x = width / getPlayers().size();
			g2.drawString(p.getScore() + "", x*i++, 18);
			//g2.drawString(p.getYMotion() + "", x*i++, 18);
		}
		
		g2.setTransform(oldXform);
	}

	public void addChild(Actor a) {
		actors.add(a);
	}

	public void removeChild(Actor a) {
		actors.remove(a);
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}
}
