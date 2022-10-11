import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public static final int PANEL_WIDTH = 200;

    private static final int MIN_RESOLUTION = 32;
    public static final int MAX_RESOLUTION = 512;

    // Controls the pixel size and map size
    private int resolution = 128;

    public ControlPanel(MapCanvas canvasMap) {

        // Generation panel components
        JPanel genPanel = new JPanel(new FlowLayout());
        JButton randomGenButton = new JButton("Generate Random");
        JButton customGenButton = new JButton("Generate Custom");

        // Seed panel components
        JPanel seedPanel  = new JPanel(new FlowLayout());
        JLabel seedLabel = new JLabel("Seed");
        JTextField seedTextField = new JTextField(8);
        seedTextField.setText("" + canvasMap.getCurrentMap().getSeed());

        // Island panel components
        JPanel islandPanel = new JPanel(new FlowLayout());
        JLabel islandLabel = new JLabel("Generate islands");
        JCheckBox islandCheckBox = new JCheckBox();
        islandCheckBox.setBackground(Color.LIGHT_GRAY);

        // Resolution panel components
        JPanel resolutionPanel = new JPanel(new FlowLayout());
        JButton decResolutionButton = new JButton("-");
        JLabel resoltionLabel = new JLabel("Resolution");
        JButton incResolutionButton = new JButton("+");
        
        // Realistic panel components
        JPanel realisticPanel = new JPanel(new FlowLayout());
        JLabel realisticLabel = new JLabel("Realistic terrain");
        JCheckBox realisticCheckBox = new JCheckBox();
        realisticCheckBox.setBackground(Color.LIGHT_GRAY);
        realisticCheckBox.setSelected(true);

        // Larger settings panels
        JPanel genSettingsPanel = new JPanel(new FlowLayout());
        JPanel displaySettingsPanel = new JPanel(new FlowLayout());

        // Add components to other components
        genPanel.add(randomGenButton);
        genPanel.add(customGenButton);
        seedPanel.add(seedLabel);
        seedPanel.add(seedTextField);
        islandPanel.add(islandLabel);
        islandPanel.add(islandCheckBox);
        resolutionPanel.add(decResolutionButton);
        resolutionPanel.add(resoltionLabel);
        resolutionPanel.add(incResolutionButton);
        realisticPanel.add(realisticLabel);
        realisticPanel.add(realisticCheckBox);
        genSettingsPanel.add(seedPanel);
        genSettingsPanel.add(islandPanel);
        displaySettingsPanel.add(resolutionPanel);
        displaySettingsPanel.add(realisticPanel);
        this.add(genPanel);
        this.add(genSettingsPanel);
        this.add(displaySettingsPanel);

        // Configure lower level panels
        genPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 70));
        genPanel.setBackground(Color.LIGHT_GRAY);
        seedPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 30));
        seedPanel.setBackground(Color.LIGHT_GRAY);
        islandPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 30));
        islandPanel.setBackground(Color.LIGHT_GRAY);
        resolutionPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 40));
        resolutionPanel.setBackground(Color.LIGHT_GRAY);
        realisticPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 30));
        realisticPanel.setBackground(Color.LIGHT_GRAY);
        
        // Configuration and custom border for generation settings panel
        TitledBorder genSettingsPanelBorder = BorderFactory.createTitledBorder("Generation Settings");
        genSettingsPanelBorder.setTitleJustification(TitledBorder.CENTER);
        genSettingsPanel.setBorder(BorderFactory.createTitledBorder(
            new TitledBorder(genSettingsPanelBorder)));
        genSettingsPanel.setPreferredSize(new Dimension(PANEL_WIDTH + 50, 100));
        genSettingsPanel.setBackground(Color.WHITE);
        
        // Configuration and custom border for display settings panel
        TitledBorder displaySettingsPanelBorder = BorderFactory.createTitledBorder("Display Settings");
        displaySettingsPanelBorder.setTitleJustification(TitledBorder.CENTER);
        displaySettingsPanel.setBorder(BorderFactory.createTitledBorder(
            new TitledBorder(displaySettingsPanelBorder)));
        displaySettingsPanel.setPreferredSize(new Dimension(PANEL_WIDTH + 50, 110));
        displaySettingsPanel.setBackground(Color.WHITE);

        // Configure control panel
        this.setPreferredSize(new Dimension(PANEL_WIDTH, MapCanvas.MAP_SIZE));
        this.setBackground(Color.WHITE);
        
        // Set every component to visible
        randomGenButton.setVisible(true);
        customGenButton.setVisible(true);
        genPanel.setVisible(true);
        seedLabel.setVisible(true);
        seedTextField.setVisible(true);
        seedPanel.setVisible(true);
        islandLabel.setVisible(true);
        islandCheckBox.setVisible(true);
        islandLabel.setVisible(true);
        genSettingsPanel.setVisible(true);
        decResolutionButton.setVisible(true);
        resoltionLabel.setVisible(true);
        incResolutionButton.setVisible(true);
        resolutionPanel.setVisible(true);
        realisticLabel.setVisible(true);
        realisticCheckBox.setVisible(true);
        realisticPanel.setVisible(true);
        displaySettingsPanel.setVisible(true);
        this.setVisible(true);

        /**
         * Generates a new map with given settings and a 
         * new auto-generated seed
         */
        randomGenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Set new random seed
                int seed = (int) (Math.random() * Integer.MAX_VALUE);
                seedTextField.setText(String.valueOf(seed));

                // Change current map (if applicable) and regenerate with new settings
                canvasMap.setCurrentMap(!islandCheckBox.isSelected());
                canvasMap.getCurrentMap().setSeed(seed);
                canvasMap.getCurrentMap().generate(seed);

                // Draw the new map
                canvasMap.repaint();
            }
        });

        /**
         * Generates a new map with given settings using the
         * seed in the seed text field
         */
        customGenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int seed = validateSeed(seedTextField.getText());

                // If the seed is -1, validateSeed() failed, so the map should not regenerate
                if (seed != -1) {
                    // Change current map (if applicable) and regenerate with new settings
                    canvasMap.setCurrentMap(!islandCheckBox.isSelected());
                    canvasMap.getCurrentMap().setSeed(seed);
                    canvasMap.getCurrentMap().generate(seed);

                    // Draw the new map
                    canvasMap.repaint();
                } 
            }
        });        

        /**
         * Decreases resolution by generating the same map with less
         * pixels but a larger pixel size. This makes the map appear
         * less defined since a smaller map was generated but drawn
         * larger to fit in the canvas.
         */
        decResolutionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (resolution != MIN_RESOLUTION) {
                    resolution >>= 1;

                    // The pixel size is 1 at highest resolution and doubles
                    // when resoultion is halved to preserve the size of the map
                    canvasMap.setPixelSize(MAX_RESOLUTION / resolution);

                    // Size is set to 2^n + 1 for the diamond-square generation to work
                    canvasMap.setAllMapSizes(resolution + 1);

                    // Rengerate the lower resolution map and draw it on the canvas
                    canvasMap.getCurrentMap().generate(canvasMap.getCurrentMap().getSeed());
                    canvasMap.repaint();
                }
            }
        });

        /**
         * Increases resolution by generating the same map with more
         * pixels but a smaller pixel size. This makes the map appear
         * more defined since a larger map was generated but drawn
         * smaller to fit in the canvas.
         */
        incResolutionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (resolution != MAX_RESOLUTION) {
                    resolution <<= 1;

                    // The pixel size is 1 at highest resolution and doubles
                    // when resoultion is halved to preserve the size of the map
                    canvasMap.setPixelSize(MAX_RESOLUTION / resolution);

                    // Size is set to 2^n + 1 for the diamond-square generation to work
                    canvasMap.setAllMapSizes(resolution + 1);

                    // Rengerate the higher resolution map and draw it on the canvas
                    canvasMap.getCurrentMap().generate(canvasMap.getCurrentMap().getSeed());
                    canvasMap.repaint();
                }
            }
        });

        /**
         * Tells the canvas to draw maps realistically or not
         */
        realisticCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                canvasMap.setRealistic(e.getStateChange() == 1);
                canvasMap.repaint();
            }
         });
    }

    /**
     * @param input The text input to turn into a seed
     * @return A seed taken from the input. If the seed was too long
     * or contained something other than digits, returns -1
     */
    private int validateSeed(String input) {
        // The default seed if tests fail
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
