
public class Actor {
	
	protected int x;
	protected int y;
	private String imagePath;
	
	public Actor(int x, int y, String imagePath ){
		this.x = x;
		this.y =y; 
		
		//add imagePath
	}
	
	//gets x-coordinate 
	public int getX(){
		return x;
		
	}
	public String getImagePath(){
		return imagePath;
	}
	//gets y-coordinate 
	public int getY(){
		return y;
	}
	
	
	
}