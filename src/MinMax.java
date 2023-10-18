import javafx.scene.control.Button;

public class MinMax extends BotController{
    private int MaxDepth = 5;
    private int[] selected = new int[2]; //[x,y]

    private Button[][] getUpdatedMap(Button[][] inputMap, int x, int y, String symbol) {
        return inputMap;
    }

    private double miniMaxAB(int depth, Button[][] map, double alpha, double beta){
        // leaf node
        if(depth == MaxDepth){
            try {
                return ObjectiveFunction(-1, map);
            } catch (Exception e){
                return 0.0;
            }
        }

        // Odd depth (Minimum condition) (player turn)
        if(depth % 2 == 1){
//            double minEval = Double.NEGATIVE_INFINITY;
            for (int i=0; i<map.length; i++){
                for (int j=0; j<map[i].length; j++){
                    // update map
                    map = getUpdatedMap(map, i, j, "X");

                    double eval = miniMaxAB(depth+1, map, alpha, beta);
//                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if(beta <= alpha){
                        break;
                    }
                }
            }
//            return minEval;
            return beta;

        // Even depth (Maximum condition) (bot turn)
        } else{
//            double maxEval = Double.POSITIVE_INFINITY;
            for (int i=0; i<map.length; i++){
                for (int j=0; j<map[i].length; j++){
                    // update map
                    map = getUpdatedMap(map, i, j, "O");
                    double eval = miniMaxAB(depth+1, map, alpha, beta);
                    if (eval > alpha) {
                        alpha = eval;
                        // its always
                        if (depth==0){
                            selected[0] = i;
                            selected[1] = j;
                        }
                    }
                    if(beta <= alpha){
                        break;
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
