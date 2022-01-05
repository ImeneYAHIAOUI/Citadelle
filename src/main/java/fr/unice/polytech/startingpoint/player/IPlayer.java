package fr.unice.polytech.startingpoint.player;
import fr.unice.polytech.startingpoint.cards.DistrictDeck;
import fr.unice.polytech.startingpoint.cards.IAToWonder;
import fr.unice.polytech.startingpoint.cards.IDistrict;
import fr.unice.polytech.startingpoint.core.Treasure;

import java.util.List;

import fr.unice.polytech.startingpoint.heros.HeroDeck;
import fr.unice.polytech.startingpoint.heros.HeroName;
import fr.unice.polytech.startingpoint.heros.IHero;
import fr.unice.polytech.startingpoint.player.IA.HerosChoice;
import fr.unice.polytech.startingpoint.player.IA.IAToHero;

import java.util.Random;

/**
 * The citadelle class needs these three methods to function.
 * When we creat a player class, whether it's for a human or a bot,
 * by implementing this interface, we would be obligated to also implement
 * its method. This way we won't need to rework the citadelle class
 */

public interface IPlayer {
    /**
     * as the name indicates, this method chooses the role of the player for the round
     */
    void chooseHero(HeroDeck heroes, Random rand, List<IPlayer> players);

    /**
     * each hero has a function, if the player chooses to, they can activate it by using
     * this method
     * @param players,districtDeck,info the list of players
     */
    void activateHero(List<IPlayer> players, DistrictDeck districtDeck, Treasure treasure, IAToHero info);

    /**
     * this method is responsible for choosing and implementing the players move.
     * getting 2 gold pieces or drawing two district cards
     * choosing what district to build? (maybe another method will take care of that, but it most
     * likely will be called here)
     */
    void doAction(Treasure treasure, IAToHero info);

    /**
     * Give the player its first district cards by adding them to hand
     * @param giveDistrict list of given district cards
     */
    void getDistrict(List<IDistrict> giveDistrict);

    /**
     * role getter
     * @return the player's role
     */
    IHero getRole();

    /**
     * hand getter
     * @return the players hand
     */
    List<IDistrict> getHand();

    /**
     * Take off the crown
     */
    void unSetCrown();

    /**
     * Add gold to the player
     * @param addedValue
     */
    void addGold(int addedValue);

    void addScore(int addedScore);
        /**
     * Remove gold from the player
     * @param removevalue
     */
    void removeGold(int removevalue);

    /**
     * Choice between district and gold
     * @param deck
     * @param treasure
     * @param info
     */
    void drawOrGetPieces(DistrictDeck deck, Treasure treasure, IAToHero info,IAToWonder info2);

    /**
     * Added end of game bonus
     * @param val
     */
    void addBonusScore(int val);

    // ========================================================================================================
    //                       WONDER: Make a choice according to the application of wonders
    // ========================================================================================================

    void applyLaboratory(Treasure tresor, IAToWonder info);
    void applyManufacture(DistrictDeck deck, Treasure tresor,IAToWonder info);
    void applyMiracleCourt(IAToWonder info);
    int applyObservatory();
    int applyLibrary();
    void applyDrocoport(IAToWonder info);
    void applyUniversity(IAToWonder info);
    void applyMagicSchool(IAToWonder informations);
    void applyCemetery(DistrictDeck deck,Treasure tresor,IDistrict card,IAToWonder info);

    // ========================================================================================================
    //                                                GETTER
    // ========================================================================================================

    int getHeroRank();
    HeroName getTargetedHero();
    List<IDistrict> getBuiltDistricts();
    int getGold();
    String getName();
    boolean getIsAssigned();
    boolean getStolenPerson();
    IPlayer getStolenBy();
    int getScore();
    boolean getCrown();
    IPlayer getChosenPlayer();
    IDistrict getCardDestroyedByCondottiere();

    // ========================================================================================================
    //                                                SETTER
    // ========================================================================================================

    void setStolenPerson();
    void setIsAssigned();
    void setTargetedHero(HeroName targetedHero);
    void unsetIsAssigned();
    void setStolenBy(IPlayer player);
    void unSetStolenPerson();
    void setScore(int score);
    void setCrown();
    void setRole(IHero hero);
    void setHand(List<IDistrict> hand);
    void setChosenPlayer(IPlayer player);
    void setCardDestroyedByCondottiere(IDistrict cardDestroyedByCondottiere);
}
