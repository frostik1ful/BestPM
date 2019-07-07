package classes.humans;

import interfaces.Colliding;
import interfaces.Onlineble;
import interfaces.Parachuting;
import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import classes.*;
import classes.Multiplayer.PocketManager;
import classes.Multiplayer.pockets.HumanPocket;
import classes.effects.SmokeEffect;
import classes.gameUnits.AirDrop;
import classes.gameUnits.Parachute;
import classes.panes.FallingDownPane;
import classes.planes.LeftPlane;
import classes.planes.Plane;
import classes.utils.Bonus;
import classes.utils.HitBox;
import classes.utils.SmokeSpawner;

import java.util.ArrayList;
import java.util.List;

public abstract class Human extends FallingDownPane implements Colliding, Parachuting, Onlineble {
    List<Bonus> bonuses = new ArrayList<>();


    private ROTATION rotation = ROTATION.LEFT;

    private List<Rectangle2D> framesCoordinates = new ArrayList<>();
    int currentFrameIndex = 15;
    private int onlineFrameIndex = currentFrameIndex;

    private int bound1 = 0;
    private int bound2 = 0;


    protected HitBox hitBox;
    private PauseTransition PT = new PauseTransition();
    private Timeline TL = new Timeline();
    private SmokeSpawner smokeSpawner;

    private Parachute parachute;
    Plane planeParent;
    private boolean imDead;
    private boolean smokeSpawning;


    Image humanImg;
    private ImageView flamesView = new ImageView(new Image("/resources/images/chairFlames.png"));
    private Rectangle2D flamesCoordinates[] = {new Rectangle2D(0, 0, 13, 14),
            new Rectangle2D(14, 0, 13, 14),
            new Rectangle2D(28, 0, 13, 14)};
    private int index = 0;


    Human(double x, double y) {
        super(x, y);
        hitBox = new HitBox(this, HitBox.HitBoxType.HUMAN);
        hitBox.setSize(10, 30);
        hitBox.setTranslateXY(15, 0);

        fillFrames();
        setFrame(currentFrameIndex);
        smokeSpawner = new SmokeSpawner(20, 30);
        getChildren().addAll(getView(), hitBox, smokeSpawner);
        parachute = new Parachute(-13, -36, this);
        getView().toFront();


    }

    private void fillFrames() {
        int stepX = 0;
        int stepY = 0;
        int frameWidth = 39;
        int frameHeight = 29;
        for (int i = 1; i < 23; i++) {
            if (i == 6 || i == 11 || i == 15 || i == 20) {
                stepY += frameHeight + 1;
                stepX = 0;
            }
            framesCoordinates.add(new Rectangle2D(stepX, stepY, frameWidth, frameHeight));
            stepX += frameWidth + 1;

        }
    }

    public void setKey(KeyEvent event) {
        if (isAlive()) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case A:
                        moveLeft();
                        break;
                    case D:
                        moveRight();
                        break;
                    case SPACE:
                        openParachute();
                        break;


                }
            } else {
                switch (event.getCode()) {
                    case A:
                    case D:

                        setDefaultFrames();
                        break;


                }

            }
        }


    }


    public void catapult(int rotatePosition) {
        hitBoxesToAdd.add(hitBox);
        setYPowerStep(0.08);
        setXPowerStep(0.02);
        setFallDownSets(rotatePosition);
        startChairFire();
        rotateToPurpose(0, 2000);


        createPocket();

        dropDown();


    }

    private void setFallDownSets(int rotatePosition) {
        switch (rotatePosition) {
            case 0:
                setDropXPower(-3);
                setDropYPower(0);
                break;
            case 1:
                setDropXPower(-2);
                setDropYPower(-1);
                break;
            case 2:
                setDropXPower(-2);
                setDropYPower(-2);
                break;
            case 3:
                setDropXPower(-0.5);
                setDropYPower(-2.5);
                break;
            case 4:
                setDropXPower(0);
                setDropYPower(-4);
                break;
            case 5:
                setDropXPower(0.5);
                setDropYPower(-2.5);
                break;
            case 6:
                setDropXPower(2);
                setDropYPower(-2);
                break;
            case 7:
                setDropXPower(2);
                setDropYPower(-1);
                break;
            case 8:
                setDropXPower(3);
                setDropYPower(0);
                break;
            case 9:
                setDropXPower(2);
                setDropYPower(1);
                break;
            case 10:
                setDropXPower(1);
                setDropYPower(2);
                break;
            case 11:
                setDropXPower(0.5);
                setDropYPower(2.5);
                break;
            case 12:
                setDropXPower(0);
                setDropYPower(3);
                break;
            case 13:
                setDropXPower(-0.5);
                setDropYPower(2.5);
                break;
            case 14:
                setDropXPower(-1);
                setDropYPower(2);
                break;
            case 15:
                setDropXPower(-2);
                setDropYPower(1);
                break;
        }
        setDropXPower(getDropXPower() + planeParent.getHorizontalSpeed() / 2);
        setDropYPower(getDropYPower() - planeParent.getVerticalSpeed() / 2);

    }

    public void openParachute() {
        if (!parachute.isOpen()) {
            setXPowerStep(0.04);
            setMaxYPower(1);
            setYPowerStep(0.1);
            getChildren().add(parachute);
            parachute.open();
            currentFrameIndex = 12;
            setFrame(currentFrameIndex);
            smokeSpawner.stop();
            TL.stop();
            getChildren().remove(flamesView);
        }
    }

    public void startChairFire() {
        smokeSpawner.setDuration(64);
        smokeSpawner.setType(SmokeEffect.SmokeType.DARKGREY);
        smokeSpawner.start();
        smokeSpawning = true;

        index = 0;
        flamesView.setViewport(flamesCoordinates[index]);
        flamesView.setTranslateX(12);
        flamesView.setTranslateY(25);
        getChildren().add(flamesView);
        TL.stop();
        TL.getKeyFrames().clear();
        TL.setCycleCount(Animation.INDEFINITE);
        TL.getKeyFrames().add(new KeyFrame(Duration.millis(64), event -> {
            index++;
            if (index == 3) {
                index = 0;
            }
            flamesView.setViewport(flamesCoordinates[index]);
        }));
        TL.play();

        PT.setDuration(Duration.seconds(1));
        PT.setOnFinished(event -> {
            smokeSpawner.stop();
            smokeSpawning = false;
            TL.stop();
            getChildren().remove(flamesView);
            if (!parachute.isOpen())
                startFallingAnimation();
        });
        PT.play();

    }

    private void setDefaultFrames() {
        if (parachute.isAlive()) {
            currentFrameIndex = rotation == ROTATION.LEFT ? 11 : 12;
            parachute.rotateToCenter();

        } else {
            currentFrameIndex = rotation == ROTATION.LEFT ? 6 : 1;
        }
        setFrame(currentFrameIndex);
    }

    public void removeParachute() {
        parachute.setAlive(false);

    }

    public void dropParachute() {
        startFalling();
    }

    public void dropParachuteFromShell() {
        parachute.setAlive(false);
        startFalling();
    }

    private void startFalling() {
        setMaxYPower(15);
        setYPowerStep(0.08);
        setXPowerStep(0.02);
        setDropYPower(0.1);
        startFallingAnimation();
    }

    public void stopEffects() {
        smokeSpawner.stop();
        getChildren().remove(flamesView);
        PT.stop();
        TL.stop();
        TL.getKeyFrames().clear();
    }

    public void startDeadAnimation() {
        imDead = true;
        hitBox.setAlive(false);
        if (parachute != null)
            parachute.setAlive(false);
        stopFallingDown();
        stopEffects();
        TL.setCycleCount(20);
        currentFrameIndex = 20;
        TL.getKeyFrames().addAll(new KeyFrame(Duration.millis(64), event -> {
            changeYby(-5);
            setOpacity(getOpacity() - 0.05);
            if (currentFrameIndex == 23) {
                currentFrameIndex = 20;
            }
            setFrame(currentFrameIndex);
            currentFrameIndex++;
        }));
        TL.setOnFinished(event -> {
            setAlive(false);
            startCreatingNewPlane();

        });
        TL.play();
    }


    private void startFallingAnimation() {

        TL.stop();
        TL.getKeyFrames().clear();
        currentFrameIndex = 17;
        TL.setCycleCount(Animation.INDEFINITE);
        TL.getKeyFrames().addAll(new KeyFrame(Duration.millis(64), event -> {
            if (currentFrameIndex == 20) {
                currentFrameIndex = 17;
            }
            setFrame(currentFrameIndex);
            currentFrameIndex++;
        }));
        TL.play();
    }

    public void stopFallingDown() {
        super.stopFallingDown();
        stopEffects();
        parachute.setAlive(false);
        changeYby(-1);
        if (rotation == ROTATION.LEFT) {
            currentFrameIndex = 6;
        } else {
            currentFrameIndex = 1;
        }
        setFrame(currentFrameIndex);
    }

    private void moveRight() {
        if (!imDead) {
            rotation = ROTATION.RIGHT;
            if (parachute.isOpen() && parachute.isAlive()) {
                if (parachute.isAlive()) {
                    parachute.rotateToRight();
                    changeXby(+2);
                    setFrame(14);
                }
            } else if (TL.getStatus() == Animation.Status.STOPPED) {
                stepRight();
            }
            avoidScreenBounds();
        }
    }

    private void moveLeft() {
        if (!imDead) {
            rotation = ROTATION.LEFT;
            if (parachute.isOpen() && parachute.isAlive()) {
                if (parachute.isAlive()) {
                    parachute.rotateToLeft();
                    changeXby(-2);
                    setFrame(13);
                }
            } else if (TL.getStatus() == Animation.Status.STOPPED) {
                stepLeft();
            }
            avoidScreenBounds();
        }

    }

    private void stepRight() {
        bound1 = 1;
        bound2 = 5;
        if (rotation == ROTATION.RIGHT) {

            if (currentFrameIndex > bound2 || currentFrameIndex < bound1) {
                setFrame(2);
                currentFrameIndex = 2;
            }
            setFrame(currentFrameIndex++);
        } else {
            setFrame(1);
            currentFrameIndex = 2;
            rotation = ROTATION.RIGHT;
        }
        changeXby(+4);

    }

    private void stepLeft() {
        bound1 = 6;
        bound2 = 10;
        if (rotation == ROTATION.LEFT) {
            if (currentFrameIndex > bound2 || currentFrameIndex < bound1) {
                setFrame(7);
                currentFrameIndex = 7;
            }
            setFrame(currentFrameIndex++);
        } else {
            setFrame(6);
            currentFrameIndex = 7;
            rotation = ROTATION.LEFT;
        }
        //setTranslateX(getTranslateX() - 4);
        changeXby(-4);

    }

    private void avoidScreenBounds() {
        if (getTranslateX() > getWindowWidth() - 20) {
            setTranslateX(0);
        } else if (getTranslateX() < 0) {
            setTranslateX(getWindowWidth() - 30);
        }
    }

    public void setFrame(int index) {
        onlineFrameIndex = index;
        getView().setViewport(framesCoordinates.get(index - 1));
    }

    /*public void setSize(double size){
        double width=getView().getImage().getWidth();
        double height=getView().getImage().getHeight();
        getView().setFitWidth(width*size);
        getView().setFitHeight(height*size);
        *//*setTranslateX(getTranslateX()*size);
        setTranslateY(getTranslateY()*size);*//*

    }*/
    public void startCreatingNewPlane() {
        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.seconds(5));
        PT.setOnFinished(event -> createNewPlane());
        PT.play();
    }

    protected void createNewPlane() {
        Plane plane = new LeftPlane();
        bonuses.forEach(plane::applyBonus);
        PocketManager.plane = plane;
        plane.createPocket();
        this.hitBox.setAlive(false);
        setAlive(false);
        panesToAdd.add(plane);
        ServerPlayer.plane = plane;
    }

    void dieFromEnemy() {
        startDeadAnimation();
    }

    void dieFromFall() {
        startDeadAnimation();
    }

    @Override
    public void addCollision(HitBox hitBox) {
        switch (hitBox.getHitBoxType()) {
            case AIR_DROP:
                AirDrop airDrop = (AirDrop) hitBox.getHitBoxParent();
                addBonus(airDrop.getBonus());
                break;
            case GROUND:
                if (getDropYPower() > 4) {
                    smokeSpawner.stop();
                    dieFromFall();
                } else {
                    smokeSpawner.stop();
                    TL.stop();
                    stopFallingDown();
                }
                break;
            case AMBAR:
                if (getDropYPower() > 4) {
                    smokeSpawner.stop();
                    dieFromFall();
                } else {
                    createNewPlane();
                    this.hitBox.setAlive(false);
                    setAlive(false);
                }
                break;
            default:
                dieFromEnemy();
                break;


        }
    }

    public void addBonus(Bonus bonus) {
        bonuses.add(bonus);
    }

    @Override
    public double getCollisionDamage() {
        return 0;
    }


    public Parachute getParachute() {
        return parachute;
    }


    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void finish() {
        parachute.setAlive(false);
        setAlive(false);
    }

    public boolean isParentAlive() {
        return isAlive();
    }

    @Override
    public void createPocket() {
        HumanPocket pocket = new HumanPocket(this);
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }


    public boolean isSmokeSpawning() {
        return smokeSpawning;
    }

    public boolean isImDead() {
        return imDead;
    }


    public int getOnlineFrameIndex() {
        return onlineFrameIndex;
    }

}
