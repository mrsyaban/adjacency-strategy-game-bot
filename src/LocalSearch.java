import javafx.scene.control.Button;

public class LocalSearch extends BotController{
    public LocalSearch(Button[][] map) {
        this.currentState = map;
    }
    @Override
    public int[] run() {
        return new int[0];
    }
}
