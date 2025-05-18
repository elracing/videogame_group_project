package group_project;

import java.awt.Graphics;
import java.awt.Image;
public class Troll extends Sprite {
    int health = 100;
    int attackPower = 20;
    boolean onPlatform = false;
    int yVelocity = 0;
    boolean movingRight = true;

    boolean hurting = false;
    boolean dying = false;
    boolean readyToRemove = false;
    int dyingFrame = 0;
    int dyingDelay = 5;
    int dyingDelayCounter = dyingDelay;

    int hurtCooldown = 30;
    int hurtCounter = 0;

    int attackCooldown = 90;
    int attackCounter = 0;

    static String[] trollPose = {"walk_rt", "attack_rt", "hurt_rt", "jump_rt", "die_rt"};
    public static final int WALK_RT = 0;
    public static final int ATTACK_RT = 1;
    public static final int HURT_RT = 2;
    public static final int JUMP_RT = 3;
    public static final int DIE_RT = 4;

    public Troll(int x, int y) {
        super("Troll", x, y, 160, 160, trollPose, 10, 2);
    }

    public void draw(Graphics pen) {
        Image img = null;

        if (dying) {
            img = animation[DIE_RT].image[Math.min(dyingFrame, animation[DIE_RT].image.length - 1)];
        } else if (hurting) {
            img = animation[HURT_RT].nextImage();
            if (animation[HURT_RT].current == animation[HURT_RT].image.length - 1) {
                hurting = false;
                animation[HURT_RT].current = 0;
            }
        } else if (attacking) {
            img = animation[ATTACK_RT].nextImage();
        } else {
            img = animation[WALK_RT].nextImage();
        }

        if (img != null) {
            if (movingRight) {
                pen.drawImage(img, x, y, w, h + 25, null);
            } else {
                pen.drawImage(img, x + w, y, -w, h + 25, null);
            }
        }
    }

    public void updateAI(Player player, String[] map, int tileSize) {
        if (dying || hurting) return;

        if (!onPlatform) return;

        // Reuse enemy patrol logic
        int footX = movingRight ? x + w : x - 1;
        int footY = y + h + 1;
        int tileFrontX = footX / tileSize;
        int tileBelowY = footY / tileSize;
        int tileFrontY = (y + h / 2) / tileSize;

        boolean outOfBounds = tileBelowY >= map.length || tileFrontX < 0 || tileFrontX >= map[0].length();
        boolean fall = outOfBounds || !isTile(map[tileBelowY].charAt(tileFrontX));
        boolean frontBlocked = !outOfBounds && isTile(map[tileFrontY].charAt(tileFrontX));

        if (fall || frontBlocked) {
            flipDirection();
        } else {
            if (movingRight) go_RT(1);
            else go_LT(1);
        }

        // Attack only on same Y tile
        int playerFeetY = (player.y + player.h - 1) / tileSize;
        int trollFeetY = (y + h - 1) / tileSize;
        int playerCenterX = player.x + player.w / 2;
        int trollCenterX = x + w / 2;

        if (Math.abs(playerCenterX - trollCenterX) <= 96 && playerFeetY == trollFeetY) {
            //turn to face the player before attacking
        	if((playerCenterX > trollCenterX && !movingRight) ||
        		(playerCenterX < trollCenterX && movingRight)) {
        		flipDirection();
        	}
        	
        	if (attackCounter == 0) {
                attacking = true;
                attackCounter = attackCooldown;
            }
        }

        if (attackCounter > 0) {
            attackCounter--;
            if (attackCounter == 0) attacking = false;
        }
    }

    private boolean isTile(char c) {
        return c == 'A' || c == 'B' || c == 'K';
    }

    public void updatePhysics(int gravity) {
        if (dying) return;
        yVelocity += gravity;
        y += yVelocity;
        onPlatform = false;
    }

    public void checkPlatformCollision(Rect platform) {
        if (yVelocity > 0 && overlaps(platform)) {
            int bottom = y + h;
            int platformTop = platform.y;
            if (bottom <= platformTop + yVelocity) {
                y = platform.y - h;
                yVelocity = 0;
                onPlatform = true;
                jumping = false;
            }
        }
    }

    public void takeDamage(double damage) {
        if (dying || hurting) return;
        health -= damage;
        hurting = true;
        hurtCounter = hurtCooldown;
        if (health <= 0) {
            dying = true;
            dyingFrame = 0;
        }
    }

    public void updateDying() {
        if (dying) {
            dyingDelayCounter--;
            if (dyingDelayCounter <= 0) {
                dyingFrame++;
                dyingDelayCounter = dyingDelay;
                if (dyingFrame >= animation[DIE_RT].image.length) {
                    readyToRemove = true;
                }
            }
        }
    }
    
    public void attackPlayer(Player player) {
    	if(dying) return;
    	player.takeDamage(attackPower);
    			
    }

    public boolean isReadyToRemove() {
        return readyToRemove;
    }

    private void flipDirection() {
        movingRight = !movingRight;
    }
}

