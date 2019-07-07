package classes.parts;

import javafx.scene.image.Image;

public class ArmorPart extends Part {

    ArmorPart(double x, double y, String imageURL) {
        super(x, y);
        setAlive(false);
        setImageInView(new Image(imageURL));
        getChildren().add(getView());

    }

    public void setAlive(boolean alive) {
        if (alive) {
            setOpacity(1);
        } else {
            setOpacity(0);
        }
        super.setAlive(alive);
    }

    @Override
    public void setPartImage() {

    }


}
