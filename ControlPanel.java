import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private static final int PANEL_WIDTH = 200;

    public ControlPanel(CanvasMap map) {
        // Regenerate panel
        JPanel regenPanel = new JPanel(new FlowLayout());
        JButton quickRegenButton = new JButton("Quick Regenerate");
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

        // Seed panel
        JPanel seedPanel  = new JPanel(new FlowLayout());
        JLabel seedLabel = new JLabel("Seed");
        JTextField seedTextField = new JTextField(8);
        seedTextField.setText("" + map.getSeed());

        // Panel with pixel and size panel
        JPanel pixelNSizePanel = new JPanel(new GridLayout());

        // Add components to other components
        regenPanel.add(quickRegenButton);
        regenPanel.add(regenButton);
        pixelSizePanel.add(pixelSizeLabel);
        pixelSizePanel.add(pixelSizeTextField);
        mapSizePanel.add(mapSizeLabel);
        mapSizePanel.add(mapSizeComboBox);
        pixelNSizePanel.add(pixelSizePanel);
        pixelNSizePanel.add(mapSizePanel);
        seedPanel.add(seedLabel);
        seedPanel.add(seedTextField);
        this.add(regenPanel);
        this.add(pixelNSizePanel);
        this.add(seedPanel);

        // Configure panels
        regenPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 70));
        regenPanel.setBackground(Color.LIGHT_GRAY);
        pixelSizePanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 60));
        pixelSizePanel.setBackground(Color.LIGHT_GRAY);
        mapSizePanel.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 60));
        mapSizePanel.setBackground(Color.LIGHT_GRAY);
        pixelNSizePanel.setPreferredSize(new Dimension(PANEL_WIDTH, 60));
        seedPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 30));
        seedPanel.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, 635));
        this.setBackground(Color.WHITE);
        
        // Set everything to visible
        quickRegenButton.setVisible(true);
        regenButton.setVisible(true);
        regenPanel.setVisible(true);
        pixelSizeLabel.setVisible(true);
        pixelSizeTextField.setVisible(true);
        pixelSizePanel.setVisible(true);
        mapSizeLabel.setVisible(true);
        mapSizeComboBox.setVisible(true);
        mapSizePanel.setVisible(true);
        pixelNSizePanel.setVisible(true);
        seedLabel.setVisible(true);
        seedTextField.setVisible(true);
        seedPanel.setVisible(true);
        this.setVisible(true);

        // Regenerate button
        regenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int pixelSize = validatePixelSize(pixelSizeTextField.getText());
                int seed = validateSeed(seedTextField.getText());

                if (pixelSize != 0) {
                    map.setPixelSize(pixelSize);
                    map.setMapSize(Integer.parseInt((String) mapSizeComboBox.getSelectedItem()));
                    
                    if (seed == -1) {
                        seed = (int) (Math.random() * Integer.MAX_VALUE);
                        seedTextField.setText(String.valueOf(seed));
                    }
                    map.generate(seed);
                }    
            }
        });

        // Quick regnerate button
        quickRegenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int pixelSize = validatePixelSize(pixelSizeTextField.getText());

                if (pixelSize != 0) {
                    map.setPixelSize(pixelSize);
                    map.setMapSize(Integer.parseInt((String) mapSizeComboBox.getSelectedItem()));
                    int seed = (int) (Math.random() * Integer.MAX_VALUE);
                    seedTextField.setText(String.valueOf(seed));
                    map.generate(seed);
                }    
            }
        });
    }

    private int validatePixelSize(String input) {
        int pixelSize = 0;
        try {
            pixelSize = Integer.parseInt(input);
            if (pixelSize < 1) {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, 
                "Pixel size must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(null, 
                "Pixel size must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return pixelSize;
    }

    private int validateSeed(String input) {
        int seed = -1;
        try {
            if (input.length() != 0) {
                seed = Integer.parseInt(input);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, 
                "The seed must be a number or is too long!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return seed;
    }
}
