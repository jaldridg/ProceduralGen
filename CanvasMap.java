import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class CanvasMap extends Canvas {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static final int PIXEL_SIZE = 5;

    private int seed;
    private Random rng;

    private double[][] heights;
    private Color[][] colors;

    public CanvasMap(int seed) {
        this.seed = seed;
        rng = new Random(seed);

        heights = generateHeightMap();
        colors = generateColorMap();
        repaint();        

        this.setBackground(Color.RED);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setVisible(true);
    }

    private double[][] generateHeightMap() {
        double[][] generatedHeights = new double[WIDTH / PIXEL_SIZE][HEIGHT / PIXEL_SIZE];
        for (int i = 0; i < generatedHeights.length; i++) {
            for (int j = 0; j < generatedHeights[i].length; j++) {
                generatedHeights[i][j] = rng.nextDouble();
            }
        }
        return generatedHeights;
    }

    private Color[][] generateColorMap() {
        if (heights == null) {
            generateHeightMap();
        }
        Color[][] generatedColors = new Color[WIDTH / PIXEL_SIZE][HEIGHT / PIXEL_SIZE];
        for (int i = 0; i < generatedColors.length; i++) {
            for (int j = 0; j < generatedColors[i].length; j++) {
                generatedColors[i][j] = generateColor(heights[i][j]);
            }
        }
        return generatedColors;
    }

    public void paint(Graphics g) {
        if (colors == null) {
            generateColorMap();
        }
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                g.setColor(colors[i][j]);
                g.fillRect(j * PIXEL_SIZE, i * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
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
