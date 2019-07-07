package classes.weapons;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import interfaces.Colliding;
import classes.utils.HitBox;
import classes.Multiplayer.pockets.BulletPocket;

import interfaces.Onlineble;
import classes.weapons.weaponShapes.BulletShape;



public class Bullet extends Weapon implements Colliding, Onlineble {
    private PathTransition path = new PathTransition();
    private Line line;
    private BulletShape shape;
    private HitBox hitBox;
    private BulletPocket pocket;

    public Bullet() {
        //setTranslateXY(x,y);
        this.setDamage(1);
        this.setWeapontype(WEAPON_TYPE.BULLET);
        shape = new BulletShape();
        hitBox = new HitBox(this,HitBox.HitBoxType.BULLET);
        hitBox.setSize(8, 8);
        hitBox.setTranslateX(-4);
        hitBox.setTranslateY(-4);
        hitBox.getRectangle().setFill(Color.BLACK);
        getChildren().addAll(shape, hitBox);


    }

    public void start() {
        //System.out.println("NEW BULLET CREATED");

        setTranslateXY(line.getStartX(),line.getStartY());
        createPocket();
        path.setNode(this);
        path.setDuration(Duration.seconds(3));
        path.setInterpolator(Interpolator.LINEAR);
        path.setPath(line);
        path.setCycleCount(1);
        path.setOnFinished(event -> setAlive(false));
        path.play();


     }

    @Override
    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public Line getLine() {
        return this.line;
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
        path.stop();
        setAlive(false);
    }

    @Override
    public double getCollisionDamage() {
        return getDamage();
    }


    @Override
    public void finish() {
        this.path.stop();
        this.setAlive(false);
    }

    @Override
    public boolean isParentAlive() {
        return isAlive();
    }

    @Override
    public void createPocket() {
        pocket = new BulletPocket(this);
        pocketsTosend.put(pocket.getPocketId(),pocket);
    }
    public void setAlive(boolean isAlive){
        if (!isAlive){
            hitBox.setAlive(false);
        }
        super.setAlive(isAlive);
    }
}
