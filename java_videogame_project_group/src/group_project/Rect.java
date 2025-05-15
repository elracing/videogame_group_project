package group_project;

import java.awt.Graphics;

public class Rect {
	int x; 
	int y;
	int w;
	int h;
	int old_x;
	int old_y;
	
	public int yVelocity = 0; // vertical speed
	
	
	//flags to detect auto-movement
	boolean moving_up = false;
	boolean moving_dn = false;
	boolean moving_lt = false;
	boolean moving_rt = false;
	
	
    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void chase(Rect r) {
    	//set flags to detect movement

    	
    	
        if (this.x > r.x) {
            this.x -= 2;
            
        	moving_up = false;
        	moving_dn = false;
        	moving_lt = true;
        	moving_rt = false;
 
            
        }

        if (this.x < r.x) {
            this.x += 2;
            
        	moving_up = false;
        	moving_dn = false;
        	moving_lt = false;
        	moving_rt = true;
        }

        if (this.y > r.y) {
            this.y -= 2;
            
        	moving_up = true;
        	moving_dn = false;
        	moving_lt = false;
        	moving_rt = false;
        }

        if (this.y < r.y) {
            this.y += 2;
            
        	moving_up = false;
        	moving_dn = true;
        	moving_lt = false;
        	moving_rt = false;
        }

    }
    
    public void moveBy(int dx, int dy) {
        this.old_x = this.x;
        this.old_y = this.y;
        this.x += dx;
        this.y += dy;
    }
    
    
    public void go_UP(int dy) {
        this.old_y = this.y;
        this.y -= dy;
    }

    public void go_DN(int dy) {
        this.old_y = this.y;
        this.y += dy;
    }

    public void go_LT(int dx) {
        this.old_x = this.x;
        this.x -= dx;
    }

    public void go_RT(int dx) {
        this.old_x = this.x;
        this.x += dx;
    }
    
    public void pushBy(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void resizeBy(int dw, int dh) {
        this.w += dw;
        this.h += dh;
    }
    
    public boolean overlaps(Rect r) {
        return r.y <= this.y + this.h && this.y <= r.y + r.h && r.x <= this.x + this.w && this.x <= r.x + r.w;
    }

    public boolean cameFromBelow() {
        return this.y > this.old_y;
    }

    public boolean cameFromAbove() {
        return this.y < this.old_y;
    }

    public boolean cameFromLeft() {
        return this.x > this.old_x;
    }

    public boolean cameFromRight() {
        return this.x < this.old_x;
    }
    
    public boolean contains(int mx, int my) {
        return mx > this.x && mx < this.x + this.w && my > this.y && my < this.y + this.h;
    }
    
	public void updatePosition() { //changes y based on velocity
	    y += yVelocity;
	}
	
	
    
    public void draw(Graphics pen) {
        pen.drawRect(this.x, this.y, this.w, this.h);
    }


    
}

