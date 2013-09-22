import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;


public class WebcamView extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private Webcam webcam;
	private WebcamPanel panel;
	
	static final int FULL_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	static final int FULL_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	static final int GAME_WIDTH = (int)(FULL_WIDTH/World.SCALE);
	static final int GAME_HEIGHT = (int)(FULL_HEIGHT/World.SCALE);
	
	private static final int FPS = 30;
	private static final int REFRESH_RATE = 1000/FPS; // time in MS
	
	boolean capture = true;
	boolean playing = false;
	
	private final static int INIT_STATE = 0, READY_STATE = 1, GAME_STATE = 2, WIN_STATE = 3;
	private int state = 0;
	
	private Rectangle[] boundaries;
	private Rectangle[] sections;
	
	World world;
	Mat frame = null;
	
	private int overTime = 0;
	
	public WebcamView() {
		super("Webcam");
		
		webcam = new Webcam();
		
		panel = new WebcamPanel(FULL_WIDTH, FULL_HEIGHT);
		this.add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FULL_WIDTH, FULL_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		new Thread(new Runnable() {
            public void run() {
            	while (true) {
            		synchronized (this) {
	   				 frame = webcam.getFrame();
	   				 webcam.getNumFaces(frame);
            		}
	   				 try {
	   					 Thread.sleep(REFRESH_RATE*2);
	   				 } catch (InterruptedException e) {
	   				 }
            	}
	        };
	    }).start();
	}
	
	public static void main(String[] args) {
		new WebcamView().run();
	}
	
	public void play() {
		if (!playing) {
			world = new World(GAME_WIDTH, GAME_HEIGHT, webcam.getFinalFaces());
			//this.add(world);
			panel.setWorld(world);
			playing = true;
			state = GAME_STATE;
		}
	}

	@Override
	public void run() {
		while (true) {
			
			switch (state) {
			case INIT_STATE:
				int faces = webcam.getFinalFaces();
				if (faces > 0) {
					panel.setFaces(faces);
					state = READY_STATE;
					boundaries = new Rectangle[faces];
					sections = new Rectangle[faces];
					int ww = FULL_WIDTH / faces;
					for (int i = 0; i < faces; i++) {
						boundaries[i] = new Rectangle(i*ww - 3, 0, 6, FULL_HEIGHT);
						sections[i] = new Rectangle(i*ww, 0, ww, FULL_HEIGHT);
					}
				}
				break;
			case READY_STATE:
				boolean ok = true;
				for (Rectangle rect : webcam.getFaces()) {
					for (Rectangle boundary : boundaries) {
						if (rect != null && boundary != null && rect.intersects(boundary)) {
							ok = false;
							break;
						}
					}
				}
				if (ok) {
					this.play();
				}
				break;
			case GAME_STATE:
				ArrayList<Player> players = world.getPlayers();
				for (Rectangle rect : webcam.getFaces())  {
					int i = 0;
					for (Rectangle sec : sections) {
						if (sec != null && rect != null && sec.contains(rect) && i < players.size()) {
							players.get(i).setRect(rect);
						} 
						i ++;
					}
				}
				if (world.isGameOver()) {
					state = WIN_STATE;
				}
				break;
			case WIN_STATE:
				if (overTime ++ > 120) {
					world = null;
					state = INIT_STATE;
					webcam.reset();
					playing = false;
					overTime = 0;
					frame = null;
					panel.reset();
				}
				break;
			}
			
			if (state < GAME_STATE && capture && frame != null) {
				BufferedImage image = webcam.matToBufferedImage(frame);
				panel.setFrame(image);
			}
			panel.repaint();
			
			//capture = !capture; //use camera every other step
			
			try {
				Thread.sleep(REFRESH_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class WebcamPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int width, height;
	private Font font;
	
	private World world = null;
	
	public WebcamPanel(int width, int height) {
		this.width = width;
		this.height = height;
		
		font = new Font("Sans-Serif", Font.PLAIN, 64);
	}
	
	private BufferedImage frame = null;
	private int numFaces = 0;
	String still = "Please stand still";
	
	public void setFrame(BufferedImage frame) {
		if (this.frame != null) frame.flush();
		this.frame = frame;
	}
	
	public void setFaces(int faces) {
		numFaces = faces;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void reset() {
		world = null;
		numFaces = -1;
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		//AffineTransform oldXform = g2.getTransform();
        //g2.scale(1f, 1f);
		g2.setColor(Color.red);
		g2.setFont(font);
		
		if (this.world != null) {
			if (!world.isGameOver()) {
				world.update();
				world.paint(g);
			} else {
				ArrayList<Player> players;
				players = world.getPlayers();
				FontMetrics fm = getFontMetrics( getFont() );
				String str;
				if (players.size() == 1) {
					str = "Game Over: " + players.get(0).getScore();
				} else {
					Player winner = world.getWinner();
					g2.setColor(Player.colors[winner.getNumber() - 1]);
					str = winner.getName() + " Player Wins!";
				}
				int ww = fm.stringWidth(str);
				g2.drawString(str, (WebcamView.GAME_WIDTH - ww)/2, 160);
			}
		} else {
			g.drawImage(frame, 0, 0, null);
		}
		
		if (numFaces <= 0)
			g2.drawString(still, 40, 120);
		else if (world != null && !world.isGameOver()) {
			g2.setColor(Color.green);
			//g2.drawString("OK! " + numFaces + " Players!", 40, 120);
			
			for (int i=1; i < numFaces; i++) {
				int x = width / numFaces;
				g2.fillRect(x*i - 3, 0, 3, height);
			}
		}
		
		//g2.setTransform(oldXform);
	}
}