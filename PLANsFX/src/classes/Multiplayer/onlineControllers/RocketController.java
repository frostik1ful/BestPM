package classes.Multiplayer.onlineControllers;

import classes.Multiplayer.pockets.OnlinePocket;
import classes.Multiplayer.pockets.RocketPocket;
import classes.weapons.Rocket;
import classes.Multiplayer.onlineUnits.OnlineRocket;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RocketController extends OnlineController {
    private Rocket rocket;
    private RocketPocket pocket;
    private boolean effectsStarted = false;


    private Queue<Float> rotations = new LinkedBlockingQueue<>();

    public RocketController(double x, double y, double rotate){

        //setTranslateXY(x,y);
        rocket = new OnlineRocket(this,x,y);
        rocket.blowUpFunc = ()->{};
        setChild(rocket);
    }
    @Override
    public void setPocket(OnlinePocket inPocket) {
        this.pocket = (RocketPocket) inPocket;
        if (pocket.isEffectsStarted()&&!effectsStarted){
            effectsStarted=true;
            this.rocket.startEffects();
        }
        else if (!pocket.isEffectsStarted()&&effectsStarted){
            effectsStarted=false;
            this.rocket.stopEffects();
        }
        addSettings(pocket.getX(),pocket.getY(),pocket.getRotation());
        rotations.clear();

        rotations.addAll(pocket.getRotations());
        //System.out.println("isRocketAlive "+rocket.isAlive());

    }
    @Override
    public void applySettings() {
        if (!rocket.isAlive()){
            //finish();
            setAlive(false);

        }
        else {
            rocket.setRotate(rotations.remove());


            rocket.changeXby(Xqueue.poll());
            rocket.changeYby(Yqueue.poll());
            //setRotate(rotateQueue.poll());

            //System.out.println("rocket rotate "+getRotate());
        }


    }



    @Override
    public void finish() {
        //rocket.setTranslateXY(getTranslateX(),getTranslateY());
        //rocket.changeLocalPositionToScene();

        rocket.finish();
        setAlive(false);

    }

    public RocketPocket getPocket() {
        return pocket;
    }

    public Rocket getRocket() {
        return rocket;
    }
}
