package interfaces;

import classes.utils.HitBox;

public interface Colliding {
     void addCollision(HitBox hitBox);
     double getCollisionDamage();
     boolean isAlive();
     double getTranslateY();

}
