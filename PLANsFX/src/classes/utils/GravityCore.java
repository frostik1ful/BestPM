package classes.utils;

public class GravityCore {
    private Speed speed;
    private double currentGravity;
    private double newGravity;
    private double gravityStep;
    private double gravPoints[] = {0,2.5,3,4,6.5};
    private int speedGear;
    public  GravityCore(Speed speed){
        this.speed = speed;
    }


    private void changeGravity(int d){

        switch (speedGear){
            case 0:
                gravityStep = 0.01;
                newGravity = gravPoints[1];

            break;
            case 1:
                if (d==0){
                    gravityStep = 0.02;
                }
                else {
                    gravityStep = 0.01 * d;

                }
                newGravity = gravPoints[d];
                break;
            case 2:
                if (d==0){
                    gravityStep = 0.02;
                }
                else {
                    gravityStep = 0.013 * d;

                }
                newGravity = gravPoints[d];
                break;
            case 3:
                if (d==0){
                    gravityStep=0.08;
                }
                else {
                    gravityStep=0.02*d;

                }
                newGravity = gravPoints[d];

                break;
//            case 4:
//                gravityStep=0.01*d;
//                newGravity = gravPoints[d];
//                break;
        }
        updateGravity();

    }
    private void updateGravity(){
        if (currentGravity+gravityStep>newGravity&&currentGravity-gravityStep<newGravity){
            currentGravity=newGravity;
        }
        else {
            if (newGravity>currentGravity){
                currentGravity+=gravityStep;
            }
            else {
                currentGravity-=gravityStep;
            }
        }

    }
    public void update(int rotatePosition) {
        speedGear = speed.getSpeedGear();
        switch (rotatePosition) {
            case 0:
                changeGravity(4);
                break;
            case 1:
                changeGravity(3);
                break;
            case 2:
                changeGravity(1);
                break;
            case 3:
                changeGravity(0);
                break;
            case 4:
                changeGravity(0);
                break;

            case 5:
                changeGravity(0);
                break;
            case 6:
                changeGravity(1);
                break;
            case 7:
                changeGravity(3);
                break;
            case 8:
                changeGravity(4);
                break;

            case 9:
                changeGravity(3);
                break;
            case 10:
                changeGravity(1);
                break;
            case 11:
                changeGravity(0);
                break;
            case 12:
                changeGravity(0);
                break;

            case 13:
                changeGravity(0);
                break;
            case 14:
                changeGravity(1);
                break;
            case 15:
                changeGravity(3);
                break;
        }
    }

    public double getCurrentGravity() {
        return currentGravity;
    }

}
