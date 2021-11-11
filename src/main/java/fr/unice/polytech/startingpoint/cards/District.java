package fr.unice.polytech.startingpoint.cards;

public class District implements IDistrict{
    private int price;
    private Color color;
    private String name;

    public District(int price, Color color, String name) {
        this.price = price;
        this.color = color;
        this.name = name;
    }

    @Override
    public int getVal() {
        return this.price;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean isWonder() {
        return false;
    }
}
