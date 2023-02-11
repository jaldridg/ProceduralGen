import java.awt.Color;

public final class Constants {
    
    // HEIGHT VALUE CUTOFFS
    public static final short MIN_HEIGHT =             0;
    public static final short MAX_HEIGHT =             Short.MAX_VALUE;

    public static final short DEEP_WATER_HEIGHT =      MIN_HEIGHT;
    public static final short SHALLOW_WATER_HEIGHT =   (short) (MAX_HEIGHT * 0.3);
    public static final short SAND_HEIGHT =            (short) (MAX_HEIGHT * 0.4);
    public static final short GRASS_HEIGHT =           (short) (MAX_HEIGHT * 0.55);
    public static final short FOREST_HEIGHT =          (short) (MAX_HEIGHT * 0.75);
    public static final short MOUNTAIN_HEIGHT =        (short) (MAX_HEIGHT * 0.9);
    public static final short SNOW_HEIGHT =            MAX_HEIGHT;


    // RGB COLORS FOR TERRAIN
    public static final Color DEEP_WATER_COLOR =     new Color(0, 0, 150);
    public static final Color RIVER_WATER_COLOR =    new Color(0, 50, 150);
    public static final Color SHALLOW_WATER_COLOR =  new Color(0, 100, 150);
    public static final Color SAND_COLOR =           new Color(255, 255, 100);
    public static final Color GRASS_COLOR =          new Color(40, 150, 0);
    public static final Color FOREST_COLOR =         new Color(25, 100, 0);
    public static final Color MOUNTAIN_COLOR =       new Color(80, 40, 0);
    public static final Color SNOW_COLOR =           new Color(125, 125, 125);


    // GENERATION VARIABLES
    public static final int RANDOM_FACTOR = 75000;
    public static final int ISLAND_GENERATION_STRENGTH = 100000;
    public static final int WATER_POINTS = 0; // 25


    // SIZE CONSTANTS FOR GUI
    public static final int MAP_SIZE = 513;
    public static final int PANEL_WIDTH = 200;

    // Extra size constants since the map is cut off without them somehow
    private final static int EXTRA_WIDTH = 16;
    private final static int EXTRA_HEIGHT = 39;

    public final static int GUI_WIDTH = PANEL_WIDTH + MAP_SIZE + EXTRA_WIDTH;
    public final static int GUI_HEIGHT = MAP_SIZE + EXTRA_HEIGHT;
}
