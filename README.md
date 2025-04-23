import java.util.*;

public class TSP {
    private int[][] matrix;
    private int n;
    private Random rand = new Random();

    public TSP(int[][] matrix) {
        this.matrix = matrix;
        this.n = matrix.length;
    }

    public String initialNode() {
        List<Integer> tour = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour, rand);
        StringBuilder sb = new StringBuilder();
        for (int city : tour) {
            sb.append(city);
        }
        return sb.toString();
    }

    public List<String> neighbours(String node) {
        List<Integer> nodeList = new ArrayList<>();
        for (char ch : node.toCharArray()) {
            nodeList.add(Character.getNumericValue(ch));
        }

        List<String> neighbors = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++) {
            for (int j = i + 1; j < nodeList.size(); j++) {
                List<Integer> newTour = new ArrayList<>(nodeList);
                // swap i and j
                int temp = newTour.get(i);
                newTour.set(i, newTour.get(j));
                newTour.set(j, temp);

                StringBuilder sb = new StringBuilder();
                for (int city : newTour) {
                    sb.append(city);
                }
                neighbors.add(sb.toString());
            }
        }

        return neighbors;
    }

    public int heuristic(String node) {
        List<Integer> tour = new ArrayList<>();
        for (char ch : node.toCharArray()) {
            tour.add(Character.getNumericValue(ch));
        }

        int cost = 0;
        for (int i = 0; i < tour.size(); i++) {
            int a = tour.get(i);
            int b = tour.get((i + 1) % tour.size());
            cost += matrix[a][b];
        }
        return cost;
    }
}
