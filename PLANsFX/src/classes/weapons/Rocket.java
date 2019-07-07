package classes.weapons;

import interfaces.Colliding;
import interfaces.Onlineble;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import classes.Multiplayer.PocketManager;
import classes.Multiplayer.pockets.OnlinePocket;
import classes.Multiplayer.pockets.RocketPocket;
import classes.effects.boom.Boom;
import classes.effects.RocketSmoke;
import classes.planes.Plane;
import interfaces.Function;
import classes.utils.HitBox;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Rocket extends Weapon implements Colliding, Onlineble {
    private Image fires[] = {new Image("resources/images/rocket/fire1.png"), new Image("resources/images/rocket/fire2.png"), new Image("resources/images/rocket/fire3.png")};
    private ImageView fireView = new ImageView(fires[0]);
    private Plane target;
    private HitBox hitBox;
    private float resolution = 1;
    private Timeline effectsLine;
    private Timeline moveLine = new Timeline(60);
    private PathTransition trs = new PathTransition();

    private double speed = 5;//3.5

    private boolean isStarted = false;

    private Circle smokePoint = new Circle();
    private Line startLine;
    private int fireNum = 1;
    private int minRadius = 20;

    private double xSpeed = 6;
    private double ySpeed;
    private double oldRotate = 90;
    private RocketPocket pocket;

    //public List<Double> rotations = new LinkedList<>();
    private Queue<Float> rotations = new LinkedBlockingQueue<>();
    public Function blowUpFunc;

    private Function ifTargetIsDeadFunction;

    private Function MainFunction;
    private Function pursuitTargetFunction;
    private Function stopFallDownFunction;

    public Rocket() {

        setTranslateXY(0, -200);
        setWeapontype(WEAPON_TYPE.ROCKET);
        setDamage(5);
        hitBox = new HitBox(this, HitBox.HitBoxType.ROCKET);
        hitBox.setSize(54, 9);
        hitBox.setTranslateY(5);
        ySpeed = 0.1;
        smokePoint.setOpacity(0);
        smokePoint.setRadius(5);
        smokePoint.setCenterX(0);
        smokePoint.setCenterY(10);
        setImageInView(new Image("/resources/images/rocket/rocket.png"));
        getView().setFitWidth(54 * resolution);
        getView().setFitHeight(19 * resolution);
        getChildren().addAll(getView(), smokePoint, hitBox);
        fireView.setTranslateX(-8);
        fireView.setTranslateY(5);
        fireView.setOpacity(0);
        getChildren().add(fireView);
        effectsLine = new Timeline(60);
        target = PocketManager.enemyPlane;
        setMaxYPower(4);
        setFunctions();
    }

    private void setFunctions() {
        pursuitTargetFunction = () -> {
            if (target.isAlive()){
                pursuitTarget();
            }
            else {
                MainFunction = ifTargetIsDeadFunction;
            }
        };

        stopFallDownFunction = ()->{
            target = PocketManager.enemyPlane;
            if (target.isAlive()){
                stopFallingDown();
                MainFunction = pursuitTargetFunction;
            }

        };

        ifTargetIsDeadFunction = () ->{
            dropDown();
            MainFunction = stopFallDownFunction;
        };

        MainFunction = pursuitTargetFunction;

    }

    protected void stop() {
        setAlive(false);
        trs.stop();
        moveLine.stop();
        effectsLine.stop();
        stopFallingDown();
    }

    private void blowUp() {
        hitBox.setAlive(false);
        effects.add(new Boom(getTranslateX(), getTranslateY()));

    }


    private void pursuitTarget() {
        double targetX = target.getTranslateX() - getTranslateX();
        double targetY = target.getTranslateY() - getTranslateY();

        double rotation = Math.atan2(targetY, targetX) * 180 / Math.PI; //Rotation to target

        double rotate = getRotate();
        boolean up = true;

        if (Math.abs(rotation - rotate) > 180) {
            if (rotation < rotate) {
                rotate = (360 - rotate) * -1;
            } else if (rotation > rotate) {
                rotate = (360 + rotate);

            }

        }
        if (rotation < rotate) {
            rotate -= Math.abs(rotate - rotation) / minRadius;

        } else if (rotation > rotate) {
            rotate += Math.abs(rotation - rotate) / minRadius;

        }

        oldRotate = Math.abs(rotate);
        if (oldRotate > 180) {
            oldRotate -= (oldRotate - 180) * 2;
        }
        if (rotate < -180 && rotation > -180 || rotate > 0 && rotate < 180 && rotation > 0 || rotate > 0 && rotate < 180 && rotation < 0 && rotate > rotation) {
            up = false;
        } else if (rotate > -180 && rotate < 0 && rotation > -180) {
            up = true;
        }


        xSpeed = speed * (90 - Math.abs(oldRotate)) / 90;
        if (up) {
            ySpeed = -speed + Math.abs(xSpeed);
        } else {
            ySpeed = speed - Math.abs(xSpeed);
        }

        setRotate(rotate);
        changeXby(xSpeed);
        changeYby(ySpeed);

        rotations.remove();
        rotations.add((float) rotate);


    }


    private void toStartPosition() {

        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.millis(650));
        trs.setDuration(Duration.seconds(0.5));
        trs.setPath(startLine);
        trs.setInterpolator(Interpolator.LINEAR);
        trs.setNode(this);
        trs.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        trs.setOnFinished(event -> {
            setDamage(5);
            PT.play();
            dropDown();
        });
        moveLine.setCycleCount(Animation.INDEFINITE);
        moveLine.getKeyFrames().add(new KeyFrame(Duration.millis(16), event1 -> MainFunction.run()));
        PT.setOnFinished(event -> {
            hitBoxesToAdd.add(hitBox);
            stopFallingDown();
            isStarted = true;
            moveLine.play();
            startEffects();

        });
        trs.play();


    }

    public void setSettings(double rotation) {
        setRotate(rotation - 90);
        rotations.add((float) getRotate());
        rotations.add((float) getRotate());
        rotations.add((float) getRotate());
        rotations.add((float) getRotate());
        /*rotations.add((float) getRotate());
        rotations.add((float) getRotate());
        rotations.add((float) getRotate());
        rotations.add((float) getRotate());*/
        setYPowerStep(0.1);
        setDamage(0);
        createPocket();

    }
    @Override
    public void start() {
        toStartPosition();


    }

    public void startEffects() {
        isStarted = true;
        effectsLine.setCycleCount(Animation.INDEFINITE);
        effectsLine.getKeyFrames().add(new KeyFrame(Duration.millis(50), event -> {
            fireView.setOpacity(1);
            fireView.setImage(fires[fireNum]);
            if (fireNum < 2) {
                fireNum++;
            } else fireNum = 0;

            Bounds bound = smokePoint.localToScene(smokePoint.getBoundsInLocal());

            double x = (bound.getMinX() + (bound.getMaxX() - bound.getMinX()) / 2);

            double y = (bound.getMinY() + (bound.getMaxY() - bound.getMinY()) / 2);
            y -= 10;
            x -= 10;
            RocketSmoke RS = new RocketSmoke(x, y);
            effects.add(RS);
        }));
        effectsLine.play();
    }

    public void stopEffects() {
        effectsLine.stop();
        fireView.setOpacity(0);
    }
    @Override
    public void dropDown(){
        setYPowerStep(0.1);
        setDropYPower(0);
        super.dropDown();
    }

    @Override
    public void setLine(Line line) {
        this.startLine = line;
    }

    @Override
    public Line getLine() {
        return this.startLine;
    }

    @Override
    public void setShotGunLines(Line... lines) {

    }


    @Override
    public Bullet[] getBullets() {
        return new Bullet[0];
    }


    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void addCollision(HitBox hitBox) {
        switch (hitBox.getHitBoxType()) {
            case PARACHUTE:
                break;
            default:
                blowUp();
                stop();

                break;
//            case PARACHUTE:
//                break;
//                default:
//                    if (isStarted){
//                        blowUpFunc.run();
//                        stop();
//                    }
//                    break;
////            case PARACHUTE:
////            case HUMAN:
////            case PLANE_BODY:
////            case PLANE_NOSE:
////                if (isStarted){
////                    //blowUp();
////                    blowUpFunc.run();
////                    stop();
////                }
////
////
////                break;
////            default:
////                if (isStarted) {
////                    //blowUp();
////                    blowUpFunc.run();
////                    stop();
////
////
////                }
////                break;
        }

    }

    @Override
    public double getCollisionDamage() {
        if (isStarted)
            return getDamage();
        else return 0;
    }


    @Override
    public void finish() {
        stop();
        blowUp();
    }

    //@Override
    public boolean isEffectsStarted() {
        return isStarted;
    }

    public OnlinePocket getPocket() {
        return pocket;
    }

    @Override
    public boolean isParentAlive() {
        return isAlive();
    }

    @Override
    public void createPocket() {
        pocket = new RocketPocket(this);
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }

    public Queue<Float> getRotations() {
        return rotations;
    }
}
