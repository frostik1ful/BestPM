package classes.gameUnits;


import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import interfaces.Colliding;
import classes.utils.HitBox;
import interfaces.Parachuting;
import classes.panes.AlivePane;

public class Parachute extends AlivePane implements Colliding {
    private Image image = new Image("resources/images/parachute/parachute.png");
    private HitBox hitBox;
    private boolean rotatedToRight = false;
    private boolean rotatedToLeft = false;
    private Parachuting parachuting;
    private boolean isOpen;

    public Parachute(double x, double y, Parachuting parachuting) {
        super(x, y);
        this.parachuting = parachuting;
        hitBox = new HitBox(this, HitBox.HitBoxType.PARACHUTE, 2);
        hitBox.setSize(60, 40);

    }


    public void open() {
        isOpen = true;
        setImageInView(image);
        hitBoxesToAdd.add(hitBox);
        getChildren().addAll(getView(), hitBox);
    }

    public void rotateToLeft() {
        if (!rotatedToLeft) {
            getTransforms().add(new Rotate(-20, 30, 44));
            rotatedToRight = false;
            rotatedToLeft = true;
        }
    }

    public void rotateToRight() {
        if (!rotatedToRight) {
            getTransforms().add(new Rotate(+20, 30, 44));
            rotatedToLeft = false;
            rotatedToRight = true;
        }

    }

    public void rotateToCenter() {
        getTransforms().clear();
        rotatedToLeft = false;
        rotatedToRight = false;
    }

    public void setAlive(boolean alive) {
        if (!alive) {
            isOpen = false;
            setOpacity(0);
            hitBox.setAlive(false);
        } else {
            setOpacity(1);
        }
        super.setAlive(alive);
    }


    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void addCollision(HitBox hitBox) {
        switch (hitBox.getHitBoxType()) {
            case HUMAN:
            case PLANE_BODY:
                break;
            default:
                this.setAlive(false);

                parachuting.dropParachute();

        }

    }

    public void testDrop() {
        this.setAlive(false);

        parachuting.dropParachute();
    }

    @Override
    public double getCollisionDamage() {
        return 0;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isRotatedToRight() {
        return rotatedToRight;
    }

    public boolean isRotatedToLeft() {
        return rotatedToLeft;
    }
}
