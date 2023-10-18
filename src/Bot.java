import javafx.scene.control.Button;

public class Bot {
    BotController decisionMaker;

    /**
     * Constructor for Bot
     *
     * @param algoritma
     * @param map
     * @param playerXTurn
     * @param roundsLeft
     */
    public Bot(String algoritma,Button[][] map, boolean playerXTurn, int roundsLeft){
        if(algoritma == "MinMax"){
            this.decisionMaker = new MinMax(map,playerXTurn,roundsLeft);
        }else if(algoritma == "Genetic"){
            this.decisionMaker = new Genetic(map);
        }else if(algoritma == "LocalSearch"){
            this.decisionMaker = new LocalSearch(map,playerXTurn,roundsLeft);
        }
    }

    /**
     * Method to set symbol for bot
     *
     * @param symbol
     */
    public void setSymbol(String symbol){
        if (symbol == "X") {
            decisionMaker.setSymbol("O", "X");
        } else {
            decisionMaker.setSymbol("X", "O");
        }
    }

    /**
     *
     * Method to return next move
     *
     * @return next move bot
     */
    public int[] move(){
        return this.decisionMaker.run();
    }
}
