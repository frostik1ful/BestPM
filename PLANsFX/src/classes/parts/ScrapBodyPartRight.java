package classes.parts;

import javafx.scene.image.Image;

public class ScrapBodyPartRight extends ScrapBodyPartLeft {
    public ScrapBodyPartRight(double x, double y) {
        super(x, y);
    }

    @Override
    public void setPartImage() {
        setImageInView(new Image("/resources/images/plane/right/scrapRight.png"));
    }
}
