package classes.parts;

import javafx.scene.image.Image;

public class RightNosePart extends LeftNosePart {
    public RightNosePart(double x, double y) {
        super(x, y);
    }

    @Override
    public void setPartImage() {
        setImageInView(new Image("/resources/images/plane/right/rightNose.png"));
    }


}
