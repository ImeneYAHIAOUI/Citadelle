package fr.unice.polytech.startingpoint;


import fr.unice.polytech.startingpoint.heros.HeroDeck;
import fr.unice.polytech.startingpoint.player.*;
import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.core.*;
import fr.unice.polytech.startingpoint.output.*;


import java.util.ArrayList;
import java.util.Collections;


public class Citadelle {
    private DistrictDeck districtDeck;
    private ArrayList<IPlayer> players;
    private HeroDeck heroes;
    private int round;

    /**
     * Main method of the game
     */
    public void game(int numberOfplayers){
        //Initialization
        districtDeck = new DistrictDeck(Initialization.districtList());
        players = new ArrayList<IPlayer>();
        heroes = Initialization.heroeList();
        round = 1;
        int NumberOfBuiltDistrict=0;

        for(int i=1;i<numberOfplayers+1;i++){
            players.add(new IA("Player"+i));
        }

        //Rounds
        while(NumberOfBuiltDistrict < 4){
            players.forEach(player -> {
                player.getDistrict(districtDeck.giveDistrict(1));
            });

            for(IPlayer player: players){
                player.HaveTheListOfHeroes(heroes);
                player.chooseHero();
                heroes.remove(player.getRole());
                NumberOfBuiltDistrict = this.maxDistrictObtained();
            }

            Display.round(players,round);
            heroes = Initialization.heroeList();
            round ++;

        }


        GameComparator compare=new GameComparator(players);

        GameResult result = compare.getResult();

        Display.displayResult(result);
    }

    /**
     * Returns the maximum number of district among all players
     * @return int
     */
    public int maxDistrictObtained(){
        int max = players.stream().mapToLong(player -> player.getHand().stream().count()).mapToInt(player -> (int) player).filter(player -> player >= 0).max().orElse(0);

        return max;
    }

    /**
     *
     *//*
    void orderTheListOfPlayersAccordingToTheirCharacterCard(){
        Collections.sort();
    }*/
} 