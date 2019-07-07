package classes.effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import classes.panes.AlivePane;


public abstract class Effect extends AlivePane {
    private ImageView view;
    private Image image;

    public Effect() {

    }

    public Effect(double x, double y) {
        super(x, y);
    }

    public abstract void playAnimation();

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImageInView(Image image) {
        view = new ImageView(image);
    }

}
