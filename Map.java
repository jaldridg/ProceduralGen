import java.awt.*;
import java.util.Random;

public abstract class Map {
    
    protected int size; // In pixels; must be 2^n + 1

    protected float[][] heights;

    protected int seed;
    protected Random rng;

    protected Image mapImage = null;
    protected Graphics2D g2d;

    protected AfterEffectsGenerator aeg;

    public Map() {
        this.size = 129;
        seed = (int) (Math.random() * Integer.MAX_VALUE);
        generate(seed);
    }

    public void generate(int seed) {
        rng = new Random(seed);

        generateHeightArray();
        normalizeHeightArray();

        aeg = new AfterEffectsGenerator(size, heights, rng);
    }

    /**
     * Generates height array using the diamond-square algorithm
     */
    protected void generateHeightArray() {
        // Initialize the array and variables
        heights = new float[size][size];
        int chunkSize = size - 1;
        float randomFactor = Constants.RANDOM_FACTOR;

        // Generate the four corners for the first square chunk
        heights[0][0] = rng.nextFloat();
        heights[0][chunkSize] = rng.nextFloat();
        heights[chunkSize][0] = rng.nextFloat();
        heights[chunkSize][chunkSize] = rng.nextFloat();
        
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
                    averageValue = (heights[i + halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 3;

                // Bottom edge case (ommit value under current point calculating average)
                } else if (i == size - 1) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 3;

                // Left edge case (ommit value left of current point when calculating average)
                } else if (j == 0) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i + halfChunk][j]
                                  + heights[i][j + halfChunk]) / 3;

                // Right edge case (ommit value right of current point when calculating average)
                } else if (j == size - 1) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i + halfChunk][j]
                                  + heights[i][j - halfChunk]) / 3;

                // Center (normal generation)
                } else {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i + halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 4;
                }
                heights[i][j] = averageValue + (rng.nextFloat() - 0.5f) * randomFactor;
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
                float average = (heights[i][j] 
                               + heights[i][j + chunkSize] 
                               + heights[i + chunkSize][j] 
                               + heights[i + chunkSize][j + chunkSize]) / 4;
                heights[i + halfChunk][j + halfChunk] = average + (rng.nextFloat() - 0.5f) * randomFactor;
            }
        }
    }

    /**
     * Normalizes the height array by first finding the min and max,
     * then making the heights positive by subtracting the min.
     * Finally, the heights become 0 to 1 after dividing by the range
     */
    protected void normalizeHeightArray() {
        // Find max and min
        float max = heights[0][0];
        float min = heights[0][0];
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                if (heights[i][j] > max) {
                    max = heights[i][j];
                } else if (heights[i][j] < min) {
                    min = heights[i][j];
                }
            }
        }
        // Calculate values before the loop for speed
        float range = Math.abs(min) + Math.abs(max);
        float rangeInv = 1 / range;
        //Normalize
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                heights[i][j] = (heights[i][j] - min) * rangeInv;
            }
        }
    }

    protected void generateAfterEffects() {
        aeg.generateAfterEffects();
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

    public float[][] getHeightArray() {
        return heights;
    }
}
