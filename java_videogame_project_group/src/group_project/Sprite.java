/*package group_project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Sprite extends Rect{

	Animation[] animation;
	boolean moving = false;
	boolean attacking = false;
	boolean jumping = false;

	final public static int LT = 0;
	final public static int RT = 1;
	final public static int attack_LT = 2;
	final public static int attack_RT = 3;
	final public static int hurt_LT = 4;
	final public static int hurt_RT = 5;
	final public static int jump_LT = 6;
	final public static int jump_RT = 7;

	int current_pose = RT;

	public Sprite (String name, int x, int y, int w, int h, String[] pose, int count, int duration) {
		super(x, y, w ,h);
		animation = new Animation[pose.length];
		for(int i = 0; i < pose.length; i++) {
			animation[i] = new Animation(name + "_" + pose[i], count, duration);
		}
	}

	public void draw(Graphics pen) {
		if(attacking && current_pose == RT) {
			pen.drawImage(animation[attack_RT].nextImage(), x, y, w, h + 20, null);
			if(Animation.current == 14) attacking = false;
		}
		if(attacking && current_pose == LT) {
			pen.drawImage(animation[attack_LT].nextImage(), x, y, w, h + 20, null);
			if(Animation.current == 14) attacking = false;
		}
		if(moving && !attacking && !jumping) {
			pen.drawImage(animation[current_pose].nextImage(), x, y, w, h + 20, null);
		}
		if (!moving & !attacking && !jumping) {
			pen.drawImage(animation[current_pose].stillImage(), x, y, w, h + 20, null);
			moving = false;
		}
		if (jumping && current_pose == LT) {
			pen.drawImage(animation[jump_LT].nextImage(), x, y, w, h + 20, null);
			if(Animation.current == 14) Animation.current = 4;
		}
		if (jumping && current_pose == RT) {
			pen.drawImage(animation[jump_RT].nextImage(), x, y, w, h, null);
			if(Animation.current == 14) Animation.current = 4;
		}
	}

	public void go_LT(int dx) {
		old_x = x;
		x -= dx;
		moving = true;
		current_pose = LT;
	}

	public void go_RT(int dx) {
		old_x = x;
		x += dx;
		moving = true;
		current_pose = RT;
	}

	public void attack_RT() {
		moving = false;
		attacking = true;
		current_pose = RT;
	}

	public void attack_LT() {
		moving = false;
		attacking = true;
		current_pose = LT;
	}

	public void jump_RT() {
		jumping = true;
		moving = false;
		attacking = false;
		current_pose = RT;
	}

	public void jump_LT() {
		jumping = true;
		moving = false;
		attacking = false;
		current_pose = LT;
	}


}
*/
package group_project;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite extends Rect{

    Animation[] animation;
    boolean moving = false;
    boolean attacking = false;
    boolean jumping = false;
    boolean dead = false;
    boolean hurting = false;

    final public static int LT = 0;
    final public static int RT = 1;
    final public static int attack_LT = 2;
    final public static int attack_RT = 3;
    final public static int hurt_LT = 4;
    final public static int hurt_RT = 5;
    final public static int jump_LT = 6;
    final public static int jump_RT = 7;
    final public static int death_LT = 8;
    final public static int death_RT = 9;

    int current_pose = RT;

    public Sprite (String name, int x, int y, int w, int h, String[] pose, int count, int duration) {
        super(x, y, w ,h);
        animation = new Animation[pose.length];
        for(int i = 0; i < pose.length; i++) {
            animation[i] = new Animation(name + "_" + pose[i], count, duration);
        }
    }

    public void draw(Graphics pen) {
    	
    	if (!dead) { 
	        if(attacking && current_pose == RT) {
	            pen.drawImage(animation[attack_RT].nextImage(), x, y, w, h + 20, null);
	            if(animation[attack_RT].current == 14) attacking = false;
	        }
	        if(attacking && current_pose == LT) {
	            pen.drawImage(animation[attack_LT].nextImage(), x, y, w, h + 20, null);
	            if(animation[attack_LT].current == 14) attacking = false;
	        }
	        if(moving && !attacking && !jumping) {
	            pen.drawImage(animation[current_pose].nextImage(), x, y, w, h + 20, null);
	        }
	        if (!moving && !attacking && !jumping) {
	            pen.drawImage(animation[current_pose].stillImage(), x, y , w, h + 20, null);
	        }
	        if (jumping && current_pose == LT) {
	            pen.drawImage(animation[jump_LT].nextImage(), x, y, w, h + 5, null);
	            if(animation[jump_LT].current == 14) animation[jump_LT].current = 4;
	        }
	        if (jumping && current_pose == RT) {
	            pen.drawImage(animation[jump_RT].nextImage(), x, y, w, h + 20, null);
	            if(animation[jump_RT].current == 14) animation[jump_RT].current = 4;
	        }
	        
	        if (hurting && current_pose == LT) {
	        	pen.drawImage(animation[hurt_LT].nextImage(), x, y, w, h + 20, null);
	        }
	        
	        if (hurting && current_pose == RT) {
	        	pen.drawImage(animation[hurt_RT].nextImage(), x, y, w, h + 20, null);
	        }
    	}
    	
        
        if ((dead) && current_pose == LT) { //loop back to 12, adds a bit of flashing
            pen.drawImage(animation[death_LT].nextImage(), x, y, w, h, null);
            if(animation[death_LT].current == 14) animation[death_LT].current = 12;
        }
        
        if ((dead) && current_pose == RT) { 
            pen.drawImage(animation[death_RT].nextImage(), x, y, w, h, null);
            if(animation[death_RT].current == 14) animation[death_RT].current = 12;
        }
        

    }
    
    public Image getCurrentImage() {
    	return animation[0].nextImage();
    }

    public void go_LT(int dx) {
        old_x = x;
        x -= dx;
        moving = true;
        current_pose = LT;
    }

    public void go_RT(int dx) {
        old_x = x;
        x += dx;
        moving = true;
        current_pose = RT;
    }

    public void attack_RT() {
        moving = false;
        attacking = true;
        current_pose = RT;
    }

    public void attack_LT() {
        moving = false;
        attacking = true;
        current_pose = LT;
    }

    public void jump_RT() {
        jumping = true;
        moving = false;
        attacking = false;
        current_pose = RT;
    }

    public void jump_LT() {
        jumping = true;
        moving = false;
        attacking = false;
        current_pose = LT;
    }
    
    public void die_RT() {
    	dead = true;
        jumping = false;
        moving = false;
        attacking = false;
        current_pose = RT;
    }
    
    public void die_LT() {
    	dead = true;
        jumping = false;
        moving = false;
        attacking = false;
        current_pose = LT;
    }
    
    public void hurt_RT() {
    	hurting = true;
    }
    
    public void hurt_LT() {
    	hurting = true;
    }
    
}

