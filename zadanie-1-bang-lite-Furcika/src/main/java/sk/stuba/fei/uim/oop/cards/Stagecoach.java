package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.List;
import java.util.Random;

public class Stagecoach extends Cards {
    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand) {
        player[rank].drawCard(pile, discardPile);
        discardPile.add(player[rank].getCardsInHand().get(cardWantToUse));
        player[rank].getCardsInHand().remove(cardWantToUse);
    }

    @Override
    public String toString() {
        return "Stagecoach";
    }
}
