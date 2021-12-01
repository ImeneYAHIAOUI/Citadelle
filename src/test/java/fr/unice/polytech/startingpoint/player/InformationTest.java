package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.core.Initialization;
import fr.unice.polytech.startingpoint.heros.HeroDeck;
import fr.unice.polytech.startingpoint.heros.IHero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InformationTest {
    Information information;
    Information information2;
    Information information3;
    Information information4;


    List<IPlayer> players;
    List<IPlayer> players1;
    List<IPlayer> players2;
    List<IDistrict> listDeck;
    IPlayer player1;
    IPlayer player2;
    IPlayer player3;
    IDistrict district1;
    IDistrict district2;
    IDistrict district3;
    IDistrict district4;
    IDistrict district5;
    DistrictDeck deck;
    DistrictDeck deck1;
    DistrictDeck deck2;
    List<Integer> cardCount1;
    List<Integer> cardCount2;
    List<IDistrict> hand1;
    List<IDistrict> hand2;
    List<IDistrict> hand3;
    List<IDistrict> hand4;
    List<List<IDistrict>>  builtDistricts;
    List<List<IDistrict>>  builtDistricts1;
    List<List<IDistrict>>  builtDistricts2;
    List<Integer> cardCount;
    List<Integer> gold;
    List<IHero>  heros;
    List<Integer> gold1;
    List<IHero> heros1;
     HeroDeck heroDeck;
    int currentHeroRank1;
    int currentHeroRank2;
    Treasure treasure;


    @BeforeEach
    void setUp(){
        try {
            district1 = new District(2, Color.YELLOW, DistrictName.MANOIR);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            district2 = new District(1,Color.BLUE,DistrictName.MANOIR);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            district3 = new District(1,Color.GREEN,DistrictName.PALAIS);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            district4 = new District(2, Color.RED,DistrictName.CHATEAU);
        } catch (CardException e) {
            e.printStackTrace();
        }
        try {
            district5 = new District(2, Color.YELLOW,DistrictName.MANOIR);
        } catch (CardException e) {
            e.printStackTrace();
        }
        treasure=new Treasure(32);
        players=new ArrayList<IPlayer>();
        players1=new ArrayList<IPlayer>();
        players2=new ArrayList<IPlayer>();
        listDeck=new ArrayList<IDistrict>();
        listDeck.add(district1);
        listDeck.add(district2);
        listDeck.add(district3);
        listDeck.add(district4);
        listDeck.add(district5);
        information = new Information();
        information2 = new Information();
        information3 = new Information();
        information4 = new Information();
        player1 = new IA("Link");
        player2 = new IA("Kirby");
        player3 = new IA("Kazuya");
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        hand3 = new ArrayList<>();
        hand4 = new ArrayList<>();
        hand1.add(district1);
        hand1.add(district2);
        hand2.add(district2);
        hand3.add(district3);
        player1.setHand(hand1);
        player2.setHand(hand2);
        player3.setHand(hand3);
        heroDeck = Initialization.heroeList();
        player1.chooseHero(heroDeck,0);
        player2.chooseHero(heroDeck,0);
        player3.chooseHero(heroDeck,0);
        player3.setCrown();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players1.add(player1);
        players1.add(player3);
        players2.add(player2);
        players2.add(player3);
        deck = new DistrictDeck(Initialization.districtList());
        deck1 = new DistrictDeck(Initialization.districtList());
        information.setDeck(deck);

        information.setCrownHolder(player2);
        information2.setCrownHolder(player1);
        information.setChosenPlayer("Link");
        information2.setChosenPlayer("Kirby");
        information.setCurrentPlayer(player2);
        information2.setCurrentPlayer(player3);
        builtDistricts=new ArrayList<>();
        builtDistricts2=new ArrayList<>();
        gold=new ArrayList<>();
        cardCount=new ArrayList<>();
        heros = new ArrayList<>();
        gold1=new ArrayList<>();
        cardCount1=new ArrayList<>();
        heros1 = new ArrayList<>();
        builtDistricts1=new ArrayList<>();
        currentHeroRank1=player2.getHeroRank();
        players.stream().
                filter(player-> player.getHeroRank()!=currentHeroRank1 ).
                forEach(player->{
                    builtDistricts.add(player.getBuiltDistricts());
                    cardCount.add(player.getHand().size());
                    gold.add(player.getGold());
                });
        players.stream().
                filter(player-> player.getHeroRank()<currentHeroRank1).
                forEach(player-> heros.add(player.getRole()));
        information.setInformationForMagician(players, player2, deck);
        currentHeroRank2=player3.getHeroRank();
        players1.stream().
                filter(player-> player.getHeroRank()!=currentHeroRank2 ).
                forEach(player->{
                    builtDistricts2.add(player.getBuiltDistricts());
                    cardCount1.add(player.getHand().size());
                    gold1.add(player.getGold());
                });
        players1.stream().
                filter(player-> player.getHeroRank()<currentHeroRank2).
                forEach(player-> heros1.add(player.getRole()));
        information2.setInformationForMagician(players1, player3, deck1);




    }
    @Test
    void getCrownHolderTest(){
        IPlayer player=information.getCrownHolder();
        IPlayer player4=information2.getCrownHolder();
        assertEquals(player2,player);
        assertEquals(player1, player4);
        assertNotEquals(player1,player);
        assertNotEquals(player2,player4);
}
    @Test
    void getChosenPlayerTest(){
        IPlayer player=information.getChosenPlayer();
        IPlayer player4=information2.getChosenPlayer();
        assertEquals(player1,player);
        assertEquals(player2, player4);
        assertNotEquals(player,player3);
        assertNotEquals(player1,player4);
    }
    @Test
    void getCurrentPlayerTest(){
        IPlayer player=information.getCurrentPlayer();
        IPlayer player4=information2.getCurrentPlayer();
        assertEquals(player2,player);
        assertEquals(player3, player4);
        assertNotEquals(player,player3);
        assertNotEquals(player1,player4);
    }
    @Test
    void getDeckTest(){
        deck2=information.getDeck();
        assertEquals(deck2,deck);

    }
    @Test
    void getCardCountTest() {
         cardCount2=information.getCardCount();
        assertEquals(cardCount,cardCount2);
        assertNotEquals(cardCount1,cardCount2);
        assertNotEquals(cardCount1,cardCount2);
    }
    @Test
    void getBuiltDistrictsTest() {
        builtDistricts1 = information.getBuiltDistricts();
        assertEquals(builtDistricts1, builtDistricts);
        assertNotEquals(information2.getBuiltDistricts(), builtDistricts);
    }
    @Test
    void getHerosTest() {
        heros1 = information.getHeros();
        assertEquals(heros,heros1);
        assertNotEquals(information2.getHeros(), heros);
    }
    @Test
    void getGoldTest() {
        gold1 =  information.getGold();
        assertEquals(gold,gold1);
        assertNotEquals(information2.getGold(), gold);
    }
    @Test
    void setInformationForKingTest() {
        information3.setInformationForKing(player2,players2 ,treasure);
        assertEquals(information3.getCurrentPlayer(),player2);
        assertEquals(information3.getCrownHolder(), player3);
        assertEquals(information3.getChosenPlayer(),null);
    }
    @Test
    void setInformationForMerchantTest() {
        information4.setInformationForMerchant(player3 ,treasure);
        assertEquals(information4.getCurrentPlayer(),player3);
        assertEquals(information4.getChosenPlayer(),null);

    }
    @Test
    void setInformationForMagicianTest() {
        information4.setInformationForMagician(players1, player3, deck1);
        assertEquals(information4.getCurrentPlayer(), player3);
        assertEquals(information4.getDeck(), deck1);
        assertEquals(information4.getBuiltDistricts(), builtDistricts2);
        assertEquals(information4.getGold(), gold1);
        assertEquals(information4.getCardCount(), cardCount1);
        assertEquals(information4.getHeros(), heros1);
    }

}
