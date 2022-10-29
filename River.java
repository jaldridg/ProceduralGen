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
        LinkedList<Point<Integer>> riverList = new LinkedList<Point<Integer>>();

        Point<Integer> currentPoint = origin;
        riverList.add(currentPoint);
        // Descend the terrain
        while (map.getHeight(currentPoint) > Constants.SAND_HEIGHT) {
            // Stop if river is on the edge of the screen
            if ((currentPoint.getX() == 0) || (currentPoint.getX() == map.getSize() - 1) ||
                (currentPoint.getY() == 0) || (currentPoint.getY() == map.getSize() - 1)) {
                break;
            }

            Point<Integer> minPoint = new Point<Integer>(currentPoint);
            int[] pointOffsets = {0, 0, -1, 1};
            // Find the min height of the adjacent pixels
            for (int i = 0; i < pointOffsets.length; i++) {
                int x = currentPoint.getX() + pointOffsets[i];
                int y = currentPoint.getY() + pointOffsets[pointOffsets.length - i - 1];
                Point<Integer> surroundingPoint = new Point<Integer>(x, y);
                if (map.getHeight(minPoint) > map.getHeight(surroundingPoint)) {
                    // Skip if lowest point is already the river
                    if (riverList.contains(surroundingPoint)) {
                        continue;       
                    }
                    
                    minPoint = surroundingPoint;
                }
            }

            // If there are no more lower adjacent pixels, stop
            // TODO: In this case, the water should actually pool up into a lake until it can flow downhill more
            if (currentPoint.equals(minPoint)) {
                break;
                
            } else {
                currentPoint = minPoint;
                riverList.add(currentPoint);
            }
        }

        // Now populate new array
        // TODO: Handle this warning somehow
        points = (Point<Integer>[]) new Point<?>[riverList.size()];
        for (int i = 0; i < riverList.size(); i++) {
            points[i] = riverList.get(i);
        }
    }

    private Point<Integer>[] generateLake(LinkedList<Point<Integer>> lakePoints, Map map) {
        Point<Integer> minHeightPoint = null;
        // Find minHeightPoint by looking at all point in the lake
        // Yes very brute force
        int[] pointOffsets = {0, 0, -1, 1};
        // Loop over all lake points
        for (int i = 0; i < lakePoints.size(); i++) {
            Point<Integer> lakePoint = lakePoints.get(i);
            // Search each surrounding point
            for (int j = 0; j < pointOffsets.length; j++) {
                int x = lakePoint.getX() + pointOffsets[j];
                int y = lakePoint.getY() + pointOffsets[pointOffsets.length - j - 1];
                Point<Integer> surroundingPoint = new Point<Integer>(x, y);
                // Skip if point is already in the lake
                if (lakePoints.contains(surroundingPoint)) {
                    continue;
    
                // Sees if this surrounding land is the next lowest point
                } else if ((minHeightPoint == null) || (map.getHeight(surroundingPoint) < map.getHeight(minHeightPoint))) {
                    minHeightPoint = surroundingPoint;
                }
            }
        }
        lakePoints.add(minHeightPoint);
        
        // TODO: we really need a getter for the surronding points and maybe whether or not a point is a valley because this is FAT
        int x = minHeightPoint.getX();
        int y = minHeightPoint.getY();
        float minHeight = map.getHeight(minHeightPoint);
        Point<Integer> northPoint = new Point<Integer>(x - 1, y);
        Point<Integer> southPoint = new Point<Integer>(x + 1, y);
        Point<Integer> eastPoint = new Point<Integer>(x, y + 1);
        Point<Integer> westPoint = new Point<Integer>(x, y - 1);
        float northHeight = map.getHeight(northPoint);
        float southHeight = map.getHeight(southPoint);
        float eastHeight = map.getHeight(eastPoint);
        float westHeight = map.getHeight(westPoint);
        boolean isNorthLower = (minHeight > northHeight) && !lakePoints.contains(northPoint);
        boolean isSouthLower = (minHeight > southHeight) && !lakePoints.contains(southPoint);
        boolean isEastLower = (minHeight > eastHeight) && !lakePoints.contains(eastPoint);
        boolean isWestLower = (minHeight > westHeight) && !lakePoints.contains(westPoint);
        // If the new point leads to lower terrain, stop
        if (isNorthLower || isSouthLower || isEastLower || isWestLower) {
            // TODO: fix this warning somehow
            Point<Integer>[] lakeArray = (Point<Integer>[]) new Point<?>[lakePoints.size()];
            for (int i = 0; i < lakeArray.length; i++) {
                lakeArray[i] = lakePoints.get(i);
            }
            return lakeArray;
        } else {
            System.out.println("Recurse" + lakePoints.size());
            return generateLake(lakePoints, map);
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
  * Finish lake by adding the lake pgoints which were not already water
  */

  // Possible edge case above: We need to look at the water level and not the height of the lakes
}
