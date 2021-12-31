import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private int seed = 1;

    public ControlPanel(CanvasMap map) {
        JButton regenButton = new JButton("Regenerate");
        
        this.add(regenButton);

        regenButton.setVisible(true);

        regenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                map.generate();
            }
        });

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(200, 635));
        this.setVisible(true);
    }  
}
