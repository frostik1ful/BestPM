package classes.effects.boom;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import classes.effects.Effect;
import classes.effects.shapes.SmokeShape;

public class BoomSmoke extends Effect {
    private SmokeShape form;
    private double size = 1;
    private Line pathLine;
    private int delay = 1000;
    private double speed = 1;

    BoomSmoke(Line pathLine, int delay, double speed) {


        this.pathLine = pathLine;
        this.delay = delay;
        this.speed = speed;
        form = new SmokeShape(0, 0, size);
        form.buildRandomShape();
        form.setRandomOrangeColor();

//        form.setCache(true);
//        form.setCacheHint(CacheHint.SPEED);

        //Color color = new Color(1,1,1,random.nextDouble());
        //form.setColor(Color.rgb(252,135+random.nextInt(50),60));

        getChildren().add(form);
        setOpacity(0);

    }

    @Override
    public void playAnimation() {
        setOpacity(0);
        PauseTransition PauseT = new PauseTransition();
        PauseT.setDuration(Duration.millis(delay));


        RotateTransition RT = new RotateTransition();
        RT.setDuration(Duration.seconds(speed));
        RT.setToAngle(60);

        PathTransition PT = new PathTransition();
        PT.setDuration(Duration.seconds(speed));
        PT.setPath(pathLine);
        PT.setCycleCount(1);


        ScaleTransition sT = new ScaleTransition();
        sT.setDuration(Duration.seconds(speed / 2));
        sT.setFromX(0.5);
        sT.setFromY(0.5);
        sT.setToX(1);
        sT.setToY(1);

        ScaleTransition sT2 = new ScaleTransition();
        sT2.setDuration(Duration.seconds(speed / 2));
        sT2.setFromX(1);
        sT2.setFromY(1);
        sT2.setToX(0.5);
        sT2.setToY(0.5);

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

    public void setColor(Color color) {
        this.form.setColor(color);
    }

}
