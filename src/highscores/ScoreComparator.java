/*
 *ScoreComparator.java
 *
 * Used to compare one score
 * to another
 * 
 * Author: Unknown
 * Edited by: Craig Hammond
 * Last Updated: 6/13/2012
 */
package highscores;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
	public int compare(Score score1, Score score2) {
		int sc1 = score1.getScore();
		int sc2 = score2.getScore();
		if (sc1 > sc2) {
			return -1;
		} //end if
		else if (sc1 < sc2) {
			return +1;
		} //end else if
		else {
			return 0;
		} //end else
	}//end constructor
}//end ScoreComparator