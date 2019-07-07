package classes.Multiplayer.onlineControllers;

import classes.Multiplayer.onlineUnits.OnlineAirDrop;
import classes.Multiplayer.pockets.AirDropPocket;
import classes.Multiplayer.pockets.OnlinePocket;

public class AirDropController extends OnlineController {
    private OnlineAirDrop airDrop;
    private AirDropPocket pocket;

    public AirDropController(double x, double y, byte bonusNumber){
        airDrop = new OnlineAirDrop(x,y,bonusNumber,this);
        setChild(airDrop);
    }


    @Override
    public void setPocket(OnlinePocket inPocket) {
        this.pocket = (AirDropPocket) inPocket;
        addSettings(pocket.getX(),pocket.getY());
    }
    @Override
    public void applySettings() {
        airDrop.changeXby(Xqueue.poll());
        airDrop.changeYby(Yqueue.poll());

        if (pocket.isEffectsStarted()&&!airDrop.isEffectsStarted()){
            airDrop.startSmokeAnimation();
        }
        if (!pocket.isParachuteAlive()&&airDrop.isParachuteAlive()){
            airDrop.dropParachuteFromShell();
        }
    }

    @Override
    public void finish() {
        airDrop.finish();
        setAlive(false);
    }
}
