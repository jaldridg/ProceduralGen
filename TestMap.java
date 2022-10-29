
// An un-used map for testing generation algorithms
public class TestMap extends Map {
    
    public TestMap() {
        super();
    }

    public void generate(int seed) {
        generateHeightArray();
    }

    protected void generateHeightArray() {
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile((float) (i * j) / (size * size));
            }
        }
    }
}
