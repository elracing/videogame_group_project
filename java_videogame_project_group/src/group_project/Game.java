package group_project;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;



public class Game extends GameBase{
	public static final int S = 32;
	String[] map = {
			"......................................",
			"......................................",
			"......................................",
			"......................................",
			"......................................",
			"...EEFC...............................",
			"AAAAAAAAAAAAAA........................",
			".............AAAA.....................",
			"......................................",
			"......................................",
			"......AAAAAAAAAAAAAAAA................",
			"............................AA........",
			".........................AAAAA........",
			"....................D.AAAA............",
			".............AAAAAAAAAAAAAAA..........",
			"...AA......AA.........................",
			".....AA...............................",
			".......AA.............................",
			"...AAAAAAAAAAAA.......................",
			"................AAAA..................",
			"....................AA................",
			"......................................",
			"......E.AA..............AFAAA...........I..I...I.",
			"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBB"
		};
	
	
	Player player = new Player(0, 0); //make player
	ArrayList<Enemy> enemies = new ArrayList<>(); //add your enemies to this list, you can control behavior as a whole this way
	ArrayList<Rect> tile = new ArrayList<>(); //placeholder list for tiles, made this to make floor overlap logic
	int gravity = 1; //used to calculate falls

	Image background = getImage("Background.png");
	Image image2 = getImage("Tile_25.png");//A
	Image image3 = getImage("Tile_40.png");//B
	Image image4 = getImage("tree1.png");//C
	Image image5 = getImage("tree2.png");//D
	Image image6 = getImage("4.png");//E Bush
	Image image7 = getImage("9.png");//F Bush
	//Image image8 = getImage("Key.png");//G Key (key can be used to take us to the next level)
	//Image image9 = getImage("Coin.png");//H Coin (coins are collected for powerup)
	Image image10 = getImage("2.png"); // I dead tree, this will hint at the next level


	
	
	public void inGameLoop() {
		double currentTime = System.currentTimeMillis(); //track time, used to calculate time on things, like jumps.
		
			player.moving = false;

			
		int top = player.y;
		int bottom= player.y + player.h-1;
		int left = player.x;
		int right = player.x + player.w-1;
			
		//change directions
			if(pressing[LT] && !player.attacking) {   
				if( (map[top/S].charAt((left-S/8)/S) !='A' ) && (map[bottom/S].charAt((left-S/8)/S) != 'A') ) {
				player.go_LT(S/8);

				 }
			}
			if(pressing[RT] && !player.attacking)   {
				if ((map[top/S].charAt((right+S/8)/S) !='A') && (map[bottom/S].charAt((right+S/8)/S) != 'A')){
				player.go_RT(S/8);
				}
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

		
	        player.onPlatform = false;
	        

//This allowed better logic for jump, making sure that as we jumped we didnt collide with any tiles
	        for (Rect platform : tile) {
	            // Check downward collision 
	            if (player.yVelocity > 0 && player.overlaps(platform)) {
	               
	                int platformTop = platform.y;

	                if (bottom <= platformTop + player.yVelocity) {
	                    player.y = platform.y - player.h;
	                    player.yVelocity = 0;
	                    player.onPlatform = true;
	                    player.jumping = false;
	                    break;
	                }
	            }

	            // Check upward collision
	            else if (player.yVelocity < 0 && player.overlaps(platform)) {
	               
	                int platformBottom = platform.y + platform.h;

	                if (top >= platformBottom + player.yVelocity) {
	                    player.y = platformBottom;
	                    player.yVelocity = 0;
	                    break;
	                }
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
//draws background
		pen.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		//this draws images based on array's indicated cols and rows
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length(); col++) {
				char c = map[row].charAt(col);
				if (c == 'A')pen.drawImage(image2, S * col, S * row, S, S, null);
				if (c == 'B')pen.drawImage(image3, S * col, S * row, S, S, null);
				if (c == 'C')pen.drawImage(image4, S * col, S * row, S, S, null);
				if (c == 'D')pen.drawImage(image5, S * col, S * row, S, S, null);
				if (c == 'E')pen.drawImage(image6, S * col, S * row, S, S, null);
				if (c == 'F')pen.drawImage(image7, S * col, S * row, S, S, null);
				if (c == 'I')pen.drawImage(image10, S * col, S * row, S, S, null);	
			}
		}
		
		player.draw(pen);	//draw player here					
	}
	public void initialize() {
		for (int row = 0; row < map.length; row++) {
	        for (int col = 0; col < map[row].length(); col++) {
	            char c = map[row].charAt(col);
	            if (c == 'A' || c == 'B' ) {
	                tile.add(new Rect(col * S, row * S, S, S));
	            }
	        }
	    }
}


}
