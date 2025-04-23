import java.util.Scanner;

public class MINIMAX {

    public static int minimax(int matches, boolean isMax) {
        if (matches == 1) {
            return isMax ? -1 : 1; 
        }

        int best;
        if (isMax) {
            best = Integer.MIN_VALUE;
            for (int i = 1; i <= 3; i++) {
                if (matches - i >= 1) {
                    best = Math.max(best, minimax(matches - i, false));
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            for (int i = 1; i <= 3; i++) {
                if (matches - i >= 1) {
                    best = Math.min(best, minimax(matches - i, true));
                }
            }
        }
        return best;
    }

    // Find best move for the AI
    public static int findBestMove(int matches) {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = 1;

        for (int i = 1; i <= 3; i++) {
            if (matches - i >= 1) {
                int moveVal = minimax(matches - i, false);
                if (moveVal > bestVal) {
                    bestVal = moveVal;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int matches = 21;

        System.out.println("Matchstick Game: Pick 1, 2 or 3 matches on your turn.");
        System.out.println("The player who picks the LAST matchstick LOSES.");
        System.out.println("Game starts with " + matches + " matchsticks.\n");

        // Game loop
        while (matches > 1) {
            // AI move
            int aiMove = findBestMove(matches);
            matches -= aiMove;
            System.out.println("AI picks: " + aiMove + " matchstick(s). Remaining: " + matches);

            if (matches == 1) {
                System.out.println("You are forced to pick the last matchstick. You LOSE!");
                break;
            }

            // Human move
            int humanMove;
            while (true) {
                System.out.print("Your turn! Pick 1, 2 or 3 matchsticks: ");
                humanMove = sc.nextInt();
                if (humanMove >= 1 && humanMove <= 3 && matches - humanMove >= 1) {
                    break;
                } else {
                    System.out.println("Invalid move! Try again.");
                }
            }
            matches -= humanMove;
            System.out.println("You picked: " + humanMove + " matchstick(s). Remaining: " + matches);

            if (matches == 1) {
                System.out.println("AI is forced to pick the last matchstick. AI LOSES. You WIN!");
                break;
            }
        }

        sc.close();
    }
}

