import java.util.Random;
import java.util.LinkedList;

public class AfterEffectsGenerator {

    private int size;

    private float[][] heights;

    private LinkedList<int[]>[] rivers;

    private Random rng;

    public AfterEffectsGenerator(int size, float[][] heights, Random rng) {
        this.size = size;
        this.heights = heights;
        this.rng = rng;
    }

    public void generateAfterEffects() {
        generateRivers();
    }
 
    private void generateRivers() {
        int numRivers = Math.abs((int) rng.nextGaussian());
        rivers = new LinkedList[numRivers];
        for (int i = 0; i < numRivers; i++) {
            // Get a point on a mountain that isn't frozen from being too high
            rivers[i] = generateRiver(i, i);
        }
    }

    private LinkedList generateRiver(int originX, int originY) {
        LinkedList river = new LinkedList<int[]>();

        int currX = originX;
        int currY = originY;
        // Descend the terrain
        do {
            river.add(new int[] {currX, currY});
            // Stop if river is on the edge of the screen
            if (currX == 0 || currX == size || currY == 0 || currY == size) {
                break;
            }
        } while (heights[currY][currX] > Constants.SAND_HEIGHT);
        return river;
    }
}