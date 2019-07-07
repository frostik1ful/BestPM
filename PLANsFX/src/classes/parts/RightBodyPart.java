package classes.parts;

import javafx.scene.image.Image;

public class RightBodyPart extends LeftBodyPart {
    public RightBodyPart(double x, double y) {
        super(x, y);
    }

    public void setPartImage() {
        setImageInView(new Image("/resources/images/plane/right/rightBody.png"));
    }

    public void setWhitePartImage() {
        whiteBody.setImage(new Image("/resources/images/plane/right/whiteRightBody.png"));
    }
}
