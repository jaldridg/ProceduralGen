import java.util.ArrayList;
import java.util.LinkedList;

// Generates Lakes and Rivers as it flows downhill
public class Water {
    private Map map;

    private Tile origin;
    
    public Water(Map map, Tile origin) {
        this.map = map;
        this.origin = origin;
        generate();
    }

    public void generate() {
        // call recursive functions to generate bodies of water until at the ocean
        Tile currentTile = origin;
        while (currentTile.getHeight() < Constants.SAND_HEIGHT) { 
            Tile minTile = map.getMinSurroundingTile(currentTile);
            // Recursively generate a river when water can flow downhill
            if (currentTile.isHigherThan(minTile)) {
                River newRiver = new River();
                newRiver = buildRiver(newRiver);
                currentTile = map.getMinSurroundingTile(newRiver.getLowestTile());
            
            // Recursively generate a lake when water is stuck in a valley
            } else {
                Lake newLake = new Lake(map);
                newLake = buildLake(newLake);
                currentTile = newLake.getMinSurroundingTile();
            }
        }
    }

    private River buildRiver(River currentRiver) {
        // Base case when we reach water
        Tile minTile = map.getMinSurroundingTile(currentRiver.getLowestTile());
        if (minTile.getHeight() < Constants.SAND_HEIGHT) {
            return currentRiver; 
        }

        // If the river can still flow, keep generating the river
        if (currentRiver.getLowestTile().isHigherThan(minTile)) {
            currentRiver.addTile(minTile);
            return buildRiver(currentRiver);

        // If not, then we need to make a lake so return the river we've got
        } else {
            return currentRiver;
        }
    }

    private Lake buildLake(Lake currentLake) {
        // Base case when we reach water
        Tile minTile = currentLake.getMinSurroundingTile();
        if (minTile.getHeight() < Constants.SAND_HEIGHT) { 
            return currentLake; 
        }

        // If the lake is still in a valley, keep generating the lake
        if (minTile.getHeight() > currentLake.getWaterLevel()) {
            currentLake.addTile(minTile);
            return buildLake(currentLake);

        // If not, when we need to make a river so return the lake we've got
        } else {
            return currentLake;
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

  // River and valley -> lake
  // River and downhill -> river
  // Lake and valley -> lake
  // Lake and downhill -> river

  // So
  // Downhill -> river
  // Valley -> Lake
}
