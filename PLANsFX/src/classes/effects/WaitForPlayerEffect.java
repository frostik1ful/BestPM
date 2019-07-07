package classes.effects;

import classes.utils.CartoonText;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class WaitForPlayerEffect extends Effect {
    private CartoonText text;
    private Timeline timeline;
    public WaitForPlayerEffect(){
        text = new CartoonText("WAITING FOR PLAYER",30);
        setTranslateXY(windowW/2-(text.getText().length()*40/2),200);
        addChildren(text);
    }
    @Override
    public void playAnimation() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame k0 = new KeyFrame(Duration.millis(200),event ->text.setText("WAITING FOR PLAYER"));
        KeyFrame k1 = new KeyFrame(Duration.millis(400),event ->text.setText("WAITING FOR PLAYER."));
        KeyFrame k2 = new KeyFrame(Duration.millis(600),event -> text.setText("WAITING FOR PLAYER.."));
        KeyFrame k3 = new KeyFrame(Duration.millis(800),event -> text.setText("WAITING FOR PLAYER..."));


        timeline.getKeyFrames().addAll(k0,k1,k2,k3);
        timeline.play();
    }
    public void stop(){
        timeline.stop();
        setAlive(false);
    }
}
