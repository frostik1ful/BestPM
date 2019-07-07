package classes.planes;

import classes.utils.*;
import interfaces.Colliding;
import interfaces.Onlineble;
import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import classes.Multiplayer.pockets.PlanePocket;
import classes.effects.BloodSplash;
import classes.effects.Effect;
import classes.effects.boom.Boom;
import classes.effects.SmokeEffect;
import classes.gameUnits.AirDrop;
import classes.gameUnits.ScrapMetalLeft;
import classes.humans.Human;
import classes.panes.FallingDownPane;
import classes.parts.*;
import classes.weapons.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Plane extends FallingDownPane implements Colliding, Onlineble {
    private Speed speed = new Speed();//5,1,0
    private GravityCore gravityCore = new GravityCore(speed);
    private Weapon selectedWeapon = new Bullet();
    private final int MAXHEALTH = 3;
    private int health = MAXHEALTH;
    private int shootDelay = 60;
    private int rotateDelay = 5;
    private double height = 35;//35 44
    private double width = 60;//70 83
    private float speedX = 1;
    private float speedY = 0;
    private boolean humanInPlane = true;


    Circle shootCircle = new Circle();
    FakeRocket fakeRocket;
    ScrapMetalLeft scrapMetal;
    Part nosePart;
    Circle bcirk;
    LeftArmorBox armorBox;
    Human human;
    HitBox bodyBox;
    HitBox noseBox;
    HitBox headBox;
    SmokeSpawner spawner;
    LeftBodyPart bodyPart;
    List<HitBox> hitBoxes = new ArrayList<>();
    int rotatePosition = 4;
    boolean isNoseOpen;

    public Plane() {
        this(0, 0);
    }

    public Plane(double x, double y) {
        super(x, y);
        setParts();
        speed.speedUp();
        speed.speedUp();
        hitBoxesToAdd.addAll(hitBoxes);

    }

    abstract void setParts();


    public void setKey(KeyEvent event) {
        if (humanInPlane) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (event.getCode()) {
                    case A:
                        rotateLeft();
                        break;
                    case D:
                        rotateRight();
                        break;
                    case W:
                        speed.speedUp();
                        break;
                    case S:
                        speed.speedDown();
                        break;
                    case CONTROL:
                        shoot();
                        break;
                    case SPACE:
                        catapult();
                        break;

//                    case UP:
//                        setTranslateY(getTranslateY() - 2);
//                        break;
//                    case DOWN:
//                        setTranslateY(getTranslateY() + 2);
//                        break;
//                    case LEFT:
//                        setTranslateX(getTranslateX() - 2);
//                        break;
//                    case RIGHT:
//                        setTranslateX(getTranslateX() + 2);
//                        break;
//                    case Q:
//                        //switchWeapon();
//                        break;
//                    case A:
//                        rotateLeft();
//                        break;
//                    case D:
//                        rotateRight();
//                        break;
//                    case W:
//                        speed.speedUp();
//                        break;
//                    case S:
//                        speed.speedDown();
//                        break;
//                    case R:
//                        human.startCreatingNewPlane();
//                        break;
//                    case K:
//                        shoot();
//                        break;
//                    case L:
//                        addDamage(1, true);
//                        break;
//                    case T:
//                        launchHomingRocket();
//                        break;
//                    case Y:
//                        selectedWeapon = new ShotGun();
//                        break;
//                    case U:
//                        addArmor(1);
//                        break;
//                    case P:
//                        catapult();
//
//                        break;
                }
            }
        } else {
            human.setKey(event);
        }
    }

    @Override
    public void dropDown() {
        setDropXPower(getHorizontalSpeed());
        setXPowerStep(0.01);
        setDropYPower(getVerticalSpeed());
        super.dropDown();
    }

    private void catapult() {
        humanInPlane = false;
        human.changeLocalPositionToScene();
        human.setRotate(getRotate());
        human.catapult(rotatePosition);
        panesToAdd.add(human);
        dropDown();
        this.toFront();

    }

    private void launchHomingRocket() {
        if (!isNoseOpen) {
            double oldSpeed = speed.getCurrentSpeed();
            speed.setNewSpeed(0.2);
            isNoseOpen = true;
            openCloseNose();
            startRocket(oldSpeed);
        }
    }

    public abstract void openCloseNose();


    private void startRocket(double oldSpeed) {
        PauseTransition PT = new PauseTransition();
        PT.setDuration(Duration.millis(650));
        PT.setOnFinished(event -> {

            Line startLine = getShootingLine(getRotationAngle(), 150);
            Rocket rocket = new Rocket();
            rocket.setSettings(getRotationAngle());
            rocket.setLine(startLine);
            weaponsToAdd.add(rocket);

            selectedWeapon = new Bullet();
            speed.setNewSpeed(oldSpeed);
        });
        PT.play();

    }

    private void stopEffects() {
        spawner.stop();
    }

    protected void dropScrap() {
        scrapMetal.setTranslateXY(getTranslateX(), getTranslateY());
        if (isHumanInPlane()){
            scrapMetal.setDropXPower(getHorizontalSpeed());
            scrapMetal.setDropYPower(getVerticalSpeed());
        }
        else {
            scrapMetal.setDropXPower(getDropXPower());
            scrapMetal.setDropYPower(getDropYPower());
        }

        scrapMetal.setRotate(scrapMetal.getRotate() + getRotate());
        scrapMetal.createPocket();
        panesToAdd.add(scrapMetal);
        hitBoxesToAdd.add(scrapMetal.getBodyBox());
        scrapMetal.dropDown();
    }

    protected abstract void dieFromFall();

    protected abstract void dieFromEnemy();
    protected void restart(){
        if (isHumanInPlane()){
            human.startCreatingNewPlane();
        }
    }
    protected void blowUp() {
        restart();
        setAlive(false);
        stopEffects();
        dropScrap();
        stopFallingDown();
        hitBoxes.forEach(hitBox -> hitBox.setAlive(false));
        effects.add(new Boom(getTranslateX() + 50, getTranslateY() + 10));



    }

    public void addDamage(double incomingDamage, boolean isDamageFromEnemy) {
        int damage = (int) incomingDamage;
        if (getAllHealth() - incomingDamage <= 0) {
            removeHealth(damage);
        } else {
            if (getArmorLeft() > 0) {
                for (int i = 0; i < damage; i++) {
                    armorBox.addOneDamage();
                    damage--;
                }
            }
            if (damage > 0) {
                removeHealth(damage);
                blink();
                updateEffectTimeLime();
            }
        }
    }

    protected void createBloodSplash() {
        human.setOpacity(0);
        human.setAlive(false);
        humanInPlane = false;
        Effect splash = new BloodSplash(headBox.getTranslateX(), headBox.getTranslateY() + 5);
        addChildren(splash);
        splash.playAnimation();

        dropDown();
    }

    public void addHeadShot() {
        if (isHumanInPlane()) {
            createBloodSplash();
            human.startCreatingNewPlane();
        }

    }

    private void blink() {
        bodyPart.blinkView();
    }

    private void updateEffectTimeLime() {
        switch (health) {
            case 3:
                spawner.stop();
                break;
            case 2:
                spawner.stop();
                spawner.setDuration(150);
                spawner.setType(SmokeEffect.SmokeType.LIGHTGREY);
                spawner.setSize(1);
                spawner.start();

                break;
            case 1:
                spawner.stop();
                spawner.setDuration(70);
                spawner.setType(SmokeEffect.SmokeType.DARKGREY);
                spawner.setSize(1.5);
                spawner.start();

                break;

        }
    }


    private void updateHealthStatus() {
        if (isAlive() && health == 0) {
            dieFromEnemy();
        }
        updateEffectTimeLime();
    }

    @Override
    public void addCollision(HitBox hitBox) {
        switch (hitBox.getHitBoxType()) {
            case BULLET:
            case ROCKET:
                addDamage(hitBox.getCollisionDamage(), true);
                break;
            case GROUND:
            case AMBAR:
                dieFromFall();
                break;
            case AIR_DROP:
                AirDrop airDrop = (AirDrop) hitBox.getHitBoxParent();
                addBonus(airDrop);
                break;

        }
    }

    @Override
    public double getCollisionDamage() {
        return 0;
    }

    private void shoot() {
        if (shootDelay == 0 && isAlive()) {

            switch (selectedWeapon.getWeapontype()) {
                case BULLET:
                    selectedWeapon = new Bullet();
                    selectedWeapon.setLine(getShootingLine(getRotationAngle(), 2000));
                    weaponsToAdd.add(selectedWeapon);
                    break;
                case SHOTGUN:
                    selectedWeapon.setShotGunLines(getShootingLine(getRotationAngle() - 12.5f, 2000), getShootingLine(getRotationAngle(), 2000), getShootingLine(getRotationAngle() + 12.5f, 2000));
                    weaponsToAdd.add(selectedWeapon);
                    break;

                case ROCKET:
                    launchHomingRocket();
                    break;
            }
            selectedWeapon.decreaseAmmo();

            if (selectedWeapon.getWeapontype() != WEAPON_TYPE.BULLET && selectedWeapon.getAmmoLeft() == 0) {
                selectedWeapon = new Bullet();

            }

            shootDelay = 20;
        }
    }

    private Line getShootingLine(double angle, double length) {
        Line line = new Line();

        Bounds bounds = shootCircle.localToScene(shootCircle.getBoundsInLocal());


        line.setStartX(bounds.getMinX());
        line.setStartY(bounds.getMinY());

        line.setEndX(line.getStartX());
        line.setEndY(line.getStartY() - length);
        line.getTransforms().add(new Rotate(angle, line.getStartX(), line.getStartY()));

        //weaponsLinesList.add(line);
        return line;
    }



    private void rotateRight() {

        if (rotateDelay == 0) {
            if (rotatePosition < 15) {
                rotatePosition++;
            } else {
                rotatePosition = 0;
            }
            double rotate = getRotate() + 22.5;
            if (rotate > 180) {
                rotate -= 360;
            }

            setRotate(rotate);
            updateDirection();
            rotateDelay = 5;
        }
    }

    private void rotateLeft() {
        if (rotateDelay == 0) {
            if (rotatePosition > 0) {
                rotatePosition--;
            } else {
                rotatePosition = 15;
            }
            double rotate = getRotate() - 22.5;
            if (rotate < -180) {
                rotate += 360;
            }
            setRotate(rotate);
            updateDirection();
            rotateDelay = 5;
        }
    }

    public void move() {

        if (isAlive() && health > 0&&isHumanInPlane()) {

            if (shootDelay > 0)
                shootDelay--;
            if (rotateDelay > 0)
                rotateDelay--;

            if (getTranslateX() > getWindowWidth() + width) {
                setTranslateX(0);
            } else if (getTranslateX() <= width * -1) {
                setTranslateX(getWindowWidth());
            } else if (getTranslateY() > getWindowHeight() - height) {
                setTranslateY(getTranslateY());
            } else if (getTranslateY() <= height * -1 + 25) {
                setTranslateY(getTranslateY() + 1);
            } else {
                speed.updateSpeed();
                gravityCore.update(rotatePosition);

                changeXby(getHorizontalSpeed());
                changeYby(-getVerticalSpeed());


            }
        }
    }

    private void addArmor(int armor) {
        if (getArmorLeft() + armor <= 3) {
            armorBox.addArmor(armor);
        }
    }

    public void setArmorLeft(int count) {
        if (armorBox.getArmorHealth() < count) {
            armorBox.addArmor(count - armorBox.getArmorHealth());
        } else if (armorBox.getArmorHealth() > count) {
            armorBox.removeArmor(armorBox.getArmorHealth() - count);
        }
    }


    private void addHealth(int health) {
        if (this.health + health > MAXHEALTH) {
            this.health = MAXHEALTH;
        } else {
            this.health += health;
        }
        updateHealthStatus();
    }

    private void removeHealth(int health) {
        if (this.health - health <= 0) {
            this.health = 0;
            // blowUp();
        } else {
            this.health -= health;
        }
        blink();
        updateHealthStatus();
    }

    public void setHealthFromController(int health) {
        if (health > this.health) {
            addHealth(health);
        } else if (this.health > health) {
            removeHealth(this.health - health);
        }
    }

    public void addHuman() {
        if (!humanInPlane) {
            human = createHuman();
            addChildren(human);
            bodyPart.toFront();
            nosePart.toFront();
            armorBox.toFront();
            spawner.toFront();
            humanInPlane = true;
        }

    }

    abstract Human createHuman();

    public void addBonus(Bonus bonus) {
        applyBonus(bonus);
    }

    private void addBonus(AirDrop airDrop) {
        applyBonus(airDrop.getBonus());
    }

    public void applyBonus(Bonus bonus) {
        switch (bonus.getBonusType()) {
            case ARMOR:
                addArmor(3);
                break;
            case HEALTH:
                addHealth(3);
                break;
            case SHOTGUN:
                setSelectedWeapon(new ShotGun());
                break;
            case ROCKET:
                setSelectedWeapon(new Rocket());
                break;
        }
    }



    void updateDirection() {
        switch (rotatePosition) {
            case 0:
                speedX = 0;
                speedY = 1;
                break;
            case 1:
                speedX = 0.25f;
                speedY = 0.75f;
                break;
            case 2:
                speedX = 0.5f;
                speedY = 0.5f;
                break;
            case 3:
                speedX = 0.75f;
                speedY = 0.25f;
                break;
            case 4:
                speedX = 1f;
                speedY = 0f;
                break;

            case 5:
                speedX = 0.75f;
                speedY = -0.25f;
                break;
            case 6:
                speedX = 0.5f;
                speedY = -0.5f;
                break;
            case 7:
                speedX = 0.25f;
                speedY = -0.75f;
                break;
            case 8:
                speedX = 0f;
                speedY = -1f;
                break;

            case 9:
                speedX = -0.25f;
                speedY = -0.75f;
                break;
            case 10:
                speedX = -0.5f;
                speedY = -0.5f;
                break;
            case 11:
                speedX = -0.75f;
                speedY = -0.25f;
                break;
            case 12:
                speedX = -1;
                speedY = 0;
                break;

            case 13:
                speedX = -0.75f;
                speedY = 0.25f;
                break;
            case 14:
                speedX = -0.5f;
                speedY = 0.5f;
                break;
            case 15:
                speedX = -0.25f;
                speedY = 0.75f;
                break;

        }

    }

    @Override
    public void setAlive(boolean isAlive) {
        super.setAlive(isAlive);
        if (!isAlive) {
            stopEffects();
        }
    }

    private int getAllHealth() {
        return getArmorLeft() + health;
    }

    public int getArmorLeft() {
        return armorBox.getArmorHealth();
    }

    private float getRotationAngle() {
        float angle = 0;
        switch (rotatePosition) {
            case 0:
                angle = 0;
                break;
            case 1:
                angle = 22.5f;
                break;
            case 2:
                angle = 45f;
                break;
            case 3:
                angle = 67.5f;
                break;
            case 4:
                angle = 90;
                break;
            case 5:
                angle = 112.5f;
                break;
            case 6:
                angle = 135f;
                break;
            case 7:
                angle = 157.5f;
                break;
            case 8:
                angle = 180;
                break;
            case 9:
                angle = 202.5f;
                break;
            case 10:
                angle = 225f;
                break;
            case 11:
                angle = 247.5f;
                break;
            case 12:
                angle = 270f;
                break;
            case 13:
                angle = 292.5f;
                break;
            case 14:
                angle = 315;
                break;
            case 15:
                angle = 337.5f;
                break;
        }
        return angle;
    }



    public int getHealth() {
        return health;
    }



    public double getHorizontalSpeed() {
        return speedX * speed.getCurrentSpeed();
    }

    public double getVerticalSpeed() {
        return speedY*speed.getCurrentSpeed()-gravityCore.getCurrentGravity();
    }


    private void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public boolean isHumanInPlane() {
        return humanInPlane;
    }

    public void setHumanInPlane(boolean humanInPlane) {
        this.humanInPlane = humanInPlane;
    }

    public Human getHuman() {
        return human;
    }

    @Override
    public void finish() {
        blowUp();
    }

    @Override
    public boolean isParentAlive() {
        return isAlive();
    }

    @Override
    public void createPocket() {

        PlanePocket pocket = new PlanePocket(this);
        pocketsTosend.put(pocket.getPocketId(), pocket);


    }

    public boolean isNoseOpen() {
        return isNoseOpen;
    }
}
