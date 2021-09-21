import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

// TODO Make a velocity tht changes which is for changing points rather than adding/subtracting random values

public class ProceduralGen extends Canvas implements KeyListener
{        
    // TODO these values are not very flexible so write a method that takes any
    // number and adjusts these values if they're not in proper form without messing
    // with the general idea
    private final int PIXEL_SIZE = 5;
    private final int NUM_PIXELS = 127; // Must be in the form 2^n - 1
    private final int SIZE = PIXEL_SIZE * NUM_PIXELS;

    // The higher the more zoomed out the noise is
    private final double NOISE_ZOOM = 4.0;

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

        generateTerrainArray();
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
        for(int i = 0; i < 7; i++) {
            int heightOneIndex = 0;
            int heightTwoIndex = (int) ((heights.length - 1) / (Math.pow(2, i)));
            int midPointIndex = (int) (heightTwoIndex / 2);
            double heightOne = heights[heightOneIndex][heightOneIndex];
            double heightTwo = heights[heightTwoIndex][heightTwoIndex];
            double averageHeight = (heightOne + heightTwo) / 2;
            double distanceToDomain = Math.abs(averageHeight - 1) > averageHeight ? averageHeight : Math.abs(averageHeight - 1);
            int distanceBetweenPoints = (int) (NUM_PIXELS / Math.pow(2, i + 1));
            double heightMultiplier = (NOISE_ZOOM * distanceToDomain) / distanceBetweenPoints;
            heights[midPointIndex][midPointIndex] = averageHeight + (Math.random() * heightMultiplier);
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


