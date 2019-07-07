package classes.Multiplayer.pockets;

import classes.weapons.Bullet;

public class BulletPocket extends OnlinePocket {

    public BulletPocket(Bullet bullet){
        this.onlinebleParent = bullet;
        updatePocket();
    }
    @Override
    public void updatePocket() {
        Bullet bullet = (Bullet) onlinebleParent;
        setX((float) bullet.getTranslateX());
        setY((float) bullet.getTranslateY());

    }
}
