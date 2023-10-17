import javafx.scene.control.Button;

public class MinMax extends BotController{
    public MinMax(Button[][] map) {
        this.currentState = map;
    }
    @Override
    public int[] run() {
        return new int[0];
    }
}
