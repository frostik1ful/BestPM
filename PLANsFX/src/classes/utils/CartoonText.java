package classes.utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CartoonText extends Text{
    private Stop stops[] = new Stop[]{new Stop(0, Color.rgb(214, 184, 15)), new Stop(1, Color.rgb(206, 30, 20))};
    private LinearGradient LG = new LinearGradient(1, 0, 1, 1, true, CycleMethod.REFLECT, stops);
    public CartoonText(String text, double size){
        super(text);
        this.setFill(LG);
        this.setStroke(Color.rgb(99, 37, 13));
        this.setStrokeWidth(1.5);
        this.setFont(Font.font("Comical Cartoon", size));
    }
}
