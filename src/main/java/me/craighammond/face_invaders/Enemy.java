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

public class Enemy extends Thread {
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
	
	//keeps the run method going
	private boolean keepRunning;
	
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
		
		keepRunning = true;
		
		start();
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
	public void makeBulletNull(){
		if(bullet!=null){
			bullet.stopRunning();
			bullet = null;
		}//end if
	}//end MakeBulletNull
	
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
	
	/**
	 * stops the run method
	 */
	public void stopRunning(){
		keepRunning = false;
	}//end stopRunning

	@Override
	public void run() {
		while (keepRunning) {
			if (isMovingRight) {
				xPos += MOVE_COUNT;
				count++;
				if (count == 8) {
					isMovingRight = false;
				}//end if
			}//end if

			else {
				xPos -= MOVE_COUNT;
				count--;
				if (count == 0) {
					isMovingRight = true;
				}//end if
			}//end else

			r2d = new Rectangle2D.Double(xPos, yPos, width, height);
			
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end while
		
		//System.out.println("ended enemy");
	}//end run

}//end Enemy
