package classes.effects;

import classes.utils.CartoonText;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BonusLabel extends Effect {
    private CartoonText text;
    private double duration = 1200;


    public BonusLabel(double x, double y, String string) {
        super(x - string.length() / 2 * 5, y);
        text = new CartoonText(string,10);
        addChildren(this.text);
    }

    @Override
    public void playAnimation() {
        FadeTransition FT = new FadeTransition();
        FT.setNode(this);
        FT.setToValue(0);
        FT.setDuration(Duration.millis(duration));
        FT.setOnFinished(event -> setAlive(false));

        TranslateTransition TT = new TranslateTransition();
        TT.setNode(this);
        TT.setDuration(Duration.millis(duration));
        TT.setToY(getTranslateY() - 80);

        FT.play();
        TT.play();
    }

}
