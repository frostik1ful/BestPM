package classes.Multiplayer.onlineUnits;

import classes.Multiplayer.pockets.AirDropAnswerPocket;
import classes.utils.Bonus;
import classes.Multiplayer.pockets.HumanAnswerPocket;
import classes.Multiplayer.onlineControllers.HumanController;
import classes.humans.RightHuman;

public class OnlineRightHuman extends RightHuman {
    private HumanController shell;
    private boolean isHumanAlive = true;

    public OnlineRightHuman(double x, double y, HumanController shell) {
        super(x, y);
        this.shell = shell;
        hitBoxesToAdd.add(getHitBox());
    }

    public void dropParachute() {
        createAnswerPocket();
    }

    public void startDeadAnimation() {
        isHumanAlive = false;
        createAnswerPocket();
        super.startDeadAnimation();
    }

    private void createAnswerPocket() {
        HumanAnswerPocket pocket = new HumanAnswerPocket(shell.getShellId(), isHumanAlive, false);
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }

    private int getPocketId() {
        return shell.getPocketId();
    }

    public void addBonus(Bonus bonus) {

    }

    public void createNewPlane() {

    }


}
