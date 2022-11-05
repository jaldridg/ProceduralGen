import java.awt.Color;

public class Tile {

    private float height;
    private Color color;

    // The lake data structure the tile may be a part of
    private Lake lake;
    // The river data structure the tile may be a part of
    private River river;

    private int x;
    private int y;
    
    public Tile(int x, int y, float height) {
        this.x = x;
        this.y = y;
        this.height = height;
        lake = null;
        river = null;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public Lake getLake() { return lake; }
    public boolean isLake() { return lake != null; }
    public void addToLake(Lake lake) { this.lake = lake; }

    public River getRiver() { return river; }
    public boolean isRiver() { return river != null; }
    public void addToRiver(River river) { this.river = river; }
    public void removeFromRiver() { river = null; }

    public boolean isWater() { return river != null || lake != null; }

    public boolean isHigherThan(Tile tile) { return height > tile.getHeight(); }
}

