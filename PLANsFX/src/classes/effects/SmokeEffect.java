package classes.effects;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import classes.effects.shapes.SmokeShape;

import java.util.Random;

public class SmokeEffect extends Effect {
    private double x;
    private double y;
    private double size = 0.3;
    private SmokeShape smokeShape;
    private Random rnd = new Random();
    private double time = 0.5;
    private double pathSize = 40;

    public SmokeEffect(Point2D point2D, SmokeType smokeType, double sizeModifier, double timeModifier) {
        this(point2D, smokeType, sizeModifier);
        this.time *= timeModifier;
    }

    public SmokeEffect(Point2D point2D, SmokeType smokeType, double sizeModifier) {
        this.x = point2D.getX();
        this.y = point2D.getY();
        this.size *= sizeModifier;


        switch (smokeType) {
            case LIGHTGREY:
                smokeShape = new SmokeShape(0, 0, size);
                smokeShape.setRandomLightGreyColor();
                break;
            case DARKGREY:
                smokeShape = new SmokeShape(0, 0, size);
                if (rnd.nextInt(100) > 80) {
                    pathSize /= 2;
                    smokeShape.setSize(0.2);
                    smokeShape.setRandomOrangeColor();
                } else
                    smokeShape.setRandomDarkGreyColor();
                break;
            case RED:
                smokeShape = new SmokeShape(0, 0, size);
                smokeShape.setColor(Color.RED);
                smokeShape.setOpacity(0.7);
                break;
        }
        smokeShape.buildShape();
        smokeShape.setStrokeWidth(0.5);
        setTranslateX(x + rnd.nextInt(20) - 10);
        setTranslateY(y);
        smokeShape.setRotate(rnd.nextInt(100));
        getChildren().add(smokeShape);
    }

    @Override
    public void playAnimation() {
        ScaleTransition ST = new ScaleTransition();
        ST.setDuration(Duration.seconds(time));
        //sT.setNode(this);
        ST.setFromX(1);
        ST.setFromY(1);
        ST.setToX(2);
        ST.setToY(2);


        FadeTransition FT = new FadeTransition();
        //FT.setNode(this);
        FT.setDuration(Duration.seconds(time));
        FT.setToValue(0);

        TranslateTransition TT = new TranslateTransition();

        TT.setToY(getTranslateY() - pathSize);
        TT.setDuration(Duration.seconds(time));


        ParallelTransition pT = new ParallelTransition(this, ST, FT, TT);
        pT.setOnFinished(event -> setAlive(false));
        pT.play();
    }

    public enum SmokeType {
        LIGHTGREY, DARKGREY, RED
    }
}
