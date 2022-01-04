import java.util.Random;

public class DefaultMap extends Map {
    
    public DefaultMap() {
        super();
    }

    public void generate(int seed) {
        rng = new Random(seed);

        generateHeightArray();
        normalizeHeightArray();
        generateColorArray();
    }
}
