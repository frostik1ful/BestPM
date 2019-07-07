package classes.gameUnits;


import classes.Multiplayer.pockets.ScrapPocket;
import interfaces.Onlineble;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import interfaces.Colliding;
import classes.utils.HitBox;
import classes.effects.SmokeEffect;
import classes.panes.FallingDownPane;
import classes.parts.ScrapBodyPartLeft;

import java.util.Random;

public class ScrapMetalLeft extends FallingDownPane implements Colliding, Onlineble {
    ScrapBodyPartLeft scrapBodyPart;
    HitBox bodyBox;
    private double rotatePurpose = 90;
    private Random rnd = getRandom();
    private boolean isEffectsStarted;
    private Timeline timeline;

    public ScrapMetalLeft(double x, double y) {
        super(x, y);
        setParts();
        addParts();
        setXPowerStep(0.02);


    }

    protected void setParts() {
        scrapBodyPart = new ScrapBodyPartLeft(0, 0);
        scrapBodyPart.setSize(0.7, 0.66);

        bodyBox = new HitBox(this, HitBox.HitBoxType.SCRAP_BODY);
        bodyBox.setSize(60, 20);
        bodyBox.setTranslateXY(12, 8);
    }

    private void addParts() {
        getChildren().addAll(scrapBodyPart, bodyBox);

    }

    protected void playEffects() {
        isEffectsStarted = true;

        timeline = new Timeline();
        KeyFrame k0 = new KeyFrame(Duration.millis(100),event -> {
            Point2D point2D = getPositionInScene();
            effects.add(new SmokeEffect(point2D, SmokeEffect.SmokeType.DARKGREY, rnd.nextInt(3)));
            point2D = new Point2D(point2D.getX() + rnd.nextInt(40), point2D.getY());
            effects.add(new SmokeEffect(point2D, SmokeEffect.SmokeType.DARKGREY, rnd.nextInt(3)));
        });
        timeline.getKeyFrames().add(k0);
        timeline.setCycleCount(70);
        timeline.setOnFinished(event -> disappear());
        timeline.play();



    }

    public void dropDown() {
        playEffects();
        rotateToPurpose(rotatePurpose, 2900);
        super.dropDown();
    }

    private void disappear() {
        FadeTransition FT = new FadeTransition();
        FT.setNode(this);
        FT.setToValue(0);
        FT.setDelay(Duration.seconds(3));
        FT.setDuration(Duration.seconds(1));
        FT.setOnFinished(event -> {
            stopFallingDown();
            setAlive(false);
        });

        FT.play();
    }

    @Override
    public void addCollision(HitBox hitBox) {
        bodyBox.setAlive(false);
        setDropYPower(0);
        setYPowerStep(0);
        setXPowerStep(0.1);
        stopRotateToPurpose();


    }

    public boolean isEffectsStarted() {
        return isEffectsStarted;
    }

    @Override
    public double getCollisionDamage() {
        return 0;
    }

    public HitBox getBodyBox() {
        return bodyBox;
    }


    @Override
    public void finish() {
        setAlive(false);
    }

    @Override
    public boolean isParentAlive() {
        return isAlive();
    }

    @Override
    public void createPocket() {
        ScrapPocket scrapPocket = new ScrapPocket(this);
        pocketsTosend.put(scrapPocket.getPocketId(),scrapPocket);
    }
}
