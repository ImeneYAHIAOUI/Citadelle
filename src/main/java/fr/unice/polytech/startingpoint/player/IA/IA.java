package fr.unice.polytech.startingpoint.player.IA;
import fr.unice.polytech.startingpoint.cards.DistrictDeck;
import fr.unice.polytech.startingpoint.cards.IDistrict;
import fr.unice.polytech.startingpoint.core.Treasure;
import fr.unice.polytech.startingpoint.heros.HeroDeck;
import fr.unice.polytech.startingpoint.heros.HeroName;
import fr.unice.polytech.startingpoint.heros.IHero;
import fr.unice.polytech.startingpoint.player.IPlayer;
import fr.unice.polytech.startingpoint.player.IA.Strategies.*;


import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import fr.unice.polytech.startingpoint.cards.*;
import fr.unice.polytech.startingpoint.player.Player;

import java.util.ArrayList;
import java.util.List;


public class IA extends Player {
    public Bots bot = Bots.nonSpecified;
    public List<HerosChoice> thoughtPathList;
    public Predicate<IDistrict> isAffordable = district -> district.getPrice()<=gold ;
    public static BiFunction<Integer ,Integer,Integer > calculScore=(score, nbBuiltCard)->  100*score+10*nbBuiltCard;
    static Predicate<IDistrict> identicalCard(IDistrict district) {
        Predicate<IDistrict> identic = d -> d.getDistrictName().equals(district.getDistrictName());
        return identic;
    }
    /**
     *
     * @param playerName the IA object is constructed the same way as a Player object,
     *                   so we also only need the name of the player here.
     */
    public IA(String playerName){
        super(playerName);
        thoughtPathList = new ArrayList<>();
    }

    // ===============================================================================================================
    //
    //                                               HERO CHOICE
    //
    // ===============================================================================================================

    /**
     * this method chooses the hero for the bot based on the information it's given
     *
     */
    @Override
    public void chooseHero(HeroDeck heroes, Random rand, List<IPlayer> players) { // LEVEL 1
        if(bot.equals(Bots.random)){
            setRole(heroes.stream().findAny().orElse(null));
            return;
        }
        IHero hero = null;
        this.thoughtPathList = new ArrayList<HerosChoice>();
        HeroDecisionStandard heroDecisionStandard = new HeroDecisionStandard();
        hero = heroDecisionStandard.heroDecision(this,players,heroes,this.thoughtPathList,rand);
        this.setRole(hero);
    }

    // ===============================================================================================================
    //
    //                                                HERO ACTION
    //
    // ===============================================================================================================

    /**
     * this methode calls the action methode for the chosen hero
     * @param players,districtDeck,info the list of players
     * @param districtDeck
     * @param treasure
     * @param info
     */
    @Override
    public void activateHero(List<IPlayer> players, DistrictDeck districtDeck, Treasure treasure, IAToHero info ) {
        switch (role.getName()){
            case Merchant -> {
                info.setInformationForMerchant(this,treasure);
                role.doAction(info);
            }
            case King-> {
                info.setInformationForKing(this,players,treasure);
                role.doAction(info);
            }
            case Magician -> {
                info.setInformationForMagician(players,this, districtDeck);
                MagicianChoice choice = new MagicianChoice();
                choice.magicienChoice(info,isAffordable);
                role.doAction(info);
                }
            case Assassin -> {
                info.setInformationForAssassinOrThief(players,this,districtDeck);
                AssassinChoice choice = new AssassinChoice();
                choice.AssassinChoice(info);
                role.doAction(info);
            }
            case Thief ->  {
                info.setInformationForAssassinOrThief(players,this,districtDeck);
                ThiefChoice choice =new ThiefChoice();
                choice.ThiefChoice(info);
                role.doAction(info);
            }
            case Bishop -> {
                info.setInformationForBishop(this,treasure);
                role.doAction(info);
            }
            case Architect -> {
                info.setInformationForArchitect(this,districtDeck);
                role.doAction(info);
            }
            case Condottiere -> {
                CondottiereChoice condottiereChoice=new CondottiereChoice();
                info.setInformationForCondottiere(players,this,districtDeck,treasure);
                condottiereChoice.makeChoice(info);
                role.doAction(info);
            }
        }
    }

    // ===============================================================================================================
    //
    //                                      CHOOSE BETWEEN GOLD OR DISTRICT
    //
    // ===============================================================================================================
    /**
     * we use a methode from DrawOrGetGoldStrategies based on what strategy we want our IA to follow
     * for know we only have one methode
     */
    @Override
    public void drawOrGetPieces(DistrictDeck deck, Treasure treasure, IAToHero info,IAToWonder info2){
        // ============================================================================================================
        // If I have the wonder I apply its power

        // Once per turn, you can discard a neighborhood card from your hand and receive a gold coin in return.
        this.applyLaboratory(treasure,info2);

        // Once per turn, you can pay three gold to draw three cards.
        this.applyManufacture(deck,treasure,info2);

        // ============================================================================================================

        DrawOrGetGoldStrategies choice =new DrawOrGetGoldStrategies();
        choice.drawOrGetPieces(deck, treasure,info,isAffordable);
    }

    // ===============================================================================================================
    //
    //                                  BUILD OR NOT BUILD? THAT IS THE QUESTION
    //
    // ===============================================================================================================

    /**
     * this method chooses what move to make for the bot based on the information it's given
     * it's random based for now
     */
    @Override
    public void doAction(Treasure treasure, IAToHero info) {
        if(this.getRole().getName().equals(HeroName.Architect)){
            ArchitectChoice architectChoice = new ArchitectChoice();
            architectChoice.buildDistrict(this,treasure,info);
        }else {
            if (hand.stream().anyMatch(isAffordable)) {
                List<IDistrict> AffordableDistricts = hand.stream().filter(isAffordable).collect(Collectors.toList());
                IDistrict chosenDistrict = AffordableDistricts.get(0);
                while (AffordableDistricts.size() > 0 && builtDistricts.stream().anyMatch(identicalCard(chosenDistrict))) {
                    AffordableDistricts.remove(chosenDistrict);
                    if (AffordableDistricts.size() > 0) chosenDistrict = AffordableDistricts.get(0);
                }
                if (builtDistricts.stream().noneMatch(identicalCard(chosenDistrict))) {
                    buildDistrict(chosenDistrict);
                    treasure.addToTreasure(chosenDistrict.getPrice());
                    info.addBuiltDistrict(chosenDistrict);
                }
            }
        }
    }




    // ===============================================================================================================
    //
    //                                                FUNCTIONS
    //
    // ===============================================================================================================

    /**
     * searching for the maximum number of built districts per player can be useful for multiple heros
     *like the magician, so this static methode can be used in all the hero Strategies classes in case the information is needed
     */
    static public int searchForMaxNumberOfCards(IAToHero infos){
        List<Integer> cardNumbers = infos.getCardCount();
        int maxCardNumber = cardNumbers.stream().max(Integer::compare).get();
        return maxCardNumber;
    }

    /**
     *same as the searchForMaxNumberOfCards, many heros may need to know the maximum amount of gold
     * possessed by a player in order to make thier choices, this methode gives this information
     */
    public static int searchForMaxGold(IAToHero infos){
        List<Integer> gold = infos.getGold();
        int maxGold =  gold.stream().max(Integer::compare).get();
        return  maxGold;
    }

    /**
     * this methode search for doubles in two hands, it's useful for the magician and for
     * choosing whether to draw or get gold
     */
    public static List<IDistrict> searchForDoubles(List<IDistrict> hand, List<IDistrict> districtList){
        List<IDistrict> doubles = new ArrayList<>();
       for(IDistrict district : hand){
           for(IDistrict district2 : districtList){
               if(district2.getDistrictName().equals(district.getDistrictName()) && !district2.equals(district)){
                   doubles.add(district2);
                   break;
               }
           }
       }
        return doubles;
    }

    /**
     * the thief and the assassin need to choose a hero to kill/steal from,
     * so once they target a player, they need to  guess what hero they chose,
     * for that they will need this information:
     * @param CardNumber number of cards in the players hand
     * @param gold amount of gold
     * @param builtDistricts list of districts built by the player
     * @param guessingHero the role of the player who's guessing
     * @return if the IA manages to guess the targeted players role, this methode
     * returns it, otherwise it returns a null
     */
    public static HeroName guessHero(int CardNumber,int gold,List<IDistrict> builtDistricts,HeroName guessingHero,List<HeroName> visibleHeros){
        if (CardNumber<2 && !visibleHeros.contains(HeroName.Magician)) return HeroName.Magician;
        int green = 0;
        int blue = 0;
        int yellow = 0;
        int red = 0;
        List<HeroName> colorHeroes = List.of(HeroName.Merchant,HeroName.Bishop,HeroName.King,HeroName.Condottiere);
        for (IDistrict district : builtDistricts) {
            switch (district.getColor()) {
                case GREEN -> green++;
                case BLUE -> blue++;
                case YELLOW -> yellow++;
                case RED -> red++;
            }
        }
        List<Integer> colorValues = List.of(green,blue,yellow,red);
        int maxValue = colorValues.stream().max(Integer::compare).orElse(null);
        HeroName guess = colorHeroes.get(colorValues.indexOf(maxValue));
        if (maxValue>0 && !visibleHeros.contains(guess)) return guess;
        if(gold > 4 && !visibleHeros.contains(HeroName.Architect)) return HeroName.Architect;
        if(gold < 1 && guessingHero != HeroName.Thief && !visibleHeros.contains(HeroName.Thief)) return HeroName.Thief;
        else return null;
    }

    /**
     * once guessHero returns the guessed hero (or null), this methode
     * is responsible for finding the Hero object with the guessed hero name
     * @param chosenHero the guessed hero name, if null, we take a random hero name
     * @param infos information classe, used to find the hero with the guessed role name
     * @return if the guessed hero has been chosen by a player, the methode return the hero
     * else it returns null
     */
    public static IHero findChosenHero(HeroName chosenHero, IAToHero infos){
        if (chosenHero == null){
            //on ne met pas l'assassin dans cette liste car il ne pas choisir lui même et on le voleur ne peut pas le choisir non plus
            List<HeroName> allHeros = new ArrayList<>();
            allHeros.add(HeroName.Merchant);
            allHeros.add(HeroName.Bishop);
            allHeros.add(HeroName.Thief);
            allHeros.add(HeroName.King);
            allHeros.add(HeroName.Condottiere);
            allHeros.add(HeroName.Magician);
            allHeros.add(HeroName.Architect);
            //on enlève les heroes visibles
            allHeros.removeAll(infos.getVisibleHeroes());
            //il faut s'assurer aussi que le voleur n'arriive pas a choisir lui même
            chosenHero = allHeros.stream().filter(h -> h != infos.getCurrentPlayer().getRole().getName()).findAny().orElse(null);
        }

        IHero Hero = null;
        for (IHero hero : infos.getHeros()){

            if (hero.getName() == chosenHero) Hero = hero;
        }


        return Hero;
    }


    /**
     * in certain cases, players can get bonuses, this method is responsible
     * for attributing them
     */
    @Override
    public void addBonusScore(int val){
        this.score += val;
    }

    public List<HerosChoice> getThoughPath(){
        return thoughtPathList;
    }

    // ========================================================================================================
    //
    //                       WONDER: Make a choice according to the application of wonders
    //
    // ========================================================================================================

    /**
     * make a choice according to the action of Library
     *
     */
    @Override
    public int applyLibrary() {
        int numberOfCard = 0;

        if(this.getBuiltDistricts().stream().map(wonder -> wonder.getDistrictName()).anyMatch(districtName -> districtName.equals(DistrictName.LIBRARY))){
            numberOfCard = 2;
        }else{
            numberOfCard = 1;
        }

        return numberOfCard;
    }

    /**
     * Find the wonder
     * @param districtName
     * @return
     */
    private IDistrict findWonder(DistrictName districtName){
        IDistrict wonder = this.getBuiltDistricts().stream()
                .filter(district -> district.isWonder() && district.getDistrictName() == districtName)
                .findAny()
                .orElse(null);
        return wonder;
    }

    @Override
    public void applyDrocoport(IAToWonder info) {
        IDistrict wonder = findWonder(DistrictName.DROCOPORT);
        if (wonder != null) {
            info.setplayer(this);
            ((IWonder) wonder).doAction(info);
        }
    }

    @Override
    public void applyUniversity(IAToWonder info) {
        IDistrict wonder = findWonder(DistrictName.UNIVERSITY);
        if (wonder != null) {
            info.setplayer(this);
            ((IWonder) wonder).doAction(info);
        }
    }

    /**
     * make a choice according to the action of Laboratory
     * @param tresor
     */
    @Override
    public void applyLaboratory(Treasure tresor,IAToWonder info) {
        if(this.getBuiltDistricts().stream().map(wonder -> wonder.getDistrictName()).anyMatch(districtName -> districtName.equals(DistrictName.LABORATOIRE))) {
            IDistrict wonder = findWonder(DistrictName.LABORATOIRE);
            IDistrict expensive = this.getHand().stream()
                    .filter(district -> district.getPrice() > 4)
                    .findAny().orElse(null);
            if (wonder != null) {
                if (this.getHand() != null)
                    info.setplayer(this);
                info.setInformationForLaboratory(tresor,expensive, info.getplayer());
                ((IWonder) wonder).doAction(info);
            }
        }
    }

    /**
     * make a choice according to the action of Manufacture
     * @param deck
     * @param tresor
     */
    @Override
    public void applyManufacture (DistrictDeck deck, Treasure tresor,IAToWonder info){
        int i;
        info.setTreasure(tresor);
        info.setplayer(this);
        info.setdistrictdeck(deck);
        IDistrict wonder =findWonder( DistrictName.MANUFACTURE);
        if (wonder != null & this.getGold() >= 3) {
            int s = 0;
            int c = 0;
            for (i = 0; i < this.getHand().size(); i++) {
                if (this.getHand().get(i).getPrice() >= 3) {
                    s = s + 1;
                } else c = c + 1;
            }
            if (s > c || this.getHand().size() == 0) {
                ((IWonder )wonder).doAction(info);
            }
        }
    }

    /**
     *  make a choice according to the action of miracle court
     */
    @Override
    public void applyMiracleCourt(IAToWonder info) {
        List<Color> color = new ArrayList<>();
        List<Color> colorList = List.of(Color.PURPLE, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        IDistrict wonder = findWonder(DistrictName.LACOURDESMIRACLES);
        if (wonder != null) {
            int val = 0;

            if (this.getBuiltDistricts().stream().map(district -> district.getColor()).anyMatch(d -> d == Color.YELLOW)) {
                val++;
                color.add(Color.YELLOW);
            }
            if (this.getBuiltDistricts().stream().map(district -> district.getColor()).anyMatch(d -> d == Color.RED)) {
                val++;
                color.add(Color.RED);
            }
            if (this.getBuiltDistricts().stream().map(district -> district.getColor()).anyMatch(d -> d == Color.BLUE)) {
                val++;
                color.add(Color.BLUE);
            }
            if (this.getBuiltDistricts().stream().map(district -> district.getColor()).anyMatch(d -> d == Color.PURPLE)) {
                val++;
                color.add(Color.PURPLE);
            }
            if (this.getBuiltDistricts().stream().map(district -> district.getColor()).anyMatch(d -> d == Color.GREEN)) {
                val++;
                color.add(Color.GREEN);
            }
            if (val == 4) {
                Color chosenColor = colorList.stream().filter(color1 ->! color.contains(color1)).findAny().orElse(Color.PURPLE);
                info.setplayer(this);
                info.setInformationForMiracleCourt(chosenColor, this);
                ((IWonder )wonder).doAction(info);
            }
        }
    }

    /**
     * make a choice according to the action of observatory
     */
    @Override
    public int applyObservatory(){
        int numberOfCard = 0;

        if(this.getBuiltDistricts().stream().map(wonder -> wonder.getDistrictName()).anyMatch(districtName -> districtName.equals(DistrictName.OBSERVATORY))){
            numberOfCard = 3;
        }else{
            numberOfCard = 2;
        }

        return numberOfCard;
    }

    @Override
    public void applyMagicSchool(IAToWonder informations){
        Color playerColor = getRole().getColor();
        if (playerColor != Color.WHITE){
            List<IDistrict> possessedWonders = getBuiltDistricts().stream().
                    filter(district -> district.isWonder()).collect(Collectors.toList());

            IWonder MagicSchool = (IWonder) possessedWonders.stream().
                    filter(district -> district.getDistrictName().equals(DistrictName.ECOLEDEMAGIE)).
                    findAny().orElse(null);

            if (MagicSchool != null){
                informations.setInformationForMagicSchool(playerColor,this);
                MagicSchool.doAction(informations);
            }
        }
    }
    @Override
    public void applyCemetery(DistrictDeck deck, Treasure tresor, IDistrict card, IAToWonder info){
        IDistrict wonder = findWonder(DistrictName.CEMETRY);
           List<IDistrict> doubles = IA.searchForDoubles(hand,this.getBuiltDistricts());
           if(role.getName()!=HeroName.Condottiere && this.getGold()>=1 && !doubles.contains(card) ){
               info.setInformationForCemetry(card,this);
               info.setTreasure(tresor);
               info.setdistrictdeck(deck);
               ((IWonder) wonder).doAction(info);
           }
       }

    
}