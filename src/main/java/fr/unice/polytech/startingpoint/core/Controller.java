package fr.unice.polytech.startingpoint.core;

import fr.unice.polytech.startingpoint.player.IPlayer;

public class Controller {
    IPlayer assassinated;
    IPlayer stolenPerson;
    IPlayer thief;

    public void  GiveGoldToTheTief(){
        if(stolenPerson!=null && thief!=null){
            int gold=stolenPerson.getGold();
            thief.addGold(gold);
            stolenPerson.removeGold(gold);
            stolenPerson=null;
            thief=null;
        }

    }
    public boolean isStolenPerson(IPlayer player){
        return player.equals(stolenPerson);
    }
    public boolean isAssasinated(IPlayer player){
        return player.equals(assassinated);
    }
    public void unSetAssassinated() {
        this.assassinated = null;
    }
    public void setAssassinated(IPlayer assassinated) {
        this.assassinated = assassinated;
    }

    public IPlayer getAssassinated() {
        return assassinated;
    }

    public IPlayer getThief() {
        return thief;
    }

    public void setThief(IPlayer thief) {
        this.thief = thief;
    }

    public IPlayer getStolenPerson() {
        return stolenPerson;
    }

    public void setStolenPerson(IPlayer stolenPerson) {
            this.stolenPerson = stolenPerson;

    }
}
