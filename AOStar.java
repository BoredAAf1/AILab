import java.util.*;

class Node {
    String name;
    boolean isSolved = false;
    boolean isAndNode = false;
    int heuristic;
    List<List<Node>> children = new ArrayList<>();
    List<Integer> cost = new ArrayList<>();

    Node(String name, int heuristic, boolean isAndNode) {
        this.name = name;
        this.heuristic = heuristic;
        this.isAndNode = isAndNode;
    }

    void addChild(List<Node> childGroup, int cost) {
        children.add(childGroup);
        this.cost.add(cost);
    }
}

public class AOStar {
    static Map<String, Node> graph = new HashMap<>();
    static Set<String> visited = new HashSet<>();

    public static void aoStar(Node node) {
        if (node.isSolved) return;
        if (node.children.isEmpty()) {
            node.isSolved = true;
            return;
        }

        int minCost = Integer.MAX_VALUE;
        List<Node> bestGroup = null;

        for (int i = 0; i < node.children.size(); i++) {
            List<Node> group = node.children.get(i);
            int groupCost = node.cost.get(i);
            int totalCost = groupCost;

            for (Node child : group) {
                totalCost += child.heuristic;
            }

            if (totalCost < minCost) {
                minCost = totalCost;
                bestGroup = group;
            }
        }

        if (bestGroup != null) {
            node.heuristic = minCost;
            boolean allSolved = true;
            for (Node child : bestGroup) {
                aoStar(child);
                if (!child.isSolved) allSolved = false;
            }
            if (allSolved) node.isSolved = true;
        }
    }

    public static void printSolution(Node node, String indent) {
        System.out.print(indent + node.name);
        if (node.children.isEmpty()) {
            System.out.println(" [SOLVED]");
            return;
        }
        System.out.println(" ->");

        int bestIndex = 0, minCost = Integer.MAX_VALUE;

        for (int i = 0; i < node.children.size(); i++) {
            int totalCost = node.cost.get(i);
            for (Node n : node.children.get(i)) {
                totalCost += n.heuristic;
            }
            if (totalCost < minCost) {
                minCost = totalCost;
                bestIndex = i;
            }
        }

        for (Node child : node.children.get(bestIndex)) {
            printSolution(child, indent + "  ");
        }
    }

    public static void main(String[] args) {
        // Create sample random AO graph
        Node A = new Node("A", 999, false);
        Node B = new Node("B", 5, false);
        Node C = new Node("C", 2, false);
        Node D = new Node("D", 1, false);
        Node E = new Node("E", 3, false);
        Node F = new Node("F", 2, false);
        Node G = new Node("G", 4, false);

        A.addChild(Arrays.asList(B), 1);
        A.addChild(Arrays.asList(C, D), 2);  // AND node
        B.addChild(Arrays.asList(E), 3);
        B.addChild(Arrays.asList(F), 2);
        C.addChild(Arrays.asList(G), 4);

        graph.put("A", A);

        System.out.println("Starting AO* algorithm...");
        aoStar(A);
        System.out.println("AO* complete. Optimal path:");
        printSolution(A, "");
    }
}

