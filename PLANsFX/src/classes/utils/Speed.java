package classes.utils;

public class Speed {
   private double currentSpeed;
   private double newSpeed;
   private int speedGear;
   private double res = 3;
   //private final int firstGear=1;
   private int countOfGears=4;
   private double points[] = {0,2.5,4,5.5};
   private boolean isSpeedReady=false;


   private float speedSteps[];

   public Speed(){
//       this.countOfGears=countOfGears;
//       this.currentSpeed=starterSpeed;
//       this.newSpeed=starterSpeed;
//       this.speedGear = starterGear;
       //this.speedSteps = new float[countOfGears];
       //currentSpeed*=res;
       //newSpeed*=res;
   }

    public void speedUp(){
        if(speedGear<countOfGears-1){
            speedGear++;
            newSpeed=points[speedGear];
            isSpeedReady = false;
        }
    }

    public void speedDown(){
        if (speedGear>=1){
            speedGear--;
            newSpeed=points[speedGear];
            isSpeedReady = false;
        }
    }

    public void updateSpeed(){
        if(newSpeed>currentSpeed){
            if (currentSpeed+0.01>newSpeed){
                currentSpeed = newSpeed;
                isSpeedReady = true;
            }
            else {
                switch (speedGear){
                    case 1:currentSpeed+=0.040f;break;
                    case 2:currentSpeed+=0.025f;break;
                    case 3:currentSpeed+=0.015f;break;
                    //case 4:currentSpeed+=0.03f;break;
                    //case 5:currentSpeed+=0.01f*res;break;

                }
            }



        }
        else {
            if (currentSpeed-0.01<newSpeed){
                currentSpeed = newSpeed;
                isSpeedReady = true;
            }
            else {
                switch (speedGear){
                    case 0:currentSpeed-=0.015f;break;
                    case 1:currentSpeed-=0.015f;break;
                    case 2:currentSpeed-=0.015f;break;
                    case 3:currentSpeed-=0.015f;break;
                    //case 4:currentSpeed-=0.03f;break;


                }
            }


        }

//        if (currentSpeed+0.01>newSpeed&&currentSpeed-0.01<newSpeed){
//            currentSpeed=newSpeed;
//        }
//        else if(newSpeed<currentSpeed){
//            currentSpeed-=0.005*res;
//        }

    }

    public boolean isSpeedReady() {
        return isSpeedReady;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public double getNewSpeed() {
        return newSpeed;
    }

    public void setNewSpeed(double newSpeed) {
        this.newSpeed = newSpeed;
    }

    public int getSpeedGear() {
        return speedGear;
    }

    public void setSpeedGear(int speedGear) {
        this.speedGear = speedGear;
    }

    public float[] getSpeedSteps() {
        return speedSteps;
    }

    public void setSpeedSteps(float[] speedSteps) {
        this.speedSteps = speedSteps;
    }

}
