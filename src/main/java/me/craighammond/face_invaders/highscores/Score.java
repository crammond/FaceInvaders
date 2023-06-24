package me.craighammond.face_invaders.highscores;

public class Score {
	private final int score;
	private final String name;

	public Score(String name, int score) {
		this.score = score;
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
}