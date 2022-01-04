import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public static final int PANEL_WIDTH = 200;

    private static final int MIN_RESOLUTION = 32;
    public static final int MAX_RESOLUTION = 512;

    private int resolution = 128;

    public ControlPanel(Map map, CanvasMap canvasMap) {
        // Regenerate panel
        JPanel regenPanel = new JPanel(new FlowLayout());
        JButton quickRegenButton = new JButton("Quick Regenerate");
        JButton customRegenButton = new JButton("Custom Regenerate");

        // Resolution panel
        JPanel resolutionPanel = new JPanel(new FlowLayout());
        JButton decResolutionButton = new JButton("-");
        JLabel resoltionLabel = new JLabel("Resolution");
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
        resolutionPanel.add(resoltionLabel);
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

        this.setPreferredSize(new Dimension(PANEL_WIDTH, CanvasMap.MAP_SIZE));
        this.setBackground(Color.WHITE);
        
        // Set everything to visible
        quickRegenButton.setVisible(true);
        customRegenButton.setVisible(true);
        regenPanel.setVisible(true);
        decResolutionButton.setVisible(true);
        resoltionLabel.setVisible(true);
        incResolutionButton.setVisible(true);
        resolutionPanel.setVisible(true);
        seedLabel.setVisible(true);
        seedTextField.setVisible(true);
        seedPanel.setVisible(true);
        genSettingsPanel.setVisible(true);
        displaySettingsPanel.setVisible(true);
        this.setVisible(true);

        // Decrease resolution
        decResolutionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (resolution != MIN_RESOLUTION) {
                    resolution /= 2;
                    canvasMap.setPixelSize(MAX_RESOLUTION / resolution);
                    map.setSize(resolution + 1);
                    map.generate(map.getSeed());
                    canvasMap.repaint();
                }
            }
        });

        // Increase resolution
        incResolutionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (resolution != MAX_RESOLUTION) {
                    resolution *= 2;
                    canvasMap.setPixelSize(MAX_RESOLUTION / resolution);
                    map.setSize(resolution + 1);
                    map.generate(map.getSeed());
                    canvasMap.repaint();
                }
            }
        });
    }
}
