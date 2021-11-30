package fr.unice.polytech.startingpoint.cards;

public class CourtOfMiracles extends DistrictD implements IWonder {
    private final String description;

    public CourtOfMiracles() {
        this.name = DistrictName.LACOURDESMIRACLES;
        this.color = Color.PURPLE;
        this.price = 2;
        this.description = "Pour le décompte final des points, la cour des miracles est considérée comme un quartier de la couleur de votre choix. Vous ne pouvez pas utilisez cette capacité si vous avez construit la cour des miracles au dernier tour de jeu.";
    }

    @Override
    public boolean isWonder() {
        return true;
    }

    @Override
    public void doAction(infoaction info) {
        int i;
        for (i = 0; i < info.getbuilddisctrict().size(); i++) {
            if (info.getbuilddisctrict().get(i).getDistrictName() == DistrictName.LACOURDESMIRACLES && i<7 && info.getbuilddisctrict().size() == 8 )   {
                    this.color=info.getchoosencolor();}
            else{this.color=Color.PURPLE;}

            }

        }
            @Override
        public void effectOfAction () {

        }

        @Override
        public String getDescription () {
            return this.description;
        }
        }


