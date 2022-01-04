
public class TestMap extends Map {
    
    public TestMap() {
        super();
    }

    public void generate(int seed) {
        generateHeightArray();
        generateColorArray();
    }

    protected void generateHeightArray() {
        heights = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                heights[i][j] = (double) (i * j) / (size * size);
            }
        }
    }
}
