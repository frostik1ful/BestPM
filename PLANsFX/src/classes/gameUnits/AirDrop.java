package classes.gameUnits;


import interfaces.Colliding;
import interfaces.Onlineble;
import interfaces.Parachuting;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import classes.Multiplayer.pockets.AirDropPocket;
import classes.effects.BonusLabel;
import classes.effects.SmokeEffect;
import classes.humans.LeftHuman;
import classes.panes.FallingDownPane;
import classes.planes.LeftPlane;
import classes.utils.Bonus;
import classes.utils.HitBox;
import classes.utils.SmokeSpawner;


public class AirDrop extends FallingDownPane implements Colliding, Parachuting, Onlineble {
    private HitBox hitBox;
    private Bonus bonus;
    private Parachute parachute;
    private SmokeSpawner smokeSpawner;
    private boolean isEffectsStarted;
    private boolean isOnGround=false;
    public AirDrop(double x,double y){
        super(x,y);
        setImageInView(new Image("resources/images/airdrop.png"));
        hitBox = new HitBox(this,HitBox.HitBoxType.AIR_DROP,1);
        hitBox.setSize(26,26);
        hitBox.changeXby(2);
        hitBox.changeYby(-1);
        hitBoxesToAdd.add(hitBox);
        parachute = new Parachute(-15,-44,this);
        parachute.open();

        bonus = new Bonus();
        smokeSpawner = new SmokeSpawner(14,0);
        smokeSpawner.setDuration(120);
        smokeSpawner.setTime(4);
        smokeSpawner.setType(SmokeEffect.SmokeType.RED);
        addChildren(smokeSpawner);
        addChildren(getView());
        addChildren(hitBox);
        addChildren(parachute);
        dropDown();
        createPocket();

    }
    public AirDrop(double x,double y,byte bonusNumber){
        this(x,y);
        bonus.setBonusByNumber(bonusNumber);
    }

    public void dropDown(){
        setMaxYPower(1.5);
        super.dropDown();
    }
    @Override
    public void addCollision(HitBox hitBox) {
         switch (hitBox.getHitBoxType()){
            case HUMAN:
            case PLANE_NOSE:
                setAlive(false);
                this.hitBox.setAlive(false);
                stopSmokeAnimation();
                stopFallingDown();
                createBonusLabel(hitBox.getHitBoxParent());

                break;


            case AIR_DROP:
                parachute.setAlive(false);
                if (!isOnGround){
                    stopFallingDown();
                    setTranslateY(hitBox.getHitBoxParent().getTranslateY()-this.hitBox.getHitboxHeight());
                    startSmokeAnimation();
                    isOnGround=true;
                }
                break;
             case AMBAR:
             case GROUND:
                 parachute.setAlive(false);
                 stopFallingDown();
                 if (!isOnGround){
                     stopFallingDown();
                     setTranslateY(hitBox.getHitBoxParent().getTranslateY()-this.hitBox.getHitboxHeight()-5);
                     startSmokeAnimation();
                     isOnGround=true;
                 }
                break;
        }
    }
    protected void createBonusLabel(){
        BonusLabel bonusLabel = new BonusLabel(this.getTranslateX(),this.getTranslateY()-20,bonus.getBonusType().toString().toUpperCase());

//        if (bonus.getBonusType()== Bonus.BonusType.HOMINGROCKET){
//            bonusLabel.setString("ROCKET");
//        }

        effects.add(bonusLabel);
    }
    protected void createBonusLabel(Colliding damageable){
        if (damageable instanceof LeftHuman || damageable instanceof LeftPlane){
            createBonusLabel();
        }

    }
    @Override
    public void dropParachute() {
        setMaxYPower(8);
    }
    public void dropParachuteFromShell() {

        parachute.setAlive(false);
    }
    public void startSmokeAnimation() {
        smokeSpawner.start();
        isEffectsStarted=true;
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(5));
        pt.setOnFinished(event -> smokeSpawner.stop());
        pt.play();

    }
    private void stopSmokeAnimation(){
        smokeSpawner.stop();
        isEffectsStarted=false;
    }

    @Override
    public double getCollisionDamage() {
        return 0;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public boolean isEffectsStarted() {
        return isEffectsStarted;
    }
    public boolean isParachuteAlive(){
        return parachute.isAlive();
    }
    @Override
    public void finish() {
        stopSmokeAnimation();
        setAlive(false);
    }
    public void setAlive(boolean isAlive){
        if (!isAlive){
            hitBox.setAlive(false);
            parachute.setAlive(false);
        }
        super.setAlive(isAlive);
    }
    @Override
    public boolean isParentAlive() {
        return isAlive();
    }

    @Override
    public void createPocket() {
        AirDropPocket pocket = new AirDropPocket(this);
        pocketsTosend.put(pocket.getPocketId(),pocket);
    }
}
