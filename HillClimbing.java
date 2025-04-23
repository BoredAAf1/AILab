import java.util.Random;

public class HillClimbing {
    static double function(double x) {
        return -Math.pow(x - 4, 2) + 16;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        double x = rand.nextDouble() * 8; // Random start in range [0,8] for better variation
        double stepSize = 0.05; // Slightly larger step size for noticeable difference
        int maxIterations = 5000;
        double bestX = x;
        double bestValue = function(x);

        System.out.println("Initial x: " + String.format("%.4f", x) + ", f(x): " + String.format("%.4f", bestValue));

        for (int i = 0; i < maxIterations; i++) {
            double newX = x + (rand.nextBoolean() ? stepSize : -stepSize);
            double newValue = function(newX);
            
            if (newValue > bestValue) {
                bestX = newX;
                bestValue = newValue;
                x = newX;
            }
        }

        System.out.println("Optimal x: " + String.format("%.4f", bestX) + ", Maximum f(x): " + String.format("%.4f", bestValue));
    }
}








