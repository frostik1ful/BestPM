package classes.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import classes.panes.AdvancedPane;

public abstract class FaceEffect extends Effect {
    Image face;
    Image mouth1 = new Image("/resources/images/human/face/mouth1.png");
    Image mouth2 = new Image("/resources/images/human/face/mouth2.png");
    Image mouth3 = new Image("/resources/images/human/face/mouth3.png");
    Image mouth4 = new Image("/resources/images/human/face/mouth4.png");
    ImageView mouthView;
    TextCloud textCloud;

    FaceEffect(double x, double y) {
        super(x, y);
        setFaceImage();

        setMouth();
        setTextCloud();

    }

    FaceEffect(double x, double y, byte jeerNum) {
        this(x, y);
        textCloud.setJeerByNumber(jeerNum);
    }

    public byte getJeerNumber() {
        return textCloud.jeerNumber;
    }

    abstract void setFaceImage();

    abstract void setMouth();

    abstract void setTextCloud();

    class TextCloud extends AdvancedPane {
        Text text = new Text();
        String jeers[] = {"        PERFECTLY \n        BALANCED","DAMN IM GOOOD", "THIS GAME IS TOO \n SMALL FOR US", "YOU'RE IS DISEASE \n I'M THE CURE", "   STILL BETTER \n   THAN YOU", "       ME > YOU", "YOU FIGHTING LIKE \n A YOUNG MAN", "    YOU SOOO \n    WEAK",
                " PRESS ALT+F4 PLS",};
        byte jeerNumber;

        TextCloud(double x, double y, Image image) {
            super(x, y);
            setOpacity(0);
            setImageInView(image);
            addChildren(getView());
            setText();
        }

        void setText() {
            text.setText(getRandomJeer());

            text.setX(8);
            text.setY(18);
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));

            text.setLineSpacing(11);
            addChildren(text);
        }

        String getRandomJeer() {
            jeerNumber = (byte) getRandom().nextInt(jeers.length);
            return jeers[jeerNumber];
        }

        void setJeerByNumber(byte num) {
            text.setText(jeers[num]);
        }
    }
}

