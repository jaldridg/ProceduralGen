import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {

    // Extra size constants since the map is cut off without them somehow
    private final static int EXTRA_WIDTH = 16;
    private final static int EXTRA_HEIGHT = 39;

    IslandMap islandMap;
    StandardMap standardMap;
    MapCanvas mapCanvas;
    ControlPanel controls;
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    public void run() {
        JFrame frame = new JFrame("Procedural Gen");
        islandMap = new IslandMap();
        standardMap = new StandardMap();
        mapCanvas = new MapCanvas(islandMap, standardMap);
        controls = new ControlPanel(mapCanvas);

        frame.setLayout(new BorderLayout());

        frame.add(mapCanvas, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.WEST);
        
        frame.setSize(ControlPanel.PANEL_WIDTH + MapCanvas.MAP_SIZE + EXTRA_WIDTH, 
            MapCanvas.MAP_SIZE + EXTRA_HEIGHT); 
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}