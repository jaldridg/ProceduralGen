
public class IslandMap extends Map {

    public IslandMap() {
        super();
    }

    // Generates normal map then applies islandDistortion()
    public void generate(int seed) {
        super.generate(seed);

        islandDistortion();
    }

    /**
     * Reduces the heights around the edge of the map by subtracting the square
     * of the distance from the center of the map.
     */
    private void islandDistortion() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double distI = Math.pow((((double) i) / size - 0.5), 2);
                double distJ = Math.pow((((double) j) / size - 0.5), 2);
                heights[i][j] = Math.max(0, heights[i][j] - (distI + distJ) * 2);
            }
        }
    }
}
