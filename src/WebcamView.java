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
	
	private static final int FPS = 15;
	private static final int REFRESH_RATE = 1000/FPS; // time in MS
	
	public WebcamView() {
		super("Webcam");
		
		webcam = new Webcam();
		
		panel = new WebcamPanel();
		this.add(panel);
		
		setVisible(true);
		setSize(1280, 800);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new WebcamView().run();
	}

	@Override
	public void run() {
		while (true) {
			Mat frame = webcam.getFrame();
			panel.setFaces(webcam.getNumFaces(frame));
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
	
	private BufferedImage frame = null;
	private int numFaces = 0;
	
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
		g2.setColor(Color.cyan);
		g2.setFont(font);
		g2.drawString(numFaces + "", 40, 120); 
		
		g2.setTransform(oldXform);
	}
}