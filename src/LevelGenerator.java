
import java.util.ArrayList;


public class LevelGenerator {
	
	private World world;
	private int maxX, maxY;
	private int maxSuperY;
	
 	private int currentHeight;
 	private int next = 0;
 	private int nextY = 0;
 	private float time = 0;
	
	public LevelGenerator(World world) {
		this.world = world;
		currentHeight = WebcamView.GAME_HEIGHT - World.GRID_SIZE;
		
		Player copy = new Player(0, 0, 1);
		//float iterations = Player.JUMP_SPEED/Player.gravity;
		int startY = copy.getY();
		copy.setOnGround();
		copy.jump();
		while(copy.getY() <= startY){
			copy.move();
			if (copy.getY() < maxY)
				maxY = copy.y;
			
			maxX += World.SPEED;
		}
		maxSuperY = (int) (maxY*1.5f);
		
		//System.out.println("max height" + maxY + " max x " + maxX);
	}
	
	public void generate() {
		if (next == 0) { //setup random creation point
			
			next = (int)(Math.random()*(maxX % World.GRID_SIZE + 2)) + 2;
			nextY = (int)(Math.random()*(-maxY % World.GRID_SIZE - 1)) + 1;
		} else if (next == 1) { //create block
			Block b = new Block(WebcamView.GAME_WIDTH, WebcamView.GAME_HEIGHT - nextY*World.GRID_SIZE, (int)(Math.random()*4 + 2));
			world.addChild(b);
			next = 0;
		}
		
		time += World.SPEED;
		
		if ((int) time % World.GRID_SIZE == 0)
			next --;
		//int r = getRan();
		//world.addChild(new Block(r,world.getWidth()),1);
	}
}
