package classes.gameUnits;

import javafx.scene.paint.Color;
import interfaces.Colliding;
import classes.utils.HitBox;
import classes.panes.AdvancedPane;

public class Ground extends AdvancedPane implements Colliding {
    private HitBox hitBox;

    public Ground() {
        hitBox = new HitBox(this, HitBox.HitBoxType.GROUND);
        hitBox.setSize(1280, 50);
        hitBox.getRectangle().setFill(Color.BLUE);
        getChildren().add(hitBox);
        setTranslateY(676);
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
