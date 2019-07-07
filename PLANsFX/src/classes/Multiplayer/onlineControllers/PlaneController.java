package classes.Multiplayer.onlineControllers;

import classes.Multiplayer.pockets.OnlinePocket;
import classes.Multiplayer.pockets.PlanePocket;
import classes.Multiplayer.onlineUnits.OnlineLeftPlane;
import classes.planes.Plane;
import classes.ROTATION;
import classes.Multiplayer.onlineUnits.OnlineRightPlane;


public class PlaneController extends OnlineController {
    private Plane plane;
    private PlanePocket pocket;
    private double rotation;
    private boolean humanInPlane = true;
    private boolean isNoseAlreadyOpen = false;
    private boolean isNoseOpen = false;

    public PlaneController(double x, double y, double rotate, ROTATION planeSide) {
        if (planeSide == ROTATION.LEFT) {
            plane = new OnlineLeftPlane(x, y, this);
        } else {
            plane = new OnlineRightPlane(x, y, this);
        }
        setChild(plane);

        plane.setRotate(rotate);

    }

    @Override
    public void setPocket(OnlinePocket inPocket) {
        if (plane.isAlive()) {
            this.pocket = (PlanePocket) inPocket;
            this.rotation = pocket.getRotation();
            this.humanInPlane = pocket.isHumanInPlane();
            this.isNoseOpen = pocket.isNoseOpen();

            plane.setRotate(rotation);
            plane.setHealthFromController(pocket.getPlaneHP());
            plane.setArmorLeft(pocket.getPlaneArmour());


            if (!humanInPlane) {
                plane.setHumanInPlane(humanInPlane);
                plane.getHuman().setOpacity(0);
            }
            if (!isNoseAlreadyOpen && isNoseOpen) {
                plane.openCloseNose();
                isNoseAlreadyOpen = true;
            } else if (!isNoseOpen) {
                isNoseAlreadyOpen = false;
            }
            if (Math.abs(plane.getTranslateX() - pocket.getX()) > 200) {
                addSettings(pocket.getX(), pocket.getY(), pocket.getRotation());
            } else {
                super.addSettings(pocket.getX(), pocket.getY(), pocket.getRotation());
            }

        }

    }

    @Override
    protected void addSettings(double x, double y, double rotate) {
        if (plane.getTranslateX() > pocket.getX()) {
            Xqueue.add(-plane.getTranslateX());
        } else {
            Xqueue.add((double) pocket.getX() - plane.getTranslateX());
        }
        Xqueue.add(0d);
        Xqueue.add(0d);
        Xqueue.add(0d);
        for (int i = 0; i < queueSize; i++) {
            Yqueue.add((y - child.getTranslateY()) / queueSize);
            rotateQueue.add(rotate - child.getRotate() / queueSize);
        }

    }

    @Override
    public void applySettings() {
        plane.changeXby(Xqueue.poll());
        plane.changeYby(Yqueue.poll());

    }


    @Override
    public void finish() {
        plane.finish();


    }

    public int getPocketId() {
        return pocket.getPocketId();
    }
}
