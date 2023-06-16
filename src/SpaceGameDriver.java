/*
 * SpaceGameDiver.java
 * 
 * Driver for SpaceGame
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */
import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class SpaceGameDriver extends Applet implements Runnable {
	//Applet Window Size
	private final int X_MAX = 800;
	private final int Y_MAX = 500;
	//Game Menu
	private Menu menu;
	//booleans for keyListeners
	private boolean nameListenerIsAdded;
	private boolean menuListenerIsAdded;
	private boolean shipListenerIsAdded;
	//Images for the game
	private GameImageSet gI = new GameImageSet();//regular GameImageSet

	// Double Buffer
	Image offscreen;
	Graphics g;
	Dimension dim;

	// Double Buffer End

	public void init() {
		// Screen Size
		setSize(X_MAX, Y_MAX);
		
		// menu
		try {
			menu = new Menu(gI.AllSets());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// adds the Menu KeyListener
		addMenuKeyListener();
		
		// other KeyListeners are not added
		shipListenerIsAdded = false;
		nameListenerIsAdded = false;

		// Double Buffer
		offscreen = createImage(this.getWidth(), this.getHeight());
		g = offscreen.getGraphics();
		// Double Buffer End

		// starts the run thread
		keyListenerCheck();

	}

	public void paint(Graphics h) {
		// Double Buffer
		g.clearRect(0, 0, (int) this.getWidth(), (int) this.getHeight());
		// Double Buffer End

		//draw menu if the game is not playing
		if (menu.getGame() == null) {
			menu.draw(g);
		}
		//else draws the game
		else menu.getGame().drawGame(g);
		

		// Double Buffer
		h.drawImage(offscreen, 0, 0, this);
		// Double Buffer End

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		repaint();
	}// end paint

	// Double Buffer
	public void update(Graphics h) {
		paint(h);
	}

	// Double Buffer End

	/**
	 * Starts run()
	 */
	public void keyListenerCheck() {
		(new Thread(this)).start();
	}
	
	/**
	 * Adds the KeyListener to the Menu
	 * and sets the menuListenerIsAdd boolean
	 * equal to true.
	 */
	public void addMenuKeyListener(){
		addKeyListener(menu);
		menuListenerIsAdded = true;
		
	}
	
	/**
	 * Removes the KeyListener from the Menu
	 * and sets the menuListenerIsAdd boolean
	 * equal to false.
	 */
	public void removeMenuKeyListener(){
		removeKeyListener(menu);
		menuListenerIsAdded = false;
	}
	
	/**
	 * Adds the KeyListener to the SpaceShip
	 * and sets the shipListenerIsAdded boolean
	 * equal to true.
	 */
	public void addSpaceShipListener(){
		addKeyListener(menu.getGame().getSpaceShip());
		shipListenerIsAdded = true;
	}
	
	/**
	 * Removes the KeyListener from the SpaceShip
	 * and sets the shipListenerIsAdded boolean
	 * equal to false.
	 */
	public void removeSpaceShipListener(){
		removeKeyListener(menu.getGame().getSpaceShip());
		shipListenerIsAdded = false;
	}
	
	/**
	 * Adds the KeyListener to the SpaceShip
	 * and sets the shipListenerIsAdded boolean
	 * equal to true.
	 */
	public void addNameListener(){
		addKeyListener(menu.nameChanger());
		nameListenerIsAdded = true;
	}
	
	/**
	 * Removes the KeyListener from the SpaceShip
	 * and sets the shipListenerIsAdded boolean
	 * equal to false.
	 */
	public void removeNameListener(){
		removeKeyListener(menu.nameChanger());
		nameListenerIsAdded = false;
	}

	@Override
	public void run() {
		while (true) {
			//if the game isn't playing
			if (menu.getGame() == null) {
				if(!menuListenerIsAdded&&!menu.changingName()){
					//adds the KeyListener to menu if
					//the person is not changing their
					//username and if it already is not
					//added. It then removes the Listener
					//from the nameMaker
					addMenuKeyListener();
					if(nameListenerIsAdded){
						removeNameListener();
					}//end if
				}//end if
					
				else if(menuListenerIsAdded&&menu.changingName()&&!nameListenerIsAdded){
					//else if the person is changing their name
					//the KeyListener is removed from
					//the Menu and it is added to the
					//nameMaker.
					removeMenuKeyListener();
					addNameListener();
				}//end else if
			}//end if

			else {//if the game is playing
				//the KeyListener is removed from the menu
				if(menuListenerIsAdded)
					removeMenuKeyListener();//end if
				
				//the KeyListener is added to and removed from
				//the spaceShip depending on whether or not
				//it is alive
				if (menu.getGame().getSpaceShip() != null) {
					if(!shipListenerIsAdded)
						addSpaceShipListener();//end if
				}//end if

				if (menu.getGame().getSpaceShip() == null) {
					if(shipListenerIsAdded)
						removeSpaceShipListener();//end if
				}//end if
			}//end else

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}// end while

	}// end run

}// end SpaceGameDriver
