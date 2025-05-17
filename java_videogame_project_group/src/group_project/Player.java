package group_project;


public class Player extends Sprite{
	
	int starsCollected = 0; //variable for "star" powerups
	int health = 100;
	double attackPower = 10 + (starsCollected * 0.10);  //increases attack based on stars collected
	boolean onPlatform = false; //detects if the player is on a platform
	
	long lastHitTime = 0;
	long hitCooldown = 1000; //1000ms (1 second) invulnerability after hit
	
	
	public final int jump_strength = -15; // negative = up


	
	
	
	//pose array
	
	static String[] pose = {"lt", "rt", "attack_lt", "attack_rt", "hurt_lt", "hurt_rt", "jump_lt", "jump_rt"}; //holds current poses for the character
		
	//constructor
	
	public Player(int x, int y) {
		super("player" , x, y, 64, 64 , pose, 15, 1); //constructor for player
	}
	
	public void takeDamage(int damage) {
		System.out.println("Player health before damage: " + health);
		health -= damage;
		if(health < 0) health = 0;
		System.out.println("Player took damage: " + damage + ". New health: " + health);
		//you can trigger hurt animation here if you want
	}

}
