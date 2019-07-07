package classes.Multiplayer.pockets;

import classes.weapons.Rocket;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RocketPocket extends OnlinePocket {
    private boolean fireStarted = false;
    private Queue<Float> rotations = new LinkedBlockingQueue<>();

    public RocketPocket(Rocket homingRocket) {
        this.onlinebleParent = homingRocket;
        updatePocket();
    }

    @Override
    public void updatePocket() {
        Rocket rocket = (Rocket) onlinebleParent;
        rotations.clear();
        rotations.addAll(rocket.getRotations());


        //setRotation(rocket.getRotate());
        setX((float) rocket.getTranslateX());
        setY((float) rocket.getTranslateY());
        this.fireStarted = rocket.isEffectsStarted();
    }

    public boolean isEffectsStarted() {
        return fireStarted;
    }

    public Queue<Float> getRotations() {
        return rotations;
    }
}
