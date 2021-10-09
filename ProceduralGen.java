import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int PIXEL_SIZE = 5;
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    private final double MAX_VELOCITY = 0.2;
    private final double MAX_ACCELERATION = 0.05;

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
    }

    private void generateTerrainArray() {
        // Generate four corners
        heights[0][0] = Math.random();
        double[] startVelocities = new double[heights.length];
        // startVelocities[0] = (2 * (Math.random() - 0.5)) * MAX_VELOCITY;
        startVelocities[0] = 0.19;
        // Generate the top row of pixels
        for (int i = 1; i < heights.length - 1; i++) {
            startVelocities[i] = generateNextVelocity(heights[i - 1][0], startVelocities[i - 1]);
            heights[i][0] = heights[i - 1][0] + startVelocities[i - 1];
            //System.out.println("heights[" + (i - 1) + "]: \t\t" + heights[i - 1][0]);
            System.out.println("startVelocities[" + (i - 1) + "]: \t" + startVelocities[i - 1]);
        }
    }

    private double generateNextVelocity(double currentHeight, double currentVel) {
        // Used for determining if we're close to our max values
        double smallestDistanceToMaxVel = Math.abs(currentVel - MAX_VELOCITY) < currentVel ? Math.abs(currentVel - MAX_VELOCITY) : MAX_VELOCITY - currentVel;
        // If values are close to the maxes, we can't make an interval that goes over
        double rightInterval = currentVel > MAX_VELOCITY - MAX_ACCELERATION ? smallestDistanceToMaxVel : MAX_ACCELERATION;
        double leftInterval = -currentVel > MAX_VELOCITY - MAX_ACCELERATION ? smallestDistanceToMaxVel : MAX_ACCELERATION;
        double interval = leftInterval + rightInterval;
        double maxAccelerationVeloctiyRatio = MAX_ACCELERATION / MAX_VELOCITY;
        // Generates a value from -1 to 1 with: 2 * (Math.random() - 0.5)
        // Multiplies it by interval / 2 so that the values are the range we want
        // Also multiplies it by maxAccelerationVeloctiyRatio because we need an acceleration given the velocity interval
        // Shift the values to the middle of the interval by adding interval / 2
        double acceleration = maxAccelerationVeloctiyRatio * (2 * (Math.random() - 0.5)) * (interval / 2) + (interval / 2);
        return currentVel + acceleration;
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