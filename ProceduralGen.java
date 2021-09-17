import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int HEIGHT = 800;
    private final int WIDTH = 1300;
    private final int SQUARE_SIZE = 5;

    private double[][] heights = new double[WIDTH / SQUARE_SIZE + 1][HEIGHT / SQUARE_SIZE + 1];

    private int thing = 10;

    public ProceduralGen()    
    {    
        Frame frame = new Frame("Canvas Example");   
        //TODO explore setSize() versus frame.setSize()
        setBackground(Color.white);
        setSize(WIDTH, HEIGHT);
        
        addKeyListener(this);
        frame.add(this);    
        frame.setSize(WIDTH, HEIGHT);    
        frame.setVisible(true); 

        repaint();
    }    

    public void paint(Graphics g) { }   
    
    public void update(Graphics g) {
        generateTerrainArray(3);
        //normalizeHeightArray();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                int c = (int) ((heights[i][j] + 5) * 30);
                g.setColor(new Color(c, c, c));
                g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void generateTerrainArray(int octaves) {
        Random r = new Random();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                // If not on the top and left borders
                if (i > 0 && j > 0) {
                    double avgHeight = (heights[i][j-1] + heights[i-1][j]) / 2;
                    heights[i][j] = avgHeight + (r.nextGaussian() / 10);
                // If on the top border
                } else if (i > 0) {
                    heights[i][j] = heights[i-1][j] + (r.nextGaussian() / 10);
                // If on the right border
                } else if (j > 0) {
                    heights[i][j] = heights[i][j-1] + (r.nextGaussian() / 10);
                }
                // If in the corner start with a random value
                if (i == 0 && j == 0) {
                    heights[i][j] = r.nextDouble();
                }
            }
        }
    }

    // TODO fix this or make a better generation algorithm that doesn't need normalization
    private void normalizeHeightArray() {
        double high = 0;
        double low = 0;
        // Find highest and lowest value
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                double height = heights[i][j];
                if(height > high) {
                    high = height;
                }
                if(height < low) {
                    low = height;
                }
            }
        }
        // Divide values by highest after adding the lowest value (because its negative)
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                heights[i][j] = (heights[i][j] - low) / high;
            }
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
