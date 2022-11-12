import java.util.ArrayList;

public class Lake {
    private ArrayList<Tile> tiles;

    // Used for edge detection for faster calculations
    private ArrayList<Tile> borderTiles;

    private Map map;

    private float waterLevel;

    public Lake (Map map) {
        tiles = new ArrayList<Tile>();
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

    public Tile getMinSurroundingTile() {
        Tile minTile = map.getSurroundingTiles(borderTiles.get(0))[0];
        for (Tile bTile : borderTiles) {
            Tile[] surroundingTiles = map.getSurroundingTiles(bTile);
            for (Tile sTile : surroundingTiles) {
                if (minTile.isHigherThan(sTile) && !sTile.isWater()) {
                    minTile = sTile;
                }
            }
        }
        return minTile;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Tile> getBorderTiles() {
        return borderTiles;
    }

    public void addTile(Tile tile) {
        if (tile.isLake()) {
            mergeWithLake(tile.getLake());
        }
        tiles.add(tile);
        borderTiles.add(tile);
        recalculateBorder();
        waterLevel = Math.max(waterLevel, tile.getHeight());
    }

    public int getSize() {
        return tiles.size();
    }

    public float getWaterLevel() {
        return waterLevel;
    }

    /*
     * Takes a lake and sets the tiles to be the larger lake (to merge them)
     */
    private void mergeWithLake(Lake lake) {
        if (this == lake) { return; }
        Lake greaterLake = lake.getSize() > this.getSize() ? lake : this;
        Lake lesserLake = greaterLake == lake ? this : lake;
        for (Tile tile : lesserLake.getTiles()) {
            tile.addToLake(greaterLake);
        }
    }
}
