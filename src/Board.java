import objectdraw.*;

/**
 * this class implements game board, and its on-screen display
 */
public class Board {

	private static final int MAX_TILE = 2048; // winning tile size
	private static final int ROWS = 4; // Number of rows in the 2D array
	private static final int COLS = 4; // Number of columns in the 2D array
	private static final int TILE_WIDTH = 100; // Tile width

	// Instance variable for the Tile 2D array
	private Tile[][] board;

	// Instance boolean variable checking whether the game is won
	private boolean wonGame;

	// Instance variable for the tile's background
	private FilledRect background;

	// Instance variables for the position of the board
	private int x;
	private int y;
	// Instance variable for the DrawingCanvas
	private DrawingCanvas canvas;

	// Instance variable for the scoreboard
	private ScoreBoard scoreboard;

	// Instance variables for the two random generators needed for the tiles (place)
	// & (value)
	private RandomIntGenerator placeChooser;
	private RandomIntGenerator valueChooser;

	// Instance variables needed to predict the motion of the tiles
	private int horizontalDirection = 0;
	private int verticalDirection = 0;

	// Instance variable for the total score of the player
	private int cumulativeScore = 0;

	// One dimensional array, pool of possibilities for the value of the random
	// generated tiles (extra credit)
	private static final int[] tileValues = { 2, 2, 2, 2, 2, 2, 2, 2, 2, 4 };

	// Integer Constants for the the spacing between tiles, board width and board
	// height
	private static final int SPACING = 5;
	private static final int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * TILE_WIDTH;
	private static final int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * TILE_WIDTH;

	// Instance variables needed for the correct addition of tiles
	private int tileCol, tileRow;

	// Instance boolean variable in order to check whether any tile has moved
	private boolean hasMoved;

	// Instance variable needed to prevent error w/reset and continue buttons (extra
	// credit tasks)
	private int prevent = 0;

	/**
	 * Constructs the board.
	 * 
	 * TODO - DESIGN: what are constructor parameters
	 */
	public Board(int x, int y, ScoreBoard scoreboard, DrawingCanvas canvas) {
		// Board constructor
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		this.scoreboard = scoreboard;

		// Setting the wonGame boolean as false
		wonGame = false;

		// Creating the board 2D array that will keep the position of each tile
		board = new Tile[ROWS][COLS];

		// Creating the tile background
		background = new FilledRect(x, y, BOARD_WIDTH, BOARD_HEIGHT, canvas);
		background.setColor(Color2048.BOARD_BG);

		// Creating the tile 4x4 block where the tiles are going to be placed
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				int xCord = x + SPACING + SPACING * j + TILE_WIDTH * j;
				int yCord = y + SPACING + SPACING * i + TILE_WIDTH * i;
				// Creating and setting the base cells for the board
				FilledRect temp = new FilledRect(xCord, yCord, TILE_WIDTH, TILE_WIDTH, canvas);
				temp.setColor(Color2048.CELL_BG);
			}
		}

		// Creating the two random generator needed to add new tiles (value) (place)
		placeChooser = new RandomIntGenerator(0, ROWS - 1);
		valueChooser = new RandomIntGenerator(0, tileValues.length - 1);

	}

	/**
	 * Randomly adds a tile to the board.
	 */
	public void addTile() {
		// Local variable that checks of the place of the tile is valid
		boolean validPlace = false;

		// While loop that searches for a valid position for the new tile
		while (!validPlace) {
			tileRow = placeChooser.nextValue();
			tileCol = placeChooser.nextValue();
			Tile current = board[tileRow][tileCol];
			// The while loop stops when it finds empty position
			if (current == null)
				break;
		}

		// Picking a random value out of the pool of choices (90% of a 2 value and a 10%
		// of a 4 value)
		int value = tileValues[this.valueChooser.nextValue()];

		// Creating the actual position of the tile and setting it to the 2D board array
		Location tempLocation = new Location(getTileX(tileCol), getTileY(tileRow));
		Tile tile = new Tile(tempLocation, TILE_WIDTH, value, this.canvas);
		board[tileRow][tileCol] = tile;

	}

	// Method that translates the position of a tile in the array to actual
	// coordinates (X Coordinate)
	public int getTileX(int col) {
		return x + SPACING + col * TILE_WIDTH + col * SPACING;
	}

	// Method that translates the position of a tile in the array to actual
	// coordinates (Y Coordinate)
	public int getTileY(int row) {
		return y + SPACING + row * TILE_WIDTH + row * SPACING;
	}

	/**
	 * has the game ended
	 */
	public boolean noMovesLeft() {
		// For loop that goes through all the values of the 2D board array(all the
		// tiles)
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// If a position is empty then there still space for moves
				if (board[row][col] == null) {
					return false;
					// If the position has a tile assigned to it
				} else if (!(board[row][col] == null)) {
					// Check the surrounding blocks to see if they are empty or are able to merge
					if (row > 0) { // This if statement is placed because it prevents an OutOfBoundsException
						Tile tileCheck = board[row - 1][col];
						Tile tileCurrent = board[row][col];
						if (tileCheck == null || tileCurrent.getValue() == tileCheck.getValue()) {
							return false;
						}

					} // Check the surrounding blocks to see if they are empty or are able to merge
					if (row < ROWS - 1) {// This if statement is placed because it prevents an OutOfBoundsException
						Tile tileCheck = board[row + 1][col];
						Tile tileCurrent = board[row][col];
						if (tileCheck == null || tileCurrent.getValue() == tileCheck.getValue()) {
							return false;
						}
					} // Check the surrounding blocks to see if they are empty or are able to merge
					if (col > 0) {// This if statement is placed because it prevents an OutOfBoundsException
						Tile tileCheck = board[row][col - 1];
						Tile tileCurrent = board[row][col];
						if (tileCheck == null || tileCurrent.getValue() == tileCheck.getValue()) {
							return false;
						}
					} // Check the surrounding blocks to see if they are empty or are able to merge
					if (col < COLS - 1) {// This if statement is placed because it prevents an OutOfBoundsException
						Tile tileCheck = board[row][col + 1];
						Tile tileCurrent = board[row][col];
						if (tileCheck == null || tileCurrent.getValue() == tileCheck.getValue()) {
							return false;
						}
					}
				}
			}
		}
		// If none of the tiles are empty or can merge then return true, which states
		// that the board is full and cannot move
		return true;
	}

	/**
	 * Moves tiles to the left, handle merges
	 */
	public boolean moveLeft() {

		// Setting the two boolean variables to false
		wonGame = false;
		hasMoved = false;

		// Setting the axis variables to the correct values
		horizontalDirection = -1;
		verticalDirection = 0;

		// Local variable that asks whether the tile can move or not
		boolean canMove = false;
		// Since we are moving every tile individually we use a for loop to go through
		// all the tiles in the array
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (!canMove) {
					// Using the boolean method moveTiles, if the tile can combine or move then it
					// will return true
					canMove = moveTiles(row, col, horizontalDirection, verticalDirection);
				} else
					// Once it has been proven that you can move or merge at least one tile the
					// canMove boolean is going to be set to true, and it cannot be set to false
					// again
					moveTiles(row, col, horizontalDirection, verticalDirection);
			}
		}
		// If any tile was moved then add a tile
		if (hasMoved) {
			addTile();
		}
		// Returns a boolean that asks whether the game is over or not
		return wonGame;
	}

	/**
	 * Moves tiles to the right, handle merges
	 */
	public boolean moveRight() {

		// Setting the two boolean variables to false
		wonGame = false;
		hasMoved = false;

		// Setting the axis variables to the correct values
		horizontalDirection = 1;
		verticalDirection = 0;

		// Local variable that asks whether the tile can move or not
		boolean canMove = false;
		// Since we are moving every tile individually we use a for loop to go through
		// all the tiles in the array
		for (int row = 0; row < ROWS; row++) {
			for (int col = COLS - 1; col >= 0; col--) {
				if (!canMove) {
					// Using the boolean method moveTiles, if the tile can combine or move then it
					// will return true
					canMove = moveTiles(row, col, horizontalDirection, verticalDirection);
				} else
					// Once it has been proven that you can move or merge at least one tile the
					// canMove boolean is going to be set to true, and it cannot be set to false
					// again
					moveTiles(row, col, horizontalDirection, verticalDirection);
			}
		}
		// If any tile was moved then add a tile
		if (hasMoved) {
			addTile();
		}
		// Returns a boolean that asks whether the game is over or not
		return wonGame;
	}

	/**
	 * Moves tiles upwards, handle merges
	 */
	public boolean moveUp() {
		// Setting the two boolean variables to false
		wonGame = false;
		hasMoved = false;

		// Setting the axis variables to the correct values
		horizontalDirection = 0;
		verticalDirection = -1;

		// Local variable that asks whether the tile can move or not
		boolean canMove = false;
		// Since we are moving every tile individually we use a for loop to go through
		// all the tiles in the array
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (!canMove) {
					// Using the boolean method moveTiles, if the tile can combine or move then it
					// will return true
					canMove = moveTiles(row, col, horizontalDirection, verticalDirection);
				} else
					// Once it has been proven that you can move or merge at least one tile the
					// canMove boolean is going to be set to true, and it cannot be set to false
					// again
					moveTiles(row, col, horizontalDirection, verticalDirection);
			}
		}
		// If any tile was moved then add a tile
		if (hasMoved) {
			addTile();

		}
		// Returns a boolean that asks whether the game is over or not
		return wonGame;
	}

	/**
	 * Moves tiles downwards, handle merges
	 */
	public boolean moveDown() {
		// Setting the two boolean variables to false
		wonGame = false;
		hasMoved = false;

		// Setting the axis variables to the correct values
		horizontalDirection = 0;
		verticalDirection = 1;

		// Local variable that asks whether the tile can move or not
		boolean canMove = false;
		// Since we are moving every tile individually we use a for loop to go through
		// all the tiles in the array
		for (int row = ROWS - 1; row >= 0; row--) {
			for (int col = 0; col < COLS; col++) {
				if (!canMove) {
					// Using the boolean method moveTiles, if the tile can combine or move then it
					// will return true
					canMove = moveTiles(row, col, horizontalDirection, verticalDirection);
				} else
					// Once it has been proven that you can move or merge at least one tile the
					// canMove boolean is going to be set to true, and it cannot be set to false
					// again
					moveTiles(row, col, horizontalDirection, verticalDirection);
			}
		}
		// If any tile was moved then add a tile
		if (hasMoved) {
			addTile();

		}
		// Returns a boolean that asks whether the game is over or not
		return wonGame;

	}

	public boolean moveTiles(int row, int col, int horizontalDirection, int verticalDirection) {
		// Setting the canMove boolean to false
		boolean canMove = false;
		boolean move = true;

		// If the current tile is empty then there is space for other moves so it
		// returns false
		Tile current = board[row][col];
		if (current == null) {
			return false;
		}

		int newCol = col;
		int newRow = row;
		// Inside this loop we are going to move each tile separately depending on which
		// direction the user intends (axis)
		while (move) {
			// while the tile is able to move then move the tile to the appropriate tile
			// inside the array
			newCol = newCol + horizontalDirection;
			newRow = newRow + verticalDirection;

			// The following if statements stop the tiles from going outside of the tile
			// board
			if (horizontalDirection == -1 && newCol < 0) {
				break;
			} else if (horizontalDirection == 1 && newCol > COLS - 1) {
				break;
			} else if (verticalDirection == -1 && newRow < 0) {
				break;
			} else if (verticalDirection == 1 && newRow > ROWS - 1) {
				break;
			}

			// If the spot in question is empty(null)
			if (board[newRow][newCol] == null) {
				// move the tile (that is being moved) to that tile position
				board[newRow][newCol] = current;

				// empty the old position
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;

				// move the image of the tile
				current.moveTo(getTileX(newCol), getTileY(newRow));

				// set the hasMoved and canMove booleans to true
				hasMoved = true;
				canMove = true;

				// If the tile in question has the same value as the moved tile and if the moved
				// tile has not merged yet
			} else if (board[newRow][newCol].getValue() == current.getValue() && current.canTileCombine()) {
				// Setting the new combined value to the tile in question tile
				int tempValue = board[newRow][newCol].getValue() * 2;
				board[newRow][newCol].updateValue(tempValue);

				// adding the score value and setting it to the scoreboard
				cumulativeScore += tempValue;
				scoreboard.setScore(cumulativeScore);

				// removing the old tile position
				board[newRow - verticalDirection][newCol - horizontalDirection].remove();
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;

				// set the hasMoved and canMove booleans to true
				hasMoved = true;
				canMove = true;

				// Setting the canTileCombine method to false since the tiles are only allowed
				// to merge only once
				board[newRow][newCol].setCanTileCombine(false);

				// If the new tile value is equal to the max tile then the game is won
				// the other condition prevents mistakes caused by the implementations of
				// continue and reset buttons
				if ((board[newRow][newCol].getValue()) == MAX_TILE && prevent() == 0) {
					wonGame = true;
					prevent++;
				}

			} else {
				// if no merging or moving takes places then this means that the block should
				// stop moving, thus the move boolean is set to false so the while loop will
				// stop
				move = false;
			}
		}
		return canMove;
	}

	/**
	 * Resets the board by removing all tiles
	 */
	public void restart() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] != null) {
					board[i][j].remove();
					board[i][j] = null;
				}
			}
		}
		// resets the score to 0 and sets the score to 0
		cumulativeScore = 0;
		scoreboard.setScore(0);

	}

	public void resetCanCombine() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null) {
				} else {

					current.setCanTileCombine(true);
				}
			}

		}
	}

	// Methods used so that the program can run properly with the reset and continue
	// button (extra credit question)
	// The two problems that arose after the implementation of the two buttons reset
	// & continue, where the following:
	// When one clicks the continue button if the user reaches the 2048 the you won
	// text is going to show up again, though
	// it shouldn't because the user has already won,these two methods allow that
	// not to happen. The next problem that appeared
	// was the following: if the user has won the game and clicks reset, and wins
	// the game again then the you win text is not going
	// to show up, the two methods allow that not to happen by using a simple
	// integer value to either prevent or allow winning.
	public int setPrevent(int i) {
		prevent = 0;
		return i;

	}

	public int prevent() {
		return prevent;
	}
}
