package group_project;


public class Player extends Sprite{
	
	int starsCollected = 0;
	int health = 100;
	double attackPower = 10 + (starsCollected * 0.10);
		
	//pose array
	
	static String[] pose = {"lt", "rt", "attack_lt", "attack_rt", "hurt_lt", "hurt_rt"};
		
	//constructor
	
	public Player(int x, int y) {
		super("player" , x, y, 200, 200 , pose, 15, 1);
	}

}
