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
        generateWater();
    }
 
    private void generateWater() {
        boolean error = false;
        System.out.println("Seed: " + map.getSeed());
        for (int i = 0; i < Constants.WATER_POINTS; i++) {
            // TODO: Get a point on a mountain that isn't frozen from being too high
            // Randomly generate it for now
            int x, y;
            float height = 0;
            do {
                x = (int) (rng.nextFloat() * size);
                y = (int) (rng.nextFloat() * size);
                height = map.getHeight(x, y);
            } while(height < Constants.GRASS_HEIGHT || height > Constants.MOUNTAIN_HEIGHT);
            try {
                Water w = new Water(map, map.getTile(x, y));
            } catch (NullPointerException e) {
                error = true;
            }
        }
        if (error) {
            System.out.println("Null Pointer!");
        }
    }
}