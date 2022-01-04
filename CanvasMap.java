import java.awt.*;
import java.util.ResourceBundle.Control;

public class CanvasMap extends Canvas {

    public static final int MAP_SIZE = 513;

    private int pixelSize = 4;

    private Image mapImage = null;
    private Graphics2D g2d;

    private Map map;

    public CanvasMap(Map map) {
        this.map = map;

        this.setPreferredSize(new Dimension(MAP_SIZE, MAP_SIZE));
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        Color[][] colors = map.getColorArray();
        if (colors == null) {
            map.generateColorArray();
        }
        mapImage = createImage(map.getSize() * pixelSize, map.getSize() * pixelSize);
        g2d = (Graphics2D) mapImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                g2d.setColor(colors[i][j]);
                g2d.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
            }
        }
        g.drawImage(mapImage, -pixelSize / 2, -pixelSize / 2, null);        
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }
}
