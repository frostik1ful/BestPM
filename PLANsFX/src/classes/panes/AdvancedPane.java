package classes.panes;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import classes.gameUnits.AirShip;
import classes.utils.CollisionManager;
import classes.utils.HitBox;
import classes.Multiplayer.pockets.OnlinePocket;
import classes.Multiplayer.pockets.PointsPocket;
import classes.effects.Effect;
import classes.weapons.Weapon;


import java.util.*;


public abstract class AdvancedPane extends Pane {
    public static int windowW;
    public static int windowH;
    public static Pane gameRoot;
    public static List<HitBox> hitBoxesToAdd;
    public static List<AlivePane> panesToAdd;
    public static List<Weapon> weaponsToAdd;
    public static LinkedList<Effect> effects;
    public static Map<Integer, OnlinePocket> pocketsTosend;

    public static AirShip.Scoreboard scoreboard;
    public static CollisionManager damageManager;

    private ImageView view = new ImageView();
    private double ResolutionSize = 1;
    private Random random = new Random();

    public AdvancedPane(double x, double y) {
        setTranslateXY(x, y);
    }

    public AdvancedPane() {

    }

    public void setTranslateXY(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public void changeYby(double y) {
        setTranslateY(getTranslateY() + y);
    }

    public void changeXby(double x) {
        setTranslateX(getTranslateX() + x);
    }

    public void changeLocalPositionToScene() {
        Point2D p = getPositionInScene();
        setTranslateXY(p.getX(), p.getY());
    }

    public Point2D getPositionInScene() {
        Bounds bounds = this.localToScene(this.getBoundsInLocal());
        return new Point2D(bounds.getMinX(), bounds.getMinY());
    }

    public Point2D getCenterOfPane() {
        double x = getTranslateX()+getPrefWidth()/2;
        double y = getTranslateY()+getPrefHeight()/2;
//        Bounds bounds = this.getBoundsInLocal();
//        double x = (bounds.getMinX() + (bounds.getMaxX() - bounds.getMinX()) / 2);
//        double y = (bounds.getMinY() + (bounds.getMaxY() - bounds.getMinY()) / 2);
        return new Point2D(x, y);
    }

    public void addChildren(Node node) {
        getChildren().add(node);
    }

    public void removeChildren(Node node) {
        getChildren().remove(node);
    }

    public void setImageInView(Image image) {
        this.view.setImage(image);
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }


    public void increaseLeftPlayerPoints() {

        scoreboard.increaseLeftPlayerPoints();
        sendPointsData();
    }

    public void decreaseLeftPlayerPoints() {

        scoreboard.decreaseLeftPlayerPoints();
        sendPointsData();
    }

    public void increaseRightPlayerPoints() {

        scoreboard.increaseRightPlayerPoints();
        sendPointsData();
    }

    public void decreaseRightPlayerPoints() {

        scoreboard.decreaseRightPlayerPoints();
        sendPointsData();
    }

    private void sendPointsData() {
        PointsPocket pocket = new PointsPocket(0, scoreboard.getLeftScore(), scoreboard.getRightScore());
        pocketsTosend.put(pocket.getPocketId(), pocket);
    }

    protected static void setPointsData(int leftPoints, int rightPoints) {
        scoreboard.setLeftScore(leftPoints);
        scoreboard.setRightScore(rightPoints);

    }

    public double getResolutionSize() {
        return ResolutionSize;
    }

    public void setResolutionSize(double resolutionSize) {
        ResolutionSize = resolutionSize;
    }

    protected double getWindowHeight() {
        return windowH;
    }


    protected double getWindowWidth() {
        return windowW;
    }


    protected Random getRandom() {
        return random;
    }


}
