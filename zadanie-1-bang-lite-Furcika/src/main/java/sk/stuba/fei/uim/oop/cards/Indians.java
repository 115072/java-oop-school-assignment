package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.List;
import java.util.Random;

public class Indians extends Cards {
    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand) {
        System.out.println("Starting to Indian the other players!");
        for (int f = 0; f < player.length; f++) {
            if (f != rank) {
                for (int l = 0; l < player[f].getCardsInHand().size(); l++) {
                    if (player[(f)].getCardsInHand().get(l) instanceof Bang) {
                        player[(f)].getCardsInHand().remove(l);
                        System.out.println("\n" + player[f].getRank() + ". player had a Bang card so the Indians didn't work");
                        break;
                    } else {
                        player[(f)].setLives(player[f].getLives() - 1);
                    }
                }
            }
        }
        discardPile.add(player[rank].getCardsInHand().get(cardWantToUse));
        player[rank].getCardsInHand().remove(cardWantToUse);
    }

    @Override
    public String toString() {
        return "Indians";
    }
}
