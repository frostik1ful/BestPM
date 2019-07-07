package classes.planes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import classes.Multiplayer.pockets.FaceEffectPocket;
import classes.effects.RightFaceEffect;
import classes.gameUnits.ScrapMetalLeft;
import classes.humans.Human;
import classes.humans.LeftHuman;
import classes.parts.FakeRocket;
import classes.parts.LeftArmorBox;
import classes.parts.LeftBodyPart;
import classes.parts.LeftNosePart;
import classes.utils.HitBox;
import classes.utils.SmokeSpawner;

public class LeftPlane extends Plane {
    public LeftPlane(double x, double y) {
        super(x, y);
    }

    public LeftPlane() {
        this(10, 620);
    }

    @Override
    void setParts() {
        nosePart = new LeftNosePart(74, 3);
        bodyPart = new LeftBodyPart(0, 0);
        if (armorBox == null)
            armorBox = new LeftArmorBox(0, 0);
        human = createHuman();

        nosePart.setSize(1.15, 0.95);
        bodyPart.setSize(0.7, 0.66);
        fakeRocket = new FakeRocket(25, 15);


        shootCircle.setRadius(1);
        shootCircle.setCenterX(95);
        shootCircle.setCenterY(25);
        shootCircle.setOpacity(0);

        bodyBox = new HitBox(this, HitBox.HitBoxType.PLANE_BODY, 3);
        bodyBox.setSize(65, 25);
        bodyBox.setTranslateXY(2, 14);


        noseBox = new HitBox(this, HitBox.HitBoxType.PLANE_NOSE, 2);
        noseBox.setSize(15, 25);
        noseBox.getRectangle().setFill(Color.RED);
        noseBox.setTranslateXY(66, 12);

        headBox = new HitBox(this, HitBox.HitBoxType.PLANE_HEAD);
        headBox.setSize(15, 15);
        headBox.setTranslateXY(37, 5);

        hitBoxes.clear();
        hitBoxes.add(bodyBox);
        hitBoxes.add(noseBox);
        hitBoxes.add(headBox);


        scrapMetal = new ScrapMetalLeft(getTranslateX(), getTranslateY());
        spawner = new SmokeSpawner(65, 21);

        getChildren().addAll(fakeRocket, human, bodyPart, nosePart, shootCircle, armorBox, spawner);
        getChildren().addAll(hitBoxes);
    }


    @Override
    public void openCloseNose() {
        Timeline timeline = new Timeline(30);
        timeline.setCycleCount(43);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15),
                event -> nosePart.getTransforms().add(new Rotate(-2, 1, 10))));
        timeline.setOnFinished(event -> {
            fakeRocket.setOpacity(0);
            timeline.getKeyFrames().clear();
            timeline.setDelay(Duration.millis(650));
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15),
                    event1 -> nosePart.getTransforms().add(new Rotate(2, 1, 10))));
            timeline.setOnFinished(event1 -> {
                fakeRocket.setOpacity(1);
                isNoseOpen = false;

            });
            timeline.play();
        });
        timeline.play();
    }

    @Override
    protected void dieFromFall() {
        if (isHumanInPlane()) {
            decreaseLeftPlayerPoints();
        }
        super.blowUp();
    }

    @Override
    protected void dieFromEnemy() {
        super.blowUp();
        if (isHumanInPlane()) {
            increaseRightPlayerPoints();
            createFaceEffectPocket();
        }

    }

    @Override
    public void addHeadShot() {
        super.addHeadShot();
        increaseRightPlayerPoints();
        createFaceEffectPocket();
    }


    private void createFaceEffectPocket() {
        RightFaceEffect face = new RightFaceEffect();
        effects.add(face);
        FaceEffectPocket pocket = new FaceEffectPocket(0, face.getJeerNumber(), (byte) 1);
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }

    @Override
    Human createHuman() {
        return new LeftHuman(25, 6, this);
    }


}
