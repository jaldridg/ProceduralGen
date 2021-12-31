import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class CanvasMap extends Canvas {

    private static final int SIDE_LENGTH = 129; // Measured in pixels
    private static final int PIXEL_SIZE = 5;

    private int seed;
    private Random rng;

    private double[][] heights;
    private Color[][] colors;

    private Image mapImage = null;
    private Graphics2D g2d;

    public CanvasMap() {
        generate();        

        this.setPreferredSize(new Dimension(SIDE_LENGTH * PIXEL_SIZE, SIDE_LENGTH * PIXEL_SIZE));
        this.setVisible(true);
    }

    public void generate() {
        seed = (int) (Math.random() * 1000);
        rng = new Random(seed);

        generateHeightArray();
        generateColorArray();
        repaint(); 
    } 

    private void generateHeightArray() {
        // Initialize the array and variables
        heights = new double[SIDE_LENGTH][SIDE_LENGTH];
        int chunkSize = SIDE_LENGTH - 1;
        double randomFactor = 5.0;

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
        
        normalizeHeightArray();
    }

    private void generateDiamondChunk(int chunkSize, double randomFactor) {
        int halfChunk = chunkSize / 2;
        for (int i = 0; i <= SIDE_LENGTH - 1; i += halfChunk) {
            for (int j = ((i + halfChunk) % chunkSize); j <= SIDE_LENGTH - 1; j += chunkSize) {
                double averageValue;
                // Top edge
                if (i == 0) {
                    averageValue = (heights[i + halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 3;
                // Bottom edge
                } else if (i == SIDE_LENGTH - 1) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i][j - halfChunk]
                                  + heights[i][j + halfChunk]) / 3;

                // Left edge
                } else if (j == 0) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i + halfChunk][j]
                                  + heights[i][j + halfChunk]) / 3;

                // Right edge
                } else if (j == SIDE_LENGTH - 1) {
                    averageValue = (heights[i - halfChunk][j]
                                  + heights[i + halfChunk][j]
                                  + heights[i][j - halfChunk]) / 3;
                // No edge
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

    private void generateSquareChunk(int chunkSize, double randomFactor) {
        int halfChunk = chunkSize / 2;
        for (int i = 0; i < SIDE_LENGTH - 1; i += chunkSize) {
            for (int j = 0; j < SIDE_LENGTH - 1; j += chunkSize) {
                double average = (heights[i][j] 
                                + heights[i][chunkSize] 
                                + heights[chunkSize][j] 
                                + heights[chunkSize][chunkSize]) / 4;
                heights[i + halfChunk][j + halfChunk] = average + (rng.nextDouble() - 0.5) * randomFactor;
            }
        }
    }

    private void normalizeHeightArray() {
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

    private void generateColorArray() {
        colors = new Color[SIDE_LENGTH][SIDE_LENGTH];
        if (heights == null) {
            generateHeightArray();
        }
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                colors[i][j] = generateColor(heights[i][j]);
            }
        }
    }

    public void paint(Graphics g) {
        if (colors == null) {
            generateColorArray();
        }
        mapImage = createImage(SIDE_LENGTH * PIXEL_SIZE, SIDE_LENGTH * PIXEL_SIZE);
        g2d = (Graphics2D) mapImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                g2d.setColor(colors[i][j]);
                g2d.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
        g.drawImage(mapImage, 0, 0, null);        
    }
    
    private Color generateColor(double height) {
        if (height == 0.0) {
            // Undefined value
            return new Color(255, 255, 255);
        } else if (height < 0.2) {
            // Dark blue
            return new Color(0, 0, 150);
        } else if (height < 0.3) {
            // Light blue
            return new Color(0, 100, 150);
        } else if (height < 0.5) {
            // Sand
            return new Color(255, 255, 100);
        } else if (height < 0.7) {
            // Light grass
            return new Color(40, 150, 0);
        } else if (height < 0.80) {
            // Dark grass
            return new Color(25, 100, 0);
        } else {
            // Mountain
            return new Color(80, 40, 0);
        }
    }

    public int getSeed() {
        return seed;
    }
}
