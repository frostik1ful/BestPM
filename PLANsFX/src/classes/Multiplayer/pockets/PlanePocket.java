package classes.Multiplayer.pockets;

import classes.planes.LeftPlane;
import classes.planes.Plane;
import classes.ROTATION;

public class PlanePocket extends OnlinePocket {
    private ROTATION planeSide;
    private float rotation;
    private boolean humanInPlane = true;
    private byte planeHP = 3;
    private byte planeArmour = 0;
    private boolean isNoseOpen = false;

    public PlanePocket(Plane plane) {
        this.onlinebleParent = plane;
        if (plane instanceof LeftPlane) {
            planeSide = ROTATION.LEFT;
        } else {
            planeSide = ROTATION.RIGHT;
        }

        updatePocket();
    }

    @Override
    public void updatePocket() {
        Plane plane = (Plane) onlinebleParent;
        this.rotation = (float) plane.getRotate();
        this.humanInPlane = plane.isHumanInPlane();
        this.planeHP = (byte) plane.getHealth();
        this.planeArmour = (byte) plane.getArmorLeft();
        this.isNoseOpen = plane.isNoseOpen();
        setX((float) plane.getTranslateX());
        setY((float) plane.getTranslateY());

    }

    public ROTATION getPlaneSide() {
        return planeSide;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    public boolean isHumanInPlane() {
        return humanInPlane;
    }

    public byte getPlaneHP() {
        return planeHP;
    }

    public byte getPlaneArmour() {
        return planeArmour;
    }

    public boolean isNoseOpen() {
        return isNoseOpen;
    }
}
