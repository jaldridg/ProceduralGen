import java.awt.*;

public class MapCanvas extends Canvas {

    public static final int MAP_SIZE = 513;

    private int pixelSize = 4;

    // Whether or not a realistic map should be drawn
    private boolean isRealistic;

    private Image mapImage = null;
    private Graphics2D g2d;

    // The two maps that could be generated. The canvas will draw
    // the currentMap which could be set to either of the two maps
    private Map currentMap;
    private StandardMap standardMap;
    private IslandMap islandMap;

    public MapCanvas(StandardMap standardMap, IslandMap islandMap) {
        isRealistic = true;

        this.standardMap = standardMap;
        this.islandMap = islandMap;
        currentMap = standardMap;

        currentMap.generateAfterEffects();

        this.setPreferredSize(new Dimension(MAP_SIZE, MAP_SIZE));
        this.setVisible(true);
    }

    /**
     * Generates the colors and displays them on an image
     */
    public void paint(Graphics g) {
        generateTileColors(isRealistic);
        mapImage = createImage(currentMap.getSize() * pixelSize, currentMap.getSize() * pixelSize);
        g2d = (Graphics2D) mapImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Tile[][] tiles = currentMap.getTiles();
        // Generate the map
        for (int i = 0; i < currentMap.getSize(); i++) {
            for (int j = 0; j < currentMap.getSize(); j++) {
                g2d.setColor(tiles[i][j].getColor());
                g2d.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
            }
        }
        // If realistic, generate the rivers on top
        if (isRealistic) {
            g2d.setColor(Constants.SHALLOW_WATER_COLOR);
            River[] rivers = currentMap.getRivers();
            for (int i = 0; i < rivers.length; i++) {
                Tile[] riverTiles = rivers[i].getTiles();
                for (int j = 0; j < riverTiles.length; j++) {
                    g2d.fillRect(riverTiles[j].getX() * pixelSize, riverTiles[j].getY() * pixelSize, pixelSize, pixelSize);
                }
            }
        }
        // Offsets the image slightly since our mapsize is not exactly cut in half
        // when we change the resolution since our size is some power of two PLUS ONE
        g.drawImage(mapImage, -pixelSize / 2, -pixelSize / 2, null);        
    }

    /**
     * Generates the color array using the currentMap's height array
     * 
     * @param realistic If {@code true}, the generated colors will be realistic
     */
    private void generateTileColors(boolean realistic) {
        Tile[][] tiles = currentMap.getTiles();
        int size = currentMap.getSize();
        if (realistic) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Tile tile = tiles[i][j];
                    tile.setColor(generateRealisticColor(tile.getHeight()));
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Tile tile = tiles[i][j];
                    tile.setColor(generateColor(tile.getHeight()));
                }
            }
        } 
    }
    
    /**
     * Uses a handful of colors and height cutoffs to generate a color
     * 
     * @param height The height value from 0 to 1
     * @return The {@code Color} of the terrain at the given height
     */
    private Color generateColor(float height) {
        if      (height > Constants.MOUNTAIN_HEIGHT)      { return Constants.MOUNTAIN_COLOR; }
        else if (height > Constants.FOREST_HEIGHT)        { return Constants.FOREST_COLOR; } 
        else if (height > Constants.GRASS_HEIGHT)         { return Constants.GRASS_COLOR; }
        else if (height > Constants.SAND_HEIGHT)          { return Constants.SAND_COLOR; }
        else if (height > Constants.SHALLOW_WATER_HEIGHT) { return Constants.SHALLOW_WATER_COLOR; }
        else                                              { return Constants.DEEP_WATER_COLOR; }
    }

    /**
     * Uses linear interpolation to generate custom colors from
     * a given height. Colors should be similar to {@code generateColor(float height)}
     * 
     * @param height The height value from 0 to 1
     * @return The {@code Color} of the terrain at the given height
     * @see {@code generateColor(float height)}
     */
    private Color generateRealisticColor(float height) {

        // Mountain to snowy/rocky mountain
        if (height > Constants.MOUNTAIN_HEIGHT) {
            int rValue = lerp(Constants.MOUNTAIN_HEIGHT, Constants.SNOW_HEIGHT, 80, 125, height);
            int gValue = lerp(Constants.MOUNTAIN_HEIGHT, Constants.SNOW_HEIGHT, 40, 125, height);
            int bValue = lerp(Constants.MOUNTAIN_HEIGHT, Constants.SNOW_HEIGHT, 0, 125, height);
            return new Color(rValue, gValue, bValue);

        // Forest to mountain
        } else if (height > Constants.FOREST_HEIGHT) {
            int rValue = lerp(Constants.FOREST_HEIGHT, Constants.MOUNTAIN_HEIGHT, 25, 80, height);
            int gValue = lerp(Constants.FOREST_HEIGHT, Constants.MOUNTAIN_HEIGHT, 100, 40, height);
            return new Color(rValue, gValue, 0);

        // Grass to forest
        } else if (height > Constants.GRASS_HEIGHT) {
            int rValue = lerp(Constants.GRASS_HEIGHT, Constants.FOREST_HEIGHT, 40, 25, height);
            int gValue = lerp(Constants.GRASS_HEIGHT, Constants.FOREST_HEIGHT, 150, 100, height);
            return new Color(rValue, gValue, 0);

        // Sand to grass
        } else if (height > Constants.SAND_HEIGHT) {
            int rValue = lerp(Constants.SAND_HEIGHT, Constants.GRASS_HEIGHT, 255, 40, height);
            int gValue = lerp(Constants.SAND_HEIGHT, Constants.GRASS_HEIGHT, 255, 150, height);
            int bValue = lerp(Constants.SAND_HEIGHT, Constants.GRASS_HEIGHT, 150, 0, height);
            return new Color(rValue, gValue, bValue);

        // Deep water to shallow, sand level water
        } else { 
            int gValue = lerp(Constants.DEEP_WATER_HEIGHT, Constants.SAND_HEIGHT, 0, 150, height);
            return new Color(0, gValue, 150);
        }
    }

    /**
     * Produces a linearly interporlated color value from a range of heights
     * 
     * @param minHeight The lowest possible height input
     * @param maxHeight The highest possible height input
     * @param minColor The red, green, or blue color value of the terrain at the {@code minHeight}
     * @param maxColor The red, green, or blue color value of the terrain at the {@code maxHeight}
     * @param height The height value, which should be in between {@code minHeight} and {@code maxHeight}
     * @return A linear interporlated color value in between {@code colorOne} and {@code colorTwo}
     * based on the {@code height}'s distance between {@code minHeight} and {@code maxHeight}
     */
    private int lerp(float minHeight, float maxHeight, int minColor, int maxColor, float height) {
        float ratio = (minColor - maxColor) / (minHeight - maxHeight);
        return (int) (minColor + ((height - minHeight) * ratio));
    }

    public void setSize(int size) {
        standardMap.setSize(size);
        islandMap.setSize(size);
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(boolean isStandardMap) {
        currentMap = isStandardMap ? standardMap : islandMap;
    }

    public boolean isRealistic() {
        return isRealistic;
    }

    public void setRealistic(boolean isRealistic) {
        this.isRealistic = isRealistic;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }

    private int getPixelCount() {
        return ((MAP_SIZE - 1) / pixelSize) + 1;
    }
}
