
public class IslandMap extends Map {

    public IslandMap() {
        super();
    }

    public void generate(int seed) {
        super.generateRaw(seed);
        islandDistortion();
        normalizeHeightArray();
    }
    
    private void islandDistortion() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                heights[i][j] -= (Math.pow(i / mapSize - 0.5, 2) + Math.pow(i / mapSize - 0.5, 2));
            }
        }
    }
}
