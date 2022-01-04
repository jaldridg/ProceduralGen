import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {

    Map map;
    CanvasMap canvasMap;
    ControlPanel controls;
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    public void run() {
        JFrame frame = new JFrame("Procedural Gen");
        map = new IslandMap();
        canvasMap = new CanvasMap(map);
        controls = new ControlPanel(map, canvasMap);

        frame.setLayout(new BorderLayout());

        frame.add(canvasMap, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.WEST);
        
        frame.setSize(800, 600);  
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}