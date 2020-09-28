import objectdraw.*;
import java.awt.Color;

/**
 * Keeps track of the score.
 *
 * This is only a skeleton. You will have to complete this module
 */
public class ScoreBoard {

	// Instance variables for the score Text and the integer score value
	private Text scoreText;
	private int scoreValue = 0;

	public ScoreBoard(Text text, FilledRect scoreDisplay) {
		// Scoreboard constructor
		this.scoreText = text;
		// Setting the score to 0
		setScore(0);
	}

	/**
	 * Resets the score-board.
	 */
	public void restart() {
		// restart method that resets the score to 0
		setScore(0);
	}

	public void setScore(int value) {
		// The setScore method sets the score accordingly
		this.scoreValue = value;
		scoreText.setText(scoreValue);

	}
}
