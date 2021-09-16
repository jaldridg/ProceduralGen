import java.awt.*;
import java.awt.event.*;

public class ProceduralGen extends Canvas implements KeyListener
{        
    private final int RADIUS = 50;
    private final int SIZE = 500;

    private int x = 75;
    private int y = 100;
    private int vx = 1;
    private int vy = 1;

    public ProceduralGen()    
    {    
        Frame frame = new Frame("Canvas Example");   

        setBackground(Color.gray);
        setSize(SIZE, SIZE);
        
        addKeyListener(this);
        frame.add(this);    
        frame.setSize(SIZE, SIZE);    
        frame.setVisible(true); 

        repaint();
    }    

    public void paint(Graphics g) { }   
    
    
    public void update(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(-1, -1, 502, 502);
        g.setColor(Color.red);  
        g.fillOval(x += vx, y += vy, RADIUS, RADIUS);
        System.out.println("x: " + x + " y: " + y);
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
