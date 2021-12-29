import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PanelControls extends JPanel {

    private int seed = 1;

    public PanelControls(CanvasMap map) {
        JButton regenButton = new JButton("Regenerate");
        
        JPanel pixelPanel = new JPanel();
        pixelPanel.setLayout(new FlowLayout());

        JLabel pixelSizeLabel = new JLabel("Pixel Size");

        // Set up pixel combo box
        int[] pixelSizes = {1, 2, 4, 5, 10, 20, 25, 50, 100};
        JComboBox<String> pixelComboBox = new JComboBox<String>();
        for (int i = 0; i < pixelSizes.length; i++) {
            pixelComboBox.addItem(String.valueOf(pixelSizes[i]));
        }

        pixelPanel.add(pixelSizeLabel);
        pixelPanel.add(pixelComboBox);
        this.add(regenButton);
        this.add(pixelPanel);

        pixelSizeLabel.setVisible(true);
        pixelComboBox.setVisible(true);
        pixelPanel.setVisible(true);
        regenButton.setVisible(true);

        regenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                map.setPixelSize(Integer.parseInt((String)pixelComboBox.getSelectedItem()));
                map.generate();
            }
        });

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(200, 600));
        this.setVisible(true);
    }  
}
