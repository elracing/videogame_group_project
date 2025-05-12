package group_project;



public class Enemy extends Rect{ //TODO, implement the enemy class, feel free to add sprites, class created just so work on interactions for now
	
	int health = 50; //base health, feel free to multiply this when creating an enemy object for different enemies
	int attackPower = 10; //same concept as health
	boolean onPlatform; //placeholder to detect if enemy is standing on a platform for overlap purposes
	
	
	
	public Enemy(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	
	

}
