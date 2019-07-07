package classes.effects;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Cloud extends Effect{
    private Path path = new Path();
    private double size =1;
    private double startX=0;
    private double startY=0;
    private int number = 0;
    private double duration = 40000;

    private TranslateTransition TT = new TranslateTransition(Duration.millis(duration),this);

    private double[] points ={-1.0, -18.0, 19.0, -36.0, 41.0, -20.0, 66.0, -51.0, 106.0, -57.0, 117.0,
            -12.0, 148.0, -9.0, 146.0, 34.0, 106.0, 35.0, 101.0, 52.0, 74.0, 54.0,
            58.0, 37.0, 40.0, 52.0, 15.0, 52.0, 1.0, 35.0, -25.0, 38.0, -32.0, 2.0, 0.0, 0.0,

    };
    private double[] points2 ={-37.0, -17.0, -13.0, -54.0, 34.0, -35.0, 54.0, -56.0, 85.0, -54.0, 90.0,
            -38.0, 137.0, -52.0, 160.0, 21.0, 89.0, 22.0, 76.0, 41.0, 34.0, 41.0, 30.0, 23.0, -4.0, 33.0,
            -19.0, 7.0, -7.0, -3.0,};

    private EventHandler toRight;
    private EventHandler toLeft;

    public Cloud(double x,double y,int number){
        super(x,y);
        setTranslateX(getTranslateX()+getRandom().nextInt(500));
        startX=x;
        startY=y;
        this.number = number;
        addChildren(path);
        path.setStrokeWidth(1.6);
        path.setStroke(Color.GREY);
        path.setFill(Color.WHITE);
        setOpacity(0.7);
        if (number==1){
            buildShape(points);
        }
        else
        buildShape(points2);

        toRight = (event)->{
            Double newDuration = duration+getRandom().nextInt(7000)-3000;
            changeYby(getRandom().nextInt(50)-25);
            if (getTranslateY()>getWindowHeight()/2){
                setTranslateY(startY);
            }

            TT.setDuration(Duration.millis(newDuration));
            TT.setToX(getWindowWidth()+50);
            TT.setOnFinished(toLeft);
            TT.play();
        };

        toLeft =(event)->{
            Double newDuration = duration+getRandom().nextInt(7000)-3000;
            changeYby(getRandom().nextInt(50)-25);
            if (getTranslateY()>getWindowHeight()/2){
                setTranslateY(startY);
            }

            TT.setDuration(Duration.millis(newDuration));
            TT.setToX(-150);
            TT.setOnFinished(toRight);
            TT.play();
        };


        start();
    }
    private void start(){

        TT.setDuration(Duration.ZERO);
        TT.setToX(getTranslateX()+1);
        setRandomDurection();
        TT.play();
    }
    private void setRandomDurection(){
        if (getRandom().nextBoolean()){
            TT.setOnFinished(toLeft);
        }
        else {
            TT.setOnFinished(toRight);
        }
    }
    private void buildShape(double [] points){
        MoveTo moveTo = new MoveTo(0,0);
        path.getElements().add(moveTo);
        int n = 0;
        for (int i=0;i<points.length/6;i++){
            CubicCurveTo QC = new CubicCurveTo();

            QC.setControlX1(points[n]*size );
            QC.setControlY1(points[n+1]*size );
            QC.setControlX2(points[n+2]*size );
            QC.setControlY2(points[n+3]*size );
            QC.setX(points[n+4]*size );
            QC.setY(points[n+5]*size );


            if (i==5){
                QC.setX(0);
                QC.setY(0);
            }

            n+=6;
            path.getElements().add(QC);
        }
    }
    @Override
    public void playAnimation() {

    }
}
