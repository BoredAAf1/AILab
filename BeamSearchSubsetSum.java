import java.util.*;

public class BeamSearchSubsetSum {

    static class Node {
        List<Integer> subset;
        int sum;

        Node(List<Integer> subset, int sum) {
            this.subset = new ArrayList<>(subset);
            this.sum = sum;
        }
    }

    public static List<Integer> beamSearch(int[] nums, int target, int beamWidth) {
        Queue<Node> beam = new LinkedList<>();
        beam.add(new Node(new ArrayList<>(), 0));

        for (int num : nums) {
            PriorityQueue<Node> candidates = new PriorityQueue<>(Comparator.comparingInt(a -> Math.abs(target - a.sum)));
            for (Node node : beam) {
                // Include
                List<Integer> with = new ArrayList<>(node.subset);
                with.add(num);
                candidates.add(new Node(with, node.sum + num));

                // Exclude
                candidates.add(new Node(new ArrayList<>(node.subset), node.sum));
            }
            beam = new LinkedList<>();
            int count = 0;
            while (!candidates.isEmpty() && count < beamWidth) {
                Node best = candidates.poll();
                if (best.sum == target) return best.subset;
                beam.add(best);
                count++;
            }
        }
        return new ArrayList<>();
    }

    public static List<Integer> stochasticBeamSearch(int[] nums, int target, int beamWidth) {
        Random rand = new Random();
        Queue<Node> beam = new LinkedList<>();
    
        for (int i = 0; i < beamWidth; i++) {
            beam.add(new Node(new ArrayList<>(), 0));
        }
    
        Node bestSoFar = null;
    
        for (int num : nums) {
            List<Node> candidates = new ArrayList<>();
            for (Node node : beam) {
                List<Integer> with = new ArrayList<>(node.subset);
                with.add(num);
                candidates.add(new Node(with, node.sum + num));
                candidates.add(new Node(new ArrayList<>(node.subset), node.sum));
            }
    
            candidates.sort(Comparator.comparingInt(a -> Math.abs(target - a.sum)));
    
            // Update best seen so far
            for (Node c : candidates) {
                if (bestSoFar == null || Math.abs(target - c.sum) < Math.abs(target - bestSoFar.sum)) {
                    bestSoFar = c;
                }
            }
    
            beam = new LinkedList<>();
            Set<Integer> used = new HashSet<>();
            int attempts = 0;
    
            while (beam.size() < beamWidth && attempts < 10 * beamWidth) {
                int idx = rand.nextInt(Math.min(candidates.size(), 2 * beamWidth));
                if (used.contains(idx)) {
                    attempts++;
                    continue;
                }
                used.add(idx);
                Node chosen = candidates.get(idx);
                if (chosen.sum == target) return chosen.subset;
                beam.add(chosen);
            }
        }
    
        // Return best-so-far if no exact match
        return bestSoFar != null ? bestSoFar.subset : new ArrayList<>();
    }
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size array: ");
        int n = sc.nextInt();
        int[] nums = new int[n];
        System.out.println("Enter array: ");
        for(int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        System.out.println("Enter target: ");
        int target = sc.nextInt();
        System.out.println("Enter Beam Width: ");
        int beamWidth = sc.nextInt();

        System.out.println("Beam Search:");
        List<Integer> result1 = beamSearch(nums, target, beamWidth);
        System.out.println("Subset: " + result1);

        System.out.println("\nStochastic Beam Search:");
        List<Integer> result2 = stochasticBeamSearch(nums, target, beamWidth);
        System.out.println("Subset: " + result2);
    }
}

