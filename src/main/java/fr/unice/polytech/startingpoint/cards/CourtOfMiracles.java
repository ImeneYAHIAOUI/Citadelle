package fr.unice.polytech.startingpoint.cards;

public class CourtOfMiracles extends DistrictD implements IWonder {
    private final String description;

    public CourtOfMiracles() {
        this.name = DistrictName.LACOURDESMIRACLES;
        this.color = Color.PURPLE;
        this.price = 2;
        this.description="Pour le décompte final des points, la cour des miracles est considérée comme un quartier de la couleur de votre choix. Vous ne pouvez pas utilisez cette capacité si vous avez construit la cour des miracles au dernier tour de jeu.";
    }
    @Override
    public boolean isWonder() {
        return true;
    }

    @Override
    public void doAction(infoaction info) {

    }
    @Override
    public void effectOfAction() {

    }

    @Override
    public String getDescription() {
        return this.description;
    }

}