package me.craighammond.face_invaders;
/*
 * Menu.java
 * 
 * Controls the Menu of the game, instantiates a single game
 * for the user to interact with
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import me.craighammond.face_invaders.highscores.HighscoreManager;

public class Menu implements KeyListener, Runnable {
	// Applet Windo Size
	private int X_MAX = 800;
	private int Y_MAX = 500;

	// Menu Choices
	private final int numChoices = 4;
	// Current Menu Choice
	private int currentSelection;

	// True if in the Directions
	private boolean inDirections;
	
	// True if in Highscores
	private boolean inHighscores;
	
	// True if in UserNameCreator
	private boolean inUserNameCreator;
	
	// boolean for Runnable
	private boolean isRunning;

	// the game
	private GamePlay game = null;
	
	// sets of images
	private ArrayList<GameImageSet> sets; 
	
	// controls the highscore system
	private HighscoreManager hm;
	
	// players name
	private String playerName;
	
	// used to make username
	private nameMaker nM;
	
	// Menu images
	private Image background;
	private Image starbg;
	private Image ship;
	
	// menu font color
	private final Color fontColor = new Color(235, 224 ,21);
	
	// menu fonts
	private final Font mainMenuFont = new Font("Maiandra GD", Font.BOLD, 18);
	private final Font mainMenuFontsmall = new Font("Maiandra GD", Font.PLAIN, 12);
	
	public Menu(ArrayList<GameImageSet> imageSets) throws IOException {
		background = ImageIO.read(getClass().getResourceAsStream("/MainMenubg.jpg"));
		ship = (new GameImageSet()).getRegularShip();
		starbg = ImageIO.read(getClass().getResourceAsStream("/starbg.jpg"));
		
		//sets the user currently at the first
		//option, which is to start the game
		currentSelection = 1;
		
		//booleans for being in
		//various parts of the menu
		inDirections = false;
		inHighscores = false;
		inUserNameCreator = false;
		
		//isRunning is only true
		//when a game is being played.
		//Checks to see if the player lost,
		//then ends the game
		isRunning = false;
		
		//images
		sets = imageSets;
		
		//Highscore system
		hm = new HighscoreManager();
		
		//sets a default highscore list
		if(hm.getScores().size()==0){
			hm.addScore("PWNAGE_GOD", 50000);
			hm.addScore("CHUCKNORIS", 25000);
			hm.addScore("SUPERMARIO", 20000);
			hm.addScore("MASTRCHIEF", 15000);
			hm.addScore("TAITO", 12500);
			hm.addScore("YOUR MOM", 10000);
			hm.addScore("A_CAT", 7500);
			hm.addScore("NOOB", 5000);
			hm.addScore("5_YR_OLD", 3000);
			hm.addScore("MIKE_ROOT", 1000);
		}
		
		//the username changer
		nM = new nameMaker();
		//sets the playerName equal
		//to the defaul nameMaker name
		playerName = nM.getName();
		
	}

	// Getters
	public GamePlay getGame() {
		return game;
	}
	
	public boolean changingName(){
		return nM.isBeingUsed();
	}
	
	public nameMaker nameChanger(){
		return nM;
	}
	//end Getters

	/**
	 * draws the entire menu system
	 * @param g
	 */
	public void draw(Graphics g) {
		// Menu
		if(!inDirections&&!inHighscores&&!inUserNameCreator)
			drawMainMenu(g);
		
		else if(inDirections&&!inHighscores&&!inUserNameCreator)
			drawDirections(g);
		
		else if(!inDirections&&inHighscores&&!inUserNameCreator)
			drawHighscoreList(g);
		
		else if(!inDirections&&!inHighscores&&inUserNameCreator)
			drawUserNameCreator(g);

	}//end draw
	
	/**
	 * draws the main menu
	 * @param g
	 */
	private void drawMainMenu(Graphics g){
		int shipX = 550;
		g.drawImage(background, 0, 0, X_MAX, Y_MAX, null);
		g.setFont(mainMenuFont);
		g.setColor(fontColor);
		
		//highscore
		if(hm.getScores().size()>0)
			g.drawString(String.valueOf(hm.getScores().get(0).getScore()), 140, 70);
		else
			g.drawString(String.valueOf(0), 140, 70);
		//current user
		g.drawString(playerName, 170, 95);
		
		
		g.setFont(mainMenuFontsmall);
		g.setColor(fontColor);
		//(c) Craig Hammond
		g.drawString("© Craig Hammond, 2012", 10, 490);
		// selection directions
		int littleDirectionsX = 585;
		g.drawString("Up and Down Arrow Keys to change", littleDirectionsX, 20);
		g.drawString("Enter to Select", littleDirectionsX, 40);
		
		if (currentSelection == 1) {
			g.drawImage(ship, shipX, 235, 50, 30, null);
		} //end if
		
		else if (currentSelection == 2) {
			g.drawImage(ship, shipX, 280, 50, 30, null);
		}//end if
		
		else if (currentSelection == 3) {
			g.drawImage(ship, shipX, 325, 50, 30, null);
		}//end if
		
		else if (currentSelection == 4) {
			g.drawImage(ship, shipX, 370, 50, 30, null);
		} //end if
		
	}// end drawMainMenu
	
	/**
	 * draws the directions
	 * @param g
	 */
	private void drawDirections(Graphics g){
		g.drawImage(starbg, 0, 0, X_MAX, Y_MAX, null);
		
		g.setFont(mainMenuFont);
		g.setColor(fontColor);
		int x = 50;
		int y = 50;
		int gap = 30;
		g.drawString("Directions", x, y);
		g.drawString("Shoot: Spacebar", x, y+gap);
		g.drawString("Go Left: Left Arrow Key", x, y+2*gap);
		g.drawString("Go Right: Right Arrow Key", x, y+3*gap);
		g.drawString("Close Game: Escape Key", x, y+4*gap);
		
		g.drawString("Player ship", 50, 250);
		g.drawImage((new GameImageSet()).getRegularShip(), 50, 260, 50, 30, null);
		
		g.drawString("Enemy", 200, 250);
		g.drawImage((new GameImageSet()).getRegularEnemy(), 200, 260, 40, 20, null);
		
		g.drawString("UFO", 350, 250);
		g.drawImage((new GameImageSet()).getRegularFlyingEnemy(), 350, 260, 40, 20, null);
		
		drawReturnToMenuString(g);
	}//end drawDirections
	
	/**
	 * draws the highscore list
	 * @param g
	 */
	private void drawHighscoreList(Graphics g){
		int x = 250;
		int spacing = 40;
		g.drawImage(starbg, 0, 0, X_MAX, Y_MAX, null);
		g.setFont(mainMenuFont);
		g.setColor(fontColor);
		g.drawString("Highscores" , 350, 50);
		for(int i = 0; i < hm.getScores().size(); i ++){
			g.drawString((i+1)+". ", x, spacing*i + 100);
			g.drawString(hm.getScores().get(i).getName(), x + 40, spacing*i + 100);
			g.drawString(String.valueOf(hm.getScores().get(i).getScore()), x + 250, spacing*i + 100);
		
		}//end for
		
		drawReturnToMenuString(g);
	}//end drawHighscoreList
	
	/**
	 * draws the username system
	 * @param g
	 */
	private void drawUserNameCreator(Graphics g){
		g.drawImage(starbg, 0, 0, X_MAX, Y_MAX, null);
		
		//x position for everything
		int x = 150;
		
		//draws the nameMaker
		nM.draw(x, 233, g);
		
		//if the person is not changing their name
		//it gives the directions to start changing
		//their name
		if(!changingName()){
			g.setColor(fontColor);
			g.setFont(mainMenuFont);
			g.drawString("Press 'S' to change Username",  x,  220);
		}//end if
		
		//else if the person is changing their
		//name it gives the directions on how
		//to use the nameMaker
		else {
			g.setColor(fontColor);
			g.setFont(mainMenuFont);
			g.drawString("Press 'S' to save Username",  x,  220);
			int y = 300;//y position of first line of instructions
			int gap = 25;//gap between the y position of each line
			g.drawString("Up and Down Arrow Keys to change character", x, y);
			g.drawString("Left Arrow Key to select character to the left", x, y+gap);
			g.drawString("Right Arrow Key to select character to" +
					" the right and to add a character", x, y+2*gap);
			g.drawString("Backspace Key to delete current character", x, y+3*gap);
		}//end else
		
		drawReturnToMenuString(g);
		
	}//end drawUserNameCreator
	
	/**
	 * gives the direction to return to the main menu
	 * @param g
	 */
	private void drawReturnToMenuString(Graphics g){
		g.setColor(fontColor);
		g.setFont(mainMenuFontsmall);
		g.drawString("Enter Key to return to Main Menu", 600, 485);
	}
	
	/**
	 * starts the run method
	 */
	private void check() {
		(new Thread(this)).start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Moves Selection Up
		if (e.getKeyCode() == KeyEvent.VK_UP && !inDirections && !inHighscores && !inUserNameCreator) {
			if (currentSelection != 1) {
				currentSelection--;
			}//end if
		}//end up if

		//Moves Selection Down
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !inDirections && !inHighscores && !inUserNameCreator) {
			if (currentSelection != numChoices) {
				currentSelection++;
			}//end if
		}//end down if

		//Chooses Selection and returns to menu
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			//starts the game
			if (currentSelection == 1 && game == null && !inDirections && !inHighscores && !inUserNameCreator) {
				isRunning = true;
				check();
				game = new GamePlay(sets.get(0));
			}//end if

			//goes in and out of the direction menu
			else if (currentSelection == 2&& !inDirections && !inHighscores && !inUserNameCreator) {
				inDirections = true;
			}//end else if
			else if (currentSelection == 2&& inDirections && !inHighscores && !inUserNameCreator){
				inDirections = false;
			}//end else if
			
			//goes in and out of the highscore list
			else if (currentSelection == 3&& !inDirections && !inHighscores && !inUserNameCreator){
				inHighscores = true;
			}//end else if
			else if (currentSelection == 3&& !inDirections && inHighscores && !inUserNameCreator){
				inHighscores = false;
			}//end else if
			
			//goes in and out of the username changing menu
			else if (currentSelection == 4&& !inDirections && !inHighscores && !inUserNameCreator){
				inUserNameCreator = true;
			}//end else if
			else if (currentSelection == 4&& !inDirections && !inHighscores && inUserNameCreator){
				playerName = nM.getName();
				inUserNameCreator = false;
			}//end else if

		}//end enter if
		
		//Used to start the nameMaker
		//Tells the driver that the nameMaker is being used
		if(e.getKeyCode()==KeyEvent.VK_S&&inUserNameCreator&&!nM.isBeingUsed()){
			if(nM.isBeingUsed()==false){
				nM.setUseState(true);
			}//end if
		}//emd if
		
		//closes program
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}//end escape if

	}//end KeyPressed

	//unused methods
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	@Override
	public void run() {
		while (isRunning) {
			if (game != null) {
				if (game.getLivesRemaining() == 0
						&& game.getSpaceShip() == null) {
					//if the player lost the in game screen
					//stays for 2 seconds and then the game
					//if ended
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					hm.addScore(playerName, game.getGameScore());
					game.stopRunning();
					game = null;
					isRunning = false;
				}//end if
			}//end if

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end while

	}//end run

}//end Menu
