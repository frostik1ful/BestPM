package classes.panes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import interfaces.Function;


abstract public class FallingDownPane extends AlivePane {
    private Timeline dropTimeline = new Timeline();
    private RotateTransition RT = new RotateTransition();

    private double maxYPower = 10;

    private double yPower = 0;
    private double xPower = 0;

    private double yPowerStep = 0.1;
    private double xPowerStep = 0.1;

    private boolean settsChanged = true;

    private Function stabilizeX = () -> {
    };


    public FallingDownPane(double x, double y) {
        super(x, y);
        setTimeLine();
    }

    protected FallingDownPane() {
        setTimeLine();
    }

    private void setTimeLine() {
        dropTimeline.setCycleCount(Animation.INDEFINITE);
        dropTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), event -> {

            changeXby(xPower);
            changeYby(yPower);
            if (settsChanged) {
                settsChanged = false;
                if (yPower < maxYPower) {
                    if ((yPower + yPowerStep) > maxYPower) {
                        yPower = maxYPower;
                    } else {
                        settsChanged = true;
                        yPower += yPowerStep;
                    }

                }

                if (yPower > maxYPower) {
                    if ((yPower - yPowerStep) < maxYPower) {
                        yPower = maxYPower;
                    } else {
                        settsChanged = true;
                        yPower -= yPowerStep;
                    }
                }
                stabilizeX.run();

                if (xPower == 0 && yPower == 0) {
                    stopFallingDown();
                }
            }

        }));
    }

    public void dropDown() {
        dropTimeline.play();
    }

//    public void rotateToPurpose(double duration) {
//        double r = getRotate();
//        double rotation = 90;
//        if (r >= -180 && r < -90)
//            rotation = -270;
//
//        RT.setNode(this);
//        RT.setToAngle(rotation);
//        RT.setDuration(Duration.millis(duration));
//        RT.play();
//
//    }

    protected void rotateToPurpose(double purpose, double duration) {
        double r = getRotate();
        if (r >= -180 && r < -90 && purpose != 0)
            purpose += -360;


        RT.setNode(this);
        RT.setToAngle(purpose);
        RT.setDuration(Duration.millis(duration));
        RT.play();

    }

    protected void stopRotateToPurpose() {
        RT.stop();
    }

    protected double getDropYPower() {
        return yPower;
    }

    public void setDropYPower(double yPower) {
        this.yPower = yPower;
        updateDropSettings();
    }

    protected double getDropXPower() {
        return xPower;
    }

    public void setDropXPower(double xPower) {
        this.xPower = xPower;
        if (xPower > 0) {
            this.stabilizeX = () -> {
                if (this.xPower > 0) {
                    this.xPower -= xPowerStep;
                    settsChanged = true;
                } else {
                    this.xPower = 0;
                }
            };
        } else {
            this.stabilizeX = () -> {
                if (this.xPower < 0) {
                    this.xPower += xPowerStep;
                    settsChanged = true;
                } else {
                    this.xPower = 0;
                }
            };
        }
        updateDropSettings();
    }

    public void stopFallingDown() {
        dropTimeline.stop();
        updateDropSettings();
    }


    private void updateDropSettings() {
        settsChanged = true;
    }


    protected void setMaxYPower(double maxYPower) {
        this.maxYPower = maxYPower;
        updateDropSettings();
    }


    protected void setYPowerStep(double yPowerStep) {
        this.yPowerStep = yPowerStep;
        updateDropSettings();
    }


    protected void setXPowerStep(double xPowerStep) {
        this.xPowerStep = xPowerStep;
        updateDropSettings();
    }


}
