import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;


public class WebcamView extends JFrame implements Runnable {
	private Webcam webcam;
	private WebcamPanel panel;
	
	private static int WIDTH = 1280;
	private static int HEIGHT = 800;
	
	private static final int FPS = 15;
	private static final int REFRESH_RATE = 1000/FPS; // time in MS
	
	public WebcamView() {
		super("Webcam");
		
		webcam = new Webcam();
		
		panel = new WebcamPanel(WIDTH, HEIGHT);
		this.add(panel);
		
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new WebcamView().run();
	}

	@Override
	public void run() {
		while (true) {
			Mat frame = webcam.getFrame();
			webcam.getNumFaces(frame);
			panel.setFaces(webcam.getFinalFaces());
			BufferedImage image = webcam.matToBufferedImage(frame);
			panel.setFrame(image);
			panel.repaint();
			try {
				Thread.sleep(REFRESH_RATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class WebcamPanel extends JPanel {
	
	private int width, height;
	
	public WebcamPanel(int width, int height) {
		this.width = width;
		this.height = height;
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
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform oldXform = g2.getTransform();
        g2.scale(1f, 1f);
        
		g.drawImage(frame, 0, 0, null);
		Font font = new Font("Serif", Font.PLAIN, 80);
		g2.setColor(Color.red);
		g2.setFont(font);
		if (numFaces <= 0)
			g2.drawString(still, 40, 120);
		else {
			g2.setColor(Color.green);
			//g2.drawString("OK! " + numFaces + " Players!", 40, 120);
			
			for (int i=1; i < numFaces; i++) {
				int x = width / numFaces;
				g2.fillRect(x*i - 3, 0, 3, height);
			}
		}
		
		g2.setTransform(oldXform);
	}
}