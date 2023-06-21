package me.craighammond.face_invaders;

/*
 * FlyingEnemy.java
 * 
 * Extends the Enemy class
 * A Flying Enemy only moves to the right, doesn't shoot
 * and it worth a random amount of points
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class FlyingEnemy extends Enemy{
	//keeps the run method running
	private boolean isRunning;
	
	/**
	 * 
	 * @param y y position of the FlyingEnemy
	 * @param w width of the FlyingEnemy
	 * @param h height of the FlyingEnemy
	 * @param enemy image of the FlyingEnemy
	 */
	public FlyingEnemy(int y, int w, int h, Image enemy) {
		super(0-w, y, w, h, enemy, null);
		isRunning = true;
		r2d = new Rectangle2D.Double(xPos, yPos, width, height);
		//sets the point value any multiple of 50 less then
		//or equal to 5000, greater than 0
		points = ((int)(Math.random()*10)+1)*50;
		move();
	}//end contructor
	
	//starts the run method
	private void move(){
		(new Thread(this)).start();
	}//end move
	
	/**
	 * shoot doesn't do anything
	 */
	public void shoot(){}//end move
	
	public void run(){
		while(isRunning){
			xPos++;
			r2d = new Rectangle2D.Double(xPos, yPos, width, height);
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (xPos==800){
				isRunning = false;
			}//end if
		}//end while
	}//end run

}//end FlyingEnemy
