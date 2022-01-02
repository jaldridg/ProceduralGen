import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private static final int PANEL_WIDTH = 200;

    public ControlPanel(CanvasMap map) {
        JButton regenButton = new JButton("Regenerate");

        // Pixel panel and its components
        JPanel pixelSizePanel = new JPanel(new FlowLayout());
        JLabel pixelSizeLabel = new JLabel("Pixel Size");
        JTextField pixelSizeTextField = new JTextField(5);
        pixelSizeTextField.setText("5");

        // Size panel and its components
        JPanel mapSizePanel = new JPanel(new FlowLayout());
        JLabel mapSizeLabel = new JLabel("Map Size");
        JComboBox<String> mapSizeComboBox = new JComboBox<>();
        for (int i = 1; i < 10; i++) {
            int size = (int) Math.pow(2, i) + 1;
            mapSizeComboBox.addItem("" + size);
        }
        mapSizeComboBox.setSelectedIndex(6);

        // Panel with pixel and size panel
        JPanel pixelNSizePanel = new JPanel(new GridLayout());

        // Add components to other components
        pixelSizePanel.add(pixelSizeLabel);
        pixelSizePanel.add(pixelSizeTextField);
        mapSizePanel.add(mapSizeLabel);
        mapSizePanel.add(mapSizeComboBox);
        pixelNSizePanel.add(pixelSizePanel);
        pixelNSizePanel.add(mapSizePanel);
        this.add(regenButton);
        this.add(pixelNSizePanel);

        // Configure panels
        pixelSizePanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 60));
        mapSizePanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 60));
        pixelNSizePanel.setPreferredSize(new Dimension(PANEL_WIDTH, 60));
        pixelNSizePanel.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, 635));
        this.setBackground(Color.LIGHT_GRAY);
        
        // Set everything to visible
        pixelSizeLabel.setVisible(true);
        pixelSizeTextField.setVisible(true);
        pixelSizePanel.setVisible(true);
        mapSizeLabel.setVisible(true);
        mapSizeComboBox.setVisible(true);
        mapSizePanel.setVisible(true);
        pixelNSizePanel.setVisible(true);
        regenButton.setVisible(true);
        this.setVisible(true);

        // Regenerate button
        regenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int pixelSize = 0;
                try {
                    pixelSize = Integer.parseInt(pixelSizeTextField.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, 
                        "Pixel size must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (pixelSize > 0) {
                    map.setPixelSize(pixelSize);
                    map.setMapSize(Integer.parseInt((String) mapSizeComboBox.getSelectedItem()));
                    map.generate();
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Pixel size must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                }        
            }
        });
    }  
}
