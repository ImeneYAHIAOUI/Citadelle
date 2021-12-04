package fr.unice.polytech.startingpoint.core;

import fr.unice.polytech.startingpoint.heros.*;
import fr.unice.polytech.startingpoint.cards.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public abstract class Initialization {

    /**
     * Initialization of the district deck
     * @return List<District>
     */
    public static List<IDistrict> districtList(){
        List<IDistrict> districtDeck = new ArrayList<IDistrict>();

        //yellow
        addCards(districtDeck,5,3,Color.YELLOW,DistrictName.MANOIR);
        addCards(districtDeck, 4,4,Color.YELLOW,DistrictName.CHATEAU);
        addCards(districtDeck, 2,5,Color.YELLOW,DistrictName.PALAIS);
        //green
        addCards(districtDeck, 3,2,Color.GREEN,DistrictName.ECHAPPE);
        addCards(districtDeck, 5,1,Color.GREEN,DistrictName.TAVERNE);
        addCards(districtDeck, 4,2,Color.GREEN,DistrictName.MARCHE);
        addCards(districtDeck, 3,3,Color.GREEN,DistrictName.COMPTOIR);
        addCards(districtDeck, 3,4,Color.GREEN,DistrictName.PORT);
        addCards(districtDeck, 2,5,Color.GREEN,DistrictName.HOTELDEVILLE);
        //blue
        addCards(districtDeck, 4,5,Color.BLUE,DistrictName.CATHEDRALE);/**number of district to change 4--2**/
        addCards(districtDeck, 3,1,Color.BLUE,DistrictName.TEMPLE);
        addCards(districtDeck,4 ,2,Color.BLUE,DistrictName.EGLISE);
        addCards(districtDeck,3 ,3,Color.BLUE,DistrictName.MONASTERE);
        //RED
        addCards(districtDeck, 5,1,Color.RED,DistrictName.TOURDEGUET);/**number of district to change 5--3**/
        addCards(districtDeck, 5,2,Color.RED,DistrictName.PRISON);/**number of district to change 5--3**/
        addCards(districtDeck, 3,3,Color.RED,DistrictName.CASERNE);
        addCards(districtDeck, 3,5,Color.RED,DistrictName.FORTERESSE);
        //Purple Wonder
        districtDeck.add(new MiracleCourt());
        districtDeck.add(new Laboratory());
        districtDeck.add(new Manufacture());

        Collections.shuffle(districtDeck);

        return districtDeck;
    }
    public static void  addCards(List<IDistrict> districtDeck,int numberOfCards,int price,Color color,DistrictName nameOfCard){
        for(int i = 0; i < numberOfCards; i++) {
            try {
                districtDeck.add(new District(price,color,nameOfCard));
            } catch (CardException e) {
                e.printStackTrace();
            }
        }

    }


    /** add chosen hero to heroList
     *
     * @return heroList
     */
    public static HeroDeck heroeList(){
        HeroDeck heroes = new HeroDeck();
        heroes.add(new King());
        heroes.add(new Merchant());
        heroes.add(new Magician());
        heroes.add(new Assassin());
        heroes.add(new Thief());
        heroes.add(new Bishop());
        return heroes;
    }
    public static int treasureOfTheGame(){
        return 30;
    }
}
