import java.awt.Color;

public final class Constants {
    
    // HEIGHT VALUE CUTOFFS
    static final float MIN_HEIGHT =             0.0f;
    static final float MAX_HEIGHT =             1.0f;

    static final float DEEP_WATER_HEIGHT =      MIN_HEIGHT;
    static final float SHALLOW_WATER_HEIGHT =   0.2f;
    static final float SAND_HEIGHT =            0.3f;
    static final float GRASS_HEIGHT =           0.45f;
    static final float FOREST_HEIGHT =          0.6f;
    static final float MOUNTAIN_HEIGHT =        0.8f;
    static final float SNOW_HEIGHT =            MAX_HEIGHT;

    // RGB COLORS FOR TERRAIN
    static final Color DEEP_WATER_COLOR =     new Color(0, 0, 150);
    static final Color RIVER_WATER_COLOR =    new Color(0, 50, 150);
    static final Color SHALLOW_WATER_COLOR =  new Color(0, 100, 150);
    static final Color SAND_COLOR =           new Color(255, 255, 100);
    static final Color GRASS_COLOR =          new Color(40, 150, 0);
    static final Color FOREST_COLOR =         new Color(25, 100, 0);
    static final Color MOUNTAIN_COLOR =       new Color(80, 40, 0);
    static final Color SNOW_COLOR =           new Color(125, 125, 125);

    // GENERATION VARIABLES
    // Higher values make denser noise but not very effective since heights are normalized
    static final float RANDOM_FACTOR = 10.0f;
}
