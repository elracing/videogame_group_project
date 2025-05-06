package group_project;


public class Player extends Sprite{
	
	int starsCollected = 0; //variable for "star" powerups
	int health = 100;
	double attackPower = 10 + (starsCollected * 0.10);  //increases attack based on stars collected
		
	//pose array
	
	static String[] pose = {"lt", "rt", "attack_lt", "attack_rt", "hurt_lt", "hurt_rt"}; //holds current poses for the character
		
	//constructor
	
	public Player(int x, int y) {
		super("player" , x, y, 200, 200 , pose, 15, 1); //constructor for player
	}

}
