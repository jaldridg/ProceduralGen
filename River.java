import java.util.ArrayList;

public class River {

    // The tiles which belong to the river
    // They should be in order that the river flows
    private ArrayList<Tile> tiles;

    public River(Tile origin) {
        tiles = new ArrayList<Tile>();
        tiles.add(origin);
        origin.addToRiver(this);
        origin.addToLake(null);
    }

    public void addTile(Tile t) {
        tiles.add(t);
        t.addToRiver(this);
    }

    public void removeTile(Tile t) {
        tiles.remove(t);
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
