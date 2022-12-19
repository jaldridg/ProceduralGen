import java.util.LinkedList;

public class River {

    // The tiles which belong to the river
    // They should be in order that the river flows
    private LinkedList<Tile> tiles;

    public River(Tile origin, Tile nextTile) {
        tiles = new LinkedList<Tile>();
        tiles.add(origin);
        tiles.add(nextTile);
    }

    public void addTile(Tile t) {
        tiles.add(t);
        t.addToRiver(this);
    }

    public Tile[] getTiles() {
        Tile[] tileArray = new Tile[tiles.size()];
        for (int i = 0; i < tileArray.length; i++) {
            tileArray[i] = tiles.get(i);
        }
        return tileArray;
    }

    public Tile getLowestTile() {
        return tiles.get(tiles.size() - 1);
    }
}
