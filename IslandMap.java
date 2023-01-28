
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
        int halfSize = size / 2;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int heightCorrection = (int) (Math.pow(i - halfSize, 2) + Math.pow(j - halfSize, 2));
                short adjustedHeight = (short) (tiles[i][j].getHeight() - heightCorrection * Constants.ISLAND_GENERTION_STRENGTH);
                tiles[i][j].setHeight((short)(Math.max(0, adjustedHeight)));
            }
        }
    }
}
