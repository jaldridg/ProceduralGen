import java.awt.*;
import java.util.Random;

public abstract class Map {
    
    protected int size; // In pixels; must be 2^n + 1

    protected Tile[][] tiles;

    protected int seed;
    protected Random rng;

    protected Image mapImage = null;
    protected Graphics2D g2d;

    private AfterEffectsGenerator aeg;

    public Map() {
        this.size = 129;
        seed = (int) (Math.random() * Integer.MAX_VALUE);
        generate(seed);
    }

    public void generate(int seed) {
        rng = new Random(seed);

        generateTileArray();
        normalizeTileHeights();

        aeg = new AfterEffectsGenerator(this, rng);
    }

    /**
     * Generates height array using the diamond-square algorithm
     */
    protected void generateTileArray() {
        // Initialize the array and variables
        tiles = new Tile[size][size];
        int chunkSize = size - 1;
        float randomFactor = Constants.RANDOM_FACTOR;

        // Generate the four corners for the first square chunk
        tiles[0][0] = new Tile(0, 0, rng.nextFloat());
        tiles[0][chunkSize] = new Tile(0, chunkSize, rng.nextFloat());
        tiles[chunkSize][0] = new Tile(chunkSize, 0, rng.nextFloat());
        tiles[chunkSize][chunkSize] = new Tile(chunkSize, chunkSize, rng.nextFloat());
        
        // Generate chunks, then generate chunks in the chunks, and so on
        while (chunkSize > 1) {
            generateSquareChunk(chunkSize, randomFactor);
            generateDiamondChunk(chunkSize, randomFactor);
            chunkSize >>= 1;
            randomFactor /= 2;
        }   
    }

    /**
     * Generates new heights by averaging a point's surrounding heights in a 
     * diamond shape around the point. Then, it adds randomness to the averaged 
     * height to generate new height values on the new point. When a new height 
     * is too close to the edge of the Map to sample all four surrounding points, 
     * it uses only the three valid surrounding heights.
     * 
     * @param chunkSize The number of pixels from opposite corners of the diamond.
     * A smaller chunkSize means the sampled heights lie closer to the new height.
     * @param randomFactor The amount of randomness added onto the averaged heights.
     * It's proportional to chunkSize
     */
    protected void generateDiamondChunk(int chunkSize, float randomFactor) {
        int halfChunk = chunkSize >> 1;
        for (int i = 0; i <= size - 1; i += halfChunk) {
            // Offsets the height in every other row by halfChunk
            for (int j = ((i + halfChunk) % chunkSize); j <= size - 1; j += chunkSize) {
                float averageValue;
                // Top edge case (ommit value above current point when calculating average)
                if (i == 0) {
                    averageValue = (tiles[i + halfChunk][j].getHeight()
                                  + tiles[i][j - halfChunk].getHeight()
                                  + tiles[i][j + halfChunk].getHeight()) / 3;

                // Bottom edge case (ommit value under current point calculating average)
                } else if (i == size - 1) {
                    averageValue = (tiles[i - halfChunk][j].getHeight()
                                  + tiles[i][j - halfChunk].getHeight()
                                  + tiles[i][j + halfChunk].getHeight()) / 3;

                // Left edge case (ommit value left of current point when calculating average)
                } else if (j == 0) {
                    averageValue = (tiles[i - halfChunk][j].getHeight()
                                  + tiles[i + halfChunk][j].getHeight()
                                  + tiles[i][j + halfChunk].getHeight()) / 3;

                // Right edge case (ommit value right of current point when calculating average)
                } else if (j == size - 1) {
                    averageValue = (tiles[i - halfChunk][j].getHeight()
                                  + tiles[i + halfChunk][j].getHeight()
                                  + tiles[i][j - halfChunk].getHeight()) / 3;

                // Center (normal generation)
                } else {
                    averageValue = (tiles[i - halfChunk][j].getHeight()
                                  + tiles[i + halfChunk][j].getHeight()
                                  + tiles[i][j - halfChunk].getHeight()
                                  + tiles[i][j + halfChunk].getHeight()) / 4;
                }
                float generatedHeight = averageValue + (rng.nextFloat() - 0.5f) * randomFactor;
                tiles[i][j] = new Tile(i, j, generatedHeight);
            }
        }
    }

    /**
     * Generates new heights by averaging a point's surrounding heights in a 
     * square shape around the point. Then, it adds randomness to the averaged 
     * height to generate new height values on the new point. 
     * 
     * @param chunkSize The number of pixels measuring the side of the square.
     * A smaller chunkSize means the sampled heights lie closer to the new height.
     * @param randomFactor The amount of randomness added onto the averaged heights.
     * It's proportional to chunkSize
     */
    protected void generateSquareChunk(int chunkSize, float randomFactor) {
        int halfChunk = chunkSize >> 1;
        for (int i = 0; i < size - 1; i += chunkSize) {
            for (int j = 0; j < size - 1; j += chunkSize) {
                float average = (tiles[i][j].getHeight() 
                               + tiles[i][j + chunkSize].getHeight() 
                               + tiles[i + chunkSize][j] .getHeight()
                               + tiles[i + chunkSize][j + chunkSize].getHeight()) / 4;
                float generatedHeight = average + (rng.nextFloat() - 0.5f) * randomFactor;
                int xIndex = i + halfChunk;
                int yIndex = j + halfChunk;
                tiles[xIndex][yIndex] = new Tile(xIndex, yIndex, generatedHeight);
            }
        }
    }

    protected void normalizeTileHeights() {
        // Find max and min
        float max = tiles[0][0].getHeight();
        float min = tiles[0][0].getHeight();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float height = tiles[i][j].getHeight();
                if (height > max) {
                    max = height;
                } else if (height < min) {
                    min = height;
                }
            }
        }

        float range = Math.abs(min) + Math.abs(max);
        float rangeInv = 1 / range;
        //Normalize
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j].setHeight((tiles[i][j].getHeight() - min) * rangeInv);
            }
        }
    }

    public void generateAfterEffects() {
        aeg.generateAfterEffects();
    }

    public Random getRNG() {
        return rng;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSeed() {
        return seed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getHeight(int x, int y) {
        return tiles[x][y].getHeight();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    // Used to get tiles neighbors, considering border tiles with less neighbors
    public Tile[] getSurroundingTiles(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        boolean topEdge = y == 0;
        boolean bottomEdge = y == size - 1;

        // Case where tile is on the left edge
        if (x == 0) {
            if (topEdge) { return new Tile[] {tiles[x + 1][y], tiles[x][y + 1]}; }
            else if (bottomEdge) { return new Tile[] {tiles[x + 1][y], tiles[x][y - 1]}; }
            return new Tile[] {tiles[x + 1][y], tiles[x][y - 1], tiles[x][y + 1] };
        
        // Case where tile is on the right edge
        } else if (x == size - 1) {
            if (topEdge) { return new Tile[] {tiles[x - 1][y], tiles[x][y + 1]}; }
            if (bottomEdge) { return new Tile[] {tiles[x - 1][y], tiles[x][y - 1]}; }
            return new Tile[] {tiles[x - 1][y], tiles[x][y - 1], tiles[x][y + 1]};
        } 

        // Remaining cases
        else if (topEdge) { return new Tile[] {tiles[x + 1][y], tiles[x - 1][y], tiles[x][y + 1]}; }
        else if (bottomEdge) { return new Tile[] {tiles[x + 1][y], tiles[x - 1][y], tiles[x][y - 1]}; }
        else { return new Tile[] {tiles[x - 1][y], tiles[x + 1][y], tiles[x][y - 1], tiles[x][y + 1]}; }
    }

    public Tile getMinSurroundingTile(Tile tile) {
        Tile[] surroundingTiles = getSurroundingTiles(tile);
        Tile minTile = surroundingTiles[0];
        for (Tile t : surroundingTiles) {
            if (minTile.isHigherThan(t)) {
                minTile = t;
            }
        }
        return minTile;
    }

    public Tile getMinSurroundingTileNoLake(Tile tile) {
        Tile[] surroundingTiles = getSurroundingTiles(tile);
        Tile minTile = null;
        for (Tile t : surroundingTiles) {
            if (t.isLake()) { continue; }
            if (minTile == null || minTile.isHigherThan(t)) {
                minTile = t;
            }
        }
        return minTile;
    }

    public boolean isOnBorder(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        boolean onXLimit = (x == 0) || (x == size - 1);
        boolean onYLimit = (y == 0) || (y == size - 1);
        return onXLimit || onYLimit;
    }
}
