/*
 * GameManagement.java
 * 
 * The GameManagement class takes various classes 
 * and makes them work specifically for the space
 * invaders game. Controls all of the
 * gameplay.
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GamePlay extends Thread {
	// Applet Constants
	private final int X_MAX = 800;
	private final int Y_MAX = 500;

	// Enemy Constants
	private final int ENEMY_WIDTH = 40;
	private final int ENEMY_HEIGHT = 20;

	// Enemy Group Constants
	private final int GROUP_COLUMN_WITDH = 60;
	private final int GROUP_ROW_HEIGHT = 30;

	private final int NUM_ENEMY_ROWS = 5;
	private final int NUM_ENEMY_COLS = 11;

	private final int ENEMY_GROUP_WIDTH = GROUP_COLUMN_WITDH * NUM_ENEMY_COLS;
	private final int ENEMY_GROUP_HEIGHT = GROUP_ROW_HEIGHT * NUM_ENEMY_ROWS;

	private final int ENEMY_GROUP_X = (int) ((X_MAX - (ENEMY_GROUP_WIDTH + (GROUP_COLUMN_WITDH - ENEMY_WIDTH))) / 2.0);
	private final int ENEMY_GROUP_Y = 100;

	// Ship Constants
	private final int SHIP_WIDTH = 50;
	private final int SHIP_HEIGHT = 30;
	private final int SHIP_X = (int) ((X_MAX - SHIP_WIDTH) / 2.0);
	private final int SHIP_Y = 400;

	// Ship
	private SpaceShip spaceship;

	// Enemies
	private ArrayList<Enemy> enemies;
	
	// Explosions
	private ArrayList<Explosion> explosions;

	// Saved Bullets
	private ArrayList<Bullet> savedEnemyBullets;
	private ArrayList<Bullet> savedShipBullets;

	// Lives
	private int numExtraShips;

	// Waits for respective respawn time
	private int shipRespawnWait;
	private int enemiesRespawnWait;

	// Prevents firing
	private int enemiesFireWait;

	// Score
	private int score;

	// Level
	private int level;
	
	// First time spawning enemies
	private boolean firstTime = true;

	// Used to stop run
	private boolean keepRunning;

	// Image of ship
	private Image spaceShipImage;

	// Image of ship Bullet
	private Image shipBulletImage;

	// Image of Enemy
	private Image enemyImage;

	// Image of Enemy Bullet
	private Image enemyBulletImage;
	
	// Image fo Flying Enemy
	private Image flyingEnemyImage;

	// moving Background
	private Image backgroundImage;
	private int backgroundImageY;
	private int backgroundMoveWait;

	// Explosion sound
	private AudioClip boom;

	// game Font
	private final Font gameFont = new Font("Arial", Font.BOLD, 15);

	/**
	 * Constructor
	 */
	public GamePlay(GameImageSet images) {
		// Pulls images from desired image set
		spaceShipImage = images.spaceShip();
		enemyImage = images.enemy();
		flyingEnemyImage = images.flyingEnemy();
		shipBulletImage = images.shipBullet();
		enemyBulletImage = images.enemyBullet();
		backgroundImage = images.Space();
		//gets the explosion noise
		boom = Applet.newAudioClip(getClass().getResource("shortBoom.wav"));
		
		//sets the background image values
		backgroundImageY = 0;
		backgroundMoveWait = 0;

		//creates the spaceship
		spaceship = new SpaceShip(SHIP_X, SHIP_Y, SHIP_WIDTH, SHIP_HEIGHT,
				spaceShipImage, shipBulletImage);// sets spaceship at the center
		
		// enemies ArrayList
		enemies = new ArrayList<Enemy>();
		
		// explosions ArrayList
		explosions = new ArrayList<Explosion>();

		//used to catch bullets from dead enemies and spaceships
		//prevents them from dissapearing along with the
		//enemy and spaceship, respectively
		savedEnemyBullets = new ArrayList<Bullet>();
		savedShipBullets = new ArrayList<Bullet>();

		//waits to respawn the ship and enemies
		shipRespawnWait = 0;
		enemiesRespawnWait = 0;

		enemiesFireWait = 0;// enemies can't fire immediately

		numExtraShips = 3;// number of lives to start with
		score = 0;// score starts at 0

		level = 1;// starts at level 1

		setEnemies();//sets all the enemies
		
		//the run method is going
		keepRunning = true;

		//starts the run() method
		start();
	}

	// Getters
	public SpaceShip getSpaceShip() {
		return spaceship;
	}

	public int getScreenWidth() {
		return X_MAX;
	}

	public int getScreenHeight() {
		return Y_MAX;
	}

	public int getLivesRemaining() {
		return numExtraShips;
	}

	public int getGameScore() {
		return score;
	}

	// End Getters

	/**
	 * Fills the enemies ArrayList
	 */
	private void setEnemies() {
		// prevents ship from shooting while enemies are still respawning
		if (enemies.size() == 0) {
			if (spaceship != null) {
				spaceship.stopShooting();
			}

			// Doesn't wait the first time
			if (firstTime) {
				for (int row = 0; row < NUM_ENEMY_ROWS; row++) {
					for (int col = 0; col < NUM_ENEMY_COLS; col++) {
						enemies.add(new Enemy(ENEMY_GROUP_X + col
								* GROUP_COLUMN_WITDH, ENEMY_GROUP_Y + row
								* GROUP_ROW_HEIGHT, ENEMY_WIDTH, ENEMY_HEIGHT,
								enemyImage, enemyBulletImage));
					}// end for
				}// end for
				firstTime = false;
			}// end if

			// Waits 1 second every time but the first
			else {

				if (enemiesRespawnWait == 1000) {
					level++;
					for (int row = 0; row < NUM_ENEMY_ROWS; row++) {
						for (int col = 0; col < NUM_ENEMY_COLS; col++) {
							enemies
									.add(new Enemy(ENEMY_GROUP_X + col
											* GROUP_COLUMN_WITDH, ENEMY_GROUP_Y
											+ row * GROUP_ROW_HEIGHT,
											ENEMY_WIDTH, ENEMY_HEIGHT,
											enemyImage, enemyBulletImage));

						}// end for
					}// end for

					enemiesRespawnWait = 0;
					enemiesFireWait = 0;
				}// end if

				else 
					enemiesRespawnWait++;

			}// end else
		}// end if
	}// end setEnemies

	/**
	 * Draws the entire game
	 * 
	 * @param g
	 */
	public void drawGame(Graphics g) {
		// Background
		g.drawImage(backgroundImage, 0, 0 + backgroundImageY, X_MAX, Y_MAX,
				null);
		g.drawImage(backgroundImage, 0, -Y_MAX + backgroundImageY, X_MAX,
				Y_MAX, null);

		// spaceship
		if (spaceship != null) {
			spaceship.draw(g);
		}

		// spaceship bullets
		if (spaceship != null) {
			for (int i = 0; i < spaceship.getBullets().size(); i++) {
				if (spaceship != null && spaceship.getBullets().get(i) != null) {
					spaceship.getBullets().get(i).draw(g);
				}//end if
			}//end for
		}//end if

		// saved spaceship bullets (from dead ship)
		for (int i = 0; i < savedShipBullets.size(); i++) {
			if(savedShipBullets.get(i)!=null)
				savedShipBullets.get(i).draw(g);
		}//end for

		// enemies
		for (int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i)!=null)
				enemies.get(i).draw(g);
		}//end for

		// enemy bullets
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) != null && enemies.get(i).getBullet() != null) {
				enemies.get(i).getBullet().draw(g);
			}//end if
		}//end for

		// saved enemy bullets (those from dead enemies)
		for (int i = 0; i < savedEnemyBullets.size(); i++) {
			if(savedEnemyBullets.get(i)!=null)
				savedEnemyBullets.get(i).draw(g);
		}//end for
		
		// explosions
		for (int i = 0; i < explosions.size(); i++) {
			if(explosions.get(i)!=null)
				explosions.get(i).draw(g);
		}//end for
		
		// Score
		g.setColor(Color.white);
		g.drawString("SCORE", (int) (X_MAX / 2.0), 20);
		g.drawString("" + score, (int) (X_MAX / 2.0), 40);

		// Life counter
		drawLives(g);

		// Level
		g.drawString("Level: " + level, 700, 40);

	}//end drawGame

	/**
	 * Controls background movement
	 */
	private void moveBackground() {
		if (backgroundMoveWait == 0) {
			backgroundImageY++;
			if (backgroundImageY > Y_MAX) {
				backgroundImageY = 0;
			}//end if
			backgroundMoveWait = 100;
		} //end if
		else
			backgroundMoveWait--;//end else
	}//end moveBackground

	/**
	 * Draws out the "Lives"
	 * 
	 * @param g
	 */
	private void drawLives(Graphics g) {
		int y = 20;//y position of spaceship images
		int x = 30;//x position of lives indicator 
		int space = 15;//space between each spaceship
		int halfway = (int) (SHIP_HEIGHT / 2.0);
		int lengthOfLivesLogo = 50;

		g.setColor(Color.white);
		g.drawString("Lives: ", x, y + halfway);

		if (numExtraShips <= 3) {
			for (int i = 0; i < numExtraShips; i++) {
				SpaceShip.drawAShip(x + i * (SHIP_WIDTH + space)
						+ lengthOfLivesLogo, y, SHIP_WIDTH, SHIP_HEIGHT, g);
			}//end for
		}//end if

		else {
			SpaceShip.drawAShip(x + lengthOfLivesLogo, y, SHIP_WIDTH,
					SHIP_HEIGHT, g);
			g.setColor(Color.white);
			g.drawString(" x " + numExtraShips, x + SHIP_WIDTH + space
					+ lengthOfLivesLogo, y + halfway);
		}//end else
	}//end drawLives

	/**
	 * Deletes any bullets from spaceship that went offscreen
	 */
	private void deleteLostSpaceShipBullet() {
		//deletes the offscreen bullets from the ship
		if (spaceship != null) {
			for (int i = 0; i < spaceship.getBullets().size(); i++) {
				if (spaceship.getBullets().get(i).getY() < -Bullet.HEIGHT) {
					spaceship.getBullets().get(i).stopRunning();
					spaceship.getBullets().remove(i);
				}// end if
			}// end for
		}// end if

		//deletes the offscreen bullets saved from dead ships
		for (int i = 0; i < savedShipBullets.size(); i++) {
			if (savedShipBullets.get(i).getY() < -Bullet.HEIGHT) {
				savedShipBullets.get(i).stopRunning();
				savedShipBullets.remove(i);
			}// end if
		}// end for
	}// end deleteLostSpaceShipBullet

	/**
	 * Deletes any bullets from enemies that went offscreen
	 */
	private void deleteLostEnemyBullets() {
		//deletes offscreen enemy bullets
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getBullet() != null
					&& enemies.get(i).getBullet().getY() > Y_MAX) {
				enemies.get(i).makeBulletNull();
			}// end if
		}// end for

		//deletes offscreen saved enemy bullets
		for (int i = 0; i < savedEnemyBullets.size(); i++) {
			if (savedEnemyBullets.get(i) != null
					&& savedEnemyBullets.get(i).getY() > Y_MAX) {
				savedEnemyBullets.get(i).stopRunning();
				savedEnemyBullets.remove(i);
			}// end if
		}// end for
	}// end deleteLostEnemyBullets

	/**
	 * Fills the savedShipBulets ArrayList<Bullet> with Bullets from a dead
	 * SpaceShip. This prevents the bullets from getting deleted along with the
	 * ship.
	 */
	private void setSavedShipBullets() {
		for (int i = 0; i < spaceship.getBullets().size(); i++) {
			savedShipBullets.add(spaceship.getBullets().get(i));
			spaceship.getBullets().remove(i);
			i--;
		}// end for
	}// end setSavedShipBullets

	/**
	 * Checks to see if an enemy bullet hit the ship
	 */
	private void checkForShipCollisions() {
		// Checks Enemy Bullets
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getBullet() != null && spaceship != null
					&& enemies.get(i).getBullet().hits(spaceship)) {
				if(enemies.get(i)!=null&&enemies.get(i).getBullet()!=null){
					explosions.add(new Explosion(enemies.get(i).getBullet().getX(),
							enemies.get(i).getBullet().getY()+Bullet.HEIGHT));
				}//end if
				enemies.get(i).makeBulletNull();
				setSavedShipBullets();
				boom.play();
				spaceship.stopRunning();
				spaceship = null;

			}// end if

		}// end for

		// Checks saved Enemy Bullets
		for (int i = 0; i < savedEnemyBullets.size(); i++) {
			if (spaceship != null && savedEnemyBullets.get(i).hits(spaceship)) {
				if(savedEnemyBullets.get(i)!=null){
					explosions.add(new Explosion(savedEnemyBullets.get(i).getX(),
							savedEnemyBullets.get(i).getX()+Bullet.HEIGHT));
				}//end if
				savedEnemyBullets.get(i).stopRunning();
				savedEnemyBullets.remove(i);
				setSavedShipBullets();
				boom.play();
				spaceship.stopRunning();
				spaceship = null;

			}// end if

		}// end for
	}// end checkForShipCollisions

	/**
	 * Checks to see if the spaceship bullet hit any enemy
	 */
	private void checkForEnemyCollisions() {
		//adds a life if the score is goes past a multiple of 20000
		int addLife = 20000;

		// Checks ship bullets
		if (spaceship != null) {
			int s = 0;
			while (s < spaceship.getBullets().size()) {
				int e = 0;
				boolean hit = false;
				while (e < enemies.size() && hit == false) {
					if (spaceship.getBullets().get(s).hits(enemies.get(e))) {
						if (enemies.get(e).getBullet() != null) {
							savedEnemyBullets.add(enemies.get(e).getBullet());
						}// end if
						int scoreBefore = score;
						score += enemies.get(e).getPoints() * level;
						int i = scoreBefore;
						boolean found = false;
						while (i < score && found == false) {
							if (i % addLife == 0 && scoreBefore != 0) {
								numExtraShips++;
								found = true;
							}//end if
							else
								i++;
						}//end while
						boom.play();
						if(spaceship!=null&&spaceship.getBullets().get(s)!=null){
						explosions.add(new Explosion(spaceship.getBullets().get(s).getX(),
								spaceship.getBullets().get(s).getY()));
						}//end if
						enemies.get(e).stopRunning();
						enemies.remove(e);
						spaceship.getBullets().get(s).stopRunning();
						spaceship.getBullets().remove(s);
						hit = true;
						s--;
						e++;
					}// end of
					e++;
				}
				s++;
			}// end while

		}// end if (spaceship bullets)

		// checks saved ship bullets (from dead ship)
		int s = 0;
		while (s < savedShipBullets.size()) {
			int e = 0;
			boolean hit = false;
			while (e < enemies.size() && hit == false) {
				if (savedShipBullets.get(s).hits(enemies.get(e))) {
					if (enemies.get(e).getBullet() != null) {
						savedEnemyBullets.add(enemies.get(e).getBullet());
					}// end if
					int scoreBefore = score;
					score += enemies.get(e).getPoints() * level;
					int i = scoreBefore;
					boolean found = false;
					while (i < score && found == false) {
						if (i % addLife == 0 && scoreBefore != 0) {
							numExtraShips++;
							found = true;
						} //end if
						else
							i++;
					}//end while
					boom.play();
					if(spaceship!=null&&spaceship.getBullets().get(s)!=null){
					explosions.add(new Explosion(spaceship.getBullets().get(s).getX(),
							spaceship.getBullets().get(s).getY()));
					}//end if
					enemies.get(e).stopRunning();
					enemies.remove(e);
					savedShipBullets.get(s).stopRunning();
					savedShipBullets.remove(s);
					hit = true;
					s--;
					e++;
				}// end if
				e++;
			}// endd while
			s++;
		}// end while

	}// end checkForEnemyCollisions

	/**
	 * Controls FlyingEnemy objects
	 */
	private void manageFlyingEnemies() {
		boolean canAdd = true;
		//Checks to make sure there aren't any
		//flying enemies already
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) instanceof FlyingEnemy) {
				if (enemies.get(i).getX() >= 800){
					enemies.get(i).stopRunning();
					enemies.remove(i);
				}//end if
				else
					canAdd = false;
			}//end if
		}//end for

		//only adds if the screen has other enemies on it
		if (enemies.size() == 0) {
			canAdd = false;
		}//end if
		
		//Decides whethor or not to add one
		if (canAdd) {
			int random = (int) (Math.random() * 20000);//they are rare-ish
			if (random != 0) {
				canAdd = false;
			}//end if
		}//end if

		//adds a new FlyingEnemy if it can
		if (canAdd) {
			enemies.add(new FlyingEnemy(60, ENEMY_WIDTH, ENEMY_HEIGHT,
					flyingEnemyImage));
		}//end if
	}// end ManageFlyingEnemies

	/**
	 * Respawns the spaceship if it is dead, waits 1 second after death before
	 * it does so
	 */
	private void createNewShip() {
		if (spaceship == null && numExtraShips > 0 && shipRespawnWait == 1000) {

			spaceship = new SpaceShip(SHIP_X, SHIP_Y, SHIP_WIDTH, SHIP_HEIGHT,
					spaceShipImage, shipBulletImage);

			numExtraShips--;
			shipRespawnWait = 0;// ship can't respawn immediately
			enemiesFireWait = 0;// enemies can't fire while ship is not spawned

		}// end if

		else if (spaceship == null && numExtraShips > 0
				&& shipRespawnWait != 1000) {
			shipRespawnWait++;
		}// end else if
	}// end createNewShip

	private void removeExplosions(){
		for (int i = 0; i < explosions.size(); i++) {
			if(explosions.get(i).isDone()){
				explosions.remove(i);
				i--;
			}//end if
			
		}//end for
	}
	/**
	 * Controls enemy fire
	 */
	private void enemyFire() {
		if (enemiesFireWait == 1000) {
			// Finds enemies which don't have other enemies below them
			ArrayList<Enemy> first = new ArrayList<Enemy>();
			for (int i = 0; i < enemies.size(); i++) {
				int j = i + 1;
				boolean canAdd = true;
				while (j < enemies.size() && canAdd) {
					if (enemies.get(j).getRectangle2D().intersects(
							new Rectangle2D.Double(enemies.get(i).getX(),
									enemies.get(i).getY(), ENEMY_WIDTH,
									ENEMY_GROUP_HEIGHT))) {
						canAdd = false;
					}// end if
					if(enemies.get(j) instanceof FlyingEnemy){
						canAdd = false;
					}//end if
					j++;
				}// end while

				if (canAdd) {
					first.add(enemies.get(i));
				}// end if

			}// end for

			// Finds enemies which can shoot (bullet is null)
			ArrayList<Enemy> second = new ArrayList<Enemy>();
			for (int i = 0; i < first.size(); i++) {
				if (first.get(i).getBullet() == null) {
					second.add(first.get(i));
				}// end if
			}// end for

			// Chooses a random enemy that can shoot, 1/2000 chance of shooting
			if (second.size() != 0) {
				int which = (int) (Math.random() * second.size());

				int shootRandom = (int) (Math.random() * (int) (2000 / ((double) level)));
				if (shootRandom == 0) {
					second.get(which).shoot();
				}// end if
			}// end if
		}// end if (which directs actual fire)

		else {
			enemiesFireWait++;
		}// end else

	}// end enemyFire

	/**
	 * Allows the run() method to stop
	 */
	public void stopRunning() {
		keepRunning = false;
	}// end stopRunning

	@Override
	public void run() {
		while (keepRunning) {
			//is constantly doing all of these things
			moveBackground();

			deleteLostSpaceShipBullet();
			deleteLostEnemyBullets();

			manageFlyingEnemies();

			checkForShipCollisions();
			checkForEnemyCollisions();

			enemyFire();

			createNewShip();

			setEnemies();
			
			removeExplosions();

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}// end while

	}// end run

}// end Game Management
