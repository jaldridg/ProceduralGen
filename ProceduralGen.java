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
        map = new DefaultMap();
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

        int max = 9;
        for (int i = 1; i < max; i++) {
            int pixelSize = (int) Math.pow(2, max - i);
            int mapSize = (int) Math.pow(2, i) + 1;
            System.out.println("pixelSize " + pixelSize + " mapSize " + mapSize + " mult " + (pixelSize * mapSize));
        }
    }
}