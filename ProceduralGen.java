import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

// TODO Make a velocity tht changes which is for changing points rather than adding/subtracting random values

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int HEIGHT = 500;
    private final int WIDTH = 500;
    private final int PIXEL_SIZE = 5;

    private final double NOISE_ZOOM = 1.0;

    private double[][] heights = new double[WIDTH / PIXEL_SIZE][HEIGHT / PIXEL_SIZE];

    public ProceduralGen()    
    {    
        JFrame frame = new JFrame("Procedural Gen");   
        setBackground(Color.white);
        
        addKeyListener(this);
        frame.add(this);    
        frame.setSize(WIDTH, HEIGHT);    
        frame.setVisible(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateTerrainArray();
        repaint();
    }    

    public void paint(Graphics g) { }   
    
    public void update(Graphics g) {
        generateTerrainArray();
        //scroll();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                g.setColor(generateColor(heights[i][j]));
                g.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
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
        } else if(height < 0.7) {
            // Light grass
            return new Color(40, 150, 0);
        } else if(height < 0.80) {
            // Dark grass
            return new Color(25, 100, 0);
        } else {
            // Mountain
            return new Color(80, 40, 0);
        }
    }

    // TODO Lets generate pixels based off the surrounding four not the top and left pixels
    private void generateTerrainArray() {
        // Start with two heights
        heights[0][0] = Math.random();
        heights[0][heights[0].length - 1] = Math.random();

        // Generate the top and bottom edges
        for(int i = 1; i < heights.length; i++) {
            for(int j = 0; j < heights[0].length; j += heights[0].length - 1) {
                heights[i][j] = generateNewHeight(heights[i - i][j]);
            }
        }
        
        // Generate the right and left edges
        for(int i = 0; i < heights.length; i += heights.length - 1) {
            for(int j = 1; j < heights[0].length - 1; j++) {
                heights[i][j] = generateNewHeight(heights[i][j - 1]);
            }
        }
        // Generate the middle
        for(int i = 1; i < heights.length - 1; i++) {
            for(int j = 1; j < heights[0].length - 1; j++) {
                heights[i][j] = generateNewHeight(heights[i + 1][j], heights[i - 1][j], heights[i][j + 1], heights[i][j - 1]);
            }
        }
    }
    /*
    // Moves array up and to the left and generates new values in bottom and right edges
    private void scroll() {
        // Move array up and to the left
        for (int i = 0; i < heights.length - 1; i++) {
            for (int j = 0; j < heights[0].length - 1; j++) {
                heights[i][j] = heights[i + 1][j + 1];
            }
        }
        // Generate the bottom left and top right corners
        heights[0][heights[0].length - 1] = generateNewHeight(heights[0][heights[0].length - 2]);
        heights[heights.length - 1][0] = generateNewHeight(heights[heights.length - 2][0]);

        // Generate the right edge and bottom right corner
        for (int i = 1; i < heights.length; i++) {
            double heightAbove = heights[i - 1][heights[0].length - 1];
            double heightToLeft = heights[i][heights[0].length - 2];
            heights[i][heights[0].length - 1] = generateNewHeight(heightAbove, heightToLeft);
        }
        // Generate the bottom edge
        for (int i = 1; i < heights[0].length - 1; i++) {
            double heightAbove = heights[heights.length - 2][i];
            double heightToLeft = heights[heights.length - 1][i - 1];
            heights[heights.length - 1][i] = generateNewHeight(heightAbove, heightToLeft);
        }
    }
    */

    private double generateNewHeight(double referenceHeight) {
        double deltaHeight = Math.random() - 0.5;
        if (((referenceHeight - 0.5) * deltaHeight) > 0) {
            return referenceHeight + deltaHeight * ((referenceHeight - 1) * (referenceHeight));
        }
        return referenceHeight + 2 * (deltaHeight * ((referenceHeight - 1) * (referenceHeight)));
    }

    private double generateNewHeight(double heightOne, double heightTwo, double heightThree, double heightFour) {
        double avgerageHeight = (heightOne + heightTwo + heightThree + heightFour) / 4;
        double deltaHeight = Math.random() - 0.5;
        return avgerageHeight + deltaHeight * ((avgerageHeight - 1) * (avgerageHeight));
    }

    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyPressed(KeyEvent e) { 
        //TODO Make a button for scrolling, refreshing, and killing the program
        repaint();
    }

    public static void main(String args[])    
    {    
        new ProceduralGen();    
    }   
}

/**
 * ARRAY STUFF
 * 
 * 
 * 
 */
