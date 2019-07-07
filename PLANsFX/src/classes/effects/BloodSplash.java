package classes.effects;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class BloodSplash extends Effect {
    public BloodSplash(double x, double y) {
        super(x, y);
    }

    @Override
    public void playAnimation() {
        Random rnd = new Random();
        for (int i = 0; i < 61; i++) {
            Circle c = new Circle(0, 0, 2);
            c.setFill(Color.RED);
            addChildren(c);
            TranslateTransition TT = new TranslateTransition(Duration.millis(800), c);
            TranslateTransition TT2 = new TranslateTransition(Duration.millis(400), c);
            FadeTransition FT = new FadeTransition();
            FT.setNode(c);
            FT.setToValue(0);
            FT.setDuration(Duration.millis(1000));
            FT.play();
            TT.setToX(c.getTranslateX() + rnd.nextInt(80) - 40);

            Double delay = rnd.nextDouble() * 300 + i * 10;
            TT.setDelay(Duration.millis(delay));
            TT2.setDelay(Duration.millis(delay));

            TT2.setToY(c.getTranslateY() - 10 - rnd.nextInt(30));
//            TT2.setOnFinished(event -> {
//                TT2.setDelay(Duration.ZERO);
//                TT2.setDuration(Duration.millis(300));
//                FT.setDuration(Duration.millis(200));
//                FT.play();
//                TT2.setToY(c.getTranslateY()+40);
//                //TT2.play();
//                TT2.setOnFinished(null);
//
//            });

            TT.play();
            TT2.play();
        }
        PauseTransition PT = new PauseTransition(Duration.millis(2000));
        PT.setOnFinished(event -> setAlive(false));
        PT.play();
    }
}
