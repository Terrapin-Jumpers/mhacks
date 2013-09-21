import java.util.ArrayList;
import java.util.List;

public class Coin extends Actor {
	protected final int POINTS = 50;
	protected List<Integer> touched = new ArrayList<Integer>();
	
	public Coin(int x, int y) {
		super(x, y);
	}

	
}
