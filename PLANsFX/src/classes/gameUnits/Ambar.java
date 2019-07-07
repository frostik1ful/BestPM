package classes.gameUnits;

import javafx.scene.image.Image;
import interfaces.Colliding;
import classes.utils.HitBox;
import classes.panes.AdvancedPane;

public class Ambar extends AdvancedPane implements Colliding {
    private HitBox hitBox;

    public Ambar() {
        hitBox = new HitBox(this, HitBox.HitBoxType.AMBAR, 3);
        hitBox.setSize(167, 80);
        setImageInView(new Image("/resources/images/ambars.png"));
        getView().setFitWidth(167);
        getView().setFitHeight(82);
        getChildren().addAll(getView(), hitBox);
        setTranslateX(getWindowWidth() / 2 - getView().getFitWidth() / 2);
        setTranslateY(getWindowHeight() - 119);
    }

    @Override
    public void addCollision(HitBox hitBox) {
    }

    @Override
    public double getCollisionDamage() {
        return 999;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    public HitBox getHitBox() {
        return hitBox;
    }
}
