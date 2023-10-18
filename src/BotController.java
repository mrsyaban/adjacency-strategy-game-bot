import javafx.scene.Cursor;
import javafx.scene.control.Button;
import java.util.Arrays;

/**
 * Classes to run a bot according to the MinMax Alpha Beta Pruning, Local Search, and Genetic algorithms
 */
abstract class BotController {
    protected Button[][] currentState;
    protected boolean playerXTurn;
    protected int roundsLeft;
    protected static final int ROW = 8;
    protected static final int COL = 8;

    public abstract int[] run();


    private Button[][] copyState(Button[][] original) {
        Button[][] copy = new Button[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new Button[original[i].length];
            for (int j = 0; j < original[i].length; j++) {
                // Creates a new Button with the text of the original Button
                copy[i][j] = new Button(original[i][j].getText());
            }
        }
        return copy;
    }

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
     * @return value of current state
     *
     */
    protected int ObjectiveFunction(Button[][] state, int turn) throws Exception{
        if(turn == 1){
            return this.countSymbol(state,false)-this.countSymbol(state, true)-(2*calculateMaxChangeableBoxes(state, true))+1;
        }else if(turn == -1){
            return this.countSymbol(state,false)-this.countSymbol(state, true)-(2*calculateMaxChangeableBoxes(state, false))+1;
        }else if(turn == 0) {
            return this.countSymbol(state,false)-this.countSymbol(state, true);
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
    protected int countSymbol(Button[][] state, Boolean player){
        int symbol = 0;
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                if(player){
                    if(state[i][j].getText().equals("X")){
                        symbol++;
                    }
                }else{
                    if(state[i][j].getText().equals("O")){
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
    protected int calculateMaxChangeableBoxes(Button[][] state, Boolean player){
        int MaxChangeable = 0;
        String enemy = player ? "O" : "X";
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                int adj = 0;
                if (state[i][j].getText().equals("")){
                    if (i > 0 && state[i-1][j].getText().equals(enemy)) {adj++;}
                    if (j > 0 && state[i][j-1].getText().equals(enemy)) {adj++;}
                    if (i < ROW-1 && state[i+1][j].getText().equals(enemy)) {adj++;}
                    if (j < COL-1 && state[i][j+1].getText().equals(enemy)) {adj++;}
//                    int startRow, endRow, startColumn, endColumn;
//
//                    if (i - 1 < 0)     // If selected button in first row, no preceding row exists.
//                        startRow = i;
//                    else               // Otherwise, the preceding row exists for adjacency.
//                        startRow = i - 1;
//
//                    if (i + 1 >= ROW)  // If selected button in last row, no subsequent/further row exists.
//                        endRow = i;
//                    else               // Otherwise, the subsequent row exists for adjacency.
//                        endRow = i + 1;
//
//                    if (j - 1 < 0)     // If selected on first column, lower bound of the column has been reached.
//                        startColumn = j;
//                    else
//                        startColumn = j - 1;
//
//                    if (j + 1 >= COL)  // If selected on last column, upper bound of the column has been reached.
//                        endColumn = j;
//                    else
//                        endColumn = j + 1;
//
//
//                    // Search for adjacency for X's and O's or vice versa, and replace them.
////                    for (int x = startRow; x <= endRow; x++) {
////                        this.setAdjacency(x, j,adj,player);
////                    }
////
////                    for (int y = startColumn; y <= endColumn; y++) {
////                        this.setAdjacency(i, y,adj,player);
////                    }
//
//                    for (int x = startRow; x <= endRow; x++) {
//                        for (int y = startColumn; y <= endColumn; y++) {
//                            if (x != 0 && y != 0 &&
//                                    !this.currentState[x][y].getText().equals(currentSymbol)) {
//                                adj++;
//                            }
//                        }
//                    }
                }
                if(MaxChangeable < adj){
                    MaxChangeable = adj;
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

    public Button[][] getUpdatedState(Button[][] currentState, int i, int j, Boolean player) {
        Button[][] newState = copyState(currentState); // Create a deep copy of the currentState

        if (newState[i][j].getText().equals("")) { // if selected value is still empty
            if (player) { // if player update with X
                newState[i][j].setText("X");
            } else { // Otherwise, it's the bot. Update with O.
                newState[i][j].setText("O");
            }


            int startRow, endRow, startColumn, endColumn;

            // Similar boundary checks as in the original function
            startRow = (i - 1 < 0) ? i : i - 1;
            endRow = (i + 1 >= ROW) ? i : i + 1;
            startColumn = (j - 1 < 0) ? j : j - 1;
            endColumn = (j + 1 >= COL) ? j : j + 1;

            for (int x = startRow; x <= endRow; x++) {
                newState = setAdjacencyState(newState, x, j, player);
            }

            for (int y = startColumn; y <= endColumn; y++) {
                newState = setAdjacencyState(newState, i, y, player);
            }
        }

        return newState;
    }

    private Button[][] setAdjacencyState(Button[][] state, int i, int j,Boolean player){
        if (player) {
            if (state[i][j].getText().equals("O")) {
                state[i][j].setText("X");
            }
        } else if (state[i][j].getText().equals("X")) {
            state[i][j].setText("O");
        }
        return state;
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

    private void setAdjacency(Button[][] state, int i, int j, Boolean player) {
        if (player) {
            if (state[i][j].getText().equals("O")) {
                state[i][j].setText("X");
            }
        } else if (state[i][j].getText().equals("X")) {
            state[i][j].setText("O");
        }
    }

    private void setAdjacency(int i, int j,int currentSymbol,Boolean player){
        if (player) {
            if (this.currentState[i][j].getText().equals("O")) {
                currentSymbol++;
            }
        } else if (this.currentState[i][j].getText().equals("X")) {
            currentSymbol++;
        }
    }

}
