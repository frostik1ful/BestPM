package classes;

import classes.effects.Cloud;
import interfaces.TimerFunction;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import classes.Multiplayer.DataManager;
import classes.Multiplayer.pockets.OnlinePocket;

import classes.effects.Effect;
import classes.gameUnits.AirShip;
import classes.gameUnits.Ambar;
import classes.gameUnits.Ground;
import classes.panes.AdvancedPane;
import classes.panes.AlivePane;
import classes.planes.Plane;
import classes.planes.RightPlane;
import classes.utils.CollisionManager;
import classes.utils.HitBox;
import classes.weapons.Bullet;
import classes.weapons.WEAPON_TYPE;
import classes.weapons.Weapon;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;

public class ClientPlayer extends Pane {
    private AnimationTimer timer;
    private CollisionManager collisionManager = new CollisionManager();
    private SocketAddress socketAddress;
    private boolean isFpsFixActive;
    private Label fpsLabel = new Label();

    private Cloud cloud = new Cloud(50, 140, 1);
    private Cloud cloud2 = new Cloud(550, 190, 2);
    private AirShip airShip = new AirShip(200, 100);
    private Ambar ambar;
    private Ground ground = new Ground();
    public static Plane plane;

    private Socket socket = new Socket();

    private long frames = 1;
    private int fpsCounter;
    private final int windowW = 1280;
    private final int windowH = 705;


    private List<HitBox> hitBoxesToAdd = new LinkedList<>();
    private List<AlivePane> panesToAdd = new LinkedList<>();
    private LinkedList<AlivePane> panesToCheck = new LinkedList<>();
    private LinkedList<AlivePane> panesToRemove = new LinkedList<>();
    private LinkedList<Effect> effects = new LinkedList<>();
    private LinkedList<Weapon> weapons = new LinkedList<>();

    private Map<Integer, OnlinePocket> pocketsTosend = new LinkedHashMap<>();

    private long lastFrameTime;
    private long fpsTime;
    private DataManager dataManager = new DataManager(pocketsTosend, socket);
    private TimerFunction timerFunc;

    public static Label col = new Label("COLLISION ");

    ClientPlayer(String ip, int port, boolean isFpsFixActive) {
        socketAddress = new InetSocketAddress(ip, port);
        this.isFpsFixActive = isFpsFixActive;
    }

    void run() {
        createContent();
        start();
    }

    public void stop() {
        timer.stop();
    }

    private void createContent() {
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Comical Cartoon.ttf"), 1);

        AdvancedPane.windowW = windowW;
        AdvancedPane.windowH = windowH;
        AdvancedPane.panesToAdd = panesToAdd;
        AdvancedPane.hitBoxesToAdd = hitBoxesToAdd;
        AdvancedPane.effects = effects;
        AdvancedPane.weaponsToAdd = weapons;
        AdvancedPane.pocketsTosend = pocketsTosend;
        AdvancedPane.scoreboard = airShip.getScoreboard();

        plane = new RightPlane();
        panesToAdd.add(plane);
        plane.createPocket();
        ambar = new Ambar();
        dataManager.setMainPlane(plane);

        airShip.start();
        fpsLabel.setTranslateX(windowW - 50);

        this.setPrefSize(windowW, windowH);


        collisionManager.addHitBox(ambar.getHitBox());
        collisionManager.addHitBox(ground.getHitBox());




        this.getChildren().addAll(new ImageView(new Image("/resources/images/back.png")), airShip, ambar, ground, cloud, cloud2, fpsLabel);
        plane.toFront();
        cloud.toFront();
        cloud2.toFront();

    }

    private void connect() {
        try {
            socket.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        plane.move();
        dataManager.updateShells();

        effects.forEach(e -> {
            panesToAdd.add(e);
            e.playAnimation();
        });
        effects.clear();

        hitBoxesToAdd.forEach(hitBox -> collisionManager.addHitBox(hitBox));
        hitBoxesToAdd.clear();

        collisionManager.checkCollision();



        weapons.forEach(weapon -> {
            if (weapon.getWeapontype() == WEAPON_TYPE.BULLET) {
                collisionManager.addHitBox(weapon.getHitBox());
                panesToAdd.add(weapon);
                weapon.start();
            } else if (weapon.getWeapontype() == WEAPON_TYPE.SHOTGUN) {
                weapon.start();
                weapon.setAlive(false);
                for (Bullet bullet : weapon.getBullets()) {
                    collisionManager.addHitBox(bullet.getHitBox());
                    panesToAdd.add(bullet);
                    bullet.start();
                }

            } else if (weapon.getWeapontype() == WEAPON_TYPE.ROCKET) {
                panesToAdd.add(weapon);
                weapon.start();

            }
        });
        weapons.clear();

        panesToAdd.forEach(pane -> {
            this.getChildren().add(pane);
            panesToCheck.add(pane);
        });
        panesToAdd.clear();
        cloud.toFront();
        cloud2.toFront();

        panesToCheck.forEach(alivePane -> {
            if (!alivePane.isAlive()) {
                panesToRemove.add(alivePane);
            }
        });
        this.getChildren().removeAll(panesToRemove);
        panesToCheck.removeAll(panesToRemove);
        panesToRemove.clear();
    }

    public void start() {
        connect();
        if (isFpsFixActive) {
            timerFunc = (time) -> {
                if (time - lastFrameTime > 16500000L) {
                    lastFrameTime = time;
                    update();
                    if (frames % 2 == 0) {
                        if (frames % 4 == 0) {
                            dataManager.getData();
                        } else {
                            dataManager.sendData();
                        }
                    }
                    fpsCounter++;
                    frames++;
                }
            };
        } else {

            timerFunc = (time1) -> {
                update();
                if (frames % 2 == 0) {
                    if (frames % 4 == 0) {
                        dataManager.getData();
                    } else {
                        dataManager.sendData();
                    }
                }
                fpsCounter++;
                frames++;
            };
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - fpsTime >= 1000000000L) {
                    fpsTime = now;
                    fpsLabel.setText("fps " + fpsCounter);
                    fpsCounter = 0;

                }
                timerFunc.run(now);


            }
        };


        timer.start();
    }

    public void setKey(KeyEvent event) {
        plane.setKey(event);
    }


}
