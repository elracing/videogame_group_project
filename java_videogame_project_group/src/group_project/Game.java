package group_project;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;



public class Game extends GameBase{
	
	
	Player player = new Player(100, 50); //make player
	ArrayList<Enemy> enemies = new ArrayList<>(); //add your enemies to this list, you can control behavior as a whole this way
	ArrayList<Rect> tile = new ArrayList<>(); //placeholder list for tiles, made this to make floor overlap logic
	int gravity = 1; //used to calculate falls

	
	
	public void inGameLoop() {
		double currentTime = System.currentTimeMillis(); //track time, used to calculate time on things, like jumps.
		
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
			
			if(pressing[SPACE] && (player.onPlatform) && (player.current_pose == Sprite.RT)) { //adds a jump by yVelocity, RT
				player.jump_RT();
				player.yVelocity = player.jump_strength;
			    player.onPlatform = false;
			}
			
			if(pressing[SPACE] && (player.onPlatform) && (player.current_pose == Sprite.LT)) { //adds a jump by yVelocity, RT
			    player.jump_LT();
				player.yVelocity = player.jump_strength;
			    player.onPlatform = false;
			}
			
			
			
			//more movement & gravity logic
			
			
	        player.yVelocity += gravity; // changes velocity given gravity
	        player.updatePosition(); //updates y position based on current velocity
			

			
	        for (Rect platform : tile) {
	            if (player.overlaps(platform)) {
	                player.y = platform.y - player.h; //keeps player on top of the platform, y adjusted for visual purposes
	                player.yVelocity = 0;
	                player.onPlatform = true;
	                player.jumping = false;
	                break;
	            }
	        }

	        // Reset gravity acceleration if on ground
	        if (player.onPlatform) {
	            gravity = 1;
	        }
			
			
			
			//enemy damage
			Iterator<Enemy> iter = enemies.iterator(); //use iterator to safety remove enemies
			while (iter.hasNext()) {
				Enemy enemy = iter.next();
			
				if (player.attacking & player.overlaps(enemy)) { //if attacking, deal damage to enemy
					enemy.health -= player.attackPower;
				}
				
				if (enemy.overlaps(player) && !player.attacking) { //if not attacking, receive damage
					player.health -= enemy.attackPower;
				}
				
				if (enemy.health <= 0) { //"kills" the enemy when health is 0 safety (no need to worry about nulls)
					iter.remove();
				}
			}
			
			
			
			
	}
		
	
	
	


	public void paint (Graphics pen) {

		
		player.draw(pen);	//draw player here		
		for (Rect tile : tile) {
		tile.draw(pen);
		}
		
		
	}
	public void initialize() {
		
		tile.add(new Rect(player.x, player.y + 500, 2000, 20)); //test floor
		

}


}
