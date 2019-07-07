package classes.parts;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class LeftNosePart extends Part {
    private double step = 0.5;

    public LeftNosePart(double x, double y) {
        super(x, y);
        setPartImage();
        getChildren().add(getView());
        play();
    }

    private void play() {

        TranslateTransition TT = new TranslateTransition();
        TT.setNode(this);
        TT.setDuration(Duration.millis(100));
        TT.setAutoReverse(true);
        TT.setCycleCount(Animation.INDEFINITE);
        TT.setFromY(getTranslateY() - step);
        TT.setToY(getTranslateY() + step);
        TT.play();

    }

    @Override
    public void setPartImage() {
        setImageInView(new Image("/resources/images/plane/left/leftNose.png"));

    }

}
