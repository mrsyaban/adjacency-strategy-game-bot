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
     * S1 - S2 + 2 x C1 + 1, for minimizer turn
     * S1 - S2, base ( terminal state )
     * where S1 represents the number of symbols for player 1,
     * S2 represents the number of symbols for player 2,
     * C1 represents the maximum number of boxes that can be changed by the maximizer,
     * and C2 represents the maximum number of boxes that can be changed by the minimizer
     *
     * @param state ,current state of board games
     * @param turn ,-1 : minimizer, 1 : maximizer, 0 : base
     * @return value of current stateAMO
     *
     */
    protected int ObjectiveFunction(Button[][] state,int turn) throws Exception{
        if(turn == 1){
            return this.countSymbol(false)-this.countSymbol(true)-(2*calculateMaxChangeableBoxes(true))+1;
        }else if(turn == -1){
            return this.countSymbol(false)-this.countSymbol(true)-(2*calculateMaxChangeableBoxes(false))+1;
        }else if(turn == 0) {
            return this.countSymbol(false)-this.countSymbol(true);
        }else{
            throw new Exception("Invalid Input");
        }
    }

    /**
     *
     * Return sum of symbol player
     *
     * @param player ,player: true, bot: false
     * @return sum of symbol player or bot
     *
     */
    protected int countSymbol(Boolean player){
        int symbol = 0;
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                if(player){
                    if(this.currentState[i][j].getText().equals("X")){
                        symbol++;
                    }
                }else{
                    if(this.currentState[i][j].getText().equals("O")){
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
     * @param player ,player: true, bot: false
     * @return maximum number of boxes that can be changed
     *
     */
    protected int calculateMaxChangeableBoxes(Boolean player){
        int MaxChangeable = 0;
        int currentSymbol = countSymbol(player);
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                if (this.currentState[i][j].getText().equals("")){
                    int adj = 0;
                    int startRow, endRow, startColumn, endColumn;

                    if (i - 1 < 0)     // If selected button in first row, no preceding row exists.
                        startRow = i;
                    else               // Otherwise, the preceding row exists for adjacency.
                        startRow = i - 1;

                    if (i + 1 >= ROW)  // If selected button in last row, no subsequent/further row exists.
                        endRow = i;
                    else               // Otherwise, the subsequent row exists for adjacency.
                        endRow = i + 1;

                    if (j - 1 < 0)     // If selected on first column, lower bound of the column has been reached.
                        startColumn = j;
                    else
                        startColumn = j - 1;

                    if (j + 1 >= COL)  // If selected on last column, upper bound of the column has been reached.
                        endColumn = j;
                    else
                        endColumn = j + 1;


                    // Search for adjacency for X's and O's or vice versa, and replace them.
                    for (int x = startRow; x <= endRow; x++) {
                        adj = this.setAdjacency(x, j,adj,player);
                    }

                    for (int y = startColumn; y <= endColumn; y++) {
                        adj = this.setAdjacency(i, y,adj,player);
                    }

                    if(MaxChangeable < adj){
                        MaxChangeable = adj;
                    }
                }
            }
        }
        return MaxChangeable;
    }


    /**
     *
     * check if selected coordinate in map is valid and update map or value
     *
     * @param i
     * @param j ,coordinate of x-axis on selected button on map
     * @param player ,player: true, bot: false
     *
     */
    protected void updateState(int i, int j,Boolean player){

        if (this.currentState[i][j].getText().equals("")){      // if selected value is still empty

            if(player){     // if player update with X
                this.currentState[i][j].setText("X");
            }else{      // Otherwise, it is bot update with O
                this.currentState[i][j].setText("O");
            }

            // Value of indices to control the lower/upper bound of rows and columns
            // in order to change surrounding/adjacent X's and O's only on the game board.
            // Four boundaries:  First & last row and first & last column.

            int startRow, endRow, startColumn, endColumn;

            if (i - 1 < 0)     // If selected button in first row, no preceding row exists.
                startRow = i;
            else               // Otherwise, the preceding row exists for adjacency.
                startRow = i - 1;

            if (i + 1 >= ROW)  // If selected button in last row, no subsequent/further row exists.
                endRow = i;
            else               // Otherwise, the subsequent row exists for adjacency.
                endRow = i + 1;

            if (j - 1 < 0)     // If selected on first column, lower bound of the column has been reached.
                startColumn = j;
            else
                startColumn = j - 1;

            if (j + 1 >= COL)  // If selected on last column, upper bound of the column has been reached.
                endColumn = j;
            else
                endColumn = j + 1;


            // Search for adjacency for X's and O's or vice versa, and replace them.
            for (int x = startRow; x <= endRow; x++) {
                this.setAdjacency(x, j,player);
            }

            for (int y = startColumn; y <= endColumn; y++) {
                this.setAdjacency(i, y,player);
            }
        }


    }

    /**
     *
     * update adjaceny value on map or currentSymbol in that state
     *
     * @param i ,coordinate of y-axis on selected button on map
     * @param j ,coordinate of y-axis on selected button on map
     * @param player ,player: true and bot: false
     */
    private void setAdjacency(int i, int j,Boolean player){
        if (player) {
            if (this.currentState[i][j].getText().equals("O")) {
                this.currentState[i][j].setText("X");
            }
        } else if (this.currentState[i][j].getText().equals("X")) {
            this.currentState[i][j].setText("O");
        }
    }

    private int setAdjacency(int i, int j,int currentSymbol,Boolean player){
        if (player) {
            if (this.currentState[i][j].getText().equals("O")) {
                return currentSymbol+1;
            }
        } else if (this.currentState[i][j].getText().equals("X")) {
            return currentSymbol+1;
        }
        return -1;
    }

}
