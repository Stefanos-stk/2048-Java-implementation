
import objectdraw.*;
import java.awt.Color;

/**
 * this class implements a movable tile on the screen.
 *
 * This is only a skeleton. You will have to implement this.
 */
public class Tile {

	// Font constant for the text inside the tile
	private static final int FONT_SIZE = 40;

	// Instance variable for the filled rectangle that represents the tile and the
	// text inside it
	private FilledRect tile;
	private Text tileText;

	// Instance variable for the value of tile
	private int tileValue;

	// Instance variable for the size of the tile
	private int size;

	// Instance boolean variable that enables the tiles to combine or not
	private boolean canCombine;

	// Tile text correction
	private static final int CORRECTION = 10;

	/**
	 * create a new tile TODO - DESIGN: what parameters will constructor take
	 */
	public Tile(Location tileLocation, int size, int tileValue, DrawingCanvas canvas) {
		// Creating the tile constructor
		this.tileValue = tileValue;
		this.size = size;

		// Creating the filled rectangle that represents the tile box and setting it to
		// the appropriate color
		tile = new FilledRect(tileLocation, size, size, canvas);
		tile.setColor(setColor());

		// Creating the text inside the tile that represents the value of the tile and
		// setting it to the appropriate color and font size
		tileText = new Text(tileValue, tileLocation, canvas);
		tileText.setFontSize(FONT_SIZE);
		tileText.setColor(Color2048.DARK_FONT);

		// Moving the text to the center of the tile
		tileText.move((size - tileText.getWidth()) / 2, (size - tileText.getHeight()) / 2);

	}

	public void moveTo(int x, int y) {
		// Method that move the tile and the text to the appropriate position
		this.tile.moveTo(x, y);
		// I created the correction constant because the method tileText.getWidth()
		// outputs the same result regardless of the text being 3 or 4 digits, this to
		// make the text look the best for all the possible scenarios I created that
		// constant
		this.tileText.moveTo(x + (size / 2) - (tileText.getWidth() / 2) - CORRECTION,
				y + (size - tileText.getHeight()) / 2);

	}

	public int getValue() {
		// Method that returns the value of the tile
		return tileValue;

	}

	public void updateValue(int tileValue) {
		// Method that updates the value of the tile
		this.tileValue = tileValue;
		tileText.setText(tileValue);
	}

	public void remove() {
		// Method that removes the tile (filled rectangle and the text) from the canvas
		tileText.removeFromCanvas();
		tile.removeFromCanvas();

	}

	public boolean canTileCombine() {
		// Method that returns whether the tile can combine or not
		return canCombine;
	}

	public void setCanTileCombine(boolean canCombine) {
		// Method that determines one whether the tile can combine or not
		// Because this method is used all the time in the board class I call the set
		// color method because it updates the color of the tile depending on its value
		tile.setColor(setColor());
		this.canCombine = canCombine;
	}

	public Color setColor() {
		// Method that returns the appropriate color depending on what is the value of
		// the tile
		if (getValue() == 2) {
			return Color2048.TILE2;
		} else if (getValue() == 4) {
			return Color2048.TILE4;
		} else if (getValue() == 8) {
			return Color2048.TILE8;
		} else if (getValue() == 16) {
			return Color2048.TILE16;
		} else if (getValue() == 32) {
			return Color2048.TILE32;
		} else if (getValue() == 64) {
			return Color2048.TILE64;
		} else if (getValue() == 128) {
			return Color2048.TILE128;
		} else if (getValue() == 256) {
			return Color2048.TILE256;
		} else if (getValue() == 512) {
			return Color2048.TILE512;
		} else if (getValue() == 1024) {
			return Color2048.TILE1024;
		} else if (getValue() == 2048) {
			return Color2048.TILE2048;
		}
		return Color2048.TILELARGE;
	}

}
