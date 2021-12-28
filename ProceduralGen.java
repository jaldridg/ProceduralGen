import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {

    CanvasMap map;
    PanelControls controls;
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    public void run() {
        JFrame frame = new JFrame("Procedural Gen");
        map = new CanvasMap();
        controls = new PanelControls(map);

        frame.setLayout(new BorderLayout());

        frame.add(map, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.WEST);
        
        frame.setSize(1000, 600);  
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}