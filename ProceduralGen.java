import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {

    CanvasMap map;
    ControlPanel controls;
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    public void run() {
        JFrame frame = new JFrame("Procedural Gen");
        map = new CanvasMap();
        controls = new ControlPanel(map);

        frame.setLayout(new BorderLayout());

        frame.add(map, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.WEST);
        
        frame.setSize(800, 600);  
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}