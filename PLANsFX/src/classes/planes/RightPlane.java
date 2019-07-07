package classes.planes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import classes.Multiplayer.pockets.FaceEffectPocket;
import classes.effects.BonusLabel;
import classes.effects.LeftFaceEffect;
import classes.gameUnits.ScrapMetalRight;
import classes.humans.Human;
import classes.humans.RightHuman;
import classes.parts.FakeRocket;
import classes.parts.RightArmorBox;
import classes.parts.RightBodyPart;
import classes.parts.RightNosePart;
import classes.utils.Bonus;
import classes.utils.HitBox;
import classes.utils.SmokeSpawner;

public class RightPlane extends Plane {
    public RightPlane(double x, double y) {
        super(x, y);
        rotatePosition = 12;
        updateDirection();
    }

    public RightPlane() {
        this(1180, 620);
    }

    @Override
    void setParts() {
        nosePart = new RightNosePart(-3, 3);
        bodyPart = new RightBodyPart(17, 0);
        if (armorBox == null)
            armorBox = new RightArmorBox(-4, 0);
        human = new RightHuman(20, 6, this);

        nosePart.setSize(1.15, 0.95);
        bodyPart.setSize(0.7, 0.66);
        fakeRocket = new FakeRocket(5, 15);
        fakeRocket.setRotate(180);
        bcirk = new Circle();
        bcirk.setRadius(1);
        bcirk.setCenterY(21);
        bcirk.setCenterX(20);
        bcirk.setFill(Color.RED);
        bcirk.setOpacity(0);

        shootCircle.setRadius(1);
        shootCircle.setCenterX(-11);
        shootCircle.setCenterY(25);
        shootCircle.setOpacity(0);

        bodyBox = new HitBox(this, HitBox.HitBoxType.PLANE_BODY);
        bodyBox.setSize(78, 25);
        bodyBox.setTranslateX(5);
        bodyBox.setTranslateY(14);

        noseBox = new HitBox(this, HitBox.HitBoxType.PLANE_NOSE, 2);
        noseBox.setSize(15, 25);
        noseBox.getRectangle().setFill(Color.RED);
        noseBox.setTranslateXY(0, 12);

        headBox = new HitBox(this, HitBox.HitBoxType.PLANE_HEAD);
        headBox.setSize(15, 15);
        headBox.setTranslateXY(32, 5);

        hitBoxes.add(bodyBox);
        hitBoxes.add(noseBox);
        hitBoxes.add(headBox);


        scrapMetal = new ScrapMetalRight(getTranslateX(), getTranslateY());
        spawner = new SmokeSpawner(20, 21);


        getChildren().addAll(fakeRocket, human, bodyPart, spawner, nosePart, shootCircle, armorBox);
        getChildren().addAll(hitBoxes);
    }


    @Override
    public void openCloseNose() {
        Timeline timeline = new Timeline(30);
        timeline.setCycleCount(43);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15),
                event -> nosePart.getTransforms().add(new Rotate(2, 14, 10))));
        timeline.setOnFinished(event -> {
            fakeRocket.setOpacity(0);
            timeline.getKeyFrames().clear();
            timeline.setDelay(Duration.millis(650));
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(15),
                    event1 -> nosePart.getTransforms().add(new Rotate(-2, 14, 10))));
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
            decreaseRightPlayerPoints();
        }
        super.blowUp();
    }

    @Override
    protected void dieFromEnemy() {
        super.blowUp();
        if (isHumanInPlane()) {
            increaseLeftPlayerPoints();
            createFaceEffectPocket();
        }

    }

    @Override
    public void addHeadShot() {
        super.addHeadShot();
        increaseLeftPlayerPoints();
        createFaceEffectPocket();
    }

    private void createFaceEffectPocket() {
        LeftFaceEffect face = new LeftFaceEffect();
        effects.add(face);
        FaceEffectPocket pocket = new FaceEffectPocket(0, face.getJeerNumber(), (byte) 0);
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }

    public void addBonus(Bonus bonus) {
        if (!isHumanInPlane()) {
            getHuman().addBonus(bonus);
        } else {
            applyBonus(bonus);
            effects.add(new BonusLabel(noseBox.getPositionInScene().getX(),
                    noseBox.getPositionInScene().getY() - 20, bonus.getBonusType().toString().toUpperCase()));
        }

    }


    @Override
    Human createHuman() {
        return new RightHuman(20, 6, this);

    }
}
