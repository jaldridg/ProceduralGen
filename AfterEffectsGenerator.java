import java.util.Random;

import java.util.LinkedList;

public class AfterEffectsGenerator {

    private int size;

    private River[] rivers;

    private Random rng;

    private Map map;

    public AfterEffectsGenerator(int size, Map map, Random rng) {
        this.size = size;
        this.map = map;
        this.rng = rng;

        generateAfterEffects();
    }

    public void generateAfterEffects() {
        generateRivers();
    }
 
    private void generateRivers() {
        // int numRivers = Math.abs((int) rng.nextGaussian());
        int numRivers = 25;
        rivers = new River[numRivers];
        for (int i = 0; i < numRivers; i++) {
            // TODO: Get a point on a mountain that isn't frozen from being too high
            // Randomly generate it for now
            int x, y;
            do {
                x = (int) (rng.nextFloat() * size);
                y = (int) (rng.nextFloat() * size);
            } while(map.getHeight(x, y) < Constants.SAND_HEIGHT);
            rivers[i] = generateRiver(x, y);
        }
    }

    private River generateRiver(int originX, int originY) {
        LinkedList<Point<Integer>> riverList = new LinkedList<Point<Integer>>();

        Point<Integer> currentPoint = new Point<Integer>(originX, originY);
        riverList.add(currentPoint);
        // Descend the terrain
        while (map.getHeight(currentPoint) > Constants.SAND_HEIGHT) {
            // Stop if river is on the edge of the screen
            if ((currentPoint.getX() == 0) || (currentPoint.getX() == size - 1) ||
                (currentPoint.getY() == 0) || (currentPoint.getY() == size - 1)) {
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
        Point<Integer>[] riverArray = (Point<Integer>[]) new Point<?>[riverList.size()];
        for (int i = 0; i < riverList.size(); i++) {
            riverArray[i] = riverList.get(i);
        }
        return new River(riverArray);
    }

    public River[] getRivers() {
        return rivers;
    }
}