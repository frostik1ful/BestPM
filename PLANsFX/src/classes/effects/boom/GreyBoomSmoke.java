package classes.effects.boom;

import javafx.animation.*;
import javafx.scene.CacheHint;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import classes.effects.Effect;
import classes.effects.shapes.SmokeShape;


public class GreyBoomSmoke extends Effect {
    private double size = 1.4;
    private Line pathLine;
    private int delay = 1000;
    private double speed = 1;

    GreyBoomSmoke(Line pathLine, int delay, double speed) {


        this.pathLine = pathLine;
        this.delay = delay;
        this.speed = speed;
        SmokeShape shape = new SmokeShape(0, 0, size);
        shape.buildRandomShape();
        shape.setRandomGreyColor();

        shape.setCache(true);
        shape.setCacheHint(CacheHint.SPEED);

        getChildren().add(shape);
        setOpacity(0);

    }


    @Override
    public void playAnimation() {
        setOpacity(0);
        PauseTransition PauseT = new PauseTransition();
        PauseT.setDuration(Duration.millis(delay));


        RotateTransition RT = new RotateTransition();
        RT.setDuration(Duration.seconds(speed));
        RT.setToAngle(10);

        PathTransition PT = new PathTransition();
        PT.setDuration(Duration.seconds(speed));
        PT.setPath(pathLine);
        PT.setCycleCount(1);


        ScaleTransition sT = new ScaleTransition();
        sT.setDuration(Duration.seconds(speed / 3));
        sT.setFromX(0.5);
        sT.setFromY(0.5);
        sT.setToX(1);
        sT.setToY(1);

        ScaleTransition sT2 = new ScaleTransition();
        sT2.setDuration(Duration.seconds(speed / 2));
        sT2.setFromX(1);
        sT2.setFromY(1);
        sT2.setToX(0.2);
        sT2.setToY(0.2);

        FadeTransition FT = new FadeTransition();
        FT.setDuration(Duration.seconds(0.7));
        FT.setToValue(0);

        ParallelTransition pT2 = new ParallelTransition(this, sT2, FT);
        pT2.setOnFinished(event -> setAlive(false));
        ParallelTransition pT = new ParallelTransition(this, sT, PT, RT);
        sT.setOnFinished(event -> pT2.play());
        PauseT.setOnFinished(event -> {
            setOpacity(1);
            pT.play();

        });
        PT.setOnFinished(event -> setAlive(false));
        PauseT.play();
    }
}
