package classes.weapons;


import javafx.scene.shape.Line;
import classes.panes.FallingDownPane;
import classes.utils.HitBox;


public abstract class Weapon extends FallingDownPane {
    private double damage;
    private WEAPON_TYPE weapontype;
    private int ammoLeft;

    Weapon() {

    }

    public abstract void start();

    double getDamage() {
        return damage;
    }

    void setDamage(float damage) {
        this.damage = damage;
    }

    public WEAPON_TYPE getWeapontype() {
        return weapontype;
    }

    void setWeapontype(WEAPON_TYPE weapontype) {
        this.weapontype = weapontype;
    }

    public int getAmmoLeft() {
        return ammoLeft;
    }

    void setAmmoLeft(int ammoLeft) {
        this.ammoLeft = ammoLeft;
    }

    public void decreaseAmmo() {
        if (weapontype != WEAPON_TYPE.BULLET) {
            ammoLeft--;
        }
    }


    public abstract void setLine(Line line);

    public abstract Line getLine();

    public abstract void setShotGunLines(Line... lines);

    //public abstract Line[] getShotGunLines();
    //public abstract Pane getWeaponShape();
    //public abstract BulletShape getBulletShape();
    public abstract Bullet[] getBullets();

    //public abstract void setEffects(List effects);
    public abstract HitBox getHitBox();

}
