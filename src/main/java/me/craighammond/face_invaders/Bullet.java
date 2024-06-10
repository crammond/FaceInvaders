package me.craighammond.face_invaders;
/*
 * Bullet.java
 *
 * A Bullet object destroys a ship
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class Bullet {
	//Bullet Coordinates
	private int xPos;
	private int yPos;
	
	//Bullet size
	final static int WIDTH = 5;
	final static int HEIGHT = 10;
	
	private Rectangle2D r2d;
	
	//Decides direction
	private boolean directionIsUp;
	
	//image of the Bullet
	private Image bulletImage;

	/**
	 * 
	 * @param x x position of the bullet
	 * @param y y position of the bullet
	 * @param isUp true if the bullet is supposed to travel upwards;
	 *  false if downward
	 */
	public Bullet(int x, int y, boolean isUp, Image ofBullet) {
		xPos = x;
		yPos = y;
		r2d = new Rectangle2D.Double(xPos, yPos, WIDTH, HEIGHT);
		directionIsUp = isUp;
		bulletImage = ofBullet;
	}//end constructor

	// Getters
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}
	
	public Rectangle2D getRectangle2D(){
		return r2d;
	}

	// End Getters

	/**
	 * @param g
	 */
	public void draw(Graphics g) {
		if(directionIsUp)
			g.drawImage(bulletImage, xPos, yPos, WIDTH, HEIGHT, null);
		else {
			g.setColor(Color.orange);
			g.fillRect(xPos, yPos, WIDTH, HEIGHT);
		}
	}//end draw
	
	/**
	 * 
	 * @param e = an Enemy
	 * @return = true if the bullet hits the Enemy
	 * 			 false if not
	 */
	public boolean hits (Enemy e){
		return r2d.intersects(e.getRectangle2D());
	}//end hits
	
	/**
	 * 
	 * @param s = a SpaceShip
	 * @return = true if the bullet hits the SpaceShip
	 * 			 false if not
	 */
	public boolean hits (SpaceShip s){
		return r2d.intersects(s.getRectangle2D());
	}//end hits

	private int stepCount = 0;
	private final static int WAIT_MILLI = 3;
	public void step() {
		if (stepCount == WAIT_MILLI) {
			if (directionIsUp)
				yPos--;
			else yPos++;
			r2d = new Rectangle2D.Double(xPos, yPos, WIDTH, HEIGHT);
			stepCount = 0;
		} else {
			stepCount++;
		}
	}
}
