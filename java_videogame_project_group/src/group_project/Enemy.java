//package group_project;



/*public class Enemy extends Rect{ //TODO, implement the enemy class, feel free to add sprites, class created just so work on interactions for now
	
	int health = 50; //base health, feel free to multiply this when creating an enemy object for different enemies
	int attackPower = 10; //same concept as health
	boolean onPlatform; //placeholder to detect if enemy is standing on a platform for overlap purposes
	
	
	
	public Enemy(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	
	

} */

package group_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Enemy extends Sprite {

    int health = 50;
    int attackPower = 10;
    boolean onPlatform = false;
    int yVelocity = 0;
    boolean movingRight = true;
    
    boolean dying = false;
    int dyingFrame = 0;
    boolean readyToRemove = false;
    int dyingDelay = 5; // controls how long each dying frame last
    int dyingDelayCounter = dyingDelay;

    static String[] enemyPose = {"rt", "attack_rt", "dying_rt"};

    public static final int WALK_RT = 0;
    public static final int ATTACK_RT = 1;
    public static final int DYING_RT = 2;

    public Enemy(int x, int y) {
        super("enemy", x, y, 64, 64, enemyPose, 18, 2);
    }
    
    public void startAttacking() {
    	attacking = true;
    	current_pose = ATTACK_RT; //Always use right pose and flip image if needed
    }
    
    public void stopAttacking() {
    	attacking = false;
    	current_pose = WALK_RT;
    }

    @Override
    public void draw(Graphics pen) {
        Image img = null;
        int yOffset = 5;

        if (dying) {
            //img = animation[DYING_RT].nextImage();
        	img = animation[DYING_RT].image[Math.min(dyingFrame,  animation[DYING_RT].image.length -1)];
        } else if (attacking) {
            img = animation[ATTACK_RT].nextImage();
        } else {
            img = animation[WALK_RT].nextImage();
        }

        if (img != null) {
            if (movingRight) {
                pen.drawImage(img, x, y , w, h + yOffset, null);
            } else {
                pen.drawImage(img, x + w, y , -w, h + yOffset, null);
            }
        } else {
            pen.setColor(Color.MAGENTA);
            pen.drawRect(x, y, w, h);
        }
    }

    public void updateAI(Player player, String[] map, int tileSize) {
        if (dying) return; //skip AI if dying

        //check player range only if alive
       if(isPlayerInAttackRange(player)) {
    	   startAttacking();
    	   //stop moving when attacking
    	   return;
       } else {
    	   stopAttacking();
       }

        if (!attacking && onPlatform) {
            int footX = movingRight ? x + w : x - 1;
            int footY = y + h + 1;
            int tileFrontX = footX / tileSize;
            int tileBelowY = footY / tileSize;

            if (tileBelowY >= map.length || tileFrontX < 0 || tileFrontX >= map[0].length()) {
                flipDirection();
                return;
            }

            char belowTile = map[tileBelowY].charAt(tileFrontX);
            char frontTile = map[(y + h / 2) / tileSize].charAt(tileFrontX);

            boolean isSolidBelow = isTile(belowTile);
            boolean isBlockedFront = isTile(frontTile);

            if (!isSolidBelow || isBlockedFront) {
                flipDirection();
            }

            if (movingRight) {
                go_RT(2);
            } else {
                go_LT(2);
            }
        }
    }
    
    public boolean isPlayerInAttackRange(Player player) {
    	int attackRange = 100; // Attack distance on x axis
    	int verticalTolerance = 30; //Allow small Y difference to account for slight position offsets
    	
    	//Calculate center points for better accuracy
    	int enemyCenterX = x + w/2;
    	int playerCenterX = player.x + player.w /2;
    	int enemyCenterY = y + h/2;
    	int playerCenterY = player.y + player.h /2;
    	
    	
    	boolean closeInX = Math.abs(playerCenterX - enemyCenterX) <= attackRange;
    	boolean alignedY = Math.abs(playerCenterY - enemyCenterY) <= verticalTolerance;
    	
    	return closeInX && alignedY;
    }

    public void updatePhysics(int gravity) {
        if (dying) return;
        yVelocity += gravity;
        y += yVelocity;
        onPlatform = false;
    }
    
    public void takeDamage(double damage) {
    	health -= damage;
    	if(health <= 0 && !dying) {
    		startDying();
    	}
    }

    public void checkPlatformCollision(Rect platform) {
        if (dying) return;
        if (yVelocity > 0 && overlaps(platform)) {
            int enemyBottom = y + h;
            int platformTop = platform.y;
            if (enemyBottom <= platformTop + yVelocity) {
                y = platform.y - h;
                yVelocity = 0;
                onPlatform = true;
            }
        }
    }

    private void flipDirection() {
        movingRight = !movingRight;
    }

    private boolean isTile(char c) {
        return c == 'A' || c == 'B' || c == 'K';
    }

    public void startDying() {
        dying = true;
        dyingFrame = 0;
        current_pose = DYING_RT;
        //attacking = false;
        //moving = false;
    }
    
    public void attackPlayer(Player player) {
    	if(dying) return;
    	player.takeDamage(attackPower);
    }
    
    public void updateDying() {
    	if(dying) {
    		dyingDelayCounter--;
    		if(dyingDelayCounter <= 0) {
    			    dyingFrame++;
    			    dyingDelayCounter = dyingDelay; //Reset delay counter
    			if(dyingFrame >= 15) { //After full 15 frames
    				
    				readyToRemove = true; //Mark for removal
    			}
    			    
    			    
    		}
    	}
    }
}

