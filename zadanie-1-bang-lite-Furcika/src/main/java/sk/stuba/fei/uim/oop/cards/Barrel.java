package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.List;
import java.util.Random;

public class Barrel extends Cards {
    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand) {
        if (player[rank].isHasBarrel()) {
            System.out.println("You can't have two barrels in front of you");
        } else {
            player[rank].setHasBarrel(true);
            player[rank].getCardsInFrontOf().add(player[rank].getCardsInHand().get(cardWantToUse));
            player[rank].getCardsInHand().remove(cardWantToUse);
        }
    }

    @Override
    public String toString() {
        return "Barrel";
    }
}
