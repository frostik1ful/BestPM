package classes.weapons.weaponShapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BulletShape extends WeaponShape{
   public Circle circle;
    public Circle c2;
    public BulletShape(){
        setTranslateX(0);
        setTranslateY(0);
        circle = new Circle(0,0,4, Color.RED);
        c2 = new Circle(0,0,2,Color.ORANGE);
        getChildren().addAll(circle,c2);

    }


}
