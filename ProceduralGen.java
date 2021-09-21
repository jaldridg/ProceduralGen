import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

// TODO Make a velocity tht changes which is for changing points rather than adding/subtracting random values

public class ProceduralGen extends Canvas implements KeyListener
{        
    // TODO these values are not very flexible so write a method that takes any
    // number and adjusts these values if they're not in proper form without messing
    // with the general idea
    private final int PIXEL_SIZE = 50;
    private final int NUM_PIXELS = 7; // Must be in the form 2^n - 1
    private final int SIZE = PIXEL_SIZE * NUM_PIXELS;

    // The higher the more zoomed out the noise is
    private final double NOISE_ZOOM = 1.0;

    private double[][] heights = new double[SIZE / PIXEL_SIZE][SIZE / PIXEL_SIZE];

    public ProceduralGen()    
    {    
        JFrame frame = new JFrame("Procedural Gen");   
        setBackground(Color.white);
        
        addKeyListener(this);
        frame.add(this);    
        frame.setSize(SIZE, 800);    
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

        // Generate the midpoint
        System.out.println("\n\n\nSTART OF BIG THING");
        for(int i = 0; i < 2; i++) {
            int id = (int) (NUM_PIXELS / Math.pow(2, i));
            generateRandomMidpoint(0, 0, id, id);
        }
    }

    private void generateRandomMidpoint(int i1, int j1, int i2, int j2) {
        double avgHeight = (heights[i1][j1] + heights[i2][j2]) / 2;
        double minDistToEdge = Math.abs(avgHeight - 1) > avgHeight ? avgHeight : Math.abs(avgHeight - 1);
        // Calculates the distance between the points and counts diagonal squares as one
        // If the points are diagonal, i or j is the diagonal squares
        // If they're not diagonal, use the sum of differences because one difference is 0
        // int distBetweenPoints = (i1 - i2) == (j1 - j2) ? Math.abs(i1 - i2) : Math.abs((i1 - i2) + (j1 - j2));
        heights[(i1 + i2) / 2][(j1 + j2) / 2] = avgHeight + (Math.random() * minDistToEdge);
        System.out.println("\nSTART OF THING");
        System.out.println("avgHeight " + avgHeight);
        System.out.println("minDistToEdge " + minDistToEdge);
        System.out.println("calculation " + (avgHeight + (Math.random() * minDistToEdge)));
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


