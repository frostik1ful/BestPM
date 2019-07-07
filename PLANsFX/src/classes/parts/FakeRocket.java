package classes.parts;

import javafx.scene.image.Image;

public class FakeRocket extends Part {

    public FakeRocket(double x, double y) {
        super(x, y);
        setPartImage();
        getChildren().add(getView());
    }

    @Override
    public void setPartImage() {
        setImageInView(new Image("/resources/images/rocket/rocket.png"));
    }


}
