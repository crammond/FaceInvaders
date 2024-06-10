package me.craighammond.face_invaders;
/*
 * Enemy.java
 * 
 * Defines an Enemy for the space game
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class Enemy {
	//Enemy coordinates
	protected int xPos;
	protected int yPos;
	
	//Enemy size
	protected int width;
	protected int height;
	
	//move count
	private int count;
	
	//is true if the Enemy is moving to the right
	private boolean isMovingRight;
	
	protected Rectangle2D r2d;
	
	//each enemy only has a single bullet
	private Bullet bullet;
	
	//images
	private Image enemyImage;
	private Image bulletImage;
	
	//the ammount the Enemy moves
	private final int MOVE_COUNT = 5;
	
	//the ammoutn of points the enemy is worth
	protected int points = 50;

	public Enemy(int x, int y, int w, int h, Image enemy, Image enemyBullet) {
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		
		//sets the Enemy in the middle of its motion
		count = 4;
		
		//starts it moving right
		isMovingRight = true;
		
		r2d = new Rectangle2D.Double(xPos, yPos, width, height);
		
		bullet = null;
		
		enemyImage = enemy;
		bulletImage = enemyBullet;
	}

	// End Getters
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle2D getRectangle2D() {
		return r2d;
	}
	

	public Bullet getBullet(){
		return bullet;
	}
	
	public int getPoints(){
		return points;
	}

	// end Getters

	/**
	 * draws the enemy
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawImage(enemyImage ,xPos, yPos, width, height, null);
	}//end draw
	
	/**
	 * sets this Enemy's Bullet
	 * equal to null
	 */
	public void makeBulletNull() {
		bullet = null;
	}
	
	/**
	 * set's this Enemy's Bullet
	 * equal to a new Bullet
	 * below the Enemy
	 */
	public void shoot(){
		if(bullet==null){
			bullet = new Bullet(xPos + (int)(width/2.0-Bullet.WIDTH/2.0), yPos + height, false,  bulletImage);
		}//end if
	}//end shoot

	private int stepCount = 0;
	private final static int WAIT_MILLI = 750;
	public void step() {
		if (stepCount == WAIT_MILLI) {
			if (isMovingRight) {
				xPos += MOVE_COUNT;
				count++;
				if (count == 8) {
					isMovingRight = false;
				}//end if
			} else {
				xPos -= MOVE_COUNT;
				count--;
				if (count == 0) {
					isMovingRight = true;
				}
			}

			r2d = new Rectangle2D.Double(xPos, yPos, width, height);
			stepCount = 0;
		} else {
			stepCount++;
		}
	}

}
