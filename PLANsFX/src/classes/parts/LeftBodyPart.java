package classes.parts;

import javafx.animation.PauseTransition;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LeftBodyPart extends Part {
    ImageView whiteBody = new ImageView();

    public LeftBodyPart(double x, double y) {
        super(x, y);
        setPartImage();
        setWhitePartImage();
        whiteBody.setOpacity(0);
        getChildren().addAll(getView(), whiteBody);

    }

    public void blinkView() {
        setEffect(new Bloom(0.1));
        whiteBody.setOpacity(1);
        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.millis(100));
        PT.setOnFinished(event -> {
            whiteBody.setOpacity(0);
            setEffect(null);
        });
        PT.play();
    }

    @Override
    public void setSize(double height, double width) {
        super.setSize(height, width);
        setWhitePartImage();
        setView(whiteBody);
        super.setSize(height, width);

    }

    @Override
    public void setPartImage() {
        setImageInView(new Image("/resources/images/plane/left/leftBody.png"));

    }

    protected void setWhitePartImage() {
        whiteBody.setImage(new Image("/resources/images/plane/left/whiteLeftBody.png"));
    }
}
