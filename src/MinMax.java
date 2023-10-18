import javafx.scene.control.Button;

public class MinMax extends BotController{

    @Override
    public int[] run(Button[][] map) {
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }
}
