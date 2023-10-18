import javafx.scene.control.Button;

/**
 * Parent Class to run a bot according to the MinMax Alpha Beta Pruning, Local Search, and Genetic algorithms
 */
abstract class BotController {
    protected Button[][] currentState;
    protected boolean playerXTurn;
    protected int roundsLeft;
    protected static final int ROW = 8;
    protected static final int COL = 8;


    /**
     *
     * produce next move position for bot based on algorithm that it uses
     * @return next move position
     */
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
     * @param turn ,-1 : minimizer (player), 1 : maximizer (bot), 0 : base
     * @param map ,map on that current state
     * @return value of current stateAMO
     *
     */

    protected int ObjectiveFunction(int turn,Button[][] map) throws Exception{
        if(turn == 1){
            return this.countSymbol(false,map)-this.countSymbol(true,map)-(2*calculateMaxChangeableBoxes(map,true))+1;
        }else if(turn == -1){
            return this.countSymbol(false,map)-this.countSymbol(true,map)+(2*calculateMaxChangeableBoxes(map,false))+1;
        }else if(turn == 0) {
            return this.countSymbol(false,map)-this.countSymbol(true,map);
        }else{
            throw new Exception("Invalid Input");
        }
    }

    /**
     *
     * Return sum of symbol player
     *
     * @param player ,player: true, bot: false
     * @param map, map in that current state
     * @return sum of symbol player or bot
     *
     */
    protected int countSymbol(Boolean player,Button[][] map){
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
     * if player == true then it will return maximum number of bot that can change player symbol
     *
     * @param map, map on that state
     * @param player ,player: true, bot: false
     * @return maximum number of boxes that can be changed
     *
     */
    protected int calculateMaxChangeableBoxes(Button[][] map,Boolean player){
        int MaxChangeable = 0;
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                int adj = 0; // adjacent from that position
                if (map[i][j].getText().equals("")){
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
                        adj = this.setAdjacency(x, j,adj,player,map);
                    }

                    for (int y = startColumn; y <= endColumn; y++) {
                        adj = this.setAdjacency(i, y,adj,player,map);
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
     * @param i ,coordinate of y-axis on selected button on map
     * @param j ,coordinate of x-axis on selected button on map
     * @param player ,player: true, bot: false
     * @param map, map in that current state
     *
     */
    protected void updateState(int i, int j,Boolean player,Button[][] map){

        if (map[i][j].getText().equals("")){      // if selected value is still empty

            if(player){     // if player update with X
                map[i][j].setText("X");
            }else{      // Otherwise, it is bot update with O
                map[i][j].setText("O");
            }

            // Value of indices to control the lower/upper bound of rows and columns
            // in order to change surrounding/adjacent X's and O's only on the game board.
            // Four boundaries:  First & last row and first & last column.

            int startRow, endRow, startColumn, endColumn;

            // Similar boundary checks as in the original function
            startRow = (i - 1 < 0) ? i : i - 1;
            endRow = (i + 1 >= ROW) ? i : i + 1;
            startColumn = (j - 1 < 0) ? j : j - 1;
            endColumn = (j + 1 >= COL) ? j : j + 1;



            // Search for adjacency for X's and O's or vice versa, and replace them.
            for (int x = startRow; x <= endRow; x++) {
                this.setAdjacency(x, j,player,map);
            }

            for (int y = startColumn; y <= endColumn; y++) {
                this.setAdjacency(i, y,player,map);
            }
        }


    }
    /**
     *
     * return updated adjaceny value on currentSymbol in that state after selected value in i,j
     *
     * @param i ,coordinate of y-axis on selected button on map
     * @param j ,coordinate of y-axis on selected button on map
     * @param player ,player: true and bot: false
     * @param currentState, map in that current state
     *
     */
    public Button[][] getUpdatedState(Button[][] currentState, int i, int j, Boolean player) {
        Button[][] newState = copy(currentState); // Create a deep copy of the currentState

        this.updateState(i,j,player,newState);

        return newState;
    }

    /**
     *
     * update adjaceny value on map or currentSymbol in that state
     *
     * @param i ,coordinate of y-axis on selected button on map
     * @param j ,coordinate of y-axis on selected button on map
     * @param player ,player: true and bot: false
     * @param map, map in that current state
     *
     */
    private void setAdjacency(int i, int j,Boolean player,Button[][] map){
        if (player) {
            if (map[i][j].getText().equals("O")) {
                map[i][j].setText("X");
            }
        } else if (map[i][j].getText().equals("X")) {
            map[i][j].setText("O");
        }
    }

    private int setAdjacency(int i, int j,int currentSymbol,Boolean player,Button[][] map){
        if (player) {
            if (map[i][j].getText().equals("O")) {
                return currentSymbol+1;
            }
        } else if (map[i][j].getText().equals("X")) {
            return currentSymbol+1;
        }
        return currentSymbol;
    }

    /**
     * create new object and copy current map to that object
     *
     * @param map , map on that curren state
     * @return copied map
     *
     */

    protected Button[][] copy(Button[][] map){
        Button[][] copiedMap = new Button[ROW][COL];

        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++) {
                copiedMap[i][j] = new Button();
                copiedMap[i][j].setText(map[i][j].getText());
            }
        }

        return copiedMap;
    }

}
