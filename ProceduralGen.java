import javax.swing.*;
import java.awt.*;

public class ProceduralGen extends JComponent implements Runnable {        

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new ProceduralGen());
    }

    public void run() {
        JFrame frame = new JFrame("Procedural Gen");

        frame.setLayout(new BorderLayout());

        frame.add(new PanelControls(), BorderLayout.WEST);
        frame.add(new CanvasMap(1), BorderLayout.CENTER);
        
        frame.setSize(1000, 600);  
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }    
}