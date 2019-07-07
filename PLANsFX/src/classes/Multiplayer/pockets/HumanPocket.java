package classes.Multiplayer.pockets;

import classes.humans.Human;
import classes.ROTATION;
import classes.humans.RightHuman;

public class HumanPocket extends OnlinePocket {
    private boolean parachuteIsOpen;
    private boolean chairFire;
    private boolean dying;
    private byte currentFrameIndex;
    private boolean parachuteRotatedR;
    private boolean parachuteRotatedL;
    private ROTATION rotation = ROTATION.LEFT;

    public HumanPocket(Human human) {
        this.onlinebleParent = human;
        if (human instanceof RightHuman) {
            rotation = ROTATION.RIGHT;
        } else {
            rotation = ROTATION.LEFT;
        }

        updatePocket();
    }


    @Override
    public void updatePocket() {
        //System.out.println("humanID "+getPocketId());
        Human human = (Human) onlinebleParent;
        this.parachuteIsOpen = human.getParachute().isOpen();
        this.chairFire = human.isSmokeSpawning();
        this.dying = human.isImDead();
        if (human.getParachute().isRotatedToLeft()) {
            parachuteRotatedL = true;
            parachuteRotatedR = false;
        } else if (human.getParachute().isRotatedToRight()) {
            parachuteRotatedR = true;
            parachuteRotatedL = false;
        } else {
            parachuteRotatedR = false;
            parachuteRotatedL = false;
        }
        setX((float) human.getTranslateX());
        setY((float) human.getTranslateY());
        setRotation((float) human.getRotate());
        this.currentFrameIndex = (byte) human.getOnlineFrameIndex();
    }


    public boolean isParachuteIsOpen() {
        return parachuteIsOpen;
    }

    public boolean isChairFire() {
        return chairFire;
    }

    public boolean isDying() {
        return dying;
    }

    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    public boolean isParachuteRotatedR() {
        return parachuteRotatedR;
    }

    public boolean isParachuteRotatedL() {
        return parachuteRotatedL;
    }


    public ROTATION getHumanSide() {
        return rotation;
    }
}
