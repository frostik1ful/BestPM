package classes.weapons;

import javafx.scene.shape.Line;
import classes.utils.HitBox;

public class ShotGun extends Weapon {
    private Line[] lines;
    private Bullet[] bullets = new Bullet[3];

    public ShotGun() {
        this.setAmmoLeft(5);
        setWeapontype(WEAPON_TYPE.SHOTGUN);
    }

    @Override
    public void start() {
        for (int i = 0; i < lines.length; i++) {
            Bullet bullet = new Bullet();
            bullet.setLine(lines[i]);
            bullets[i] = bullet;

        }
    }

    @Override
    public void setLine(Line line) {

    }

    @Override
    public Line getLine() {
        return null;
    }

    @Override
    public void setShotGunLines(Line... lines) {
        this.lines = lines;

    }





    @Override
    public HitBox getHitBox() {
        return null;
    }

    public Line[] getLines() {
        return lines;
    }

    public Bullet[] getBullets() {
        return bullets;
    }
}
