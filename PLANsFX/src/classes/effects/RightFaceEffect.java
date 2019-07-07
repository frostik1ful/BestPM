package classes.effects;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;

public class RightFaceEffect extends FaceEffect{

    public RightFaceEffect(double x, double y) {
        super(x, y);
    }

    private RightFaceEffect(double x, double y, byte jeerNum) {
        super(x, y, jeerNum);
    }
    public RightFaceEffect(){
        super(0,0);
        setTranslateXY(getWindowWidth()-100,50);
    }
    public RightFaceEffect(byte jeerNum){
        this(0,0,jeerNum);
        setTranslateXY(getWindowWidth()-100,50);
    }

    @Override
    void setFaceImage() {
        face = new Image("/resources/images/human/face/rightFace.png");
        setImageInView(face);
        getView().setTranslateX(140);
        getView().setTranslateY(38);
        addChildren(getView());
    }

    @Override
    void setMouth() {
        mouthView = new ImageView(mouth3);
        addChildren(mouthView);
        mouthView.setTranslateX(160);
        mouthView.setTranslateY(105);
    }

    @Override
    void setTextCloud() {
        textCloud = new TextCloud(0,18,new Image("/resources/images/human/face/rightCloud.png"));
        addChildren(textCloud);
    }



    @Override
    public void playAnimation() {
        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.seconds(1));
        PT.setOnFinished(event -> setAlive(false));
        Timeline line = new Timeline();
        line.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> mouthView.setImage(mouth3)));
        line.getKeyFrames().add(new KeyFrame(Duration.millis(600),event -> mouthView.setImage(mouth4)));
        line.setCycleCount(6);
        line.setOnFinished(event ->{
            mouthView.setImage(mouth3);
            PT.play();
        } );


        TranslateTransition TT = new TranslateTransition();
        TT.setNode(this);
        TT.setFromX(getTranslateX());
        TT.setToX(getWindowWidth()-250);
        TT.setDuration(Duration.millis(1500));
        TT.setOnFinished(event -> {
            textCloud.setOpacity(1);
            line.play();
        });
        TT.play();
    }
}
