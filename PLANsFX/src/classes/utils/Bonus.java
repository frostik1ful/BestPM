package classes.utils;

import java.util.Random;

public class Bonus{
    private BonusType bonusType;
    private byte bonusNumber;
    public Bonus(){
        bonusType= BonusType.ARMOR;
        Random rnd = new Random();
        bonusNumber = (byte) rnd.nextInt(BonusType.values().length);
        bonusType=BonusType.values()[bonusNumber];
    }
    public Bonus(byte bonusNumber){
        setBonusByNumber(bonusNumber);
    }
    public void setBonusByNumber(byte bonusNumber){
        this.bonusNumber = bonusNumber;
        bonusType=BonusType.values()[bonusNumber];
    }
    public BonusType getBonusType() {
        return bonusType;
    }

    public byte getBonusNumber() {
        return bonusNumber;
    }

    public enum BonusType  {
        ARMOR,HEALTH,SHOTGUN,ROCKET
    }
}
