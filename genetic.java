import java.util.*;

class Chromosome {
    List<Integer> genes;
    double fitness;

    Chromosome(int size) {
        genes = new ArrayList<>(Collections.nCopies(size, 0));
        fitness = 0.0;
    }
}

class GeneticAlgorithm {
    private int populationSize;
    private int maxGenerations;
    private double crossoverRate;
    private double mutationRate;
    private List<Chromosome> population;
    private Random rand;

    public GeneticAlgorithm(int popSize, int maxGen, double crossRate, double mutRate) {
        this.populationSize = popSize;
        this.maxGenerations = maxGen;
        this.crossoverRate = crossRate;
        this.mutationRate = mutRate;
        this.rand = new Random();

        System.out.println("Initializing population with size " + popSize);
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome chrom = new Chromosome(4);
            initializeChromosome(chrom);
            population.add(chrom);

            System.out.print("Chromosome " + i + ": " + chrom.genes + " = ");
            System.out.println(evaluateEquation(chrom));
        }
    }

    private void initializeChromosome(Chromosome chrom) {
        for (int i = 0; i < chrom.genes.size(); i++) {
            chrom.genes.set(i, rand.nextInt(31));
        }
    }

    private int evaluateEquation(Chromosome chrom) {
        return chrom.genes.get(0) + 2 * chrom.genes.get(1) +
               3 * chrom.genes.get(2) + 4 * chrom.genes.get(3);
    }

    private double calculateFitness(Chromosome chrom) {
        int result = evaluateEquation(chrom);
        double deviation = Math.abs(result - 30);
        return 1.0 / (1.0 + deviation);
    }

    private Chromosome selectParent() {
        double totalFitness = population.stream().mapToDouble(c -> c.fitness).sum();
        double randomValue = rand.nextDouble() * totalFitness;
        double sum = 0;

        for (Chromosome chrom : population) {
            sum += chrom.fitness;
            if (sum >= randomValue) {
                return chrom;
            }
        }
        return population.get(population.size() - 1);
    }

    private void crossover(Chromosome parent1, Chromosome parent2) {
        if (rand.nextDouble() < crossoverRate) {
            int crossPoint = rand.nextInt(parent1.genes.size());
            for (int i = crossPoint; i < parent1.genes.size(); i++) {
                int temp = parent1.genes.get(i);
                parent1.genes.set(i, parent2.genes.get(i));
                parent2.genes.set(i, temp);
            }
        }
    }

    private void mutate(Chromosome chrom) {
        for (int i = 0; i < chrom.genes.size(); i++) {
            if (rand.nextDouble() < mutationRate) {
                chrom.genes.set(i, rand.nextInt(31));
            }
        }
    }

    private boolean isOptimalSolution(Chromosome chrom) {
        return evaluateEquation(chrom) == 30;
    }

    public Chromosome solve() {
        Chromosome bestEver = new Chromosome(4);
        double bestFitness = 0.0;

        for (int generation = 0; generation < maxGenerations; generation++) {
            for (Chromosome chrom : population) {
                chrom.fitness = calculateFitness(chrom);
                if (chrom.fitness > bestFitness) {
                    bestFitness = chrom.fitness;
                    bestEver = new Chromosome(4);
                    bestEver.genes = new ArrayList<>(chrom.genes);

                    if (isOptimalSolution(chrom)) {
                        System.out.println("\nPerfect solution found in generation " + generation + "!");
                        return chrom;
                    }
                }
            }

            if (generation % 10 == 0) {
                Chromosome best = Collections.max(population, Comparator.comparingDouble(c -> c.fitness));
                System.out.println("Generation " + generation + " best result: " + evaluateEquation(best));
            }

            List<Chromosome> newPopulation = new ArrayList<>();

            Chromosome best = Collections.max(population, Comparator.comparingDouble(c -> c.fitness));
            newPopulation.add(best);

            while (newPopulation.size() < populationSize) {
                Chromosome parent1 = selectParent();
                Chromosome parent2 = selectParent();
                crossover(parent1, parent2);
                mutate(parent1);
                mutate(parent2);

                newPopulation.add(parent1);
                if (newPopulation.size() < populationSize) {
                    newPopulation.add(parent2);
                }
            }

            population = newPopulation;
        }

        return bestEver;
    }
}

public class genetic {
    public static void main(String[] args) {
        System.out.println("Solving: a + 2b + 3c + 4d = 30\n");

        GeneticAlgorithm ga = new GeneticAlgorithm(20, 100, 0.8, 0.1);
        Chromosome solution = ga.solve();

        System.out.println("\nBest solution found:");
        System.out.println("a = " + solution.genes.get(0));
        System.out.println("b = " + solution.genes.get(1));
        System.out.println("c = " + solution.genes.get(2));
        System.out.println("d = " + solution.genes.get(3));

        int result = solution.genes.get(0) + 2 * solution.genes.get(1) +
                     3 * solution.genes.get(2) + 4 * solution.genes.get(3);
        System.out.println("\nEquation: " + solution.genes.get(0) + " + 2(" + solution.genes.get(1) + ") + 3(" + solution.genes.get(2) + ") + 4(" + solution.genes.get(3) + ") = " + result);
        System.out.println("Difference from target: " + Math.abs(result - 30));
    }
}

