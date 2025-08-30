package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.List;
import java.util.Random;

public class Beer extends Cards {

    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand) {
        player[rank].setLives(player[rank].getLives() + 1);
        System.out.println("You drank a beer so you get 1 life, nice!");
        discardPile.add(player[rank].getCardsInHand().get(cardWantToUse));
        player[rank].getCardsInHand().remove(cardWantToUse);
    }

    @Override
    public String toString() {
        return "Beer";
    }


}
