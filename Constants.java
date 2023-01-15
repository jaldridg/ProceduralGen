import java.awt.Color;

public final class Constants {
    
    // HEIGHT VALUE CUTOFFS
    public static final float MIN_HEIGHT =             0.0f;
    public static final float MAX_HEIGHT =             1.0f;

    public static final float DEEP_WATER_HEIGHT =      MIN_HEIGHT;
    public static final float SHALLOW_WATER_HEIGHT =   0.2f;
    public static final float SAND_HEIGHT =            0.3f;
    public static final float GRASS_HEIGHT =           0.45f;
    public static final float FOREST_HEIGHT =          0.6f;
    public static final float MOUNTAIN_HEIGHT =        0.8f;
    public static final float SNOW_HEIGHT =            MAX_HEIGHT;


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
    // Higher values make denser noise but not very effective since heights are normalized
    public static final float RANDOM_FACTOR = 10.0f;
    public static final int WATER_POINTS = 25;


    // SIZE CONSTANTS FOR GUI
    public static final int MAP_SIZE = 513;
    public static final int PANEL_WIDTH = 200;

    // Extra size constants since the map is cut off without them somehow
    private final static int EXTRA_WIDTH = 16;
    private final static int EXTRA_HEIGHT = 39;

    public final static int GUI_WIDTH = PANEL_WIDTH + MAP_SIZE + EXTRA_WIDTH;
    public final static int GUI_HEIGHT = MAP_SIZE + EXTRA_HEIGHT;
}
