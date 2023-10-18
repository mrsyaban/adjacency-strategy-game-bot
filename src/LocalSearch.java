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

    private int[] hillClimb() {
        int maxScore = Integer.MIN_VALUE;
        int bestI=-1, bestJ=-1;
        Button[][] state = this.currentState;


        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (state[i][j].getText().equals("")) {
                    Button[][] child = getUpdatedState(state, i, j, false);
//                    System.out.println(Arrays.deepToString(child));
                    try {
                        int score = ObjectiveFunction(this.roundsLeft == 1 ? 0 : -1, child);
//                        System.out.println(score);
                        if (score > maxScore) {
                            maxScore = score;
                            bestI = i;
                            bestJ = j;
                        }
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        return new int[]{bestI, bestJ};
    }

    @Override
    public int[] run() {
        int[] bestMove = hillClimb();
        return new int[]{bestMove[0], bestMove[1]};
    }

}
