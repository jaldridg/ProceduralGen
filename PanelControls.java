import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PanelControls extends JPanel {

    public PanelControls() {
        JButton regenButton = new JButton("Regenerate");
        this.add(regenButton);
        regenButton.setVisible(true);

        regenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
        });

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(200, 600));
        this.setVisible(true);
    }  
}
