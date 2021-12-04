package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.core.Initialization;
import fr.unice.polytech.startingpoint.heros.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HeroDecisionStandardTest {
    IA ia1 = null;
    IA ia2 = null;
    IA ia3 = null;
    List<IPlayer> players = null;
    HeroDeck heroes = null;
    List<HerosChoice> thoughPath = null;
    HeroDecisionStandard heroDecisionStandard = null;
    IDistrict district1 = null;
    IDistrict district2 = null;
    IDistrict district3 = null;
    IDistrict district4 = null;
    IDistrict district5 = null;
    IDistrict district6 = null;
    IDistrict district7 = null;
    IDistrict district8 = null;
    IHero hero = null;
    Random rand = null;

    private IDistrict addCards(int price, Color color, DistrictName nameOfCard){
        IDistrict district = null;
        try {
            district = new District(price,color,nameOfCard);
        } catch (CardException e) {
            e.printStackTrace();
        }
        return district;
    }

    @BeforeEach
    void setup(){
        this.ia1 = new IA("IA1");
        this.ia2 = new IA("IA2");
        this.ia3 = new IA("IA3");

        this.rand = new Random();

        this.heroDecisionStandard = new HeroDecisionStandard();
        this.players = new ArrayList<IPlayer>();
        this.players.add(this.ia1);
        this.players.add(this.ia2);
        this.players.add(this.ia3);
        this.thoughPath = new ArrayList<HerosChoice>();
        this.heroes = null;
        this.heroes = Initialization.heroeList();

        this.district1 = addCards(2,Color.YELLOW,DistrictName.MARCHE);
        this.district2 = addCards(2,Color.RED,DistrictName.MARCHE);
        this.district3 = addCards(2,Color.BLUE,DistrictName.MARCHE);
        this.district4 = addCards(2,Color.GREEN,DistrictName.MARCHE);
        this.district5 = addCards(2,Color.PURPLE,DistrictName.MARCHE);
        this.district6 = addCards(2,Color.YELLOW,DistrictName.MARCHE);
        this.district7 = addCards(2,Color.YELLOW,DistrictName.MARCHE);
        this.district8 = addCards(2,Color.YELLOW,DistrictName.MARCHE);
    }

    @Test
    void testHeroDecisionKing(){
        ia1.addGold(20);
        ia2.addGold(20);
        ia3.addGold(20);


        ia1.buildDistrict(district1);
        ia1.buildDistrict(district1);
        ia1.buildDistrict(district1);
        ia1.buildDistrict(district2);

        ia2.buildDistrict(district5);
        ia2.buildDistrict(district6);

        ia3.buildDistrict(district1);

        List<IDistrict> districtList = new ArrayList<IDistrict>();
        districtList.add(district1);
        districtList.add(district2);
        districtList.add(district1);
        ia1.getDistrict(districtList);

        assertEquals(3,this.ia1.getHand().size());

        this.rand = mock(Random.class);
        when(rand.nextFloat()).thenReturn((float) 0.1);
        this.ia1.setRole(this.heroDecisionStandard.heroDecision(this.ia1,players,heroes,thoughPath,rand));

        assertEquals(HeroName.King,ia1.getRole().getName());
    }

    @Test
    void testHeroDecisionMerchan(){
        ia1.addGold(20);
        ia2.addGold(20);
        ia3.addGold(20);


        ia1.buildDistrict(district4);
        ia1.buildDistrict(district4);
        ia1.buildDistrict(district4);
        ia1.buildDistrict(district2);

        ia2.buildDistrict(district5);
        ia2.buildDistrict(district6);

        ia3.buildDistrict(district1);

        List<IDistrict> districtList = new ArrayList<IDistrict>();
        districtList.add(district1);
        districtList.add(district2);
        districtList.add(district1);
        ia1.getDistrict(districtList);

        assertEquals(3,this.ia1.getHand().size());

        this.rand = mock(Random.class);
        when(rand.nextFloat()).thenReturn((float) 0.1);
        this.ia1.setRole(this.heroDecisionStandard.heroDecision(this.ia1,players,heroes,thoughPath,rand));

        assertEquals(HeroName.Merchant,ia1.getRole().getName());
    }

    @Test
    void testHeroDecisionMerchanBecauseKingIsAlreadyChoosen(){
        ia1.addGold(20);
        ia2.addGold(20);
        ia3.addGold(20);


        ia1.buildDistrict(district1);
        ia1.buildDistrict(district1);
        ia1.buildDistrict(district1);
        ia1.buildDistrict(district2);

        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district4);
        ia2.buildDistrict(district4);

        ia3.buildDistrict(district1);

        List<IDistrict> districtList = new ArrayList<IDistrict>();
        districtList.add(district1);
        districtList.add(district2);
        districtList.add(district1);
        ia1.getDistrict(districtList);
        ia2.getDistrict(districtList);

        assertEquals(3,this.ia1.getHand().size());

        this.rand = mock(Random.class);
        when(rand.nextFloat()).thenReturn((float) 0.1);
        assertEquals(6,this.heroes.size());
        this.ia1.setRole(this.heroDecisionStandard.heroDecision(this.ia1,players,heroes,thoughPath,rand));
        assertEquals(5,this.heroes.size());
        this.ia2.setRole(this.heroDecisionStandard.heroDecision(this.ia2,players,heroes,thoughPath,rand));
        assertEquals(4,this.heroes.size());

        assertEquals(HeroName.King,ia1.getRole().getName());     // Chose king
        assertEquals(HeroName.Merchant,ia2.getRole().getName()); // Chose Merchant beacuase king is alredy chosen
    }

    @Test
    void testHeroDecisionMagicien(){
        ia2.addGold(20);
        ia3.addGold(20);

        this.district1 = addCards(6,Color.YELLOW,DistrictName.MARCHE);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(1);

        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district4);
        ia2.buildDistrict(district4);

        ia3.buildDistrict(district1);

        List<IDistrict> districtList = new ArrayList<IDistrict>();
        districtList.add(district1);
        districtList.add(district1);
        districtList.add(district1);
        ia1.getDistrict(districtList);
        ia2.getDistrict(districtList);

        assertEquals(3,this.ia1.getHand().size());

        this.rand = mock(Random.class);
        when(rand.nextFloat()).thenReturn((float) 0.1);
        assertEquals(6,this.heroes.size());
        this.ia1.setRole(this.heroDecisionStandard.heroDecision(this.ia1,players,heroes,thoughPath,rand));
        assertEquals(5,this.heroes.size());

        assertEquals(HeroName.Magician,ia1.getRole().getName());     // Chose Magicien
    }

    @Test
    void testHeroDecisionAssassin(){
        ia2.addGold(20);
        ia3.addGold(20);

        this.district1 = addCards(6,Color.YELLOW,DistrictName.MARCHE);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(1);

        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district4);
        ia2.buildDistrict(district4);

        ia3.buildDistrict(district1);

        List<IDistrict> districtList = new ArrayList<IDistrict>();
        districtList.add(district1);
        districtList.add(district1);
        districtList.add(district1);
        ia1.getDistrict(districtList);
        ia2.getDistrict(districtList);

        assertEquals(3,this.ia1.getHand().size());

        this.rand = mock(Random.class);
        when(rand.nextFloat()).thenReturn((float) 0.5);
        assertEquals(6,this.heroes.size());
        this.ia2.setRole(this.heroDecisionStandard.heroDecision(this.ia2,players,heroes,thoughPath,rand));
        assertEquals(5,this.heroes.size());

        assertEquals(HeroName.Assassin,ia2.getRole().getName());     // Chose Assassin
    }

    @Test
    void testHeroDecisionNeedGoldBecauseMoreHeroAttackAvailable(){
        ia2.addGold(20);
        ia3.addGold(20);

        this.district1 = addCards(6,Color.YELLOW,DistrictName.MARCHE);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(6);
        ia1.buildDistrict(district1);
        ia1.addGold(1);

        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district1);
        ia2.buildDistrict(district4);
        ia2.buildDistrict(district4);

        ia3.buildDistrict(district1);

        List<IDistrict> districtList = new ArrayList<IDistrict>();
        districtList.add(district8);
        districtList.add(district8);
        districtList.add(district8);
        ia2.getDistrict(districtList);
        ia1.getDistrict(districtList);
        ia2.addGold(6);

        assertEquals(3,this.ia1.getHand().size());

        this.rand = mock(Random.class);
        when(rand.nextFloat()).thenReturn((float) 0.6);
        assertEquals(6,this.heroes.size());

        // I withdraw the assassin
        heroes.chooseHero(HeroName.Assassin);
        assertEquals(5,this.heroes.size());

        this.ia2.setRole(this.heroDecisionStandard.heroDecision(this.ia2,players,heroes,thoughPath,rand));
        assertEquals(4,this.heroes.size());

        assertEquals(HeroName.King,ia2.getRole().getName());     // Chose King
    }
}