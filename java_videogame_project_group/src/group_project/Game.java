package group_project;

import java.awt.Graphics;

public class Game extends GameBase{
	
	
	Player player = new Player(100, 50); //make player
	
	

	
	
	public void inGameLoop() {
		
			player.moving = false;
	
		//change directions
			if(pressing[LT] && !player.attacking) {   
				player.go_LT(5);
			}
			if(pressing[RT] && !player.attacking)   {
				player.go_RT(5);
			}
			
			if(pressing[_F] && (player.current_pose == Sprite.RT))   { //press f to attack, right side position
				player.attack_RT();
			}
			
			if(pressing[_F] && (player.current_pose == Sprite.LT))   {//press f to attack, left side position
				player.attack_LT();
			}
			
			
			
			
	}
		
	
	
	


	public void paint (Graphics pen) {

		
		player.draw(pen);	//draw player here		
		
	}
	public void initialize() {
		


}


}
