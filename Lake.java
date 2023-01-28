import java.util.ArrayList;

public class Lake {
    private ArrayList<Tile> tiles;

    // Used for edge detection for faster calculations
    private ArrayList<Tile> borderTiles;

    private Map map;

    // Constructor for a dead lake
    public Lake() {
        tiles = null;
        borderTiles = null;
    }

    public Lake(Map map, Tile deepestTile) {
        this.map = map;
        tiles = new ArrayList<Tile>();
        borderTiles = new ArrayList<Tile>();
        this.addTile(deepestTile);
    }

    // Looks at the points in the border and recalculates
    private void recalculateBorder() {
        ArrayList<Tile> newBorder = new ArrayList<Tile>();
        for (Tile t : borderTiles) {
            Tile[] surroundingTiles = map.getSurroundingTiles(t);
            for (Tile st : surroundingTiles) {
                if (st.isLake()) { continue; }
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

    // Gets the lake tile adjacent to the min surrounding tile
    public Tile getMinSurroundingSource() {
        Tile minTile = getMinSurroundingTile();
        for (Tile t : map.getSurroundingTiles(minTile)) {
            if (t.getLake() == this) {
                return t;
            }
        }
        return null; // This should never happen (but it is)
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setBorder(ArrayList<Tile> borderTiles) {
        this.borderTiles = borderTiles;
    }

    public Tile getShallowestTile() {
        return tiles.get(tiles.size() - 1);
    }

    public Tile getDeepestTile() {
        return tiles.get(0);
    }

    public ArrayList<Tile> getBorderTiles() {
        return borderTiles;
    }

    public void addTile(Tile tile) {
        tile.addToLake(this);
        River r = tile.getRiver();
        if (r != null) {
            r.removeTile(tile);
            tile.addToRiver(null);
        }
        tiles.add(tile);
        borderTiles.add(tile);

        // See if two lakes are now connected and merge
        for (Tile sTile : map.getSurroundingTiles(tile)) {
            Lake lake = sTile.getLake();
            if (lake == null) { continue; }
            if (!lake.equals(this)) {
                Lake mergedLake = mergeWithLake(sTile.getLake());
                tiles = mergedLake.getTiles();
                borderTiles = mergedLake.getBorderTiles();
            }
        }

        recalculateBorder();
    }

    public int getSize() {
        return tiles.size();
    }

    public short getWaterLevel() {
        return tiles.get(tiles.size() - 1).getHeight();
    }

    /*
     * Takes a lake and sets the tiles to be the larger lake (to merge them)
     */
    private Lake mergeWithLake(Lake lake) {
        if (this == lake) { return this; }
        Lake greaterLake = lake.getSize() > this.getSize() ? lake : this;
        Lake lesserLake = greaterLake == lake ? this : lake;
        
        greaterLake.setTiles(mergeTilesets(greaterLake, lesserLake));
        greaterLake.setBorder(mergeBorders(greaterLake, lesserLake));
        
        return greaterLake;
    }

    // Two finger sort which ensures lake tiles are still sorted
    private ArrayList<Tile> mergeTilesets(Lake greaterLake, Lake lesserLake) {
        // Dummy edge case: There are other tiles at max height
        Tile dummyTile = new Tile(-1, -1, Short.MAX_VALUE);
        ArrayList<Tile> greaterTiles = greaterLake.getTiles();
        ArrayList<Tile> lesserTiles = lesserLake.getTiles();
        int expectedSize = lesserTiles.size() + greaterTiles.size();
        greaterTiles.add(dummyTile);
        lesserTiles.add(dummyTile);

        int i = 0;
        int j = 0;
        ArrayList<Tile> newTiles = new ArrayList<>();
        while (newTiles.size() < expectedSize) {
            Tile gTile = greaterTiles.get(i);
            Tile lTile = lesserTiles.get(j);
            if (gTile.isHigherThan(lTile)) {
                lTile.addToLake(greaterLake);
                newTiles.add(lTile);
                j++;
            } else {
                newTiles.add(gTile);
                i++;
            }
        }
        // Remove dummies
        greaterTiles.remove(greaterTiles.size() - 1);
        lesserTiles.remove(lesserTiles.size() - 1);
        return newTiles;
    }

    private ArrayList<Tile> mergeBorders(Lake greaterLake, Lake lesserLake) {
        ArrayList<Tile> newBorder = new ArrayList<>();
        for (Tile t : greaterLake.getBorderTiles()) { newBorder.add(t); }
        for (Tile t : lesserLake.getBorderTiles()) {  newBorder.add(t); }
        return newBorder;
    }

    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (!(o instanceof Lake)) { return false;}

        // If any of the lake's tiles belong to the other lake, they must be the same
        Lake lake = (Lake) o;
        Tile testTile = tiles.get(0);
        for (Tile t : lake.getTiles()) {
            if (t.equals(testTile)) {
                return true;
            }
        }
        return false;
    }
}
