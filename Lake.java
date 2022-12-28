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
        waterLevel = deepestTile.getHeight();
        deepestTile.addToLake(this);
        River r = deepestTile.getRiver();
        if (r != null) {
            r.removeTile(deepestTile);
            deepestTile.addToRiver(null);
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
        Tile minTile = map.getMinSurroundingTileNoLake(borderTiles.get(0));
        for (Tile bTile : borderTiles) {
            Tile sTile = map.getMinSurroundingTileNoLake(bTile);
            if (minTile.isHigherThan(sTile)) {
                minTile = sTile;
            }
        }
        return minTile;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
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
        River r = tile.getRiver();
        if (r != null) {
            r.removeTile(tile);
            tile.addToRiver(null);
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
        
        greaterLake.setTiles(mergeTilesets(greaterLake, lesserLake));
        // This is our way of destroying the smaller lake
        
        lesserLake = new Lake();
    }

    // Two finger sort which ensures lake tiles are still sorted
    private ArrayList<Tile> mergeTilesets(Lake greaterLake, Lake lesserLake) {
        Tile dummyTile = new Tile(-1, -1, 1.1f);
        ArrayList<Tile> greaterTiles = greaterLake.getTiles();
        ArrayList<Tile> lesserTiles = lesserLake.getTiles();
        int expectedSize = lesserTiles.size() + greaterTiles.size();
        lesserTiles.add(dummyTile);
        greaterTiles.add(dummyTile);

        int i = 0;
        int j = 0;
        ArrayList<Tile> newTiles = new ArrayList<>();
        while (newTiles.size() < expectedSize) {
            Tile gTile = greaterTiles.get(i);
            Tile lTile = lesserTiles.get(j);
            if (gTile.isHigherThan(lTile)) {
                newTiles.add(lTile);
                j++;
            } else {
                newTiles.add(gTile);
                i++;
            }
        }
        return newTiles;
    }
}
