package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.List;
import java.util.Random;

public class Bang extends Cards {
    @Override
    public void use(Player[] player, int rank, List<Cards> pile, List<Cards> discardPile, int cardWantToUse, Random random) {
        int playerToShoot = chooseTo(player, "Bang", rank);
        boolean searchForMissedCard = true;
        if (player[playerToShoot].isHasBarrel()) {
            System.out.println("The player you want to shoot has a barrel so he has a 25% chance to dodge your bullet");
            boolean[] dodgeBulletPercentage = {false, false, false, true};
            if (dodgeBulletPercentage[random.nextInt(4)]) {
                System.out.println(player[(playerToShoot)].getRank() + ".player managed to dodge the bullet");
                searchForMissedCard = false;
            }
        }
        if (searchForMissedCard) {
            boolean hasMissed = false;
            boolean searchInFrontCards = true;
            for (int f = 0; f < player[playerToShoot].getCardsInHand().size(); f++) {
                if (player[playerToShoot].getCardsInHand().get(f) instanceof Missed) {
                    discardPile.add(player[playerToShoot].getCardsInHand().get(f));
                    player[playerToShoot].getCardsInHand().remove(f);
                    System.out.println(player[playerToShoot].getRank() + ". player had a Missed card so you missed the shot");
                    searchInFrontCards = false;
                    hasMissed = false;
                    break;
                } else {
                    hasMissed = true;
                }
            }
            if (searchInFrontCards) {
                for (int f = 0; f < player[playerToShoot].getCardsInFrontOf().size(); f++) {
                    if (player[playerToShoot].getCardsInFrontOf().get(f) instanceof Missed) {
                        discardPile.add(player[playerToShoot].getCardsInFrontOf().get(f));
                        player[playerToShoot].getCardsInFrontOf().remove(f);
                        System.out.println(playerToShoot + ". player had a Missed card so you missed the shot");
                        break;
                    } else {
                        hasMissed = true;
                    }
                }
            }
            if (hasMissed) {
                player[playerToShoot].setLives(player[playerToShoot].getLives() - 1);
            }

        }
        discardPile.add(player[rank].getCardsInHand().get(cardWantToUse));
        player[rank].getCardsInHand().remove(cardWantToUse);
    }

    @Override
    public String toString() {
        return "Bang";
    }


}
