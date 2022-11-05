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

        Lake currentLake = null;
        int woop = 0;
        // Descend the terrain
        while (currentTile.getHeight() > Constants.SAND_HEIGHT) {
            if (woop++ > 100) {
                woop = 0;
            }
            // We're stuck
            // TODO: Here's what we actually want to do
            // river -> river if we can go downhill
            // river -> lake if were at a valley
            // lake -> lake if dry valley (meaning valley in terms of nonwater tiles)
            // lake -> river if we can go downhill from the lake border
            if (map.isValley(currentTile)) {
                // Start building the lake
                if (currentLake == null) {
                    currentLake = new Lake(map);
                }
                currentLake.addTile(currentTile);
                currentTile.addToLake(currentLake);
                // The lake will have to rise to this water level and include this point
                Tile minTile = currentLake.getMinSurroundingTile();
                // But if the point is water, add the current lake to the preexisting lake
                if (minTile.isWater()) {
                    if (minTile.isLake()) {
                        currentLake.mergeLake(minTile.getLake());
                        currentLake = minTile.getLake();
                    }
                    if (minTile.isRiver()) {
                        minTile.removeFromRiver();
                        minTile.addToLake(currentLake);
                    }   
                }
                currentLake.setWaterLevel(minTile.getHeight());
                currentTile = minTile;
            // Keep flowing
            } else {
                currentLake = null;
                Tile[] surroundingTiles = map.getSurroundingTiles(currentTile);
                Tile minTile = surroundingTiles[0];
                // Get lowest nonwater tile
                for (int i = 1; i < surroundingTiles.length; i++) {
                    Tile tile = surroundingTiles[i];
                    if (!tile.isWater()) {
                        if (tile.getHeight() < minTile.getHeight()) {
                            minTile = tile;
                        }
                    }
                }
                riverList.add(minTile);
                currentTile = minTile;
                currentTile.addToRiver(this);
            }
        }
        // Turn river list into tiles
        tiles = new Tile[riverList.size()];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = riverList.get(i);
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
