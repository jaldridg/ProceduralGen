import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

// TODO Implement algorithm that takes midpoints of corners with added randomness until every pixel is colored
// or make a velocity that changes which is for changing points rather than adding/subtracting random values

public class ProceduralGen extends Canvas implements KeyListener
{        
    // TODO these values are not very flexible so write a method that takes any
    // number and adjusts these values if they're not in proper form without messing
    // with the general idea
    // TODO Also NUM_PIXELS is confusing. Is it total or..?
    private final int PIXEL_SIZE = 5;
    private final int NUM_PIXELS = 65; // Must be in the form 2^n + 1
    private final int SIZE = PIXEL_SIZE * NUM_PIXELS;

    private double[][] heights = new double[SIZE / PIXEL_SIZE][SIZE / PIXEL_SIZE];

    public ProceduralGen()    
    {    
        JFrame frame = new JFrame("Procedural Gen");   
        setBackground(Color.white);
        
        addKeyListener(this);
        frame.add(this);    
        frame.setSize(SIZE, SIZE);    
        frame.setVisible(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        repaint();
    }    

    public void paint(Graphics g) { }   
    
    public void update(Graphics g) {
        generateTerrainArray();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                g.setColor(generateColor(heights[i][j]));
                g.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    
    private void generateTerrainArray() {
        // Generate the four corners
        heights[0][0] = Math.random();
        heights[heights.length - 1][0] = Math.random();
        heights[0][heights.length - 1] = Math.random();
        heights[heights.length - 1][heights.length - 1] = Math.random();

        for(int i = 0; i < 0; i++) {
            int id = (int) (NUM_PIXELS / Math.pow(2, i));
            // Subtract one from id on first iteration to stay in bounds
            id = i == 0 ? id - 1 : id;
            generateRandomMidpoint(0, 0, 0, id);
        }
        int id = NUM_PIXELS - 1;
        splitChunk(0, 0, id, 0, 0, id, id, id);
    }
    
    /**
     * @param i1 col index of top left point
     * @param j1 row index of top left point
     * @param i2 col index of top right point
     * @param j2 row index of top right point
     * @param i3 col index of bottom left point
     * @param j3 row index of bottom left point
     * @param i4 col index of bottom right point
     * @param j4 row index of bottom right point
     */
    private void splitChunk (int i1, int j1, int i2, int j2, int i3, int j3, int i4, int j4) {
        // Make midpoints of the given points and a diagonal midpoint
        generateRandomMidpoint(i1, j1, i2, j2);
        generateRandomMidpoint(i1, j1, i3, j3);
        generateRandomMidpoint(i4, j4, i2, j2);
        generateRandomMidpoint(i4, j4, i3, j3);
        generateRandomMidpoint(i1, j1, i4, j4);
    }

    private void generateRandomMidpoint(int i1, int j1, int i2, int j2) {
        double avgHeight = (heights[i1][j1] + heights[i2][j2]) / 2;
        double minDistToEdge = Math.abs(avgHeight - 1) > avgHeight ? avgHeight : Math.abs(avgHeight - 1);
        // Calculates the distance between the points and counts diagonal squares as one
        // If the points are diagonal, i or j is the diagonal squares
        // If they're not diagonal, use the sum of differences because one difference is 0
        int pixelDistanceToPoint = (i1 - i2) == (j1 - j2) ? Math.abs(i1 - i2) : Math.abs((i1 - i2) + (j1 - j2));
        double scalingFactor = pixelDistanceToPoint / NUM_PIXELS;
        double randomDelta = Math.random() * minDistToEdge;
        heights[(i1 + i2) / 2][(j1 + j2) / 2] = avgHeight + (randomDelta * scalingFactor);
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

    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyPressed(KeyEvent e) { 
        repaint();
    }

    public static void main(String args[])    
    {    
        new ProceduralGen();    
    }   
}


