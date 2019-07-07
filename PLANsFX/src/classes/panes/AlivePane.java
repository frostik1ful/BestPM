package classes.panes;


public abstract class AlivePane extends AdvancedPane {
    private boolean isAlive = true;

    public AlivePane(double x, double y) {
        super(x, y);
    }

    public AlivePane() {

    }

    public boolean isAlive() {
        return isAlive;
    }


    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
