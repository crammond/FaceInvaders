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

public class FlyingEnemy extends Enemy {
	
	/**
	 * 
	 * @param y y position of the FlyingEnemy
	 * @param w width of the FlyingEnemy
	 * @param h height of the FlyingEnemy
	 * @param enemy image of the FlyingEnemy
	 */
	public FlyingEnemy(int y, int w, int h, Image enemy) {
		super(0-w, y, w, h, enemy, null);
		r2d = new Rectangle2D.Double(xPos, yPos, width, height);
		//sets the point value any multiple of 50 less then
		//or equal to 5000, greater than 0
		points = ((int)(Math.random()*10)+1)*50;
	}//end contructor
	
	/**
	 * shoot doesn't do anything
	 */
	public void shoot(){}//end move

	private int stepCount = 0;
	private final static int WAIT_MILLI = 15;
	public void step(){
		if (stepCount == WAIT_MILLI) {
			xPos++;
			r2d = new Rectangle2D.Double(xPos, yPos, width, height);
		} else {
			stepCount++;
		}
	}

}
