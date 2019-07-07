package classes.Multiplayer.pockets;

public class HumanAnswerPocket extends AnswerPocket {
    private boolean isHumanAlive;
    private boolean isParachuteAlive;

    public HumanAnswerPocket(int id, boolean isHumanAlive, boolean isParachuteAlive) {
        super(id);
        this.isHumanAlive = isHumanAlive;
        this.isParachuteAlive = isParachuteAlive;
    }

    public boolean isHumanAlive() {
        return isHumanAlive;
    }

    public boolean isParachuteAlive() {
        return isParachuteAlive;
    }
}
