
public class Obstacle  extends Actor{
	
	protected int damage;
	
	public Obstacle(int x,int y,String path,int damage){
		//calls super constuctor passing values of x,y, and the image path
		super(x,y,path);
		
		//sets damage to the parameter
		this.damage = damage;
		
	}
	
	public void move(){
		
	}
}
