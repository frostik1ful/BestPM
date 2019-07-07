package classes.Multiplayer.pockets;

public class PointsPocket extends AnswerPocket {

    private byte leftPoints, rightPoints;

    public PointsPocket(int id, int leftPoints, int rightPoints) {
        super(id);
        this.leftPoints = (byte) leftPoints;
        this.rightPoints = (byte) rightPoints;
    }

    public int getLeftPoints() {
        return leftPoints;
    }

    public int getRightPoints() {
        return rightPoints;
    }
}
