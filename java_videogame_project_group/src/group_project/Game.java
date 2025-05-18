//restarted protion
package group_project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;





public class Game extends GameBase{
	public static final int S = 32;
	//int enemiesDefeated = 0; //tracks enemies killed
	int killThreshold = 10; //number needed to reach in order to trigger key spawn
	
	String[][] map = {
			{
			//level 1
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
			"....................C.AAAA............",
			"..................AAAAAAAAAAAAAAA.....",
			"...AA..........AA.....................",
			".....AA...............................",
			".......AA.............................",
			"...AAAAAAAAAAAA.......................",
			"................AAAA..................",
			"....................AA................",
			"......................................",
			".C....E.AA................AFAAA...G...",
			"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
		},
			//level 2
			
		{
			"......................................",
			"......................................",
			"......................................",
			"......................................",
			"......................................",
			"......................................",
			"........D........D....................",
			".KKKKKKKKKKKKKKKKKKKK.................",
			"......................................",
			"..............................D.......",
			".......................KKKKKKKKKKKK...",
			"......................................",
			"......................D...............",
			".................KKKKKKKKKKKK.........",
			"......................................",
			"...............DJ.........D...........",
			"......KKKKKKKKKKKKKKKKKKKKKKKKK.......",
			"......................................",
			"DJ....................................",
			"KKKK..................................",
			"KKKKKKK...............................",
			"KKKKKKKKKK............................",
			"KKKKKKKKKKKKK.............D.....G..D..",
			"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK"
		},
		//level 3
		{
			"......................................",
			"......................................",
			"......................................",
			"....L..I..IJ...I...J..................",
			"....BBBBBBBBBBBBBBBBBB................",
			"......................................",
			"..........................BBBBB.......",
			"....................BBBBB.............",
			"....B............BBB..................",
			"....B........BBBB.....................",
			"....B.....BBB.........................",
			"....B.................................",
			"....B.IJ..............................",
			"....BBBBBBBBB......M.................",
			"...............BBBBBBBB...J...........",
			".........................BBBBB........",
			"......................................",
			"..................................J..E",
			".........................I....BBBBBBBB",
			"......................BBBBBBBB........",
			".L.I..............BBBB................",
			"BBBBBB...JJ...........................",
			"BBBBBBBBBBBBBBBL...M...IJM........G..F",
			"BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"
		}
	};
	int currentLevel = 0;
	String[] currmap = map[currentLevel];
	int[] enemiesDefeatedByLevel = new int[map.length];
			
	
	Player player = new Player(0, 0); //make player
	ArrayList<Enemy> enemies = new ArrayList<>(); //add your enemies to this list, you can control behavior as a whole this way
	ArrayList<Rect> tile = new ArrayList<>(); //placeholder list for tiles, made this to make floor overlap logic
	Troll boss = null;
	int gravity = 1; //used to calculate falls

	Image background = getImage("Background.png");
	Image image2 = getImage("Tile_25.png");//A tile for level 1
	Image image3 = getImage("Tile_40.png");//B tile for level 3
	Image image4 = getImage("tree1.png");//C tree for level 1
	Image image5 = getImage("tree2.png");//D tree for level 2
	Image image6 = getImage("4.png");//E Bush for level 1
	Image image7 = getImage("9.png");//F Bush for level1 
	Image image8 = getImage("Key.png");//G Key (key can be used to take us to next level)
	//Image image9 = getImage("Coin.png");//H Coin (coins are collected for powerup)
	Image image10 = getImage("2.png"); // I ...tree for level 3
	Image background2_1 = getImage("background2-1.png");
	Image background2_2 = getImage("background2-2.png");
	Image background3 = getImage("background3.png");
	Image image11 = getImage("10.png");// J this is grass for level 3
	Image image12 = getImage("Tile_56.png"); //K tile  for level 2
	Image image13 = getImage("5.png");//L stone for level 3
	Image image14 = getImage("3.png");//M stone for level1 =3
	
	Image testEnemy = getImage("enemy_dying_rt_15.png");
	
	BufferedImage[] key = new BufferedImage[4];
	int keyFrameIndex = 0;
	long lastKeyFrameTime = System.currentTimeMillis();
	long keyFrameDelay = 150;
	
	
	public void inGameLoop() {
		 //track time, used to calculate time on things, like jumps.
		double currentTime = System.currentTimeMillis();
		
		player.moving = false;
			
		int top = player.y;
		int bottom= player.y + player.h-1;
		int left = player.x;
		int right = player.x + player.w-1;
		//created variables to make it easier to calculate the boundaries
		int leftTile = (left - S / 8) / S;
		int rightTile = (right + S / 8) / S;
		int topTile = top / S;
		int bottomTile = bottom / S;
		
		//animating the key
		long now = System.currentTimeMillis();
		if (now - lastKeyFrameTime >= keyFrameDelay) {
		    keyFrameIndex = (keyFrameIndex + 1) % key.length;
		    lastKeyFrameTime = now;
		}
			
	
		//change directions
	

		//when player goes left or right it cant walk through the tile
		//had to modify it because it was giving me an error when the player went towards the right boundary, it froze the game
		//this section also had to get fixed after levels were added, since each level has different chars for the tiles, had to settle with creating a boolean method
		if (pressing[LT] && !player.attacking) {
		    if (topTile >= 0 && bottomTile < currmap.length && leftTile >= 0 && leftTile < currmap[0].length()) {
		        if (!isTile(currmap[topTile].charAt(leftTile)) && !isTile(currmap[bottomTile].charAt(leftTile))) {
		            player.go_LT(S / 8);
		        }
		    }
		}
		if (pressing[RT] && !player.attacking) {
		    if (topTile >= 0 && bottomTile < currmap.length && rightTile >= 0 && rightTile < currmap[0].length()) {
		        if (!isTile(currmap[topTile].charAt(rightTile)) && !isTile(currmap[bottomTile].charAt(rightTile))) {
		            player.go_RT(S / 8);
		        }
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
			
			//makes it so that the player does not go out of bounds
			if (player.x < 0) {
			    player.x = 0;
			}

			int maxX = currmap[0].length() * S - player.w ; // max position based on the tile map width
			if (player.x > maxX) {
			    player.x = maxX;
			}
			
			
			
			//more movement & gravity logic
	        player.yVelocity += gravity; // changes velocity given gravity
	        player.updatePosition(); //updates y position based on current velocity
			


	        player.onPlatform = false;
	        


	        for (Rect platform : tile) {
	            // Check downward collision 
	            if (player.yVelocity > 0 && player.overlaps(platform)) {
	                int playerBottom = player.y + player.h;
	                int platformTop = platform.y;

	                if (playerBottom <= platformTop + player.yVelocity) {
	                    player.y = platform.y - player.h;
	                    player.yVelocity = 0;
	                    player.onPlatform = true;
	                    player.jumping = false;
	                    break;
	                }
	            }

	            // Check upward collision
	            else if (player.yVelocity < 0 && player.overlaps(platform)) {
	                int playerTop = player.y;
	                int platformBottom = platform.y + platform.h;

	                if (playerTop >= platformBottom + player.yVelocity) {
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
	        
	        //this loads the next level when the sprite collides with the key
	        int centerX = (player.x + player.w / 2) / S;
	        int bottomY = (player.y + player.h - 1) / S;

	        if (bottomY >= 0 && bottomY < currmap.length && centerX >= 0 && centerX < currmap[bottomY].length()) {
	            char tile = currmap[bottomY].charAt(centerX);
	            if (tile == 'G' && enemiesDefeatedByLevel[currentLevel] >= killThreshold) loadNextLevel();
	        }

	        //Enemy gravity, platform detection, and patrol logic
	        for (Enemy enemy : enemies) {
	            enemy.updatePhysics(gravity);
	            for (Rect platform : tile) {
	                enemy.checkPlatformCollision(platform);
	            }
	            if (enemy.onPlatform) {
	                enemy.updateAI(player,currmap, S);
	            }
	            
	            enemy.updateDying();
	        }
	        
	        if(boss != null) {
	        	boss.updatePhysics(gravity);
	        	for (Rect platform : tile) boss.checkPlatformCollision(platform);
	        	boss.updateAI(player, currmap, S);
	        }
			
			
			//enemy damage
			Iterator<Enemy> iter = enemies.iterator(); //use iterator to safety remove enemies
			while (iter.hasNext()) {
				Enemy enemy = iter.next();
			
				if (player.attacking && player.overlaps(enemy)) { //if attacking, deal damage to enemy
					enemy.takeDamage(player.attackPower);
				}
				
				if (enemy.overlaps(player) && !player.attacking && !enemy.dying) {//if not attacking, receive damage
					 now = System.currentTimeMillis();
					if(now - player.lastHitTime >= player.hitCooldown) {
						enemy.attackPlayer(player);
						player.health -= enemy.attackPower;
					}
					
				}
				
			/*	if(enemy.isPlayerInAttackRange(player , currmap, S)) {
					enemy.startAttacking();
				} else {
					enemy.stopAttacking();
				}
			*/
				
				//Let enemy handle its own dying update 
				enemy.updateDying();
				
				if(enemy.readyToRemove) {
					iter.remove();
					enemiesDefeatedByLevel[currentLevel]++;
				
				/*if (enemy.health <= 0 && !enemy.dying) { //"kills" the enemy when health is 0 safety (no need to worry about nulls)
					enemy.startDying();
					enemiesDefeated++;
				*/
					//iter.remove();
					//enemiesDefeated++;
				}
			}
			
			if(boss != null) {
				if(player.attacking && player.overlaps(boss)) {
					boss.takeDamage(player.attackPower);
				}
				
				boss.updatePhysics(gravity);
				for(Rect platform : tile) boss.checkPlatformCollision(platform);
				boss.updateAI(player, currmap, S);
				boss.updateDying();
				
				if(boss.readyToRemove) {
					boss = null;
					enemiesDefeatedByLevel[currentLevel]++;
				}
			}
			
			
	}
		
	
	
	


	public void paint (Graphics pen) {
		
		//draws background
		drawBackground(pen);
		
	
		//this draws images based on array's indicated cols and rows
		for (int row = 0; row < currmap.length; row++) {
			for (int col = 0; col < currmap[row].length(); col++) {
				char c = currmap[row].charAt(col);
				if (c == 'A')pen.drawImage(image2, S * col, S * row, S, S, null);
				if (c == 'B')pen.drawImage(image3, S * col, S * row, S, S, null);
				if (c == 'C')pen.drawImage(image4, S * col, S * row, S, S, null);
				if (c == 'D')pen.drawImage(image5, S * col, S * row, S, S, null);
				if (c == 'E')pen.drawImage(image6, S * col, S * row, S, S, null);
				if (c == 'F')pen.drawImage(image7, S * col, S * row, S, S, null);
				if (c == 'I')pen.drawImage(image10, S * col, S * row, S, S, null);
				if (c == 'G' && key[keyFrameIndex] != null && enemiesDefeatedByLevel[currentLevel] >= killThreshold && currentLevel < 2) pen.drawImage(key[keyFrameIndex], S * col, S * row, S, S, null);
				if (c == 'J')pen.drawImage(image11, S * col, S * row, S, S, null);
				if (c == 'K')pen.drawImage(image12, S * col, S * row, S, S, null);
				if (c == 'L')pen.drawImage(image13, S * col, S * row, S, S, null);
				if (c == 'M')pen.drawImage(image14, S * col, S * row, S, S, null);
				
			}
		}
		
		
		player.draw(pen);	//draw player here
		
		//Draw all enemies
		for (Enemy enemy: enemies) {
			enemy.draw(pen);
		}
		
		if(boss != null) boss.draw(pen);
		
		//pen.drawImage(testEnemy, 400, 400, 64, 64, null);
		
	
	}
	
	public void initialize() {
		currmap = map[currentLevel];
		tile.clear();
		enemies.clear();
		boss = null;
		
		//adjust enemy positions (Level 1 only)

		//Enemies are placed on known solid tiles to ensure proper patrol
		
		// Set the map
		    
		for (int row = 0; row < currmap.length; row++) {
	        for (int col = 0; col < currmap[row].length(); col++) {
	            char c = currmap[row].charAt(col);
	            if (c == 'A' || c == 'B' || c=='K') {
	                tile.add(new Rect(col * S, row * S, S, S));
	            }
	        }
	    }
		
		if (currentLevel == 0) {
			  enemies.clear();
		        enemies.add(new Enemy(6 * S, 5 * S - 64));
		        enemies.add(new Enemy(20 * S, 10 * S - 64));
		        enemies.add(new Enemy(10 * S, 10 * S - 64));
		        enemies.add(new Enemy(18 * S, 14 * S - 64));
		        enemies.add(new Enemy(26 * S, 14 * S - 64));
		        enemies.add(new Enemy(34 * S, 14 * S - 64));
		        enemies.add(new Enemy(15 * S, 17 * S - 64));
		        enemies.add(new Enemy(23 * S, 18 * S - 64));
		        enemies.add(new Enemy(30 * S, 18 * S - 64));
		        enemies.add(new Enemy(11 * S, 22 * S - 64));
		}
		
		 if (currentLevel == 1) {
			  enemies.clear();
		        enemies.add(new Enemy(2 * S, 6 * S - 64)); 
		        enemies.add(new Enemy(10 * S, 11 * S - 64)); 
		        enemies.add(new Enemy(4 * S, 12 * S - 64)); 
		        enemies.add(new Enemy(28 * S, 7 * S - 64));
		        enemies.add(new Enemy(24 * S, 13 * S - 64)); 
		        enemies.add(new Enemy(22 * S, 15 * S - 64)); 
		        enemies.add(new Enemy(4 * S, 19 * S - 64));
		        enemies.add(new Enemy(5 * S, 21 * S - 64)); 
		        enemies.add(new Enemy(16 * S, 21 * S - 64));
		        enemies.add(new Enemy(20 * S, 7 * S - 64));
		} 
		
		 if (currentLevel == 2) {
            enemies.add(new Enemy(33 * S, 17 * S - 64));
            enemies.add(new Enemy(10 * S, 7 * S - 64));
            enemies.add(new Enemy(16 * S, 10 * S - 64));
            enemies.add(new Enemy(20 * S, 13 * S - 64));
            enemies.add(new Enemy(25 * S, 14 * S - 64));
            enemies.add(new Enemy(30 * S, 17 * S - 64));
            enemies.add(new Enemy(34 * S, 19 * S - 64));
            enemies.add(new Enemy(15 * S, 20 * S - 64));
            enemies.add(new Enemy(26 * S, 21 * S - 64));
            //boss = new Troll(35 * S, 21 * S - 160);
            boss = new Troll(3 * S, 4 *S -160);
        }
		
	    
	    //animating the key
	    try {
	        BufferedImage keySheet = ImageIO.read(new File("Key.png"));
	        int frameWidth = keySheet.getWidth() / 4;
	        int frameHeight = keySheet.getHeight();
	        for (int i = 0; i < 4; i++) {
	            key[i] = keySheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    // Reset player position
	    player.x = 0;
	    player.y = 0;
	    player.yVelocity = 0;
		
}
	
	public void loadNextLevel() {
		currentLevel++;
	    if (currentLevel >= map.length) {
	        currentLevel = 0; // loop back or end game
	    }
	    initialize(); // 
	}
	
	public void drawBackground(Graphics pen) {
		if (currentLevel == 0) pen.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	   if (currentLevel == 1) pen.drawImage(background3, 0, 0, getWidth(), getHeight(), null);
	   
	   if (currentLevel == 2) { 
		   pen.drawImage(background2_1, 0, 0, getWidth(), getHeight(), null);
       pen.drawImage(background2_2, 0, 0, getWidth(), getHeight(), null);}
	}
	
	public boolean isTile(char tile) {
		 return tile == 'A' || tile == 'B' || tile == 'K';
	}
	
	
	
}
