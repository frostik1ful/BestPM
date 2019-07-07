package classes.utils;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import interfaces.Colliding;
import classes.panes.AdvancedPane;

import java.util.ArrayList;
import java.util.List;

public class HitBox extends AdvancedPane implements Colliding {
    private boolean isAlive = true;
    private Rectangle rectangle = new Rectangle(1, 1, Color.ORANGE);
    private HitBoxType hitBoxType;
    private Colliding parent;
    private List<Circle> circles = new ArrayList<>();
    private int countOfCircles = 0;
    private boolean manualCirclesCount = false;

    public HitBox(Colliding parent, HitBoxType hitBoxType, int countOfCircles) {
        this(parent, hitBoxType);
        this.countOfCircles = countOfCircles;
        manualCirclesCount = true;
    }

    public HitBox(Colliding parent, HitBoxType hitBoxType) {
        this.parent = parent;

        this.hitBoxType = hitBoxType;
        getChildren().add(rectangle);

        setOpacity(0);
    }

    private void updateCircles(double width, double height) {
        double circleR;//radius
        double circleD;//diameter
        double step;
        boolean horizontal = true;
        if (!circles.isEmpty()) {
            getChildren().removeAll(circles);
            circles.clear();
        }

        if (width < height) {
            circleR = width / 2;
            if (countOfCircles == 0) {
                countOfCircles = (int) (height / width);
                step = circleR;
            } else {
                step = circleR;
            }
            horizontal = false;


        } else {
            circleR = height / 2;
            if (countOfCircles == 0) {
                countOfCircles = (int) (width / height);
                step = circleR;
            } else {
                step = circleR;
            }
        }

        circleD = circleR * 2;


        for (int i = 0; i < countOfCircles; i++) {
            Circle circle = createCircle(circleR);
            if (horizontal) {
                circle.setTranslateX(step);
                circle.setTranslateY(circleR);
                step += circleR * 2;
            } else {
                circle.setTranslateX(circleR);
                circle.setTranslateY(step);
                step += circleR * 2;
                //getChildren().add(circle);
            }
            if (manualCirclesCount) {
                double spaceLeft = (countOfCircles - 1) * circleD;
                double stepBack;
                if (horizontal)
                    stepBack = (rectangle.getWidth() - circleD) - spaceLeft;
                else {
                    stepBack = (rectangle.getHeight() - circleD) - spaceLeft;
                }
                step += stepBack / (countOfCircles - 1);

            }

            circles.add(circle);
        }
        getChildren().addAll(circles);


    }

    public String toString() {
        return hitBoxType.toString();
    }

    private Circle createCircle(double radius) {
        return new Circle(radius, Color.GREEN);
    }

    @Override
    public void addCollision(HitBox hitBox) {
        parent.addCollision(hitBox);
    }

    @Override
    public double getCollisionDamage() {
        return parent.getCollisionDamage();
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }


    public enum HitBoxType {
        GROUND, AMBAR, PLANE_BODY, PLANE_NOSE, PLANE_HEAD, SCRAP_BODY, HUMAN, PARACHUTE, AIR_DROP, ROCKET, BULLET,
    }


    boolean isIntersects(HitBox hitBox) {
        if (this.localToScene(this.getBoundsInLocal()).intersects(hitBox.localToScene(hitBox.getBoundsInLocal()))) {
            if (getHitBoxType() == HitBoxType.GROUND || hitBox.getHitBoxType() == HitBoxType.GROUND) {
                return true;
            }
            for (Circle circle1 : circles) {
                for (Circle circle2 : hitBox.getCircles()) {
                    if (isCirclesCollides(circle1, circle2)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean isCirclesCollides(Circle c1, Circle c2) {
        Point2D c1Center = c1.localToScene(c1.getCenterX(), c1.getCenterY());
        Point2D c2Center = c2.localToScene(c2.getCenterX(), c2.getCenterY());
        double minDist = c1.getRadius() + c2.getRadius();
        return c1Center.distance(c2Center)<minDist;
    }


    public Rectangle getRectangle() {
        return rectangle;
    }


    public void setSize(double width, double height) {

        this.rectangle.setWidth(width);
        this.rectangle.setHeight(height);
        setPrefSize(width, height);
        updateCircles(width, height);
    }

    public double getHitboxHeight() {
        return rectangle.getHeight();
    }

//    public double getHitboxWidth() {
//        return rectangle.getWidth();
//    }

    public Colliding getHitBoxParent() {
        return parent;
    }

    public HitBoxType getHitBoxType() {
        return hitBoxType;
    }

    private List<Circle> getCircles() {
        return circles;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

//    public int getCountOfCircles() {
//        return countOfCircles;
//    }
//
//    public void setCountOfCircles(int countOfCircles) {
//        this.countOfCircles = countOfCircles;
//    }
}
