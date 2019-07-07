package classes.Multiplayer;

import classes.Multiplayer.pockets.OnlinePocket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class OnlineData implements Serializable {
    private List<OnlinePocket> onlinePockets = new ArrayList<>();

    OnlineData() {
    }

    List<OnlinePocket> getOnlinePockets() {
        return onlinePockets;
    }

    void setOnlinePockets(Map<Integer, OnlinePocket> onlinePockets) {
        onlinePockets.forEach((id, pocket) -> this.onlinePockets.add(pocket));
    }
}
