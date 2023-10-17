import javafx.scene.control.Button;

public class MinMax extends BotController{
    public MinMax(Button[][] map) {
        this.currentState = map;
    }
    @Override
    public int[] run() {
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }
}
