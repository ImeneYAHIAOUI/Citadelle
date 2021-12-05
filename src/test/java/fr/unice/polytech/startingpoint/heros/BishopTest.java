package fr.unice.polytech.startingpoint.heros;

import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.heros.character.Bishop;
import fr.unice.polytech.startingpoint.player.IA;
import fr.unice.polytech.startingpoint.player.Information;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopTest {
    Bishop bishop = null;
    Information info;

    @BeforeEach
    void setUp() {
        this.bishop = new Bishop();
        info = new Information();
    }

    @Test
    void testDoActionWithoutBlueDistrict() {
        Information info = new Information();
        Treasure treasure = new Treasure(30);
        IA player = new IA("Mooncake");
        HeroDeck heroes = new HeroDeck();
        IDistrict distrcit1 = null;
        IDistrict distrcit2 = null;
        IDistrict distrcit3 = null;

        player.addGold(2);

        heroes.add(this.bishop);

        try {
            distrcit1 = new District(2, Color.YELLOW, DistrictName.CHATEAU);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            distrcit2 = new District(1, Color.GREEN, DistrictName.TAVERNE);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            distrcit3 = new District(6, Color.GREEN, DistrictName.MARCHE);
        } catch (CardException e) {
            e.printStackTrace();
        }

        assertEquals(2, player.getGold());

        player.addGold(1 + 6);
        player.buildDistrict(distrcit1);
        player.buildDistrict(distrcit2);
        player.buildDistrict(distrcit3);

        player.setRole(heroes.get(0));
        player.activateHero(null, null, treasure,info);

        assertEquals(0, player.getGold());
    }

    @Test
    void testDoActionWithBlueDistrict() {
        Treasure treasure = new Treasure(30);
        IA player = new IA("Mooncake");
        HeroDeck heroes = new HeroDeck();
        IDistrict distrcit1 = null;
        IDistrict distrcit2 = null;
        IDistrict distrcit3 = null;
        player.addGold(2);
        heroes.add(this.bishop);
        try {
            distrcit1 = new District(2, Color.YELLOW, DistrictName.CHATEAU);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            distrcit2 = new District(1, Color.PURPLE, DistrictName.TAVERNE);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            distrcit3 = new District(6, Color.BLUE, DistrictName.MARCHE);
        } catch (CardException e) {
            e.printStackTrace();
        }
        assertEquals(2, player.getGold());
        player.addGold(2 + 1 + 6);
        player.buildDistrict(distrcit1);
        player.buildDistrict(distrcit2);
        player.buildDistrict(distrcit3);

        player.setRole(heroes.get(0));
        player.activateHero(null, null, treasure,info);
        assertEquals(3, player.getGold());

    }
    @Test
    void testDoActionWhenTreasueIsEmpty() { //When Treasue Is Empty
        Information info = new Information();
        Treasure treasure = new Treasure(12);
        IA player = new IA("Mooncake");
        HeroDeck heroes = new HeroDeck();
        IDistrict distrcit1 = null;
        IDistrict distrcit2 = null;
        IDistrict distrcit3 = null;

        player.addGold(treasure.removeGold(11));
        assertEquals(11,player.getGold());
        assertEquals(1,treasure.getPieces());

        heroes.add(this.bishop);
        player.setRole(heroes.get(0));

        try {
            distrcit1 = new District(5, Color.BLUE, DistrictName.CATHEDRALE);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            distrcit2 = new District(1, Color.PURPLE, DistrictName.TAVERNE);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            distrcit3 = new District(6, Color.BLUE, DistrictName.MARCHE);
        } catch (CardException e) {
            e.printStackTrace();
        }
        player.buildDistrict(distrcit1);
        assertEquals(6,player.getGold());
        player.activateHero(null, null, treasure,info);
        assertEquals(7,player.getGold());
        player.buildDistrict(distrcit2);
        player.buildDistrict(distrcit3);
        assertEquals(0,player.getGold());
    }
}

