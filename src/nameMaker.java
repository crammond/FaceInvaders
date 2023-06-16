/*
 * nameMaker.java
 * 
 * Allows a user to create a string of
 * 1 to 10 allowed characters
 * 
 * Author: Craig Hammond
 * Last Updated: 6/13/2012
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class nameMaker implements KeyListener {
	private String name;
	//which letter the person is chaning
	private int letterOn;
	//maximum string length
	private final int MAX = 10;
	//allowed characters
	private final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', ' '};
	private boolean isBeingUsed;
	private final Font font = new Font("Lucida Console", Font.PLAIN, 17);

	public nameMaker() {
		name = "DEFAULT";
		letterOn = name.length() - 1;
		isBeingUsed = false;
	}//end construstor

	// Getters
	public String getName() {
		return name;
	}

	public boolean isBeingUsed() {
		return isBeingUsed;
	}

	// End Getters

	// Setters
	public void setUseState(boolean b) {
		isBeingUsed = b;
	}
	// End Setters

	public void draw(int x, int y, Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 200, 35);
		g.setFont(font);
		//if this nameMaker is being used character
		//that is being edited
		if (isBeingUsed) {
			String temp = name.substring(0,letterOn)+'|'+name.charAt(letterOn)+'|'+name.substring(letterOn+1);
			g.setColor(Color.red);
			g.drawString(temp, x+10, y+22);
		}//end if
		
		//otherwise it just draw the string in black
		else {
			g.setColor(Color.black);
			g.drawString(name, x+10, y+22);
		}//end else
	}//end draw

	@Override
	public void keyPressed(KeyEvent e) {
		if (isBeingUsed) {
			//moves over to the character to the left
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (letterOn > 0) {
					letterOn--;
				}//end if
			}//end if

			//moves over to the character to the right
			//adds a character if there is none
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (letterOn < MAX) {
					letterOn++;
					if(letterOn>name.length()-1){
						name = name+'A';
					}//end if
				}//end if
			}//end if
			
			//deletes the current character
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (letterOn > 0) {
					int temp = name.length()-1;
					if(letterOn==name.length()-1){
						letterOn--;
					}//end uf
					name = name.substring(0, temp);
				}//end if
			}//end if

			//changes the the selected character the the next
			//character in chars[]
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				Character temp = name.charAt(letterOn);
				int i = 0;
				while (!temp.equals(chars[i])) {
					i++;
				}//end while
				if (i == chars.length - 1) {
					temp = chars[0];
				}//end if 
				else
					temp = chars[i + 1];
				name = name.substring(0, letterOn) + temp
						+ name.substring(letterOn + 1);
			}//end if

			
			//changes the the selected character the the previous
			//character in chars[]
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				Character temp = name.charAt(letterOn);
				int i = 0;
				while (!temp.equals(chars[i])) {
					i++;
				}//end while
				if (i == 0) {
					temp = chars[chars.length - 1];
				} //end if
				else
					temp = chars[i - 1];
				name = name.substring(0, letterOn) + temp
						+ name.substring(letterOn + 1);
			}//end if

			if (e.getKeyCode() == KeyEvent.VK_S) {
				isBeingUsed = false;
			}//end if
		}//end if
	}//end KeyPressed

	//unused methods
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}//end nameMaker
