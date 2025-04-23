import java.io.*;
import java.util.*;

class ReadData {
    public Map<String, Integer> readHeuristics(String inputFile) {
        Map<String, Integer> heuristics = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                heuristics.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error: File not found");
            return Collections.emptyMap();
        }
        return heuristics;
    }

    public Map<String, List<Pair<String, Integer>>> readGraph(String inputFile) {
        Map<String, List<Pair<String, Integer>>> graph = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String city1 = parts[0];
                String city2 = parts[1];
                int distance = Integer.parseInt(parts[2]);
                graph.computeIfAbsent(city1, k -> new ArrayList<>()).add(new Pair<>(city2, distance));
            }
        } catch (IOException e) {
            System.out.println("Error: File not found");
            return Collections.emptyMap();
        }
        return graph;
    }
}

public class ASTARGFBS{
    private final Map<String, List<Pair<String, Integer>>> graph;
    private final Map<String, Integer> heuristics;

    public InformedSearch(Map<String, List<Pair<String, Integer>>> graph, Map<String, Integer> heuristics) {
        this.graph = graph;
        this.heuristics = heuristics;
    }

    private void reconstructPath(Map<String, String> parent, String start, String end, int totalCost) {
        List<String> path = new ArrayList<>();
        String current = end;
        while (!current.equals(start)) {
            path.add(current);
            current = parent.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        System.out.println("Path: " + String.join(" -> ", path));
        System.out.println("Total Path Cost: " + totalCost);
    }

    public void bestFirstSearch(String start, String end) {
        PriorityQueue<Pair<String, Integer>> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> heuristics.get(a.getKey())));
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> cost = new HashMap<>();  // Track cumulative cost

        frontier.add(new Pair<>(start, 0));
        parent.put(start, start);
        cost.put(start, 0);

        while (!frontier.isEmpty()) {
            Pair<String, Integer> current = frontier.poll();
            String currentCity = current.getKey();

            if (currentCity.equals(end)) {
                System.out.println("Goal reached: " + currentCity);
                reconstructPath(parent, start, end, cost.get(end));
                return;
            }

            for (Pair<String, Integer> neighbor : graph.getOrDefault(currentCity, new ArrayList<>())) {
                int newCost = cost.get(currentCity) + neighbor.getValue();
                if (!cost.containsKey(neighbor.getKey()) || newCost < cost.get(neighbor.getKey())) {
                    cost.put(neighbor.getKey(), newCost);
                    parent.put(neighbor.getKey(), currentCity);
                    frontier.add(new Pair<>(neighbor.getKey(), neighbor.getValue()));
                }
            }
        }
        System.out.println("City not found");
    }

    public void aStarSearch(String start, String end) {
        Map<String, Integer> g = new HashMap<>();
        g.put(start, 0);
        PriorityQueue<Pair<String, Integer>> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> g.get(a.getKey()) + heuristics.get(a.getKey())));
        Map<String, String> parent = new HashMap<>();
        frontier.add(new Pair<>(start, 0));
        parent.put(start, start);

        while (!frontier.isEmpty()) {
            Pair<String, Integer> current = frontier.poll();
            String currentCity = current.getKey();
            if (currentCity.equals(end)) {
                System.out.println("Goal reached: " + currentCity + " | Distance: " + g.get(currentCity));
                reconstructPath(parent, start, end, g.get(currentCity));
                return;
            }
            for (Pair<String, Integer> neighbor : graph.getOrDefault(currentCity, new ArrayList<>())) {
                int newCost = g.get(currentCity) + neighbor.getValue();
                if (!g.containsKey(neighbor.getKey()) || newCost < g.get(neighbor.getKey())) {
                    g.put(neighbor.getKey(), newCost);
                    parent.put(neighbor.getKey(), currentCity);
                    frontier.add(new Pair<>(neighbor.getKey(), newCost));
                }
            }
        }
        System.out.println("City not found");
    }
}

class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

public class ASTARGBFS {
    public static void main(String[] args) {
        ReadData reader = new ReadData();
        Map<String, Integer> heuristics = reader.readHeuristics("heuristics.txt");
        Map<String, List<Pair<String, Integer>>> graph = reader.readGraph("graph.txt");

        InformedSearch search = new InformedSearch(graph, heuristics);
        search.bestFirstSearch("Arad", "Bucharest");
        search.aStarSearch("Arad", "Bucharest");
    }
}
