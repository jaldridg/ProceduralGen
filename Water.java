
// Generates Lakes and Rivers as it flows downhill
public class Water {

    public static void flow(Map map, Tile origin) {
        // call recursive functions to generate bodies of water until at the ocean
        Tile currentTile = origin;
        Tile nextTile = map.getMinSurroundingTile(currentTile);
        while (currentTile.getHeight() >= Constants.SAND_HEIGHT) {
            // Let's not deal with things on the edge of the map
            if (map.isOnBorder(nextTile)) { break; }

            // Recursively generate a river when water can flow downhill
            if (currentTile.isHigherThan(nextTile)) {
                River newRiver = new River(nextTile);
                newRiver = generateRiver(map, newRiver);
                currentTile = newRiver.getLowestTile();
                nextTile = map.getMinSurroundingTileNoLake(currentTile);
        
            // Recursively generate a lake when water is stuck in a valley
            } else {
                Lake newLake = new Lake(map, currentTile);
                // Lake may have merged so set to that lake
                newLake = newLake.getTiles().get(0).getLake();
                newLake = generateLake(newLake);
                currentTile = newLake.getShallowestTile();
                nextTile = newLake.getMinSurroundingTile();
            }
        }
    }

    private static River generateRiver(Map map, River currentRiver) {
        // Base case when we reach water
        Tile minTile = map.getMinSurroundingTileNoLake(currentRiver.getLowestTile());
        if (minTile.getHeight() < Constants.SAND_HEIGHT) {
            return currentRiver; 
        }

        // If the river can still flow, keep generating the river
        if (currentRiver.getLowestTile().isHigherThan(minTile)) {
            currentRiver.addTile(minTile);
            return generateRiver(map, currentRiver);

        // If not, we need to make a lake so return the river we've got
        } else {
            return currentRiver;
        }
    }

    private static Lake generateLake(Lake currentLake) {
        Tile minTile = currentLake.getMinSurroundingTile();
        // Base case when we reach water
        if (minTile.getHeight() < Constants.SAND_HEIGHT) { 
            return currentLake;
        }

        // If the lake is still in a valley, keep generating the lake
        if (minTile.getHeight() > currentLake.getWaterLevel()) {
            currentLake.addTile(minTile);
            return generateLake(currentLake);

        // If not, we need to make a river so return the lake we've got
        } else {
            return currentLake;
        }
    }

    // A debugging function (may crash if point is on the edge of map)
    private void printLocalHeights(Tile tile, Map map, int range) {
        int x = tile.getX();
        int y = tile.getY();
        System.out.print("\t");
        for (int i = -range; i < range + 1; i++) {
            System.out.print("  " + (x + i) + "\t");
        }
        System.out.println();
        for (int i = -range; i < range + 1; i++) {
            System.out.print(y + i + "\t");
            for (int j = 0; j < range * 2 + 1; j++) {
                Tile t = map.getTile(x + j - range, y + i);
                if (t.isRiver()) {
                    System.out.print("  R\t");
                } else if (t.isLake()) {
                    System.out.print("  L\t");
                } else {
                    System.out.print(String.format("%.4f\t", t.getHeight()));
                }
            }
            System.out.println();
        }

    }
}