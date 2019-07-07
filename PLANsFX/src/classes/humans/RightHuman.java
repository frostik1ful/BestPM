package classes.humans;

import javafx.scene.image.Image;
import classes.utils.Bonus;
import classes.Multiplayer.PocketManager;
import classes.Multiplayer.pockets.FaceEffectPocket;
import classes.ClientPlayer;
import classes.effects.BonusLabel;
import classes.effects.LeftFaceEffect;
import classes.planes.Plane;
import classes.planes.RightPlane;

public class RightHuman extends Human {

    public RightHuman(double x, double y, Plane plane) {
        this(x, y);
        this.planeParent = plane;
    }

    public RightHuman(double x, double y) {
        super(x, y);
        humanImg = new Image("/resources/images/human/rightHumans.png");
        currentFrameIndex = 16;
        setImageInView(humanImg);
        setFrame(currentFrameIndex);
    }

    public void catapult(int rotate) {
        if (rotate >= 8) {
            rotate -= 8;
        } else {
            rotate += 8;
        }
        super.catapult(rotate);
    }

    @Override
    public void createNewPlane() {
        Plane plane = new RightPlane();
        bonuses.forEach(plane::applyBonus);
        PocketManager.plane = plane;
        plane.createPocket();
        this.hitBox.setAlive(false);
        setAlive(false);
        panesToAdd.add(plane);
        ClientPlayer.plane = plane;
    }

    void dieFromFall() {
        decreaseRightPlayerPoints();
        super.dieFromFall();
    }

    void dieFromEnemy() {
        LeftFaceEffect face = new LeftFaceEffect();
        effects.add(face);
        FaceEffectPocket pocket = new FaceEffectPocket(0, face.getJeerNumber(), (byte) 0);
        pocketsTosend.put(pocket.getPocketId(), pocket);
        increaseLeftPlayerPoints();
        super.dieFromEnemy();
    }

}
