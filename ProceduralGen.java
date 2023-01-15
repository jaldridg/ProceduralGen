import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {

    // Both availible maps
    IslandMap islandMap;
    StandardMap standardMap;

    // The canvas the maps are drawn onto
    MapCanvas mapCanvas;
    
    ControlPanel controls;
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    /**
     * Sets up the Prodedural Gen window, adding the control panel and 
     * the map Canvas. 
     */
    public void run() {
        JFrame frame = new JFrame("Procedural Gen");
        islandMap = new IslandMap();
        standardMap = new StandardMap();
        mapCanvas = new MapCanvas(standardMap, islandMap);
        controls = new ControlPanel(mapCanvas);

        frame.setLayout(new BorderLayout());

        frame.add(mapCanvas, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.WEST);
        
        frame.setSize(Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}