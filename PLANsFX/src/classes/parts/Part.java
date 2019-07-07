package classes.parts;

import classes.panes.AlivePane;

public abstract class Part extends AlivePane {

    Part(double x, double y) {
        setTranslateXY(x, y);

    }

    public void setSize(double size) {
        double width = getView().getImage().getWidth();
        double height = getView().getImage().getHeight();
        getView().setFitWidth(width * size);
        getView().setFitHeight(height * size);
        setTranslateX(getTranslateX() * size);
        setTranslateY(getTranslateY() * size);

    }

    public void setSize(double height, double width) {
        double imgWidth = getView().getImage().getWidth();
        double imgHeight = getView().getImage().getHeight();
        getView().setFitWidth(imgWidth * width);
        getView().setFitHeight(imgHeight * height);
        setTranslateX(getTranslateX() * width);
        setTranslateY(getTranslateY() * height);

    }

    public abstract void setPartImage();


}
