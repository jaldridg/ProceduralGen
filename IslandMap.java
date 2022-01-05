
public class IslandMap extends Map {

    public IslandMap() {
        super();
    }

    public void generate(int seed) {
        super.generate(seed);

        islandDistortion();
    }

    private void islandDistortion() {
        // Reduce heights around the edge of the map
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double distI = Math.pow((((double) i) / size - 0.5), 2);
                double distJ = Math.pow((((double) j) / size - 0.5), 2);
                heights[i][j] = Math.max(0, heights[i][j] - (distI + distJ) * 2);
            }
        }
    }
}
