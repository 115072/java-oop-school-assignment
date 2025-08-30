package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.inputvalidation.InputValidation;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Cards {
    public abstract void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand);

    public int chooseTo(Player[] player, String cardName, int rank) {
        ArrayList<Integer> players = new ArrayList<>();
        for (int i = 0; i < player.length; i++) {
            if (i != rank && !player[i].isDead()) {
                players.add(player[i].getRank());
            }
        }
        InputValidation checker = new InputValidation();
        int playerToShoot = ZKlavesnice.readInt("\nwhich player you want to " + cardName + "? " + Arrays.toString(players.toArray()));
        playerToShoot = checker.checkAnswer(players, playerToShoot);
        for (int i = 0; i < player.length; i++) {
            if (playerToShoot == player[i].getRank()) {
                playerToShoot = i;
                break;
            }
        }
        return playerToShoot;
    }
}

