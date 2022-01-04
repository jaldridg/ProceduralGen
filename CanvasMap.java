import java.awt.*;
import java.util.Random;

public class CanvasMap extends Canvas {

    private int mapSize;
    private int pixelSize;

    private Image mapImage = null;
    private Graphics2D g2d;

    private Map map;

    public CanvasMap(Map map) {
        this.map = map;

        mapSize = map.getMapSize();
        pixelSize = map.getPixelSize();

        this.setPreferredSize(new Dimension(mapSize * pixelSize, mapSize * pixelSize));
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        Color[][] colors = map.getColorArray();
        if (colors == null) {
            map.generateColorArray();
        }
        mapImage = createImage(mapSize * pixelSize, mapSize * pixelSize);
        g2d = (Graphics2D) mapImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                g2d.setColor(colors[i][j]);
                g2d.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
            }
        }
        g.drawImage(mapImage, 0, 0, null);        
    }
}
