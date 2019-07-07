package classes.effects;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LeftFaceEffect extends FaceEffect {


    public LeftFaceEffect() {
        super(-100, 50);

    }

    public LeftFaceEffect(byte jeerNum) {
        super(-100, 50, jeerNum);
    }

    @Override
    void setFaceImage() {
        face = new Image("/resources/images/human/face/leftFace.png");
        setImageInView(face);
        getView().setTranslateY(38);
        addChildren(getView());


    }

    @Override
    void setMouth() {
        mouthView = new ImageView(mouth1);
        addChildren(mouthView);
        mouthView.setTranslateX(40);
        mouthView.setTranslateY(105);

    }

    @Override
    void setTextCloud() {
        textCloud = new TextCloud(82, 18, new Image("/resources/images/human/face/leftCloud.png"));
        addChildren(textCloud);
    }


    @Override
    public void playAnimation() {
        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.seconds(1));
        PT.setOnFinished(event -> setAlive(false));

        Timeline line = new Timeline();
        line.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> mouthView.setImage(mouth1)));
        line.getKeyFrames().add(new KeyFrame(Duration.millis(600), event -> mouthView.setImage(mouth2)));
        line.setCycleCount(6);
        line.setOnFinished(event -> {
            mouthView.setImage(mouth2);
            PT.play();
        });


        TranslateTransition TT = new TranslateTransition();
        TT.setNode(this);
        TT.setFromX(getTranslateX());
        TT.setToX(10);
        TT.setDuration(Duration.millis(1500));
        TT.setOnFinished(event -> {
            textCloud.setOpacity(1);
            line.play();
        });
        TT.play();


    }


}
