package me.craighammond.face_invaders;
/*
 * GameImageSet.java
 * 
 * Not currently being used to its full potential, but
 * will allow the user to choose different image packs
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class GameImageSet {
	private GameImages gI;
	private Image spaceship;
	private Image enemy;
	private Image shipBullet;
	private Image enemyBullet;
	private Image background;
	private Image flyingEnemy;
	
	
	public GameImageSet(){
		 gI = new GameImages();
	}
	
	private GameImageSet(Image SpaceShip, Image Enemy, Image FlyingEnemy, Image ShipBullet, Image EnemyBullet, Image Background){
		spaceship = SpaceShip;
		enemy = Enemy;
		shipBullet = ShipBullet;
		enemyBullet = EnemyBullet;
		background = Background;
		flyingEnemy = FlyingEnemy;
	}
	
	//getters
	public Image spaceShip(){
		return spaceship;
	}
	public Image enemy(){
		return enemy;
	}
	public Image shipBullet(){
		return shipBullet;
	}
	public Image enemyBullet(){
		return enemyBullet;
	}
	public Image Space(){
		return background;
	}
	
	public Image flyingEnemy(){
		return flyingEnemy;
	}
	
	public Image getRegularShip(){
		return gI.getRegularShip();
	}
	
	public Image getRegularEnemy(){
		return gI.getRegularEnemy();
	}
	
	public Image getRegularFlyingEnemy(){
		return gI.getRegularFlyingEnemy();
	}
	
	private GameImageSet regularImageSet(){
		return new GameImageSet(gI.getRegularShip(), gI.getRegularEnemy(), 
				gI.getRegularFlyingEnemy(),gI.getRegularShipBullet(), 
				gI.getRegularEnemyBullet(), gI.getRegularBackground());
	}
	
	public ArrayList<GameImageSet> AllSets() {
		ArrayList<GameImageSet> temp = new ArrayList<GameImageSet>();
		temp.add(regularImageSet());
		return temp;
		
	}
	// end getters
	
	private class GameImages{
		
		private BufferedImage regShip;
		private BufferedImage regEnemy;
		private BufferedImage regShipBullet;
		private BufferedImage regEnemyBullet;
		private BufferedImage regBackground;
		private BufferedImage regFlyingEnemy;
		
		public GameImages(){
			try {
				regShip = ImageIO.read(getClass().getResourceAsStream("/SpaceShipGIF.gif"));
				regEnemy = ImageIO.read(getClass().getResourceAsStream("/SpaceGameEnemyGIF.gif"));
				regEnemyBullet = ImageIO.read(getClass().getResourceAsStream("/EnemyBulletGIF.gif"));
				regShipBullet = ImageIO.read(getClass().getResourceAsStream("/SpaceShipBulletGIF.gif"));
				regBackground = ImageIO.read(getClass().getResourceAsStream("/starbg.jpg"));
				regFlyingEnemy = ImageIO.read(getClass().getResourceAsStream("/FlyingEnemyGIF.gif"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public Image getRegularShip(){
			return regShip;
		}
		
		public Image getRegularEnemy(){
			return regEnemy;
		}
		
		public Image getRegularShipBullet(){
			return regShipBullet;
		}
		
		public Image getRegularEnemyBullet(){
			return regEnemyBullet;
		}
		
		public Image getRegularFlyingEnemy(){
			return regFlyingEnemy;
		}
		
		public Image getRegularBackground(){
			return regBackground;
		}
		
	}
}
