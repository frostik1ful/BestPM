package classes.Multiplayer.pockets;


import interfaces.Onlineble;

import java.io.Serializable;

public abstract class OnlinePocket implements Serializable {
    private static int globalPocketId = 0;
    private int pocketId;
    private boolean isNew = true;
    transient Onlineble onlinebleParent;
    private float x;
    private float y;
    private float rotation;

    public OnlinePocket() {
        pocketId = globalPocketId;
        globalPocketId++;

    }

    public boolean isAlive() {
        return onlinebleParent.isParentAlive();
    }

    public abstract void updatePocket();

    public int getPocketId() {
        return pocketId;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Onlineble getOnlinebleParent() {
        return onlinebleParent;
    }
}
