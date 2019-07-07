package classes.parts;

import javafx.scene.image.Image;

public class ScrapBodyPartLeft extends Part {

    public ScrapBodyPartLeft(double x, double y) {
        super(x, y);
        setPartImage();
        getChildren().add(getView());
    }

    @Override
    public void setPartImage() {
        setImageInView(new Image("/resources/images/plane/left/scrapLeft.png"));
    }


}
