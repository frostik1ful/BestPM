package classes.utils;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import classes.gameUnits.AirDrop;
import classes.panes.AdvancedPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class AirDropSpawner extends AdvancedPane {
    private PauseTransition PT = new PauseTransition();
    private double duration;
    private double x;
    private List<AirDrop> airDrops = new LinkedList<>();
    public void start(){
        setRandomSpawnSettings();
        PT.setDuration(Duration.seconds(duration));
        PT.setOnFinished(event ->{
            if (checkIfCanCreateAirDrop()){
                AirDrop airDrop = new AirDrop(x,-20);
                airDrops.add(airDrop);
                panesToAdd.add(airDrop);
            }
            setRandomSpawnSettings();
            PT.setDuration(Duration.seconds(duration));
            PT.play();
        });
        PT.play();
    }
    private boolean checkIfCanCreateAirDrop(){
        int maxAirDropQuantity = 5;
        if (airDrops.size()<maxAirDropQuantity){
            return true;
        }

        removeDeadAirDrops();

        return airDrops.size()<maxAirDropQuantity;


    }
    private void removeDeadAirDrops(){
        List <AirDrop> airDropsToRemove = new ArrayList<>();
        airDrops.forEach(airDrop -> {
            if (!airDrop.isAlive()){
                airDropsToRemove.add(airDrop);
            }
        });
        airDrops.removeAll(airDropsToRemove);
    }
    private void setRandomSpawnSettings(){
        duration = getRandom().nextInt(20)+20;
        x = getRandom().nextInt((int) getWindowWidth()-20);
        if (x<30){
            x=30;
        }
    }

}
