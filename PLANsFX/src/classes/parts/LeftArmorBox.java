package classes.parts;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import classes.panes.AlivePane;

import java.util.LinkedList;
import java.util.List;

public class LeftArmorBox extends AlivePane {
    ArmorPart armorPart1;
    ArmorPart armorPart2;
    ArmorPart armorPart3;
    protected List<ArmorPart> parts = new LinkedList<>();
    private int armorHP = 0;

    public LeftArmorBox(double x, double y) {
        super(x, y);
        setParts();
        getChildren().addAll(parts);

    }

    public void addOneDamage() {
        for (ArmorPart part : parts) {
            if (part.isAlive()) {
                startBlinking(part);
                armorHP--;
                break;
            }
        }
    }

    private void startBlinking(ArmorPart part) {
        part.setAlive(false);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(3);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150), event -> {
            if (!part.isAlive()) {
                part.setOpacity(1);
            } else {
                timeline.stop();
            }

        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), event -> {
            if (!part.isAlive()) {
                part.setOpacity(0);
            } else {
                timeline.stop();
            }
        }));
        timeline.play();
    }

    protected void setParts() {
        armorPart1 = new ArmorPart(0, 0, "/resources/images/plane/left/leftArmor1.png");
        armorPart2 = new ArmorPart(44, 0, "/resources/images/plane/left/leftArmor2.png");
        armorPart3 = new ArmorPart(76, 0, "/resources/images/plane/left/leftArmor3.png");
        armorPart1.setSize(0.7, 0.66);
        armorPart2.setSize(0.7, 0.66);
        armorPart3.setSize(0.7, 0.66);

        parts.add(armorPart1);
        parts.add(armorPart2);
        parts.add(armorPart3);
    }

    public void addArmor(int count) {

        for (int i = 0; i < count; i++) {
            for (ArmorPart part : parts) {
                if (!part.isAlive()) {
                    part.setAlive(true);
                    armorHP++;
                    break;
                }
            }

        }
    }

    public void removeArmor(int count) {
        for (int i = 0; i < count; i++) {
            for (ArmorPart part : parts) {
                if (part.isAlive()) {
                    startBlinking(part);
                    armorHP--;
                    break;
                }
            }
        }
    }

    public int getArmorHealth() {
        return armorHP;
    }
}
