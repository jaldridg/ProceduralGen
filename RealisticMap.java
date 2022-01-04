import java.awt.*;

public class RealisticMap extends Map{

    public RealisticMap() {
        super();
    }

    public void generate() {

    }

    protected Color generateColor(double height) {
        // Deep to shallow ocean
        if (height < 0.3) {
            return new Color(0, (int) (height * 500), 150);

        // Sand to light grass
        } else if (height < 0.45) {
            int rValue = lerp(0.3, 0.45, 255, 40, height);
            int gValue = lerp(0.3, 0.45, 255, 150, height);
            int bValue = lerp(0.3, 0.45, 100, 0, height);
            return new Color(rValue, gValue, bValue);

        // Light grass to dark grass
        } else if (height < 0.6) {
            int rValue = lerp(0.45, 0.6, 40, 25, height);
            int gValue = lerp(0.45, 0.6, 150, 100, height);
            return new Color(rValue, gValue, 0);

        // Dark grass to mountain
        } else if (height < 0.80) {
            int rValue = lerp(0.6, 0.8, 25, 80, height);
            int gValue = lerp(0.6, 0.8, 100, 40, height);
            return new Color(rValue, gValue, 0);

        // Mountain to snow
        } else {
            int rValue = lerp(0.8, 1.0, 80, 255, height);
            int gValue = lerp(0.8, 1.0, 40, 255, height);
            int bValue = lerp(0.8, 1.0, 0, 255, height);
            return new Color(rValue, gValue, bValue);
        }
    }

    private int lerp(double minHeight, double maxHeight, double colorOne, double colorTwo, double height) {
        double ratio = (colorOne - colorTwo) / (minHeight - maxHeight);
        return (int) (colorOne + ((height - minHeight) * ratio));
    }

    // A slower version where the transition happens faster at the end
    private int lerpSquare(double minHeight, double maxHeight, double colorOne, double colorTwo, double height) {
        double ratio = (colorOne - colorTwo) / (minHeight - maxHeight);
        double scaledHeight = height - minHeight;
        return (int) (colorOne + (scaledHeight * scaledHeight * ratio));
    }
}
