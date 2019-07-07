package classes.Multiplayer.pockets;

public class PlaneAnswerPocket extends AnswerPocket {
    private byte health, armor;


    public PlaneAnswerPocket(int id, byte health, byte armor) {
        super(id);
        this.health = health;
        this.armor = armor;
    }

    public byte getHealth() {
        return health;
    }

    public byte getArmor() {
        return armor;
    }
}
