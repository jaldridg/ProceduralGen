import java.awt.*;
import java.awt.event.*;

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
    
    // TODO I think incrementing by square size in the for loops is breaking things
    // I think it needs to be one then we multiply i and j by square size to move the squares to the right spots
    public void update(Graphics g) {
        generateTerrainArray(3);
        for(int i = 0; i < heights.length; i++) {
            for(int j = 0; j < heights[0].length; j++) {
                int c = (int) (heights[i][j] * 255.0);
                g.setColor(new Color(c, c, c));
                g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void generateTerrainArray(int octaves) {
        for(int i = 0; i < heights.length; i++) {
            for(int j = 0; j < heights[0].length; j++) {
                double height = 0;
                for(int k = 1; k <= octaves; k++) {
                    height += Math.random() / (2.0 * octaves);
                }
                heights[i][j] = height;
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
