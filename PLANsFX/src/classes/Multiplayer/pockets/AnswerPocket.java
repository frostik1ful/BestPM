package classes.Multiplayer.pockets;

public abstract class AnswerPocket extends OnlinePocket{
    private int answerId;
    private transient boolean  isAlive = true;
    AnswerPocket(int id){
        this.answerId = id;

    }
    public void updatePocket() {
        isAlive = false;
    }
    public boolean isAlive(){
        return isAlive;
    }
    public int getAnswerId(){
        return answerId;
    }
}
