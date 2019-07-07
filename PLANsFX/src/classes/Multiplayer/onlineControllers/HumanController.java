package classes.Multiplayer.onlineControllers;

import classes.humans.Human;
import classes.Multiplayer.pockets.HumanPocket;
import classes.Multiplayer.pockets.OnlinePocket;
import classes.ROTATION;
import classes.Multiplayer.onlineUnits.OnlineLeftHuman;
import classes.Multiplayer.onlineUnits.OnlineRightHuman;

public class HumanController extends OnlineController {
    private Human human;
    private HumanPocket pocket;
    private boolean parachuteOpen;
    private boolean imDying;

    public HumanController(double x, double y, ROTATION rotation) {

        if (rotation == ROTATION.LEFT) {
            human = new OnlineLeftHuman(x, y, this);
        } else {
            human = new OnlineRightHuman(x, y, this);
        }
        setChild(human);

    }


    @Override
    public void setPocket(OnlinePocket inPocket) {
        this.pocket = (HumanPocket) inPocket;
        if (pocket.isDying()) {
            if (!imDying) {
                human.startDeadAnimation();
                imDying = true;
            }

            return;
        }

        if (pocket.isChairFire()) {
            if (!human.isSmokeSpawning()) {
                human.startChairFire();
            }
        } else {
            human.stopEffects();
        }

        if (parachuteOpen) {
            if (pocket.isParachuteRotatedL()) {
                human.getParachute().rotateToCenter();
                human.getParachute().rotateToLeft();
            } else if (pocket.isParachuteRotatedR()) {
                human.getParachute().rotateToCenter();
                human.getParachute().rotateToRight();
            } else {
                human.getParachute().rotateToCenter();
            }
        }
        if (pocket.isParachuteIsOpen() && !parachuteOpen) {
            human.openParachute();
            parachuteOpen = true;
        } else if (parachuteOpen && !pocket.isParachuteIsOpen()) {
            parachuteOpen = false;
            human.removeParachute();
        }
        //setPrefSize(human.getPrefHeight(),human.getPrefWidth());

        human.setFrame(pocket.getCurrentFrameIndex());
        if (Math.abs(human.getTranslateX()-pocket.getX())>200){
            addSettings(pocket.getX(),pocket.getY(),pocket.getRotation());
        }
        else {
            super.addSettings(pocket.getX(),pocket.getY(),pocket.getRotation());
        }
    }

    @Override
    protected void addSettings(double x,double y,double rotate){
        if (human.getTranslateX()>pocket.getX()) {
            Xqueue.add(-human.getTranslateX());
        }
        else {
            Xqueue.add((double)pocket.getX()-human.getTranslateX());
        }
        Xqueue.add(0d);
        Xqueue.add(0d);
        Xqueue.add(0d);
        for (int i = 0;i<queueSize;i++){
            Yqueue.add((y-child.getTranslateY())/queueSize);
            rotateQueue.add(rotate-child.getRotate()/queueSize);
        }

    }

    @Override
    public void applySettings() {
        if (imDying)
            return;
        human.changeXby(Xqueue.poll());
        human.changeYby(Yqueue.poll());
        human.setRotate(rotateQueue.poll());
    }


    public HumanPocket getPocket() {
        return pocket;
    }

    public int getPocketId() {
        return pocket.getPocketId();
    }

    public void finish() {
        setAlive(false);
        human.finish();
    }
}
