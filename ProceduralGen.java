import java.awt.*;
import java.awt.event.*;

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int HEIGHT = 800;
    private final int WIDTH = 1300;
    private final int SQUARE_SIZE = 10;

    private double[][] heights = new double[WIDTH / SQUARE_SIZE + 1][HEIGHT / SQUARE_SIZE + 1];

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
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < WIDTH; i += SQUARE_SIZE) {
            for(int j = 0; j < HEIGHT; j += SQUARE_SIZE) {
                int c = (int) (heights[i][j] * 255);
                g.setColor(new Color(c, c, c));
                g.fillRect(i, j, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private double[][] generateTerrainArray(int octaves) {
        
        for(double[] row : heights) {
            for(double height : row) {
                for(int i = 0; i < octaves; i++) {
                    height += Math.random() / (2.0 * octaves);
                }
            }
        }
        return heights;
    }

    public static void main(String args[])    
    {    
        new ProceduralGen();    
    }   
    
    public void keyTyped(KeyEvent e) { }
    public void keyPressed(KeyEvent e) { 
        repaint();
    }
    public void keyReleased(KeyEvent e) { }
}
