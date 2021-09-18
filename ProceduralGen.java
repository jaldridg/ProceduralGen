import java.awt.*;
import java.awt.event.*;

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int HEIGHT = 800;
    private final int WIDTH = 1300;
    private final int PIXEL_SIZE = 5;

    private final double NOISE_ZOOM = 1.0;

    private double[][] heights = new double[WIDTH / PIXEL_SIZE][HEIGHT / PIXEL_SIZE];

    public ProceduralGen()    
    {    
        Frame frame = new Frame("Procedural Gen");   
        setBackground(Color.white);
        
        addKeyListener(this);
        frame.add(this);    
        frame.setSize(WIDTH, HEIGHT);    
        frame.setVisible(true); 

        repaint();
    }    

    public void paint(Graphics g) { }   
    
    public void update(Graphics g) {
        generateTerrainArray();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                int c = (int) (heights[i][j] * 255);
                g.setColor(new Color(c, c, c));
                g.fillRect(i * PIXEL_SIZE, j * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    private void generateTerrainArray() {
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                // If not on the top and left borders
                if (i > 0 && j > 0) {
                    heights[i][j] = generateNewHeight(heights[i - 1][j], heights[i][j - 1]);
                } 
                // If on the top border
                else if (i > 0) {
                    heights[i][j] = generateNewHeight(heights[i - 1][j]);
                } 
                // If on the right border 
                else if (j > 0) {
                    heights[i][j] = generateNewHeight(heights[i][j - 1]);
                }
                // If in the corner start with a random value
                if (i == 0 && j == 0) {
                    heights[i][j] = Math.random();
                }
            }
        }
    }
    private double generateNewHeight(double referenceHeight) {
        double deltaHeight = Math.random() - 0.5;
        return referenceHeight + deltaHeight * ((referenceHeight - 1) * (referenceHeight));
    }

    private double generateNewHeight(double heightOne, double heightTwo) {
        double avgerageHeight = (heightOne + heightTwo) / 2;
        double deltaHeight = Math.random() - 0.5;
        return avgerageHeight + deltaHeight * ((avgerageHeight - 1) * (avgerageHeight));
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
