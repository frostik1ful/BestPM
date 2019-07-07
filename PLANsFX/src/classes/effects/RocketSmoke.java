package classes.effects;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;



public class RocketSmoke extends Effect {
    private Image images[] = {new Image("/resources/images/rocket/rs1.png"), new Image("/resources/images/rocket/rs2.png"),
            new Image("/resources/images/rocket/rs3.png"), new Image("/resources/images/rocket/rs4.png"),
            new Image("/resources/images/rocket/rs5.png"),};
    private int frameNum = 0;
    private int imgNums[] = {1, 1, 2, 3, 4, 3, 3, 2, 1, 1, 0, 0};
    private int time = imgNums.length;
    private Timeline timeline = new Timeline();



    public RocketSmoke(double x, double y) {
        super(x,y);

        setView(new ImageView(images[frameNum]));
        getView().setFitWidth(21);
        getView().setFitHeight(21);
        getChildren().add(getView());
        setOpacity(0.8);

    }

    @Override
    public void playAnimation() {
        timeline.setCycleCount(time);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(80), event -> {
            getView().setRotate(getRandom().nextInt(100));
            getView().setImage(images[imgNums[frameNum]]);
            setOpacity(getOpacity()-0.02*frameNum);
            frameNum++;
        }));
        timeline.setOnFinished(event -> setAlive(false));
        timeline.play();


    }
}
