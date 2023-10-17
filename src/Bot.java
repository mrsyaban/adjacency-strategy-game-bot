import javafx.scene.control.Button;

public class Bot {
    public int[] move(Button[][] map){
        BotController decissionMaker = new LocalSearch(map);
        return decissionMaker.run();
    }
}
