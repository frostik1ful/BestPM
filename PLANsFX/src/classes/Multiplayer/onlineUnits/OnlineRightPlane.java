package classes.Multiplayer.onlineUnits;

import classes.utils.Bonus;
import classes.Multiplayer.pockets.AirDropAnswerPocket;
import classes.Multiplayer.pockets.HeadShotPocket;
import classes.Multiplayer.pockets.PlaneAnswerPocket;
import classes.Multiplayer.onlineControllers.PlaneController;
import classes.planes.RightPlane;

public class OnlineRightPlane extends RightPlane {
    private PlaneController shell;

    public OnlineRightPlane(double x, double y, PlaneController shell) {
        super(x, y);
        this.shell = shell;

    }
    @Override
    public void addDamage(double incomingDamage, boolean isDamageFromEnemy) {
        if (incomingDamage > 0) {
            super.addDamage(incomingDamage, isDamageFromEnemy);
            PlaneAnswerPocket pocket = new PlaneAnswerPocket(getPocketId(), (byte) getHealth(), (byte) getArmorLeft());
            pocketsTosend.put(pocket.getPocketId(), pocket);
        }

    }
    @Override
    protected void blowUp() {
        if (isAlive()) {
            shell.setAlive(false);
            super.blowUp();
        }
    }
    @Override
    protected void restart(){

    }
    @Override
    public void dropScrap(){

    }

    @Override
    protected void dieFromFall() {
    }

    @Override
    protected void dieFromEnemy() {
    }
    @Override
    public void applyBonus(Bonus bonus) {

    }


    @Override
    public void addHeadShot() {
        if (isHumanInPlane()) {
            createBloodSplash();
            HeadShotPocket pocket = new HeadShotPocket(getPocketId());
            pocketsTosend.put(pocket.getPocketId(), pocket);
        }
    }

    private int getPocketId() {
        return shell.getPocketId();
    }

    public void dropDown() {

    }
}
