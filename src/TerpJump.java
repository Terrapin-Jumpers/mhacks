import java.awt.Toolkit;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TerpJump extends JFrame implements Runnable {
	World world;
	
	public TerpJump() {
		super("TerpJump");
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		this.setVisible(true);
		this.setSize(xSize, ySize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new TerpJump();
	}
	
	public void run() {
		
	}
}