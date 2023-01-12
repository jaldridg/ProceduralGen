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

    // Gets the lake tile adjacent to the min surrounding tile
    public Tile getMinSurroundingSource() {
        Tile minTile = getMinSurroundingTile();
        for (Tile t : map.getSurroundingTiles(minTile)) {
            if (t.getLake() == this) {
                return t;
            }
        }
        return null; // This should never happen
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
            if (lake != null && lake != this) {
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

    public float getWaterLevel() {
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
        Tile dummyTile = new Tile(-1, -1, 1.1f);
        ArrayList<Tile> greaterTiles = greaterLake.getTiles();
        ArrayList<Tile> lesserTiles = lesserLake.getTiles();
        int expectedSize = lesserTiles.size() + greaterTiles.size();
        greaterTiles.add(dummyTile);
        lesserTiles.add(dummyTile);
        // TODO: Remove after debugging
        if (greaterTiles.size() == 10) {
            int x = 0;
        }

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

    // Doesn't actually calculate the new border
    private ArrayList<Tile> mergeBorders(Lake greaterLake, Lake lesserLake) {
        ArrayList<Tile> newBorder = new ArrayList<>();
        for (Tile t : greaterLake.getBorderTiles()) {
            newBorder.add(t);
        }
        for (Tile t : lesserLake.getBorderTiles()) {
            newBorder.add(t);
        }
        return newBorder;
    }
}
