package classes.Multiplayer.onlineControllers;

import classes.Multiplayer.pockets.BulletPocket;
import classes.Multiplayer.pockets.OnlinePocket;
import classes.weapons.Bullet;

public class BulletController extends OnlineController {
    private Bullet bullet;
    private BulletPocket pocket;

    public BulletController(double x, double y){
        bullet = new Bullet();
        bullet.setTranslateXY(x,y);
        setChild(bullet);
        //addChildren(bullet);
    }
    @Override
    public void setPocket(OnlinePocket inPocket) {
        this.pocket = (BulletPocket) inPocket;
        addSettings(pocket.getX(),pocket.getY());
    }

    @Override
    public void applySettings() {
        bullet.changeXby(Xqueue.poll());
        bullet.changeYby(Yqueue.poll());
    }



    @Override
    public void finish() {
        bullet.finish();
        setAlive(false);
    }
}
