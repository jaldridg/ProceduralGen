import java.awt.*;
import java.util.Random;

public abstract class Map {
    
    protected int size; // In pixels

    protected int seed;
    protected Random rng;

    protected double[][] heights;

    protected Image mapImage = null;
    protected Graphics2D g2d;

    public Map() {
        this.size = 129;
        seed = (int) (Math.random() * Integer.MAX_VALUE);
        generate(seed);
    }

    public void generate(int seed) {
        rng = new Random(seed);

        generateHeightArray();
        normalizeHeightArray();
    }

    protected void generateHeightArray() {
        // Initialize the array and variables
        heights = new double[size][size];
        int chunkSize = size - 1;
        double randomFactor = 10.0;

        // Generate four corners
        heights[0][0] = rng.nextDouble();
        heights[0][chunkSize] = rng.nextDouble();
        heights[chunkSize][0] = rng.nextDouble();
        heights[chunkSize][chunkSize] = rng.nextDouble();
        
        while (chunkSize > 1) {
            generateSquareChunk(chunkSize, randomFactor);
            generateDiamondChunk(chunkSize, randomFactor);
            chunkSize /= 2;
            randomFactor /= 2;
        }   
    }

    protected void generateDiamondChunk(int chunkSize, double randomFactor) {
        int halfChunk = chunkSize / 2;
        for (int i = 0; i <= size - 1; i += halfChunk) {
            for (int j = ((i + halfChunk) % chunkSize); j <= size - 1; j += chunkSize) {
                double averageValue;
                // Top edge case
                if (i == 0) {
                    averageValue = (heights[i + halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 3;
                // Bottom edge case
                } else if (i == size - 1) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 3;

                // Left edge case
                } else if (j == 0) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i + halfChunk][j]
                                  + heights[i][j + halfChunk]) / 3;

                // Right edge case
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
                heights[i][j] = averageValue + (rng.nextDouble() - 0.5) * randomFactor;
            }
        }
    }

    protected void generateSquareChunk(int chunkSize, double randomFactor) {
        int halfChunk = chunkSize / 2;
        for (int i = 0; i < size - 1; i += chunkSize) {
            for (int j = 0; j < size - 1; j += chunkSize) {
                double average = (heights[i][j] 
                                + heights[i][j + chunkSize] 
                                + heights[i + chunkSize][j] 
                                + heights[i + chunkSize][j + chunkSize]) / 4;
                heights[i + halfChunk][j + halfChunk] = average + (rng.nextDouble() - 0.5) * randomFactor;
            }
        }
    }

    protected void normalizeHeightArray() {
        // Find max and min
        double max = heights[0][0];
        double min = heights[0][0];
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                if (heights[i][j] > max) {
                    max = heights[i][j];
                } else if (heights[i][j] < min) {
                    min = heights[i][j];
                }
            }
        }
        //Normalize
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                heights[i][j] = (heights[i][j] - min) / (Math.abs(min) + Math.abs(max));
            }
        }
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

    public double[][] getHeightArray() {
        return heights;
    }
}
