package classes.effects.boom;

import classes.utils.CartoonText;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import classes.effects.Effect;


public class BoomText extends Effect {
    private CartoonText text;


    BoomText(double x, double y, double size) {
        text = new CartoonText("BOOM!",size);
        setTranslateX(x);
        setTranslateY(y);
        addChildren(text);
    }

    @Override
    public void playAnimation() {
        ScaleTransition ST = new ScaleTransition();
        ST.setNode(this);
        ST.setDuration(Duration.millis(200));
        ST.setFromX(0.5);
        ST.setFromY(0.5);
        ST.setToX(1);
        ST.setToY(1);

        ScaleTransition ST2 = new ScaleTransition();
        ST2.setNode(this);
        ST2.setDuration(Duration.millis(500));
        ST2.setFromX(1);
        ST2.setFromY(1);
        ST2.setToX(1.2);
        ST2.setToY(1.2);

        FadeTransition FT = new FadeTransition();
        FT.setNode(this);
        FT.setDuration(Duration.millis(200));
        FT.setToValue(0.2);
        FT.setOnFinished(event -> setAlive(false));

        ST.setOnFinished(event -> ST2.play());
        ST.play();
        ST2.setOnFinished(event -> {

            ST.setDuration(Duration.millis(200));
            ST.setFromX(1);
            ST.setFromY(1);
            ST.setToX(0);
            ST.setToY(0);
            ST.setOnFinished(event1 -> setAlive(false));
            ST.play();
            FT.play();

        });

    }
}
