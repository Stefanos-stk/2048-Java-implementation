import java.awt.Color;

/**
 * Color2048.java
 * 
 * Color scheme of the game 2048. 
 */
public class Color2048 {
	
	// background colors
	public static final Color BOARD_BG = new Color(187, 173, 160);
	public static final Color CELL_BG = new Color(205, 192, 180);

	// transparent Game Over shroud
	public static final Color GAMEOVER_BG = new Color(255, 255, 255, 150);
	
	// tile colors based on value
	public static final Color TILE2 = new Color(238, 228, 218);
	public static final Color TILE4 = new Color(237, 224, 200);
	public static final Color TILE8 = new Color(242, 177, 121);
	public static final Color TILE16 = new Color(245, 149, 99);
	public static final Color TILE32 = new Color(246, 124, 95);
	public static final Color TILE64 = new Color(246, 94, 59);
	public static final Color TILE128 = new Color(237, 207, 114);
	public static final Color TILE256 = new Color(237, 204, 97);
	public static final Color TILE512 = new Color(237, 200, 80);
	public static final Color TILE1024 = new Color(237, 197, 63);
	public static final Color TILE2048 = new Color(237, 194, 46);
	public static final Color TILELARGE = new Color(0, 0, 0);
	
	// font colors	DARK for values <=4, else LIGHT
	public static final Color DARK_FONT = new Color(132, 121, 107);
	public static final Color LIGHT_FONT = new Color(255, 255, 255);
}
