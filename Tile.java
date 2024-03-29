import java.awt.Color;

public class Tile {

    private short height;
    private Color color;

    // The lake that the tile may belong to
    private Lake lake;
    // The river that the tile may belong to
    private River river;

    private int x;
    private int y;
    
    public Tile(int x, int y, short height) {
        this.x = x;
        this.y = y;
        this.height = height;
        lake = null;
        river = null;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public short getHeight() { return height; }
    public void setHeight(short height) { this.height = height; }

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

    // Need to cover case where tiles are identical heights so title is slightly misleading
    public boolean isHigherThan(Tile tile) { return height >= tile.getHeight(); }

    // Note: only works for tile to tile comparison
    public boolean equals(Tile tile) {
        return tile.getX() == this.x && tile.getY() == this.y;
    }
}

