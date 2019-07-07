package classes.Multiplayer.onlineUnits;

import classes.Multiplayer.onlineControllers.AirDropController;
import classes.Multiplayer.pockets.AirDropAnswerPocket;
import classes.gameUnits.AirDrop;
import classes.utils.Bonus;
import interfaces.Colliding;
import classes.humans.RightHuman;
import classes.planes.RightPlane;


public class OnlineAirDrop extends AirDrop {
    private AirDropController controller;
    public OnlineAirDrop(double x, double y, byte bonusNumber,AirDropController controller) {
        super(x, y, bonusNumber);
        stopFallingDown();
        this.controller = controller;
    }
    @Override
    protected void createBonusLabel(Colliding damageable) {
        if (damageable instanceof RightHuman || damageable instanceof RightPlane) {
            super.createBonusLabel();
            createBonusAnswer();
        }
    }
    private void createBonusAnswer(){
        AirDropAnswerPocket pocket = new AirDropAnswerPocket(controller.getShellId());
        pocketsTosend.put(pocket.getPocketId(),pocket);
    }

    @Override
    public void createPocket() {

    }
}
