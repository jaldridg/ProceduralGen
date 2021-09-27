import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

// TODO Implement algorithm that takes midpoints of corners with added randomness until every pixel is colored
// or make a velocity that changes which is for changing points rather than adding/subtracting random values

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int PIXEL_SIZE = 5;
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    private final double MAX_NOISE_CHANGE = 0.2;

    //private final int NOISE_CONSTANT = 2;

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
        double[] startVelocities = new double[heights.length];
        startVelocities[0] = (2 * (Math.random() - 0.5)) * MAX_NOISE_CHANGE;
        for(int i = 1; i < heights.length - 1; i++) {
            startVelocities[i] = generateNextVelocity(heights[i - 1][0], startVelocities[i - 1]);
            heights[i][0] += startVelocities[i - 1];
        }
    }

    private double generateNextVelocity(double currentHeight, double currentVel) {
        // The smallest distance from being too big or too small
        double minDistToMax = Math.abs(currentHeight - 1) > currentHeight ? currentHeight  : Math.abs(currentHeight - 1);
        double randomDelta = (2 * (Math.random() - 0.5)) * minDistToMax * MAX_NOISE_CHANGE;
        return currentHeight + randomDelta;

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