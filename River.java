import java.util.ArrayList;
import java.util.LinkedList;

public class River {
    private Tile[] tiles;

    private Tile origin;
    
    public River(Tile origin) {
        this.origin = origin;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void generateRiver(Map map) {
        LinkedList<Tile> riverList = new LinkedList<Tile>();

        Tile currentTile = origin;
        riverList.add(currentTile);

        Lake currentLake;
        // Descend the terrain
        while (currentTile.getHeight() > Constants.SAND_HEIGHT) {
            // We're stuck
            if (map.isValley(currentTile)) {
                // Start building the lake
                currentLake = new Lake(new ArrayList<Tile>(), map);
                currentLake.addTile(currentTile);
                // The lake will have to rise to this water level and include this point
                Tile minTile = currentLake.getMinSurroundingTile();
                // But if the point is already a lake, add the current lake to the preexisting lake
                if (minTile.isLake()) {
                    currentLake.mergeLake(minTile.getLake());
                }
                float fillHeight = map.getMinSurroundingTile(currentTile).getHeight();
            // Keep flowing
            } else {
                riverList.add(map.getMinSurroundingTile(currentTile));
            }
        }
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
