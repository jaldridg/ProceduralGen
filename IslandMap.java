
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
                float distI = (float) Math.pow(((double) i / size - 0.5), 2);
                float distJ = (float) Math.pow(((double) j / size - 0.5), 2);
                float heightCorrection = (distI + distJ) * 2;
                tiles[i][j].setHeight((short)(Math.max(0, tiles[i][j].getHeight() - heightCorrection)));
            }
        }
    }
}
