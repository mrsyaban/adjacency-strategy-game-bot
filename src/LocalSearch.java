import javafx.scene.control.Button;

/**
 * Class to run a bot according to the Local Search algorithm with hill climb
 */
public class LocalSearch extends BotController{
    public LocalSearch(Button[][] map, boolean playerXTurn, int roundsLeft) {
        this.currentState = map;
        this.playerXTurn = playerXTurn;
        this.roundsLeft = roundsLeft;
    }

    // The hillClimb method evaluates all possible moves and returns the coordinates of the best move.
    private int[] hillClimb() {
        // Initialize variables to keep track of the highest score and corresponding move.
        int maxScore = Integer.MIN_VALUE;
        int bestI=-1, bestJ=-1;
        Button[][] state = this.currentState;


        // Loop through the current game state to evaluate all potential moves.
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                // Check if the current cell is empty, indicating a potential move.
                if (state[i][j].getText().equals("")) {
                    // Generate a new game state after making a move at the current cell.
                    Button[][] child = getUpdatedState(state, i, j, false);

                    // Evaluate the new game state using the ObjectiveFunction.
                    int score = ObjectiveFunction(this.roundsLeft == 1 ? 0 : 1, child);

                    // If the score is higher than the current maxScore, update maxScore and move coordinates.
                    if (score > maxScore) {
                        maxScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }

        // Return the best move's coordinates.
        return new int[]{bestI, bestJ};
    }

    @Override
    public int[] run() {
        int[] bestMove = hillClimb();
        this.roundsLeft--;
        return new int[]{bestMove[0], bestMove[1]};
    }

}
