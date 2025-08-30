package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.List;
import java.util.Random;

public class Prison extends Cards {
    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random rand) {
        int playerToPrison = chooseTo(player, "Prison", rank);
        if (player[playerToPrison].isInPrison()) {
            System.out.println("He is already in prison, you can't prison him");
            playerToPrison = chooseTo(player, "Prison", rank);
        }
        player[playerToPrison].setInPrison(true);
        player[playerToPrison].getCardsInFrontOf().add(player[rank].getCardsInHand().get(cardWantToUse));
        player[rank].getCardsInHand().remove(cardWantToUse);
    }

    @Override
    public String toString() {
        return "Prison";
    }
}
