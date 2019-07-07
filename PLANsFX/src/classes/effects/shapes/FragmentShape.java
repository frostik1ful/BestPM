package classes.effects.shapes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class FragmentShape extends Pane {
    private Polygon polygon;

    public FragmentShape(double height, double width) {
        polygon = new Polygon();
        polygon.getPoints().addAll(width / 2, height, 0d, height - height * 0.7, width / 2, 0d, width, height - height * 0.7);
        polygon.setFill(Color.ORANGE);
        getChildren().add(polygon);

    }

    public void setColor(Color color) {
        this.polygon.setFill(color);
    }
}
