package classes.Multiplayer.onlineUnits;

import classes.Multiplayer.pockets.HumanAnswerPocket;
import classes.Multiplayer.onlineControllers.HumanController;
import classes.humans.LeftHuman;

public class OnlineLeftHuman extends LeftHuman {
    private HumanController shell;
    private boolean isHumanAlive = true;

    public OnlineLeftHuman(double x, double y, HumanController shell) {
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

    public void createNewPlane() {

    }


}
