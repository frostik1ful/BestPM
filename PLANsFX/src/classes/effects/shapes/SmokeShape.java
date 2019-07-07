package classes.effects.shapes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Random;

public class SmokeShape extends Pane {
    private Path path = new Path();
    private double size = 1;

    private double[] points = {0.0, -15.67, 15.65, -14.0, 17.0, -1.34,
            29.65, -19.34, 49.33, -7.67, 37.0, 8.0, 53.66, 24.65,
            39.0, 28.0, 29.65, 22.33, 24.0, 34.33, 12.0, 35.0, 12.0,
            22.33, 4.65, 37.0, -20.66, 20.0, -0.34, 14.0, -17.34, 14.32,
            -11.34, -10.0, 0.33, -0.34
    };

    private double startX = 0;
    private double startY = 0;

    private Random random = new Random();
    private Color color = new Color(1, 1, 1, random.nextDouble());

    public SmokeShape(double x, double y, double size) {
        this.startX = x;
        this.startY = y;
        this.size = size;
        path.setFill(Color.WHITE);
        path.setStrokeWidth(0);
        MoveTo moveTo = new MoveTo();
        moveTo.setX(startX);
        moveTo.setY(startY);
        path.getElements().addAll(moveTo);
        getChildren().addAll(path);
    }

    public void buildShape() {
        int n = 0;
        for (int i = 0; i < 6; i++) {
            CubicCurveTo QC = new CubicCurveTo();

            QC.setControlX1(points[n] * size);
            QC.setControlY1(points[n + 1] * size);
            QC.setControlX2(points[n + 2] * size);
            QC.setControlY2(points[n + 3] * size);
            QC.setX(points[n + 4] * size);
            QC.setY(points[n + 5] * size);


            if (i == 5) {
                QC.setX(startX);
                QC.setY(startY);
            }

            n += 6;
            path.getElements().add(QC);
        }
    }

    public void buildRandomShape() {
        int n = 0;
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            CubicCurveTo QC = new CubicCurveTo();

            QC.setControlX1(points[n] * size + (r.nextInt((int) (10 * size))));
            QC.setControlY1(points[n + 1] * size + (r.nextInt((int) (10 * size))));
            QC.setControlX2(points[n + 2] * size + (r.nextInt((int) (10 * size))));
            QC.setControlY2(points[n + 3] * size + (r.nextInt((int) (19 * size))));
            QC.setX(points[n + 4] * size + (r.nextInt((int) (10 * size))));
            QC.setY(points[n + 5] * size + (r.nextInt((int) (10 * size))));


            if (i == 5) {
                QC.setX(startX);
                QC.setY(startY);
            }

            n += 6;
            path.getElements().add(QC);
        }

    }

    public void setRandomLightGreyColor() {
        color = Color.rgb(100 + random.nextInt(100), 100 + random.nextInt(100), 100 + random.nextInt(100));
        color = color.grayscale();
        setColor(color);
    }

    public void setRandomDarkGreyColor() {
        color = Color.rgb(10 + random.nextInt(100), 10 + random.nextInt(100), 10 + random.nextInt(100));
        color = color.grayscale();
        setColor(color);
    }

    public void setRandomGreyColor() {
        color = Color.rgb(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100));
        color = color.grayscale();
        setColor(color);
    }

    public void setRandomOrangeColor() {
        setColor(Color.rgb(252, 135 + random.nextInt(50), 60));
    }


    public void setColor(Color color) {
        this.path.setFill(color);
    }

    public void setStrokeWidth(double n) {
        this.path.setStrokeWidth(n);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
