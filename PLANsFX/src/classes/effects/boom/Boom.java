package classes.effects.boom;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import classes.effects.Effect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boom extends Effect {

    private List<Effect> subEffects = new ArrayList<>();
    private TranslateTransition TT = new TranslateTransition();

    public Boom(double x, double y) {
        super(x, y);

//        setCacheShape(true);
//        setCache(true);
//        setCacheHint(CacheHint.QUALITY);

        TT.setNode(this);
        TT.setToX(getTranslateX() + 4);
        TT.setDuration(Duration.millis(60));
        TT.setCycleCount(12);
        TT.setAutoReverse(true);
        //.scene.effect.Effect glow = new Glow(0.3);
        Random random = new Random();
        for (int i = 1; i <= 8; i++) {
            Line line = new Line();
            line.setStartX(0);
            line.setStartY(0);
            line.setEndX(0);
            line.setEndY(-140);
            line.getTransforms().add(new Rotate(i * 36 + random.nextInt(30), line.getStartX(), line.getStartY()));
            GreyBoomSmoke greySmoke = new GreyBoomSmoke(line, 300 + random.nextInt(300), random.nextDouble() + 1);
            subEffects.add(greySmoke);
            getChildren().add(greySmoke);

        }

        for (int i = 1; i <= 10; i++) {
            Line line = new Line();
            line.setStartX(0);
            line.setStartY(0);
            line.setEndX(0);
            line.setEndY(-100);
            line.getTransforms().add(new Rotate(i * 36 + random.nextInt(30), line.getStartX(), line.getStartY()));
            BoomSmoke smoke = new BoomSmoke(line, random.nextInt(300), random.nextDouble() + 0.5);
            //smoke.setEffect(glow);
            subEffects.add(smoke);
            getChildren().add(smoke);

        }

        for (int i = 1; i <= 18; i++) {
            double angle = i * 20 + random.nextInt(30);
            Line line = new Line();
            line.setStartX(random.nextInt(40) - 20);
            line.setStartY(random.nextInt(20) - 10);
            line.setEndX(random.nextInt(40) - 20);
            line.setEndY(line.getStartY() - 80);
            line.getTransforms().add(new Rotate(angle, 0, 0));
            BoomFragment fragment = new BoomFragment(random.nextDouble() * 10, random.nextDouble() * 10, random.nextInt(100), random.nextInt(500), line, angle);
            //fragment.setEffect(glow);
            subEffects.add(fragment);
            getChildren().add(fragment);


        }
        BoomText boomText = new BoomText(-71, 5, 20);
        subEffects.add(boomText);
        getChildren().add(boomText);


    }

    @Override
    public void playAnimation() {
        TT.play();
        subEffects.forEach(Effect::playAnimation);

        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.seconds(2));
        PT.setOnFinished(event -> setAlive(false));
        PT.play();


    }


}
