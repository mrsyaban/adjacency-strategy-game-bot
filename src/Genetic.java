import javafx.scene.control.Button;

public class Genetic extends BotController{

    @Override
    public int[] run() {
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }


}
