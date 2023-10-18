import javafx.scene.control.Button;

/**
 * Class to run a bot according to the MinMax Alpha Beta Pruning algorithm
 */
public class MinMax extends BotController{
    private final int defaultMaxDepth = 5; // default maximum depth of child generating
    private int MaxDepth; // leaf's depth
    private int[] selected = new int[2]; // [x,y]

    public MinMax(Button[][] map, boolean playerXTurn, int roundsLeft) {
        this.currentState = map;
        this.playerXTurn = playerXTurn;
        this.roundsLeft = roundsLeft;
        this.MaxDepth = Math.min(defaultMaxDepth, roundsLeft);
        this.selected[0] = -1;
        this.selected[1] = -1;
    }
    /**
     *
     * @param depth
     * @param map
     * @param alpha
     * @param beta
     * @return heuristic value
     */
    private double miniMaxAB(int depth, Button[][] map, double alpha, double beta){
        // leaf node
        if(depth == this.MaxDepth){
            try {
                //
                if (this.roundsLeft == depth) {
                    return ObjectiveFunction(0, map);
                } else if (depth%2 == 1){
                    // Minimum
                    return ObjectiveFunction(-1, map);
                }
                // Maximum
                return ObjectiveFunction(1, map);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        // Odd depth (Minimum condition) (player turn)
        if(depth % 2 == 1){
            // Generating its children
            outerloop:
            for (int i=0; i<map.length; i++){
                for (int j=0; j<map[i].length; j++){
                    if (map[i][j].getText().isEmpty()) {
                        // update map
                        Button[][] childMap = getUpdatedState(map, i, j, true);
                        double eval = miniMaxAB(depth + 1, childMap, alpha, beta);
                        beta = Math.min(beta, eval);

                        // pruning
                        if (beta <= alpha) {
                            break outerloop;
                        }
                    }
                }
            }
            return beta;

        // Even depth (Maximum condition) (bot turn)
        } else{
            // Generating its children
            outerloop:
            for (int i=0; i<map.length; i++){
                for (int j=0; j<map[i].length; j++){
                    if (map[i][j].getText().isEmpty()) {
                        // update map
                        Button[][] childMap = getUpdatedState(map, i, j, false);
                        double eval = miniMaxAB(depth + 1, childMap, alpha, beta);
                        if (eval > alpha) {
                            alpha = eval;
                            // assign selected coordinate
                            if (depth == 0) {
                                this.selected[0] = i;
                                this.selected[1] = j;
                            }
                        }

                        // pruning
                        if (beta <= alpha) {
                            break outerloop;
                        }
                    }
                }
            }
            return alpha;
        }
    }

    @Override
    public int[] run() {
        double superMaxValue = miniMaxAB(0, this.currentState, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return this.selected;
    }


}
