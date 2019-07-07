package classes.utils;

import classes.ServerPlayer;
import classes.Multiplayer.DataManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private DataManager messageManager;
    private boolean running = true;
    private Socket socket;

    public Server(DataManager messageManager,int port) {
        this.messageManager = messageManager;
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("server running");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //while (running) {
            try {
                socket = serverSocket.accept();
                ServerPlayer.socket = socket;
                messageManager.setSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}

    }

    public void stopServer() {

        try {
            serverSocket.close();

        } catch (IOException e) {
            //e.printStackTrace();
        }



    }


}
