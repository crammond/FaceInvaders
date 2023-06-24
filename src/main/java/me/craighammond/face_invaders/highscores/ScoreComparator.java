package me.craighammond.face_invaders.highscores;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
	public int compare(Score score1, Score score2) {
		int sc1 = score1.getScore();
		int sc2 = score2.getScore();
		return Integer.compare(sc2, sc1);
	}
}