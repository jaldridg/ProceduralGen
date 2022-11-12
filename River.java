import java.util.ArrayList;
import java.util.LinkedList;

public class River {
    private Map map;

    private Tile[] tiles;

    private Tile origin;
    
    public River(Map map, Tile origin) {
        this.map = map;
        this.origin = origin;
        generateRiver();
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void generateRiver() {
        LinkedList<Tile> tileList = new LinkedList<Tile>();
        tileList = flow(origin, tileList);
        tiles = new Tile[tileList.size()];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = tileList.get(i);
        }
    }

    private LinkedList<Tile> flow(Tile currentTile, LinkedList<Tile> list) {
        if (currentTile.getHeight() < Constants.SAND_HEIGHT) { return list; }

        Tile minTile = map.getMinSurroundingTile(currentTile);
        if (currentTile.isHigherThan(minTile)) {
            list.add(minTile);
            return flow(minTile, list);
        } else {
            return list;
        }
    }

    // A debugging function (may crash if point is on the edge of map)
    private void printLocalHeights(Tile tile, Map map) {
        int x = tile.getX();
        int y = tile.getY();
        System.out.print("\t");
        int range = 2;
        for (int i = -range; i < range + 1; i++) {
            System.out.print(y + i + "\t");
        }
        System.out.println();
        for (int i = -range; i < range + 1; i++) {
            System.out.print(x + i + "\t");
            for (int j = 0; j < range * 2 + 1; j++) {
                Tile t = map.getTile(x + i - range, y + j - range);
                if (t.isRiver()) {
                    System.out.print("  R\t");
                } else if (t.isLake()) {
                    System.out.print("  L\t");
                } else {
                    System.out.print(String.format("%.4f\t", map.getTile(x + i - range, y + j - range).getHeight()));
                }
            }
            System.out.println();
        }

    }

    // For degugging
    private String printTileXY(Tile tile) {
       return "(" + tile.getX() + ", " + tile.getY() + ")";
    }
    /*
  * Can you go downhill from the current river point or current lake points?
  *     Yes: End lake (if applicable) and make the current river point the lowest surrounding point
  *     No: Begin the lake by adding this trapped points to lakePoints
  *         No matter what, the water will build up and flow to lowest point
  *         Count the surrounding min point as part of the lake
  *             But if surrounding point is part of a lake, this trapped point should be added to the preexisting lake
  *         The water level is now at that height
  *         Then, test the surrounding points to each point in the lake, ignoring lake points (river points are okay though)
  *         
  * Finish lake by adding the lake points which were not already water
  */

  // Possible edge case above: We need to look at the water level and not the height of the lakes
}
