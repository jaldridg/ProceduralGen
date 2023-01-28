import java.util.Random;

public class AfterEffectsGenerator {

    private int size;

    private Random rng;

    private Map map;

    public AfterEffectsGenerator(Map map, Random rng) {
        this.map = map;
        size = map.getSize();
        this.rng = rng;
    }

    public void generateAfterEffects() {
        // TODO: Uncomment when generation is working again
        generateWater();
    }
 
    private void generateWater() {
        for (int i = 0; i < Constants.WATER_POINTS; i++) {
            int x, y;
            short height = 0;
            do {
                x = (int) (rng.nextFloat() * size);
                y = (int) (rng.nextFloat() * size);
                height = map.getHeight(x, y);
            } while(height < Constants.GRASS_HEIGHT || height > Constants.MOUNTAIN_HEIGHT);
            try {
                Water.flow(map, map.getTile(x, y));
            } catch (NullPointerException e) {
                // TODO: Fix the errors thrown
            }
        }
    }
}