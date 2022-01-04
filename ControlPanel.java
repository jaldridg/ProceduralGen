import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    private static final int PANEL_WIDTH = 200;

    public ControlPanel(Map map, CanvasMap canvasMap) {
        // Regenerate panel
        JPanel regenPanel = new JPanel(new FlowLayout());
        JButton quickRegenButton = new JButton("Quick Regenerate");
        JButton customRegenButton = new JButton("Custom Regenerate");

        // Resolution panel
        JPanel resolutionPanel = new JPanel(new FlowLayout());
        JButton decResolutionButton = new JButton("-");
        JLabel resoultionLabel = new JLabel("Resolution");
        JButton incResolutionButton = new JButton("+");

        // Seed panel
        JPanel seedPanel  = new JPanel(new FlowLayout());
        JLabel seedLabel = new JLabel("Seed");
        JTextField seedTextField = new JTextField(8);
        seedTextField.setText("" + map.getSeed());

        // Larger settings panels
        JPanel genSettingsPanel = new JPanel(new FlowLayout());
        JPanel displaySettingsPanel = new JPanel(new FlowLayout());

        // Add components to other components
        regenPanel.add(quickRegenButton);
        regenPanel.add(customRegenButton);
        resolutionPanel.add(decResolutionButton);
        resolutionPanel.add(resoultionLabel);
        resolutionPanel.add(incResolutionButton);
        seedPanel.add(seedLabel);
        seedPanel.add(seedTextField);
        genSettingsPanel.add(seedPanel);
        displaySettingsPanel.add(resolutionPanel);
        this.add(regenPanel);
        this.add(genSettingsPanel);
        this.add(displaySettingsPanel);

        // Configure panels
        regenPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 70));
        regenPanel.setBackground(Color.LIGHT_GRAY);
        resolutionPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 40));
        resolutionPanel.setBackground(Color.LIGHT_GRAY);
        seedPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 30));
        seedPanel.setBackground(Color.LIGHT_GRAY);
        genSettingsPanel.setBackground(Color.WHITE);

        TitledBorder genSettingsPanelBorder = BorderFactory.createTitledBorder("Generation Settings");
        genSettingsPanelBorder.setTitleJustification(TitledBorder.CENTER);
        genSettingsPanel.setBorder(BorderFactory.createTitledBorder(
            new TitledBorder(genSettingsPanelBorder)));
        genSettingsPanel.setPreferredSize(new Dimension(PANEL_WIDTH + 50, 65));
        displaySettingsPanel.setBackground(Color.WHITE);

        TitledBorder displaySettingsPanelBorder = BorderFactory.createTitledBorder("Display Settings");
        displaySettingsPanelBorder.setTitleJustification(TitledBorder.CENTER);
        displaySettingsPanel.setBorder(BorderFactory.createTitledBorder(
            new TitledBorder(displaySettingsPanelBorder)));
        displaySettingsPanel.setPreferredSize(new Dimension(PANEL_WIDTH + 50, 75));

        this.setPreferredSize(new Dimension(PANEL_WIDTH, 635));
        this.setBackground(Color.WHITE);
        
        // Set everything to visible
        quickRegenButton.setVisible(true);
        customRegenButton.setVisible(true);
        regenPanel.setVisible(true);
        seedLabel.setVisible(true);
        seedTextField.setVisible(true);
        seedPanel.setVisible(true);
        genSettingsPanel.setVisible(true);
        this.setVisible(true);

        // Custom regenerate button
        customRegenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                

            }
        });

        // Quick regnerate button
        quickRegenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                  
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
