package classes.Multiplayer.onlineControllers;

import classes.Multiplayer.onlineUnits.OnlineLeftScrap;
import classes.Multiplayer.onlineUnits.OnlineRightScrap;
import classes.Multiplayer.pockets.OnlinePocket;
import classes.Multiplayer.pockets.ScrapPocket;
import classes.ROTATION;
import classes.gameUnits.ScrapMetalLeft;


public class ScrapController extends OnlineController{
    private ScrapMetalLeft scrap;
    private ScrapPocket pocket;
    private boolean isEffectsStarted;

    public ScrapController(double x, double y, ROTATION rotation){
        if (rotation == ROTATION.LEFT){
            scrap = new OnlineLeftScrap(x,y);
        }
        else {
            scrap = new OnlineRightScrap(x,y);
        }
        setChild(scrap);
    }

    @Override
    public void setPocket(OnlinePocket inPocket) {
        this.pocket = (ScrapPocket) inPocket;
        if (!isEffectsStarted&&pocket.isEffectsStarted()){
            if (pocket.getScrapSide() == ROTATION.LEFT){
                ((OnlineLeftScrap) scrap).playEffects();
            }
            else {
                ((OnlineRightScrap) scrap).playEffects();
            }

            isEffectsStarted = true;
        }
        addSettings(pocket.getX(),pocket.getY(),pocket.getRotation());
    }

    @Override
    public void applySettings() {
        scrap.changeXby(Xqueue.poll());
        scrap.changeYby(Yqueue.poll());
        scrap.setRotate(rotateQueue.poll());
    }



    @Override
    public void finish() {
        setAlive(false);
        scrap.finish();
    }
}
