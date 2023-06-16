/*
 * Score.java
 * 
 * A Score is a String which is the
 * name of the one who got the score
 * and the score itself which is an
 * integer.
 * 
 * Author: Unknown
 * Edited by: Craig Hammond
 * Last Updated: 6/13/2012
 */
package highscores;

import java.io.Serializable;

public class Score implements Serializable {
	private int score;
	private String name;

	// Getters
	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
	// end Getters

	public Score(String name, int score) {
		this.score = score;
		this.name = name;
	}//end Constructor
}//end Score