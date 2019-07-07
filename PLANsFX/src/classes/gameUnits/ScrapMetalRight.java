package classes.gameUnits;

import classes.utils.HitBox;
import classes.parts.ScrapBodyPartRight;

public class ScrapMetalRight extends ScrapMetalLeft {
    public ScrapMetalRight(double x, double y) {
        super(x, y);

    }

    protected void setParts() {
        scrapBodyPart = new ScrapBodyPartRight(0, 0);
        scrapBodyPart.setSize(0.7, 0.66);

        bodyBox = new HitBox(this, HitBox.HitBoxType.SCRAP_BODY);
        bodyBox.setSize(60, 20);
        bodyBox.setTranslateXY(5, 8);

    }

}
