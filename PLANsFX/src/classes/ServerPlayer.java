package classes;

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
import classes.effects.*;

import classes.gameUnits.AirShip;
import classes.gameUnits.Ambar;
import classes.gameUnits.Ground;
import classes.panes.AdvancedPane;
import classes.panes.AlivePane;
import classes.planes.LeftPlane;
import classes.planes.Plane;
import classes.utils.Server;
import classes.utils.AirDropSpawner;
import classes.utils.CollisionManager;
import classes.utils.HitBox;
import classes.weapons.Bullet;
import classes.weapons.WEAPON_TYPE;
import classes.weapons.Weapon;

import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerPlayer extends Pane {
    private AnimationTimer timer;
    private CollisionManager collisionManager = new CollisionManager();
    private int port;
    private boolean isFpsFixActive;
    private Label fpsLabel = new Label();

    private Cloud cloud = new Cloud(50, 140, 1);
    private Cloud cloud2 = new Cloud(550, 190, 2);
    private AirShip airShip = new AirShip(200, 100);
    private AirDropSpawner airDropSpawner;
    private Ambar ambar;
    private Ground ground = new Ground();
    public static Plane plane;
    private WaitForPlayerEffect waitEffect;

    private long frames = 1;
    private int fpsCounter;
    private final int windowW = 1280;
    private final int windowH = 705;

    private List<HitBox> hitBoxesToAdd = new LinkedList<>();
    private List<AlivePane> panesToAdd = new LinkedList<>();
    private List<AlivePane> panesToCheck = new LinkedList<>();
    private List<AlivePane> panesToRemove = new LinkedList<>();
    private List<Weapon> weapons = new LinkedList<>();
    private LinkedList<Effect> effects = new LinkedList<>();

    private Map<Integer, OnlinePocket> pocketsToSend = new LinkedHashMap<>();


    private long lastFrameTime;
    private long fpsTime;
    public static Socket socket = new Socket();
    private DataManager dataManager = new DataManager(pocketsToSend, socket);

    private TimerFunction timerFunc;
    private Server server;

    public static Label col = new Label("COLLISION ");

    ServerPlayer(int port, boolean isFpsFixActive) {
        this.port = port;
        this.isFpsFixActive = isFpsFixActive;
    }

    void run() {
        createContent();
        start();
    }

    public void stop() {
        timer.stop();
        server.stopServer();

    }

    private void createContent() {

        //ShapeCreator cs = new ShapeCreator(350, 300);
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Comical Cartoon.ttf"), 1);

        AdvancedPane.windowW = windowW;
        AdvancedPane.windowH = windowH;
        AdvancedPane.hitBoxesToAdd = hitBoxesToAdd;
        AdvancedPane.panesToAdd = panesToAdd;
        AdvancedPane.effects = effects;
        AdvancedPane.weaponsToAdd = weapons;
        AdvancedPane.pocketsTosend = pocketsToSend;
        AdvancedPane.damageManager = collisionManager;
        AdvancedPane.scoreboard = airShip.getScoreboard();

        airDropSpawner = new AirDropSpawner();


        plane = new LeftPlane();
        plane.createPocket();
        ambar = new Ambar();
        waitEffect = new WaitForPlayerEffect();
        dataManager.setMainPlane(plane);
        airShip.start();
        collisionManager.addHitBox(ambar.getHitBox());
        collisionManager.addHitBox(ground.getHitBox());

        panesToAdd.add(plane);

        fpsLabel.setTranslateX(windowW - 50);

        this.setPrefSize(windowW, windowH);
        this.getChildren().addAll(new ImageView(new Image("/resources/images/back.png")), airShip, ambar, ground,
                cloud, cloud2, fpsLabel);
        plane.toFront();
        cloud.toFront();
        cloud2.toFront();

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
        lastFrameTime = System.nanoTime();

        effects.add(waitEffect);
        update();
        waitEffect.toFront();

        server = new Server(dataManager, port);

        if (isFpsFixActive) {
            TimerFunction timerFunc2 = (time) -> {
                if (time - lastFrameTime > 16500000L) {
                    lastFrameTime =time;
                    update();
                    if (frames % 2 == 0) {
                        if (frames % 4 == 0) {
                            dataManager.sendData();
                        } else {
                            dataManager.getData();
                        }
                    }
                    fpsCounter++;
                    frames++;
                }
            };

            timerFunc = (time) -> {
                if (time- lastFrameTime > 16500000L) {

                    lastFrameTime =time;
                    if (frames % 2 == 0 && socket.isConnected()) {
                        timerFunc = timerFunc2;
                        waitEffect.stop();
                        airDropSpawner.start();
                        if (frames % 4 == 0) {
                            dataManager.sendData();
                        } else {
                            dataManager.getData();

                        }
                    }
                    fpsCounter++;
                    frames++;
                }
            };
        } else {
            TimerFunction timerFunc2 = (time1) -> {
                update();
                if (frames % 2 == 0) {
                    if (frames % 4 == 0) {
                        dataManager.sendData();
                    } else {
                        dataManager.getData();
                    }
                }
                fpsCounter++;
                frames++;
            };

            timerFunc = (time1) -> {
                if (frames % 2 == 0 && socket.isConnected()) {
                    timerFunc = timerFunc2;
                    waitEffect.stop();
                    airDropSpawner.start();
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

        server.start();
        timer.start();

    }

    public void setKey(KeyEvent event){
        plane.setKey(event);
    }
}
