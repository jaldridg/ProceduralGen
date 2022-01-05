import java.awt.*;

public class CanvasMap extends Canvas {

    public static final int MAP_SIZE = 513;

    private Color[][] colorArray;

    private int pixelSize = 4;

    private boolean isRealistic;

    private Image mapImage = null;
    private Graphics2D g2d;

    private Map map;

    public CanvasMap(Map map) {
        this.map = map;

        this.setPreferredSize(new Dimension(MAP_SIZE, MAP_SIZE));
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        generateColorArray(isRealistic);
        mapImage = createImage(map.getSize() * pixelSize, map.getSize() * pixelSize);
        g2d = (Graphics2D) mapImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < colorArray.length; i++) {
            for (int j = 0; j < colorArray[i].length; j++) {
                g2d.setColor(colorArray[i][j]);
                g2d.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
            }
        }
        g.drawImage(mapImage, -pixelSize / 2, -pixelSize / 2, null);        
    }

    protected void generateColorArray(boolean realistic) {
        double[][] heights = map.getHeightArray();
        colorArray = new Color[heights.length][heights.length];
        if (realistic) {
            for (int i = 0; i < colorArray.length; i++) {
                for (int j = 0; j < colorArray[i].length; j++) {
                    colorArray[i][j] = generateRealisticColor(heights[i][j]);
                }
            }
        } else {
            for (int i = 0; i < colorArray.length; i++) {
                for (int j = 0; j < colorArray[i].length; j++) {
                    colorArray[i][j] = generateColor(heights[i][j]);
                }
            }
        } 
    }
    
    protected Color generateColor(double height) {
        // Dark blue
        if (height < 0.2) {
            return new Color(0, 0, 150);

        // Light blue
        } else if (height < 0.3) {
            return new Color(0, 100, 150);

        // Sand
        } else if (height < 0.45) {
            return new Color(255, 255, 100);

        // Light grass
        } else if (height < 0.6) {
            return new Color(40, 150, 0);

        // Dark grass
        } else if (height < 0.80) {
            return new Color(25, 100, 0);

        // Mountain
        } else {
            return new Color(80, 40, 0);
        }
    }

    protected Color generateRealisticColor(double height) {
        // Deep to shallow ocean
        if (height < 0.3) {
            int gValue = lerp(0.0, 0.3, 0, 150, height);
            return new Color(0, gValue, 150);

        // Sand to light grass
        } else if (height < 0.45) {
            int rValue = lerp(0.3, 0.45, 255, 40, height);
            int gValue = lerp(0.3, 0.45, 255, 150, height);
            int bValue = lerp(0.3, 0.45, 150, 0, height);
            return new Color(rValue, gValue, bValue);

        // Light grass to dark grass
        } else if (height < 0.6) {
            int rValue = lerp(0.45, 0.6, 40, 25, height);
            int gValue = lerp(0.45, 0.6, 150, 100, height);
            return new Color(rValue, gValue, 0);

        // Dark grass to mountain
        } else if (height < 0.80) {
            int rValue = lerp(0.6, 0.8, 25, 80, height);
            int gValue = lerp(0.6, 0.8, 100, 40, height);
            return new Color(rValue, gValue, 0);

        // Mountain to rocky/snowy mountain
        } else {
            int rValue = lerp(0.8, 1.0, 80, 125, height);
            int gValue = lerp(0.8, 1.0, 40, 125, height);
            int bValue = lerp(0.8, 1.0, 0, 125, height);
            return new Color(rValue, gValue, bValue);
        }
    }

    private int lerp(double minHeight, double maxHeight, double colorOne, double colorTwo, double height) {
        double ratio = (colorOne - colorTwo) / (minHeight - maxHeight);
        return (int) (colorOne + ((height - minHeight) * ratio));
    }

    public void setRealistic(boolean isRealistic) {
        this.isRealistic = isRealistic;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }
}
