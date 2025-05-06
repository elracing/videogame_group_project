package group_project;

import java.awt.Graphics;


public class Sprite extends Rect{
	
		Animation[] animation;
		
		boolean moving = false; //for moving
		boolean attacking = false; //for attacking, which will prevent moving
		
		//array indexes for poses
		final public static int LT = 0;
		final public static int RT = 1;
		final public static int attack_LT = 2;
		final public static int attack_RT = 3;
		final public static int hurt_LT = 4;
		final public static int hurt_RT = 5;
		
		
		int current_pose = RT;
		
		
		public Sprite (String name, int x, int y, int w, int h, String[] pose, int count, int duration) {
			
			super(x, y, w ,h);
			
			animation = new Animation[pose.length];
			
			for(int i = 0; i < pose.length; i++) {
				
				animation[i] = new Animation(name + "_" + pose[i], count, duration);
				
			}
			
			
		}
		

		public void draw(Graphics pen) {
			//if attacking, lock into attack pose and free on last frame, right side
			if(attacking && current_pose == RT) {
				pen.drawImage(animation[attack_RT].nextImage(), x, y, w, h, null); 
				if(Animation.current == 14) {
					attacking = false;
				}
			}
			//if attacking, lock into attack pose and free on last frame, left side
			if(attacking && current_pose == LT) {
				pen.drawImage(animation[attack_LT].nextImage(), x, y, w, h, null); 
				if(Animation.current == 14) {
					attacking = false;
				}
				
			}
			//movement without attacking
			if(moving && !attacking) {
				pen.drawImage(animation[current_pose].nextImage(), x, y, w, h, null);

			}
			
			//standing still without attacking
			if (!moving & !attacking) {
				pen.drawImage(animation[current_pose].stillImage(), x, y, w, h,  null);
				
				moving = false;
			}
			
		}
		

		
		public void go_LT(int dx)
		{
			old_x = x;
			
			x -= dx;		

			moving = true;
			
			current_pose = LT;
		}
		
		
		public void go_RT(int dx)
		{
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
		
		
		
	}


