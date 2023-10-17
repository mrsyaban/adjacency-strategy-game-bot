import javafx.scene.Cursor;
import javafx.scene.control.Button;

/**
 * Classes to run a bot according to the MinMax Alpha Beta Pruning, Local Search, and Genetic algorithms
 */
public class BotController {
    private Button[][] currentState;
    private Bot bot;
    private static final int ROW = 8;
    private static final int COL = 8;

    public BotController(Button[][] map,Bot bot) {
        this.currentState = map;
        this.bot = bot;
    }

    public int[] MinMax(){
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }

    public  int[] LocalSearch(){
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }

    public  int[] Genetic(){
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }

    /**
     *
     * Return value of current state based on objective function
     * S1 - S2 - 2 x C2 + 1, for maximizer turn
     * S1 - S2 + 2 x C2 + 1, for minimizer turn
     * S1 - S2, base ( terminal state )
     * where s1 represents the number of symbols for player 1,
     * s2 represents the number of symbols for player 2,
     * c1 represents the maximum number of boxes that can be changed by the maximizer,
     * and c2 represents the maximum number of boxes that can be changed by the minimizer
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
     */
    protected int countSymbol(Button[][] map,Boolean player){
        int symbol = 0;
        if(player){
            for (int i = 0; i < ROW; i++){
                for (int j = 0; j < COL; j++) {
                    if (map[i][j].getText().equals("")){

                    }
                }
            }
            return symbol;
        }else{

            return symbol;
        }
    }
}
