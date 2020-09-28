import objectdraw.*;
import java.awt.event.*;
import java.awt.Color;

/**
 * Game 2048
 * 
 * 2048 is a game in which player moves tiles (with cursor input) to
 * combine tiles with matching values.  The goal is to reach 2048.
 * 
 *
 * This is only a skeleton.  You will have to complete it.
 *
 * DESCRIBE ANY EXTRA CREDIT WORK YOU HAVE DONE
 * 
 * Suggested window size: 480x660
 * 
 * Stefanos Stoikos
 */
public class Game2048 extends WindowController implements KeyListener {
	
	// board and size information
	private static final int NUM_CELLS = 4;		// board size
	private static final int TILE_SIZE = 100;	// tile height/width
	private static final int TILE_BORDER = 5;	// space between tiles
	private static final int BOX_HEIGHT = 80;	// score/reset box
	private static final int BOX_WIDTH = 135;	// score/reset box

	// font sizes
	private static final int TITLE_FONT_SIZE = 100;	// game title
	private static final int CONTROL_FONT_SIZE = 30; // score/reset
	private static final int OVER_FONT_SIZE = 50;	// OVER/WON message
	
	// game state
	private Board board;				// the playing board
	private boolean keyDown = false; 	// is key currently pressed?
	private boolean gameIsOver = false;	// is game over
	
	//Locations constants for texts, boxes and buttons
	private Location gameOverLoc = new Location(100,355);
	private Location displayScoreLoc = new Location(270, 40);
	private Location displayTextScoreLoc = new Location( 300, 75);
	private Location scoreTextLabelLoc = new Location(290,40);
	private Location boardLoc = new Location(20,150);
	private Location resetButtonLoc = new Location(240-BOX_WIDTH/2,580);
	private Location resetTextLoc = new Location(240-BOX_WIDTH/2 + 25,600);
	private Location continueButtonLoc = new Location(240-BOX_WIDTH/2,410);
	private Location continueTextLoc = new Location(240-BOX_WIDTH/2+10,430);
	
	// Instance variable for title
	private Text title;
	
	// Constant for title
	private static final int TITLE_LOCATION = 20;
	
	// Instance variable for the Score-board
	private ScoreBoard scoreboard;
	
	// Instance variables for the win text, game over text and the transparent display for the extra credit question
	private Text winText;
	private Text gameOverText;
	private FilledRect displayBox;
	
	// Instance variable needed to show the score of the user
	private Text displayText;
	
	//Instance variables for the text and filled rectangle needed for reset button (extra credit)
	private FilledRect resetButton;
	private Text resetText;
	
	//Instance variables for the text and filled rectangle needed for continue button(extra credit)
	private FilledRect continueButton;
	private Text continueText;
	/**
	 * Set-up and start the game.
	 */
	public void begin() {
		
		// get ready to handle the arrow keys
		requestFocus();
		addKeyListener(this);
		setFocusable(true);
		canvas.addKeyListener(this);
		
		//Creating the display box for the score box and setting it to the appropriate color 
	    displayBox = new FilledRect(displayScoreLoc, BOX_WIDTH, BOX_HEIGHT, canvas);
	    displayBox.setColor(Color2048.BOARD_BG);
	    
	    //Creating the display text for the score box and setting it to the appropriate color & font 
	    displayText = new Text("",displayTextScoreLoc, canvas);
	    displayText.setFontSize(CONTROL_FONT_SIZE);
	    displayText.setColor(Color2048.LIGHT_FONT);

	    //Initializing the scoreboard and its components
	    this.scoreboard = new ScoreBoard(displayText,displayBox);
	    Text scoreLabel = new Text("Score:",scoreTextLabelLoc,canvas);
	    scoreLabel.setFontSize(CONTROL_FONT_SIZE);
	    scoreLabel.setColor(Color2048.LIGHT_FONT);
		
	    //Initialing the text that shows the 2048 title and setting it to the appropriate color and font 
		title = new Text("2048",TITLE_LOCATION,TITLE_LOCATION,canvas);
		title.setFontSize(TITLE_FONT_SIZE);
		title.setColor(Color2048.DARK_FONT);
		
		//Initializing the board class and adding a random tile
		board = new Board((int)boardLoc.getX(),(int)boardLoc.getY(),scoreboard,canvas);
		board.addTile();

		//Initializing the win text,setting it to the appropriate font and hiding it 
		winText = new Text("YOU WON!",gameOverLoc.getX()+30,gameOverLoc.getY(),canvas);
		winText.setFontSize(OVER_FONT_SIZE);
		winText.hide();
		
		//Initializing the game over text,setting it to the appropriate font and hiding it
		gameOverText = new Text("GAME OVER",gameOverLoc,canvas);
		gameOverText.setFontSize(OVER_FONT_SIZE);
		gameOverText.hide();
		
		//Initializing the transparent background for the game over text(extra credit question) and hiding it
		displayBox = new FilledRect(0,gameOverLoc.getY(),getWidth(),gameOverText.getHeight(),canvas);
		displayBox.setColor(Color2048.GAMEOVER_BG);
		displayBox.hide();
		
		//Initializing the reset button and setting it to the appropriate color
		resetButton = new FilledRect(resetButtonLoc,BOX_WIDTH,BOX_HEIGHT,canvas);
		resetButton.setColor(Color2048.TILE64);
		
		//Initializing the reset text and setting it to the appropriate font 
		resetText = new Text("Reset",resetTextLoc,canvas);
		resetText.setFontSize(CONTROL_FONT_SIZE);
		
		//Initializing the continue button and setting it to the appropriate color and hiding it
		continueButton= new FilledRect(continueButtonLoc,BOX_WIDTH,BOX_HEIGHT,canvas);
		continueButton.setColor(Color2048.TILE512);
		continueButton.hide();
		
		//Initializing the continue button and setting it to the appropirate font and hiding it
		continueText = new Text("Continue",continueTextLoc,canvas);
		continueText.setFontSize(CONTROL_FONT_SIZE);
		continueText.hide();
	
		
	}

	
	/**
	 * method that Resets the game and scoreboard
	 */
	private void restart() {
		//Empties the board of any tiles
		board.restart();
		
		//Hides all the pop-up button, texts and transparent background
		gameOverText.hide();
		winText.hide();
		displayBox.hide();
		continueButton.hide();
		continueText.hide();
		
		//Adding a new tile
		board.addTile();
		
		//Setting the gameIsOver boolean value to false since the game is restarted 
		gameIsOver = false;
		
		//Reseting the prevent function so that we avoid mistakes due to the extra credit buttons
		board.setPrevent(0);

	}


	/**
	 * Event handler, called when mouse is clicked.
	 * 
	 * Handles the reset button.
	 * 
	 * @param point    mouse coordinates
	 */
	public void onMouseClick(Location point) {
		//If the reset button is clicked then the reset method is called
		if(resetButton.contains(point)) {
			restart();
		//If the continue button is clicked then the win text,continue button are hidden so the user can continue playing
		}else if(continueButton.contains(point)) {
			winText.hide();
			continueButton.hide();
			continueText.hide();
			displayBox.hide();
			
			//Setting the gameIsOver boolean to false since the player wants to continue playing 
			gameIsOver = false;
			
		}
	}

	/**
	 * (required) KeyListener event handler for a key having been pressed and
	 * released.
	 * 
	 * @param e    event (key that was typed)
	 */
	public void keyTyped(KeyEvent e) {}

	/**
	 * (required) KeyListener event handler for a key having been released.
	 * 
	 * @param e    event (key that was released)
	 */
	public void keyReleased(KeyEvent e) {
		// make sure each keystroke is only processed once
		keyDown = false;
	}

	/**
	 * (required) KeyListener event handler for a key having been pressed.
	 * 
	 * Handles the game state (playing or game over) and 
	 * handles arrow keys by moving the tiles in the indicated direction.
	 * 
	 * @param e    event (key that was pressed)
	 */
	public void keyPressed(KeyEvent e) {
		if(!gameIsOver) {
			if(!keyDown) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					//The moveUp method when called moves the tiles(if possible) and merges them (if possible), and returns a boolean value on whether the game is won
					if(board.moveUp()) {
						//When the game is won then the display box, win text and continue button appear
						displayBox.show();
						displayBox.sendToFront();
						
						winText.show();
						winText.sendToFront();
						
						continueButton.show();
						continueButton.sendToFront();
						
						continueText.show();
						continueText.sendToFront();
						//GameIsOver is set true since the game is won
						gameIsOver = true;
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					//The moveDown method when called moves the tiles(if possible) and merges them (if possible), and returns a boolean value on whether the game is won
					if(board.moveDown()) {
						displayBox.show();
						displayBox.sendToFront();
						
						winText.show();
						winText.sendToFront();
						
						continueButton.show();
						continueButton.sendToFront();
						
						continueText.show();
						continueText.sendToFront();
						//GameIsOver is set to true since the game is won
						gameIsOver = true;
					}
					
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					//The moveLeft method when called moves the tiles(if possible) and merges them (if possible), and returns a boolean value on whether the game is won
					if(board.moveLeft()) {
						displayBox.show();
						displayBox.sendToFront();
						
						winText.show();
						winText.sendToFront();
						
						continueButton.show();
						continueButton.sendToFront();
						
						continueText.show();
						continueText.sendToFront();
						//GameIsOver is set to true since the game is won
						gameIsOver = true;
					}
					
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					//The moveRight method when called moves the tiles(if possible) and merges them (if possible), and returns a boolean value on whether the game is won
					if(board.moveRight()){
						displayBox.show();
						displayBox.sendToFront();
						
						winText.show();
						winText.sendToFront();
						
						continueButton.show();
						continueButton.sendToFront();
						
						continueText.show();
						continueText.sendToFront();
						//GameIsOver is set to true since the game is won
						gameIsOver = true;
					}
				}
				keyDown = true;
				
			}

			
			//This if statement checks whether there are any possible moves left for the user, if not then the game is over
			if (board.noMovesLeft()) {
				gameIsOver = true;
				//The transparent display appears
				displayBox.show();
				displayBox.sendToFront();
				
				//The game over text appears
				gameOverText.show();
				gameOverText.sendToFront();
			}
			//Every time the user has clicked a button to move the tiles, each tile is allowed to merge with only one tile
			//The ability of the tiles to merge is thus reset
			board.resetCanCombine();
		}
	
	}
}
