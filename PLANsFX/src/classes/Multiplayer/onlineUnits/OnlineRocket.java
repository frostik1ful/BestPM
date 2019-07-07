package classes.Multiplayer.onlineUnits;

import classes.utils.HitBox;
import classes.Multiplayer.pockets.AnswerPocket;
import classes.Multiplayer.pockets.RocketAnswerPocket;
import classes.Multiplayer.onlineControllers.RocketController;
import classes.weapons.Rocket;

public class OnlineRocket extends Rocket {
    private RocketController shell;

    public OnlineRocket(RocketController shell, double x, double y) {
        super();
        this.shell = shell;
        setTranslateXY(x, y);
    }

    @Override
    public void startEffects() {
        hitBoxesToAdd.add(getHitBox());
        super.startEffects();
    }

    @Override
    public void addCollision(HitBox hitBox) {

        switch (hitBox.getHitBoxType()) {
            case PARACHUTE:
                break;
            default:
                if (isEffectsStarted()) {
                    blowUpFunc.run();
                    stop();
                    sendAnswer();
                }
                break;

        }

    }

    private void sendAnswer() {
        AnswerPocket pocket = new RocketAnswerPocket(shell.getPocket().getPocketId());
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }

}
