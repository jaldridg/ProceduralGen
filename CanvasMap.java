import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class CanvasMap extends Canvas {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int PIXEL_SIZE = 5;

    private int seed;
    private Random rng;

    private double[][] heights = new double[WIDTH / PIXEL_SIZE][HEIGHT / PIXEL_SIZE];
    private Color[][] colors = new Color[WIDTH / PIXEL_SIZE][HEIGHT / PIXEL_SIZE];

    private Image mapImage = null;
    private Graphics2D g2d;

    public CanvasMap() {
        generate();        

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        // Generate first pixel
        heights[0][0] = 0.5;
        // Generate top
        for (int i = 1; i < heights.length; i++) {
            heights[i][0] = heights[i - 1][0] + rng.nextGaussian();
        }
        // Generate left
        for (int i = 1; i < heights[0].length; i++) {
            heights[0][i] = heights[0][i - 1] + rng.nextGaussian();
        }
        // Generate middle
        for (int i = 1; i < heights.length; i++) {
            for (int j = 1; j < heights[i].length; j++) {
                double average = (heights[i - 1][j] + heights[i][j - 1]) / 2;
                heights[i][j] = average + rng.nextGaussian();
            }
        }
        normalizeHeightArray();
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
        mapImage = createImage(WIDTH, HEIGHT);
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

    public int getSeed() {
        return seed;
    }
    
    private Color generateColor(double height) {
        if (height < 0.2) {
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
}
