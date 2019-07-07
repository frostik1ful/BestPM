package classes.utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import classes.effects.Effect;
import classes.effects.SmokeEffect;
import classes.panes.AdvancedPane;

public class SmokeSpawner extends AdvancedPane {
    private SmokeEffect.SmokeType type;
    private double duration;
    private double size=1;
    private double time=1;
    private Timeline TL;
    private Circle point;
    private Effect effect;
    public SmokeSpawner(double x, double y){
        super(x,y);
        this.point = new Circle(1);
        getChildren().add(point);
        this.TL = new Timeline();
        setOpacity(0);
    }
    public SmokeSpawner(double x, double y,SmokeEffect.SmokeType type, double duration, double size){
        this(x, y);
        //setEffectsToScene(effectsToAdd);
        this.type=type;
        this.duration=duration;
        this.size=size;

    }

    public void start(){
        TL.setCycleCount(Animation.INDEFINITE);
        TL.getKeyFrames().add(new KeyFrame(Duration.millis(duration),event -> {
            Bounds bound = point.localToScene(point.getBoundsInLocal());

            double x = (bound.getMinX() + (bound.getMaxX() - bound.getMinX()) / 2);
            double y = (bound.getMinY() + (bound.getMaxY() - bound.getMinY()) / 2);
            x -= 12;
            y -= 5;
            Point2D point2D = new Point2D(x, y);
            effects.add(new SmokeEffect(point2D, type, size,time));



        }));
        TL.play();
    }
    public void stop(){
        TL.stop();
        TL.getKeyFrames().clear();
    }



    public SmokeEffect.SmokeType getType() {
        return type;
    }

    public void setType(SmokeEffect.SmokeType type) {
        this.type = type;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Circle getPoint() {
        return point;
    }

    public void setPoint(Circle point) {
        this.point = point;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
