
public class IslandMap extends Map {

    public IslandMap() {
        super();
    }
    
    protected void normalizeHeightArray() {
        // Find max and min
        double max = heights[0][0];
        double min = heights[0][0];
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                if (heights[i][j] > max) {
                    max = heights[i][j];
                } else if (heights[i][j] < min) {
                    min = heights[i][j];
                }
            }
        }
        double range = Math.abs(min) + Math.abs(max);

        // Reduce heights around the edge of the map
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double rootTwo = 1.414;
                double distI = Math.pow((((double) i) / size - 0.5) * (range / rootTwo), 2);
                double distJ = Math.pow((((double) j) / size - 0.5) * (range / rootTwo), 2);
                heights[i][j] -= distI + distJ;
            }
        }

        //Normalize
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i].length; j++) {
                heights[i][j] = (heights[i][j] - min) / range;
            }
        }
    }
}
