import javafx.scene.control.Button;
public class LocalSearch extends BotController{
    @Override
    public int[] run(Button[][] map) {
        System.out.println(this.countSymbol(true,map));
        try{
            System.out.println(this.ObjectiveFunction(-1,map));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new int[]{(int) (Math.random()%8), (int) (Math.random()%8)};
    }

}
