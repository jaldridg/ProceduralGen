import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {

    // Extra size constants since the map is cut off without them
    private final static int EXTRA_WIDTH = 10;
    private final static int EXTRA_HEIGHT = 10;

    Map map;
    CanvasMap canvasMap;
    ControlPanel controls;
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    public void run() {
        JFrame frame = new JFrame("Procedural Gen");
        map = new DefaultMap();
        canvasMap = new CanvasMap(map);
        controls = new ControlPanel(map, canvasMap);

        frame.setLayout(new BorderLayout());

        frame.add(canvasMap, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.WEST);
        
        frame.setSize(ControlPanel.PANEL_WIDTH + CanvasMap.MAP_SIZE + EXTRA_WIDTH, 
            CanvasMap.MAP_SIZE + EXTRA_HEIGHT); 
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}