package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.List;
import java.util.Random;

public class CatBalou extends Cards {
    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand) {
        int playerToCatBalou = chooseTo(player, "Cat Balou", rank);
        String handOrTableChoice = ZKlavesnice.readString("\nDo you want to discard from his hand or from the table?[h/t]");
        if (handOrTableChoice.equals("h")) {
            if (player[playerToCatBalou].getCardsInHand().isEmpty()) {
                System.out.println("You can not play this card on the player because he hasn't got any cards");
            } else {
                int number = rand.nextInt(player[playerToCatBalou].getCardsInHand().size());
                System.out.println("you removed " + player[playerToCatBalou].getRank() + ".players " + player[playerToCatBalou].getCardsInHand().get(number).toString() + " card");
                discardPile.add(player[playerToCatBalou].getCardsInHand().get(number));
                player[playerToCatBalou].getCardsInHand().remove(number);
            }
        } else if (handOrTableChoice.equals("t")) {
            if (player[playerToCatBalou].getCardsInFrontOf().isEmpty()) {
                System.out.println("You can not play this card on the player because he hasn't got any cards");
            } else {
                int number = rand.nextInt(player[playerToCatBalou].getCardsInFrontOf().size());
                System.out.println("you removed " + player[playerToCatBalou].getRank() + ".players " + player[playerToCatBalou].getCardsInFrontOf().get(number).toString() + " card");
                discardPile.add(player[playerToCatBalou].getCardsInFrontOf().get(number));
                player[playerToCatBalou].getCardsInFrontOf().remove(number);
            }
        }
        discardPile.add(player[rank].getCardsInHand().get(cardWantToUse));
        player[rank].getCardsInHand().remove(cardWantToUse);
    }

    @Override
    public String toString() {
        return "Cat Balou";
    }
}
