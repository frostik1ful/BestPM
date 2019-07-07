package classes.parts;

public class RightArmorBox extends LeftArmorBox {

    public RightArmorBox(double x, double y) {
        super(x, y);
    }

    protected void setParts() {
        armorPart1 = new ArmorPart(15, 0, "/resources/images/plane/right/rightArmor1.png");
        armorPart2 = new ArmorPart(51, 0, "/resources/images/plane/right/rightArmor2.png");
        armorPart3 = new ArmorPart(83, 0, "/resources/images/plane/right/rightArmor3.png");
        armorPart1.setSize(0.7, 0.66);
        armorPart2.setSize(0.7, 0.66);
        armorPart3.setSize(0.7, 0.66);

        parts.add(armorPart3);
        parts.add(armorPart2);
        parts.add(armorPart1);
    }

}
