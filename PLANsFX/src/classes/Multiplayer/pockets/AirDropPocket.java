package classes.Multiplayer.pockets;

import classes.gameUnits.AirDrop;

public class AirDropPocket extends OnlinePocket {
    private byte bonusNumber;
    private boolean isEffectsStarted;
    private boolean isParachuteAlive = true;

    public AirDropPocket(AirDrop airDrop) {
        this.onlinebleParent = airDrop;
        bonusNumber = airDrop.getBonus().getBonusNumber();
    }

    @Override
    public void updatePocket() {
        AirDrop airDrop = (AirDrop) onlinebleParent;
        this.isEffectsStarted = airDrop.isEffectsStarted();
        this.isParachuteAlive = airDrop.isParachuteAlive();
        setX((float) airDrop.getTranslateX());
        setY((float) airDrop.getTranslateY());
    }

    public boolean isEffectsStarted() {
        return isEffectsStarted;
    }

    public byte getBonusNumber() {
        return bonusNumber;
    }

    public boolean isParachuteAlive() {
        return isParachuteAlive;
    }
}
