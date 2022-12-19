import java.util.ArrayList;

public class Lake {
    private ArrayList<Tile> tiles;

    // Used for edge detection for faster calculations
    private ArrayList<Tile> borderTiles;

    private Map map;

    private float waterLevel;

    // Constructor used for a "dead" lake
    public Lake() {
        tiles = null;
        borderTiles = null;
    }

    public Lake (Map map, Tile deepestTile) {
        this.map = map;
        tiles = new ArrayList<Tile>();
        tiles.add(deepestTile);
        borderTiles = new ArrayList<Tile>();
        borderTiles.add(deepestTile);
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

    public Tile getShallowestTile() {
        return tiles.get(tiles.size() - 1);
    }

    public ArrayList<Tile> getBorderTiles() {
        return borderTiles;
    }

    public void addTile(Tile tile) {
        if (tile.isLake()) {
            mergeWithLake(tile.getLake());
        }
        tile.addToLake(this);
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
            greaterLake.addTile(tile);
        }
        // This is our way of destroying the smaller lake
        lesserLake = new Lake();
    }
}
