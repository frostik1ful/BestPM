package classes.Multiplayer.onlineControllers;

import classes.panes.AlivePane;

import classes.Multiplayer.pockets.OnlinePocket;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class OnlineController {
    Queue<Double> Xqueue = new LinkedBlockingQueue<>();
    Queue<Double> Yqueue = new LinkedBlockingQueue<>();
    Queue<Double> rotateQueue = new LinkedBlockingQueue<>();
    private static int globalShellId;
    private int shellId;
    private boolean isAlive = true;
    int queueSize = 4;
    AlivePane child;

    OnlineController(){
        shellId=globalShellId;
        globalShellId++;

    }

    protected void addSettings(double x,double y,double rotate){
        for (int i = 0;i<queueSize;i++){
            Xqueue.add((x-child.getTranslateX())/queueSize);
            Yqueue.add((y-child.getTranslateY())/queueSize);
            rotateQueue.add(rotate-child.getRotate()/queueSize);
        }

    }
    protected void addSettings(double x,double y){
        for (int i = 0;i<queueSize;i++){
            Xqueue.add((x-child.getTranslateX())/queueSize);
            Yqueue.add((y-child.getTranslateY())/queueSize);
            rotateQueue.add(0d);
        }
    }

    public int getShellId() {
        return shellId;
    }

    public AlivePane getChild() {
        return child;
    }

    public void setChild(AlivePane child) {
        this.child = child;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public abstract void applySettings();
    public abstract void setPocket(OnlinePocket inPocket);
    public abstract void finish();
}
