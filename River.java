import java.util.LinkedList;

public class River {
    Point<Integer>[] points;

    Point<Integer> origin;
    
    public River(Point<Integer> origin) {
        this.origin = origin;
    }

    public Point<Integer>[] getPoints() {
        return points;
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
                    if (riverList.getLast().equals(surroundingPoint)) {
                        continue;       
                    }
                    
                    minPoint = surroundingPoint;
                }
            }

            // If there are no more lower adjacent pixels, stop
            // TODO: In this case, the water should actually pool up into a lake until it can flow downhill more
            if (currentPoint.equals(minPoint)) {
                break;
            }

            currentPoint = minPoint;
            riverList.add(currentPoint);
        }

        // Now populate new array
        // TODO: Handle this warning somehow
        points = (Point<Integer>[]) new Point<?>[riverList.size()];
        for (int i = 0; i < riverList.size(); i++) {
            points[i] = riverList.get(i);
        }
    }
    // private Point<Integer>[] generateLake(Point<Integer>[] lakePoints, Point<) {

    // }
}
