package classes.gameUnits;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import classes.panes.AdvancedPane;

public class AirShip extends AdvancedPane {
    private Scoreboard scoreboard = new Scoreboard(59,27);
    private double yStarts[] = {150,200,350,300};
    private int startNum=-1;
    public AirShip(double x,double y){
        super(x,y);
        setImageInView(new Image("/resources/images/airShip/airShip.png"));
        addChildren(getView());
        addChildren(scoreboard);

    }
    public void start(){
        double xTarget = getWindowWidth()+getPrefWidth();
        TranslateTransition TT = new TranslateTransition();
        TT.setNode(this);
        TT.setFromX(getTranslateX());
        TT.setToX(xTarget);
        TT.setDuration(Duration.seconds(35));
        TT.setOnFinished(event -> {
            setTranslateY(getYStartPos());
            TT.setFromX(-200);
            TT.play();
        });
        TT.play();
    }
    private double getYStartPos(){
        startNum++;
        if (startNum>3){
            startNum=0;
        }
        return yStarts[startNum];
    }

    public class Scoreboard extends AdvancedPane{
        private ImageView views[] = {new ImageView(),new ImageView(),new ImageView(),new ImageView()};

        private String blueNumberPath = "/resources/images/airShip/blueNumbers/";
        private String redNumberPath = "/resources/images/airShip/redNumbers/";
        private String numbers[] ={"0.png","1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png",};
        private int leftScore;
        private int rightScore;
        Scoreboard(double x,double y){
            super(x,y);
            views[1].setTranslateX(17);
            views[2].setTranslateX(40);
            views[3].setTranslateX(57);

            views[0].setImage(new Image(redNumberPath+numbers[0]));
            views[1].setImage(new Image(redNumberPath+numbers[0]));
            views[2].setImage(new Image(blueNumberPath+numbers[0]));
            views[3].setImage(new Image(blueNumberPath+numbers[0]));


            getChildren().addAll(views);

        }
        public void increaseLeftPlayerPoints(){
            if (leftScore<99){
                leftScore++;
                updateScore();
            }

        }
        public void decreaseLeftPlayerPoints(){
            if (leftScore>0){
                leftScore--;
                updateScore();
            }

        }
        public void increaseRightPlayerPoints(){
            if (rightScore<99){
                rightScore++;
                updateScore();
            }

        }
        public void decreaseRightPlayerPoints(){
            if (rightScore>0){
                rightScore--;
                updateScore();
            }

        }

        private void updateScore(){

                if (leftScore>=10){
                    setNumberInView(0,leftScore/10);
                }
                setNumberInView(1,leftScore%10);
                if (rightScore>=10){
                    setNumberInView(2,rightScore/10);
                }
                setNumberInView(3,rightScore%10);

        }
        private void setNumberInView(int numberOfView,int number){
            //System.out.println("%%%%%%%%%%% "+numberOfView+" "+number);
            if (numberOfView<2){
                views[numberOfView].setImage(new Image(redNumberPath+numbers[number]));
            }
            else {
                views[numberOfView].setImage(new Image(blueNumberPath+numbers[number]));
            }
        }



    public int getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(int leftScore) {
        this.leftScore = leftScore;
        updateScore();
    }

    public int getRightScore() {
        return rightScore;
    }

    public void setRightScore(int rightScore) {
        this.rightScore = rightScore;
        updateScore();
    }
}

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
