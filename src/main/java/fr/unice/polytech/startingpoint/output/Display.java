package fr.unice.polytech.startingpoint.output;

import fr.unice.polytech.startingpoint.cards.Color;
import fr.unice.polytech.startingpoint.player.Player;

import java.util.List;


public class Display {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Display() {}


    public static String displayRank(List<Player> players) {
        StringBuilder ranking;
        int rank = 1;
        ranking = new StringBuilder("1st place : " + players.get(0) + " -> " + players.get(0).getScore() + " points.\n");
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getScore() != players.get(i - 1).getScore()) rank = i + 1;
                String s = switch (rank) {
                    case 1 -> "st";
                    case 2 -> "nd";
                    case 3 -> "rd";
                    default -> "th";

                };
            ranking.append(rank+s+" place : "+players.get(i)+" -> "+players.get(i).getScore()+" points.\n");


        }
        return ranking.toString();
    }

    public static String displayWinners(List<Player> ranking){
        StringBuilder winners = new StringBuilder("" + ranking.get(0));
         int nbWinners = 1;

        for(int i = 1; i < ranking.size();i++){
            if (ranking.get(i).getScore() == ranking.get(0).getScore()) {
                winners.append(" - ").append(ranking.get(i));
                nbWinners++;
            }
        }
        String plural = nbWinners>1 ? "s":"";
        winners = new StringBuilder("Winner" + plural + " : " + winners + "\n\n");
        return winners.toString();
    }

    public static String displayResult(GameResult result){
        List<Player> rankedPlayers = result.getRanking();
        return displayWinners(rankedPlayers)+displayRank(rankedPlayers);
    }

    /**
     * Displays all elements of the players of the round
     * @param playersList
     * @param round
     */
    static public void displayRound(List<Player> playersList, int round){
        System.out.println("\n" +
                " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄ \n" +
                "▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌\n" +
                " ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀ \n");

        System.out.println("\tRound : " + round + "\n");

        playersList.forEach(player -> {
            System.out.println("\t" + player + " ;");

            System.out.print("\t" + "District -> [ ");
            player.getHand().forEach(district -> {
                if(district.getColor() == Color.YELLOW)
                    System.out.print(ANSI_YELLOW + district.getDistrictName() + ANSI_RESET +" , ");

                if(district.getColor() == Color.BLUE)
                    System.out.print(ANSI_BLUE + district.getDistrictName() + ANSI_RESET +" , ");

                if(district.getColor() == Color.RED)
                    System.out.print(ANSI_RED + district.getDistrictName() + ANSI_RESET +" , ");

                if(district.getColor() == Color.GREEN)
                    System.out.print(ANSI_GREEN + district.getDistrictName() + ANSI_RESET +" , ");

                if(district.getColor() == Color.PURPLE)
                    System.out.print(ANSI_PURPLE + district.getDistrictName() + ANSI_RESET +" , ");
            });
            System.out.println(" ]");

            System.out.print("\t" + "Hero -> [ ");
            if(player.getRole().getColor() == Color.YELLOW)
                System.out.print(ANSI_YELLOW + player.getRole().getName() + ANSI_RESET);

            if(player.getRole().getColor() == Color.PURPLE)
                System.out.print(ANSI_PURPLE + player.getRole().getName() + ANSI_RESET);

            if(player.getRole().getColor() == Color.RED)
                System.out.print(ANSI_RED + player.getRole().getName() + ANSI_RESET);

            if(player.getRole().getColor() == Color.BLUE)
                System.out.print(ANSI_BLUE + player.getRole().getName() + ANSI_RESET);

            if(player.getRole().getColor() == Color.GREEN)
                System.out.print(ANSI_GREEN+ player.getRole().getName() + ANSI_RESET);
            System.out.println(" ]\n");
        });
    }
}