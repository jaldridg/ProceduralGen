import java.util.Random;

public class AfterEffectsGenerator {

    private int size;

    private River[] rivers;

    private Random rng;

    private Map map;

    public AfterEffectsGenerator(Map map, Random rng) {
        this.map = map;
        size = map.getSize();
        this.rng = rng;
    }

    public void generateAfterEffects() {
        generateRivers();
    }
 
    private void generateRivers() {
        int numRivers = 25;
        rivers = new River[numRivers];
        for (int i = 0; i < numRivers; i++) {
            // TODO: Get a point on a mountain that isn't frozen from being too high
            // Randomly generate it for now
            int x, y;
            do {
                x = (int) (rng.nextFloat() * size);
                y = (int) (rng.nextFloat() * size);
            } while(map.getHeight(x, y) < Constants.SAND_HEIGHT);
            rivers[i] = new River(map, map.getTile(x, y));
        }
    }

    public River[] getRivers() {
        return rivers;
    }
}