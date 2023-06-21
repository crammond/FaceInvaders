package me.craighammond.face_invaders;
/*
 * SpaceShip.java
 * 
 * A class which defines a SpaceShip
 * 
 * A SpaceShip can move left and right and fire 
 * Bullet objects upward
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class SpaceShip extends Thread implements KeyListener{

	//Ship Coordinates
	private int xPos;
	private int yPos;
	
	//Ship Size
	private int height;
	private int width;
	
	//Ship Collision
	private Rectangle2D r2d;
	
	//Ship Bullets
	private ArrayList<Bullet> bullets;
	
	//Ship Movement
	private boolean goRight;
	private boolean goLeft;
	//Ship shooting
	private boolean shootBoolean;
	
	//Thread boolean
	private boolean keepRunning;
	
	//Images
	private static Image spaceShipImage;
	private Image bulletImage;
	
	//Firing wait
	private int shipFireWait;
	
	//Firing wait constant
	private final int SHIP_FIRE_WAIT_TIME = 100;
	
	//Number of bullets allowed on Screen
	private final int MAX_NUM_BULLETS = 8;
	
	//Shot Audio
	private AudioClip shotAudio;

	/**
	 * 
	 * @param x desired initial x position of spaceship
	 * @param y desired initial y position of spaceship
	 * @param l desired length of spaceship
	 * @param w desired width of spaceship
	 * @param ship image of the ship
	 */
	public SpaceShip(int x, int y, int w, int h, Image ship, Image shipBullet) {
		xPos = x;
		yPos = y;
		height = h;
		width = w;
		
		//sets the audio for shoot to a short laser beam sound
		shotAudio = Applet.newAudioClip(getClass().getResource("Photonshot.wav"));
	
		shipFireWait = SHIP_FIRE_WAIT_TIME;
		
		bullets = new ArrayList<Bullet>();
		
		r2d = new Rectangle2D.Double(xPos, yPos, width, height);
		
		goRight = false;
		goLeft = false;
		shootBoolean = false;
		
		spaceShipImage = ship;
		bulletImage = shipBullet;
		
		keepRunning = true;
		
		start();
	}

	// Getters
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public Rectangle2D getRectangle2D(){
		return r2d;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	// End Getters
	
	/**
	 * Draws the spaceShip
	 */
	public void draw(Graphics g){
		g.drawImage(spaceShipImage, xPos, yPos, width, height, null);
		
	}//end draw
	
	/**
	 * Draws a picture of a ship, not the actual ship
	 * @param x x coordinate of the image
	 * @param y y coordinate of the image
	 * @param g
	 */
	public static void drawAShip(int x, int y, int w, int h, Graphics g){
		g.drawImage(spaceShipImage, x, y, w, h, null);
	}//end drawAShip
	
	/**
	 * Sets bullet equal to a new bullet just above the
	 * center of the SpaceShip
	 */
	private void shoot(){
		if(shipFireWait==SHIP_FIRE_WAIT_TIME&&bullets.size()<MAX_NUM_BULLETS){
			bullets.add(new Bullet(xPos + (int)(width/2.0-Bullet.WIDTH/2.0), yPos, true, bulletImage));
			shipFireWait = 0;
			shotAudio.play();
		}//end if
	}//end shoot
	
	/**
	 * Stops the running thread
	 */
	public void stopRunning(){
		keepRunning = false;
	}//end stopRunning
	
	public void stopShooting(){
		shipFireWait=0;
	}//end stopShooting

	@Override
	public void keyPressed(KeyEvent e) {
		//Moves Right
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			goRight = true;
		}//end if
		
		//Moves Left
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			goLeft = true;
		}//end if
		
		//Shoots
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			shootBoolean = true;
		}//end if
		
		//closes program
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}//end if
	}//end KeyPressed

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			goRight=false;
		}//end if
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			goLeft = false;
		}//end if
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			shootBoolean = false;
		}//end if
	}//end KeyReleased

	
	public void keyTyped(KeyEvent e) {}

	@Override
	public void run() {
		while(keepRunning){
			if(goRight&&xPos<=800-width){
				xPos++;
			}//end if
			if(goLeft&&xPos>=0){
				xPos--;
			}//end if
			if(shootBoolean){
				shoot();
			}//end if
			
			r2d = new Rectangle2D.Double(xPos, yPos, width, height);
			
			if(shipFireWait<SHIP_FIRE_WAIT_TIME){
				shipFireWait++;
			}//end if
			
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end while
		
		//System.out.println("ended ship");
	}//end run

}//end SpaceShip
