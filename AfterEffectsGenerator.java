import java.util.Random;

import java.util.LinkedList;

public class AfterEffectsGenerator {

    private int size;

    private float[][] heights;

    private int[][][] rivers;

    private Random rng;

    public AfterEffectsGenerator(int size, float[][] heights, Random rng) {
        this.size = size;
        this.heights = heights;
        this.rng = rng;

        generateAfterEffects();
    }

    public void generateAfterEffects() {
        generateRivers();
    }
 
    private void generateRivers() {
        // int numRivers = Math.abs((int) rng.nextGaussian());
        int numRivers = 25;
        rivers = new int[numRivers][][];
        for (int i = 0; i < numRivers; i++) {
            // TODO: Get a point on a mountain that isn't frozen from being too high
            // Randomly generate it for now
            int x = (int) (rng.nextFloat() * size);
            int y = (int) (rng.nextFloat() * size);
            rivers[i] = generateRiver(x, y);
        }
    }

    private int[][] generateRiver(int originX, int originY) {
        LinkedList<int[]> riverList = new LinkedList<int[]>();

        int riverLength = 0;
        int currX = originX;
        int currY = originY;
        // Descend the terrain
        do {
            riverLength++;
            riverList.add(new int[] {currX, currY});
            // Stop if river is on the edge of the screen
            if (currX == 0 || currX == size || currY == 0 || currY == size) {
                break;
            }

            int minX = currX;
            int minY = currY;
            float minHeight = heights[minX][minY];
            int[] pointOffsets = {0, 0, -1, 1};
            // Find the min height of the adjacent pixels
            for (int i = 0; i < pointOffsets.length; i++) {
                float height = heights[currX + pointOffsets[i]][currY + pointOffsets[pointOffsets.length - i - 1]];
                if (minHeight > height) {
                    // Make sure we're not considering a river point already
                    if (riverLength > 1) {
                        int[] pastRiverPoint = riverList.get(riverLength - 1);
                        if (pastRiverPoint[0] == currY && pastRiverPoint[1] == currX) {
                            continue;
                        }
                    }
                    
                    minHeight = height;
                    minX = currX + pointOffsets[i];
                    minY = currY + pointOffsets[pointOffsets.length - i - 1];
                }
            }

            // If there are no more lower adjacent pixels, stop
            if (minX == currX && minY == currY) {
                break;
            } 

            currX = minX;
            currY = minY;

            System.out.println("In da woop: " + riverLength + " (" + currX + ", " + currY + ")");
        } while (heights[currX][currY] > Constants.SHALLOW_WATER_HEIGHT);

        // Now populate new array
        int[][] riverArray = new int[riverLength][2];
        for (int i = 0; i < riverLength; i++) {
            riverArray[i][0] = riverList.get(i)[0];
            riverArray[i][1] = riverList.get(i)[1];
        }
        return riverArray;
    }

    public int[][][] getRiverArrays() {
        return rivers;
    }
}