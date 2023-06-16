/*
 * HighscoreManager.java
 * 
 * Manages a list of highscores
 * 
 * Author: Unknown
 * Edited by: Craig Hammond
 * Last Updated: 6/13/2012
 */
package highscores;

import java.util.*;
import java.io.*;

public class HighscoreManager {
	// An arraylist of the type "score" we will use to work with the scores
	// inside the class
	private ArrayList<Score> scores;

	// The name of the file where the highscores will be saved
	private static final String HIGHSCORE_FILE = "scores.dat";

	// Initialising an in and outputStream for working with the file
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;

	public HighscoreManager() {
		// initialising the scores-arraylist
		scores = new ArrayList<Score>();
	}
	
	// Getters
	public ArrayList<Score> getScores() {
		loadScoreFile();
		sort();
		return scores;
	}
	//end Getters
	
	/**
	 * sorts the scores from best to worst
	 */
	private void sort() {
		ScoreComparator comparator = new ScoreComparator();
		Collections.sort(scores, comparator);
	}//end sort

	/**
	 * adds a Score to the list
	 * limits the list to ten scores
	 * @param name name of person who got the score
	 * @param score the person's score
	 */
	public void addScore(String name, int score) {
		loadScoreFile();
		scores.add(new Score(name, score));
		sort();
		int desiredLength = 10;
		if(scores.size()>desiredLength){
			for(int i = scores.size()-1; i>desiredLength-1; i--)
				scores.remove(i) ;//end for
		}//end if
		updateScoreFile();
	}//end addScore

	/**
	 * loads the file with the highscores
	 */
	public void loadScoreFile() {
		try {
			inputStream = new ObjectInputStream(new FileInputStream(
					HIGHSCORE_FILE));
			scores = (ArrayList<Score>) inputStream.readObject();
		} catch (FileNotFoundException e) {
//			System.out.println("[Load] FNF Error: " + e.getMessage());
		} catch (IOException e) {
//			System.out.println("[Load] IO Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
//			System.out.println("[Load] CNF Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
//				System.out.println("[Load] IO Error: " + e.getMessage());
			}
		}
	}

	/**
	 * updates the file with the most current highscores
	 */
	public void updateScoreFile() {
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(
					HIGHSCORE_FILE));
			outputStream.writeObject(scores);
		} catch (FileNotFoundException e) {
//			System.out.println("[Update] FNF Error: " + e.getMessage()
//					+ ",the program will try and make a new file");
		} catch (IOException e) {
//			System.out.println("[Update] IO Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
//				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}//end updateScoreFile
}//end HighscoreManager