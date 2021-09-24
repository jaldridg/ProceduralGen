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
    private final int NUM_PIXELS = 129; // Must be in the form 2^n + 1
    private final int SIZE = PIXEL_SIZE * NUM_PIXELS;

    //private final int NOISE_CONSTANT = 2;

    private double[][] heights = new double[NUM_PIXELS][NUM_PIXELS];

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
        for(int i = 0; i < heights.length; i++) {
            System.out.println(heights[heights.length / 2][i]);
        }
    }

    private void generateTerrainArray() {
        // Generate four corners
        heights[0][0] = Math.random();
        heights[heights.length - 1][0] = Math.random();
        heights[0][heights.length - 1] = Math.random();
        heights[heights.length - 1][heights.length - 1] = Math.random();
        int size = NUM_PIXELS - 1;
        splitChunks(0, 0, size);
    }
    
    /**
     * Recursively generates the heights of a square of a given sideLength
     * at a given rowId and colId by taking midpoints until completed
     */
    private void splitChunks (int colId, int rowId, int sideLength) {
        // Stop at the pixel level
        if(sideLength == 1) { return; }

        // Points at the corners of the chunk
        // Top right, top left, bottom left, and bottom right
        int[] tLIds = {colId, rowId};
        int[] tRIds = {colId + sideLength, rowId};
        int[] bLIds = {colId, rowId + sideLength};
        int[] bRIds = {colId + sideLength, rowId + sideLength};

        // Make midpoints of the given points and a diagonal midpoint
        generateRandomMidpoint(tRIds[0], tRIds[1], tLIds[0], tLIds[1]);
        generateRandomMidpoint(bRIds[0], bRIds[1], bLIds[0], bLIds[1]);
        generateRandomMidpoint(tRIds[0], tRIds[1], bRIds[0], bRIds[1]);
        generateRandomMidpoint(tLIds[0], tLIds[1], bLIds[0], bLIds[1]);
        generateRandomMidpoint(tRIds[0], tRIds[1], bLIds[0], bLIds[1]);

        // Make chunks within this chunk
        int halfLength = sideLength / 2;
        splitChunks(colId, rowId, halfLength);
        splitChunks(halfLength + colId, rowId, halfLength);
        splitChunks(colId, halfLength + rowId, halfLength);
        splitChunks(halfLength + colId, halfLength + rowId, halfLength);
    }

    private void generateRandomMidpoint(int i1, int j1, int i2, int j2) {
        // Calculates the distance between the points and counts diagonal squares as one
        // If the points are diagonal, i or j is the diagonal squares
        // If they're not diagonal, use the sum of differences because one difference is 0
        int pixelDistanceToPoint = (i1 - i2) == (j1 - j2) ? Math.abs(i1 - i2) : Math.abs((i1 - i2) + (j1 - j2));
        double avgHeight = (heights[i1][j1] + heights[i2][j2]) / 2;
        double minDistToEdge = Math.abs(avgHeight - 1) > avgHeight ? avgHeight : Math.abs(avgHeight - 1);
        double randomDelta = 2 * (Math.random() - 0.5) * minDistToEdge; 
        heights[(i1 + i2) / 2][(j1 + j2) / 2] = avgHeight + randomDelta;
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