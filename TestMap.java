
// An un-used map for testing generation algorithms
public class TestMap extends Map {
    
    public TestMap() {
        super();
    }

    public void generate(int seed) {
        generateHeightArray();
    }

    protected void generateHeightArray() {
        heights = new float[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                heights[i][j] = (float) (i * j) / (size * size);
            }
        }
    }
}
