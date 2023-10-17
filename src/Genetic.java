import javafx.scene.control.Button;

public class Genetic extends BotController{

    public Genetic(Button[][] map) {
        this.currentState = map;
    }
    @Override
    public int[] run() {
        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }


}
