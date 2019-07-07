package classes.Multiplayer.pockets;

import classes.ROTATION;
import classes.gameUnits.ScrapMetalLeft;
import classes.gameUnits.ScrapMetalRight;

public class ScrapPocket extends OnlinePocket{
    private boolean isEffectsStarted;
    private ROTATION rotation;

    public ScrapPocket(ScrapMetalLeft scrap){
        this.onlinebleParent = scrap;
        if (scrap instanceof ScrapMetalRight){
            rotation = ROTATION.RIGHT;
        }
        else {
            rotation = ROTATION.LEFT;
        }
        updatePocket();

    }
    @Override
    public void updatePocket() {
        ScrapMetalLeft scrap = (ScrapMetalLeft) onlinebleParent;
        isEffectsStarted = scrap.isEffectsStarted();

        setX((float) scrap.getTranslateX());
        setY((float) scrap.getTranslateY());
        setRotation((float) scrap.getRotate());
    }

    public boolean isEffectsStarted() {
        return isEffectsStarted;
    }


    public ROTATION getScrapSide() {
        return rotation;
    }
}
