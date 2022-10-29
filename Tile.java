import java.awt.Color;

public class Tile {

    private float height;
    private Color color;

    private boolean isLake;

    private int x;
    private int y;
    
    public Tile(float height) {
        this.height = height;
        isLake = false;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean isLake() { return isLake; }
    public void setLake(boolean isLake) { this.isLake = isLake; }
}

