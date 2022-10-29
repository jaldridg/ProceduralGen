import java.util.ArrayList;

public class Lake {
    private ArrayList<Tile> tiles;

    // Used for edge detection for faster calculations
    private ArrayList<Tile> borderTiles;

    private Map map;

    public Lake (ArrayList<Tile> tiles, Map map) {
        this.tiles = tiles;
        this.map = map;
        calculateBorder();
    }

    // Looks at every point to make the border
    // TODO: Find a way to not make this as brute force
    private void calculateBorder() {
        borderTiles = new ArrayList<Tile>();
        for (Tile t : tiles) {
            Tile[] surroundingTiles = map.getSurroundingTiles(t);
            for (Tile st : surroundingTiles) {
                if (st.isLake()) {
                    continue;
                }
                // Add if there is a tile not completely surrounded by lake tiles
                borderTiles.add(t);
                break;
            }
        }
    }

    // Looks at the points in the border and recalculates
    private void recalculateBorder() {
        ArrayList<Tile> newBorder = new ArrayList<Tile>();
        for (Tile t : borderTiles) {
            Tile[] surroundingTiles = map.getSurroundingTiles(t);
            for (Tile st : surroundingTiles) {
                if (st.isLake()) {
                    continue;
                }
                // Add if there is a tile not completely surrounded by lake tiles
                newBorder.add(t);
                break;
            }
        }
        borderTiles = newBorder;
    }

    public ArrayList<Tile> getBorderTiles() {
        return borderTiles;
    }

    public void addTile(Tile tile) {
        borderTiles.add(tile);
        recalculateBorder();
    }
}
