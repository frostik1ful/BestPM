package classes.Multiplayer;

import classes.Multiplayer.pockets.OnlinePocket;
import classes.planes.Plane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class DataManager {
    private Socket socket;
    private OnlineData dataIn;
    private OnlineData dataOut;
    private List<Integer> pocketToDel = new ArrayList<>();
    private Map<Integer, OnlinePocket> pocketsToSend = new LinkedHashMap<>();
    private PocketManager pocketManager = new PocketManager();

    public DataManager(Map<Integer, OnlinePocket> onlinePockets, Socket socket) {
        this.socket = socket;
        this.pocketsToSend = onlinePockets;

    }


    public void sendData() {
        pocketsToSend.forEach((id, pocket) -> {
            if (pocket.isAlive()) {
                pocket.updatePocket();
            } else {
                pocketToDel.add(pocket.getPocketId());
            }
        });
        pocketToDel.forEach((id) -> pocketsToSend.remove(id));
        pocketToDel.clear();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataOut = new OnlineData();
            dataOut.setOnlinePockets(pocketsToSend);
            objectOutputStream.writeObject(dataOut);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pocketManager.removeOldPockets();

    }

    public void getData() {
        pocketManager.clearIncomingPockets();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            dataIn = (OnlineData) objectInputStream.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pocketManager.addNewPockets(dataIn.getOnlinePockets());
        pocketManager.updatePockets();
    }

    public void updateShells() {
        pocketManager.update();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PocketManager getPocketManager() {
        return pocketManager;
    }

    public void setMainPlane(Plane plane) {
        this.pocketManager.plane = plane;
    }
}
