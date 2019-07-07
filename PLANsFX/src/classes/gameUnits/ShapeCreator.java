package classes.gameUnits;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import classes.panes.AdvancedPane;

import java.util.ArrayList;
import java.util.List;

public class ShapeCreator extends AdvancedPane {
    private double size = 1;
    private double lastX = 0;
    private double lastY = 0;
    private ColorPicker colorPicker = new ColorPicker();

    private List<CubicCurveTo> lines = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();

    private Path path = new Path();
    private Button butAddCCurve = new Button("+CCurve");
    private Button butAddQuadCurve = new Button("+QuadCurve");
    private Button butPrint = new Button("printCoords");

    public ShapeCreator(double x, double y) {
        super(x, y);
        addChildren(path);
        setFirstPoint();
        setButtons();
        setActions();

        //path.setFill(Color.RED);
        //path.setStrokeWidth(0);

    }

    private void setFirstPoint() {
        MoveTo moveTo = new MoveTo();
        moveTo.setX(lastX);
        moveTo.setY(lastY);
        Circle circle = new Circle(5, Color.RED);
        circle.setCenterX(moveTo.getX());
        circle.setCenterY(moveTo.getY());

        circle.setOnMouseDragged(event -> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            moveTo.setX(circle.getCenterX());
            moveTo.setY(circle.getCenterY());
            lastX = circle.getCenterX();
            lastY = circle.getCenterY();
        });

        circles.add(circle);
        addChildren(circle);

        path.getElements().addAll(moveTo);
    }

    private void setButtons() {
        colorPicker.setTranslateX(500);
        colorPicker.setTranslateY(0);

        butAddCCurve.setTranslateX(650);
        butAddCCurve.setTranslateY(0);

        butAddQuadCurve.setTranslateX(650);
        butAddQuadCurve.setTranslateY(40);

        butPrint.setTranslateX(650);
        butPrint.setTranslateY(80);

        getChildren().addAll(colorPicker, butAddCCurve, butAddQuadCurve, butPrint);


    }

    private void setActions() {
        butAddCCurve.setOnAction(event -> addCubicCurve(lastX, lastY));

        butAddQuadCurve.setOnAction(event -> addQuadCurve(lastX, lastY));

        butPrint.setOnAction(event -> printLines());


        colorPicker.setOnAction(event -> path.setFill(colorPicker.getValue()));
    }

    private void addCubicCurve(double x, double y) {
        CubicCurveTo CC = new CubicCurveTo();
        CC.setControlX1((x + 20) * size);
        CC.setControlY1((y) * size);
        CC.setControlX2((x + 30) * size);
        CC.setControlY2((y) * size);
        CC.setX((x + 40) * size);
        CC.setY(y * size);

        lastX = CC.getX();
        lastY = CC.getY();
        path.getElements().addAll(CC);

        Circle circle = new Circle(5, Color.RED);
        Circle circle2 = new Circle(5, Color.RED);
        Circle circle3 = new Circle(5, Color.RED);

        circle.setCenterX(CC.getControlX1());
        circle.setCenterY(CC.getControlY1());

        circle2.setCenterX(CC.getControlX2());
        circle2.setCenterY(CC.getControlY2());

        circle3.setCenterX(CC.getX());
        circle3.setCenterY(CC.getY());

        circle.setOnMouseDragged(event -> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            CC.setControlX1(circle.getCenterX());
            CC.setControlY1(circle.getCenterY());
            lastX = circle.getCenterX();
            lastY = circle.getCenterY();
        });
        circle2.setOnMouseDragged(event -> {
            circle2.setCenterX(event.getX());
            circle2.setCenterY(event.getY());
            CC.setControlX2(circle2.getCenterX());
            CC.setControlY2(circle2.getCenterY());
            lastX = circle2.getCenterX();
            lastY = circle2.getCenterY();
        });

        circle3.setOnMouseDragged(event -> {
            circle3.setCenterX(event.getX());
            circle3.setCenterY(event.getY());
            CC.setX(circle3.getCenterX());
            CC.setY(circle3.getCenterY());
            lastX = circle3.getCenterX();
            lastY = circle3.getCenterY();
        });
        setRedCirc();
        circles.add(circle);
        circles.add(circle2);
        circles.add(circle3);
        lines.add(CC);
        getChildren().addAll(circle, circle2, circle3);
    }

    private void addQuadCurve(double x, double y) {
        QuadCurveTo QC = new QuadCurveTo();
        QC.setControlX((x + 20) * size);
        QC.setControlY((y) * size);
        QC.setX((x + 40) * size);
        QC.setY(y * size);

        lastX = QC.getX();
        lastY = QC.getY();
        path.getElements().addAll(QC);

        Circle circle = new Circle(5, Color.RED);
        Circle circle2 = new Circle(5, Color.RED);

        circle.setCenterX(QC.getControlX());
        circle.setCenterY(QC.getControlY());

        circle2.setCenterX(QC.getX());
        circle2.setCenterY(QC.getY());

        circle.setOnMouseDragged(event -> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            QC.setControlX(circle.getCenterX());
            QC.setControlY(circle.getCenterY());
            lastX = circle.getCenterX();
            lastY = circle.getCenterY();
        });

        circle2.setOnMouseDragged(event -> {
            circle2.setCenterX(event.getX());
            circle2.setCenterY(event.getY());
            QC.setX(circle2.getCenterX());
            QC.setY(circle2.getCenterY());
            lastX = circle2.getCenterX();
            lastY = circle2.getCenterY();
        });
        setRedCirc();
        circles.add(circle);
        circles.add(circle2);
        //lines.add(QC);
        getChildren().addAll(circle, circle2);
    }

    private void setRedCirc() {
        for (Circle c : circles) {
            c.setFill(Color.BLUE);
        }
    }

    private void printLines() {
        for (CubicCurveTo q : lines) {
            System.out.print(q.getControlX1() + ", " + q.getControlY1() + ", " + q.getControlX2() + ", " + q.getControlY2() + ", " + q.getX() + ", " + q.getY() + ", ");
        }
        System.out.println();
        System.out.println("color " + path.getFill());
//        for (QuadCurveTo q:lines){
//            System.out.print(q.getControlX()+", "+q.getControlY()+", "+q.getX()+", "+q.getY()+", ");
//
//        }
    }
}
