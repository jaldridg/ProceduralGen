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
        startVelocities[0] = (2 * (Math.random() - 0.5)) * MAX_VELOCITY;
        for (int i = 1; i < heights.length - 1; i++) {
            startVelocities[i] = generateNextVelocity(heights[i - 1][0], startVelocities[i - 1]);
            heights[i][0] += heights[i - 1][0] + startVelocities[i - 1];
            System.out.println("heights[" + (i - 1) + "]: \t\t" + heights[i - 1][0]);
            System.out.println("startVelocities[" + (i - 1) + "]: \t" + startVelocities[i - 1]);
        }
    }

    private double generateNextVelocity(double currentHeight, double currentVel) {
        double smallestDistanceToMaxVel = Math.abs(currentVel - MAX_VELOCITY) > currentVel ? Math.abs(currentVel - MAX_VELOCITY) : currentVel;
        double acceleration = MAX_ACCELERATION * (Math.random() - 0.5);
        // If near the max vel, cuts off one side of the generated acceleration to stay within range
        if (smallestDistanceToMaxVel < MAX_ACCELERATION) {
            // The side of the acceleration range that is closest to zero doesn't need snipped
            double wholeAcceleration = Math.copySign(Math.random() * MAX_ACCELERATION, -currentVel);
            // The side that is withing the cuttoff needs to be rescaled
            double cuttoffAcceleration = Math.copySign(Math.random() * smallestDistanceToMaxVel, -currentVel);
            // What percentage of the acceptable range is the cuttoffAcceleration
            double cuttoffVsWholeRatio = cuttoffAcceleration / (wholeAcceleration + MAX_ACCELERATION);
            acceleration = (Math.random() > cuttoffVsWholeRatio) ? wholeAcceleration : cuttoffAcceleration;
        }
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