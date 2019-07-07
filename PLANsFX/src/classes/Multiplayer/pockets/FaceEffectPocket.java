package classes.Multiplayer.pockets;

public class FaceEffectPocket extends AnswerPocket {
    private byte textNumber;
    private byte faceSide;

    public FaceEffectPocket(int id, byte textNumber, byte faceSide) {
        super(id);
        this.textNumber = textNumber;
        this.faceSide = faceSide;
    }

    public byte getTextNumber() {
        return textNumber;
    }

    public byte getFaceSide() {
        return faceSide;
    }
}
