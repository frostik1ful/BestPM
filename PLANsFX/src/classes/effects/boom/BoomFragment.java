package classes.effects.boom;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import classes.effects.Effect;
import classes.effects.shapes.FragmentShape;


import java.util.Random;

public class BoomFragment extends Effect {
    private Line pathLine = new Line();
    private int delay = 1000;
    private int speed = 1;

    BoomFragment(double x, double y, int delay, int speed, Line line, double angle) {
        this.delay = delay;
        this.speed = speed;
        this.pathLine = line;
        Random random = getRandom();
        double height = 60 + random.nextInt(30);
        double width = 10 + random.nextInt(10);
        setTranslateXY(x - width / 2, y - height / 2);
        FragmentShape shape = new FragmentShape(height, width);
//        shape.setCache(true);
//        shape.setCacheHint(CacheHint.SPEED);

        shape.setColor(Color.rgb(225, 45 + random.nextInt(95), 0));
        getChildren().add(shape);
        this.getTransforms().add(new Rotate(angle, width / 2, height / 2));


    }

    @Override
    public void playAnimation() {
        PathTransition PT = new PathTransition();
        PT.setDelay(Duration.millis(delay));
        PT.setDuration(Duration.millis(speed));
        PT.setInterpolator(Interpolator.LINEAR);
        PT.setPath(pathLine);
        PT.setCycleCount(1);
        PT.setNode(this);
        PT.play();


        FadeTransition FT = new FadeTransition();
        FT.setDelay(Duration.millis(delay));
        FT.setDuration(Duration.millis(1000));
        FT.setToValue(0);
        FT.setNode(this);
        FT.play();
        PT.setOnFinished(event -> setAlive(false));

    }

}
