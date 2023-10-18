import javafx.scene.control.Button;

public class Bot {
    BotController decisionMaker;
    public int[] move(Button[][] map, boolean playerXTurn, int roundsLeft){
        this.decisionMaker = new MinMax(map, playerXTurn, roundsLeft);
        return this.decisionMaker.run();
    }
}
