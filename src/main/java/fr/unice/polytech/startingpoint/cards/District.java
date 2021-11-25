package fr.unice.polytech.startingpoint.cards;

public class District extends DistrictD{

    public District(int price, Color color, DistrictName name) throws CardException{
        if(price < 0)
            throw new CardException("District cannot have a price below 0");

        this.name=name;
        this.color=color;
        this.price=price;
    }

    @Override
    public boolean isWonder() {
        return false;
    }
}
