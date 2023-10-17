import javafx.scene.Cursor;
import javafx.scene.control.Button;

/**
 * Classes to run a bot according to the MinMax Alpha Beta Pruning, Local Search, and Genetic algorithms
 */
abstract class BotController {
    protected Button[][] currentState;
    protected static final int ROW = 8;
    protected static final int COL = 8;

    public abstract int[] run();

    /**
     *
     * Return value of current state based on objective function
     * S1 - S2 - 2 x C2 + 1, for maximizer turn
     * S1 - S2 + 2 x C2 + 1, for minimizer turn
     * S1 - S2, base ( terminal state )
     * where S1 represents the number of symbols for player 1,
     * S2 represents the number of symbols for player 2,
     * C1 represents the maximum number of boxes that can be changed by the maximizer,
     * and C2 represents the maximum number of boxes that can be changed by the minimizer
     *
     * @param state ,current state of board games
     * @param turn ,-1 : minimizer, 1 : maximizer, 0 : base
     * @return value of current state
     *
     */
    protected int ObjectiveFunction(Button[][] state,int turn){
        return 0;
    }

    /**
     *
     * Return sum of symbol player
     *
     * @param map ,current map of game
     * @param player ,player: true, bot: false
     * @return sum of symbol player or bot
     *
     */
    protected int countSymbol(Button[][] map,Boolean player){
        int symbol = 0;
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                if(player){
                    if(map[i][j].getText().equals("X")){
                        symbol++;
                    }
                }else{
                    if(map[i][j].getText().equals("O")){
                        symbol++;
                    }
                }
            }
        }
        return symbol;
    }

    /**
     *
     * return the maximum number of boxes that can be changed by player or bot
     *
     * @param map ,current map of game
     * @param player ,player: true, bot: false
     * @return maximum number of boxes that can be changed
     *
     */
    protected int calculateMaxChangeableBoxes(Button[][] map,Boolean player){
        return 0;
    }

    /**
     *
     * check if selected coordinate in map is valid and update that map
     *
     * @param coor ,coordinate of selected button on map
     *
     */
    protected void updateMap(int[] coor){

    }

}
