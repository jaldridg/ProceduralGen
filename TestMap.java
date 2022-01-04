
public class TestMap extends Map {
    
    public TestMap() {
        super();
    }

    public void generate(int seed) {
        generateUniformHeightArray(0.5);
        generateColorArray();
    }

    private void generateUniformHeightArray(double uniformHeight) {
        heights = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                heights[i][j] = uniformHeight;
            }
        }
    }
}
