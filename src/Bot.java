import javafx.scene.control.Button;

public class Bot {
    BotController decisionMaker;

    public Bot(String algoritma,Button[][] map, boolean playerXTurn, int roundsLeft){
        if(algoritma == "MinMax"){
            this.decisionMaker = new MinMax(map,playerXTurn,roundsLeft);
        }else if(algoritma == "Genetic"){

        }else{
            this.decisionMaker = new LocalSearch(map,playerXTurn,roundsLeft);
        }
    }

    public void setSymbol(String symbol){
        if (symbol == "X") {
            decisionMaker.setSymbol("O", "X");
        } else {
            decisionMaker.setSymbol("X", "O");
        }
    }

    public int[] move(){
        return this.decisionMaker.run();
    }
}
