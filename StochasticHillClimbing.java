import java.util.Random;
public class NormalHillClimbing {
    static Random rand = new Random();

    // Objective function: Rastrigin function (simplified for 1D)
    public static double rastrigin(double x) {
        return 10 + (x * x) - (10 * Math.cos(2 * Math.PI * x));
    }

    public static double hillClimb(double start, double stepSize, int iterations) {
        double current = start;
        double bestValue = rastrigin(current);
        
        for (int i = 0; i < iterations; i++) {
            double candidate = current + (rand.nextDouble() * 2 - 1) * stepSize;
            double candidateValue = rastrigin(candidate);
            
            if (candidateValue < bestValue) { // Only accept better moves
                current = candidate;
                bestValue = candidateValue;
            }
        }
        return current;
    }
}
public class StochasticHillClimbing {
    static Random rand = new Random();
    
    // Objective function: Rastrigin function (simplified for 1D)
    public static double rastrigin(double x) {
        return 10 + (x * x) - (10 * Math.cos(2 * Math.PI * x));
    }

    public static double hillClimb(double start, double stepSize, int iterations, double probability) {
        double current = start;
        double bestValue = rastrigin(current);
        
        for (int i = 0; i < iterations; i++) {
            double candidate = current + (rand.nextDouble() * 2 - 1) * stepSize;
            double candidateValue = rastrigin(candidate);
            
            if (candidateValue < bestValue || rand.nextDouble() < probability) {
                current = candidate;
                bestValue = candidateValue;
            }
        }
        return current;
    }
    public static void main(String[] args) {
        double bestSolution = hillClimb(rand.nextDouble() * 10 - 5, 0.1, 1000, 0.1);
        System.out.println("Best found solution: " + bestSolution);
        System.out.println("Function value at best solution: " + rastrigin(bestSolution));
    }
}


