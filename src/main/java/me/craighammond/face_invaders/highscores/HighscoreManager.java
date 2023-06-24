package me.craighammond.face_invaders.highscores;

import java.util.*;
import java.io.*;

public class HighscoreManager {
	private ArrayList<Score> scores;
	private static final int SCORES_SIZE = 10;

	private static final String HIGHSCORE_FILE = "scores.txt";

	public HighscoreManager() {
		scores = new ArrayList<>(SCORES_SIZE);
	}

	public ArrayList<Score> getScores() {
		loadScoreFile();
		sort();
		return scores;
	}
	
	/**
	 * sorts the scores from best to worst
	 */
	private void sort() {
		scores.sort(new ScoreComparator());
	}

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
		if(scores.size()>SCORES_SIZE){
			scores.subList(SCORES_SIZE, scores.size()).clear();
		}
		updateScoreFile();
	}

	/**
	 * loads the file with the highscores
	 */
	public void loadScoreFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_FILE))) {
			String line;
			scores = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				if (line.equals("")) continue;
				String[] split = line.split(",");
				if (split.length < 2) {
					scores.clear();
					break;
				}
				int score;
				try {
					score = Integer.parseInt(split[1]);
				} catch (NumberFormatException ignored) {
					scores.clear();
					break;
				}
				scores.add(new Score(split[0], score));
			}
		} catch (IOException e) {
			scores = new ArrayList<>();
		}
	}

	/**
	 * updates the file with the most current highscores
	 */
	public void updateScoreFile() {
		try (Writer writer = new BufferedWriter(new FileWriter(HIGHSCORE_FILE, false))) {
			for (Score score : scores.subList(0, SCORES_SIZE)) {
				writer.write(String.format("%s,%d\n", score.getName(), score.getScore()));
			}
		} catch (IOException ignored) {}
	}
}