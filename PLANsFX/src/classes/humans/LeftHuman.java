package classes.humans;

import javafx.scene.image.Image;
import classes.Multiplayer.pockets.FaceEffectPocket;
import classes.effects.RightFaceEffect;
import classes.planes.Plane;

public class LeftHuman extends Human {
    public LeftHuman(double x, double y, Plane plane) {
        this(x, y);
        this.planeParent = plane;
    }

    public LeftHuman(double x, double y) {
        super(x, y);
        humanImg = new Image("/resources/images/human/leftHumans.png");
        setImageInView(humanImg);
    }

    void dieFromFall() {
        decreaseLeftPlayerPoints();
        super.dieFromFall();
    }

    void dieFromEnemy() {
        RightFaceEffect face = new RightFaceEffect(windowW - 100, 50);
        effects.add(face);
        FaceEffectPocket pocket = new FaceEffectPocket(0, face.getJeerNumber(), (byte) 1);
        pocketsTosend.put(pocket.getPocketId(), pocket);
        increaseRightPlayerPoints();
        super.dieFromEnemy();
    }
}
