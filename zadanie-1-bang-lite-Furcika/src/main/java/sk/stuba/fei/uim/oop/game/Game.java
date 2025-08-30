package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.inputvalidation.InputValidation;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.*;


public class Game {

    public final String[] STRING_ANSWERS = {"y", "n"};

    public Game() {
        System.out.println("BANG GAME");
        Random rand = new Random();
        int numberOfPlayers = ZKlavesnice.readInt("Enter the number of players:");
        while (numberOfPlayers <= 1 || numberOfPlayers >= 5) {
            numberOfPlayers = ZKlavesnice.readInt("Enter the number of players:");
        }
        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player((i + 1));
        }
        List<Cards> pile = new ArrayList<>();
        List<Cards> discardedPile = new ArrayList<>();
        this.inicializeCardPile(pile);
        this.handOutStartingCards(players, pile);
        int turns = 1;
        while (true) {
            int alivecounter = 0;
            int playerIndex = 0;
            for (int i = 0; i < players.length; i++) {
                if (!players[i].isDead()) {
                    alivecounter++;
                    playerIndex = i;
                }
            }
            if (alivecounter == 1) {
                System.out.println("\nCongratulations!!! " + players[playerIndex].getRank() + ".player won the game!!!");
                break;

            }
            this.playTurns(pile, turns, players, discardedPile, rand);
            turns++;
        }

    }

    private void checkInprisonment(Player player, Random rand, List<Cards> discardPile) {
        if (player.isInPrison()) {
            System.out.println(player.getRank() + ".player has a 25% chance te leave the prison.");
            boolean[] leavePrisonPercentage = {false, false, false, true};
            if (leavePrisonPercentage[rand.nextInt(4)]) {
                System.out.println("You managed to escape the prison!!!");
                player.setInPrison(false);
                for (int i = 0; i < player.getCardsInFrontOf().size(); i++) {
                    if (player.getCardsInFrontOf().get(i) instanceof Prison) {
                        discardPile.add(player.getCardsInFrontOf().get(i));
                        player.getCardsInFrontOf().remove(i);
                    }
                }
            } else {
                System.out.println(player.getRank() + ".player can't have a turn because he is in prison");
            }
        }
    }

    private void handOutStartingCards(Player[] player, List<Cards> card) {
        for (Player value : player) {
            for (int j = 0; j < 4; j++) {
                value.getCardsInHand().add(card.get(card.size() - 1));
                card.remove(card.size() - 1);
            }
        }

    }


    private void playTurns(List<Cards> pile, int turn, Player[] players, List<Cards> discardPile, Random rand) {
        System.out.println("~~~~~~~~ " + turn + ".turn ~~~~~~~~");
        int playerIndex = 0;
        while (true) {
            this.checkPlayerHasDynamite(players, playerIndex, rand, discardPile);
            this.checkInprisonment(players[playerIndex], rand, discardPile);
            if (!players[playerIndex].isInPrison()) {
                playerIndex = checkIfPlayerDead(players, playerIndex, discardPile);
                int alivecounter = checkIfOnePlayerLives(players);
                if (alivecounter == 1) {
                    break;
                }
                System.out.println(players[playerIndex].getRank() + ".player ");
                System.out.println("lives: " + players[playerIndex].getLives());
                players[playerIndex].drawCard(pile, discardPile);
                players[playerIndex].showCards(players[playerIndex]);
                String wantToPlayCards = ZKlavesnice.readString("\ndo you want to play any of your cards?[y/n]");
                InputValidation checker = new InputValidation();
                wantToPlayCards = checker.checkAnswer(STRING_ANSWERS, wantToPlayCards);
                if (wantToPlayCards.equals("y")) {
                    ArrayList<Integer> cardsCanBePlayed = new ArrayList<>();
                    int cardWantToPlay = 1;
                    while (true) {
                        if (players[playerIndex].getCardsInHand().size() == 0 || cardWantToPlay == 0) {
                            break;
                        }
                        cardsCanBePlayed.add(0);
                        for (int i = 0; i < players[playerIndex].getCardsInHand().size(); i++) {
                            cardsCanBePlayed.add((i + 1));
                        }
                        players[playerIndex].showCards(players[playerIndex]);
                        cardWantToPlay = ZKlavesnice.readInt("\nWhich card do you want to play? If you enter 0 you'll end your turn");
                        cardWantToPlay = checker.checkAnswer(cardsCanBePlayed, cardWantToPlay);
                        if (cardWantToPlay != 0) {
                            players[playerIndex].getCardsInHand().get((cardWantToPlay) - 1).use(players, playerIndex, pile, discardPile, (cardWantToPlay - 1), rand);
                        }
                        cardsCanBePlayed.clear();
                    }
                }
            }
            this.removeInPrionsment(players[playerIndex], discardPile);
            this.checkIfPlayerHasMoreCardsThanLives(players[playerIndex], discardPile);
            System.out.println("\n----------------------------------");

            if (playerIndex == (players.length - 1)) {
                break;
            }
            playerIndex++;
        }


    }

    private int checkIfOnePlayerLives(Player[] players) {
        int alivecounter = 0;
        for (int i = 0; i < players.length; i++) {
            if (!players[i].isDead()) {
                alivecounter++;
            }
        }
        return alivecounter;
    }

    private void removeInPrionsment(Player player, List<Cards> discardPile) {
        player.setInPrison(false);
        for (int i = 0; i < player.getCardsInFrontOf().size(); i++) {
            if (player.getCardsInFrontOf().get(i) instanceof Prison) {
                discardPile.add(player.getCardsInFrontOf().get(i));
                player.getCardsInFrontOf().remove(i);
            }
        }
    }

    private int checkIfPlayerDead(Player[] players, int playerIndex, List<Cards> discardPile) {
        int newPlayerindex = playerIndex;
        if (players[playerIndex].getLives() < 1 && !players[playerIndex].isDead()) {
            if (players[playerIndex].getCardsInHand().size() != 0) {
                discardPile.addAll(players[playerIndex].getCardsInHand());
            }
            System.out.println(players[playerIndex].getRank() + ". player hasn't got any lives so he is dead :(");
            players[playerIndex].setDead(true);
            int i = playerIndex;
            while (true) {
                if (!players[i].isDead()) {
                    newPlayerindex = i;
                    break;
                }
                if (i == (players.length - 1)) {
                    i = 0;
                }
                i++;
            }
        }
        return newPlayerindex;
    }

    private void checkIfPlayerHasMoreCardsThanLives(Player player, List<Cards> discardPile) {
        if (player.getCardsInHand().size() > player.getLives()) {
            int cardsMustDrop = player.getCardsInHand().size() - player.getLives();
            System.out.println("\nyou got more cards in your hand than lives, you must drop " + cardsMustDrop + " cards!!!");
            String[] cardsMustDropArray = new String[cardsMustDrop];
            InputValidation checker = new InputValidation();
            ArrayList<Integer> cardsCanDropList = new ArrayList<>();
            for (int i = 0; i < player.getCardsInHand().size(); i++) {
                cardsCanDropList.add((i + 1));
            }
            for (int j = 0; j < cardsMustDrop; j++) {
                int ans = ZKlavesnice.readInt((j + 1) + ".card: ");
                ans = checker.checkAnswer(cardsCanDropList, ans);
                cardsMustDropArray[j] = String.valueOf(ans);
            }
            Arrays.sort(cardsMustDropArray, Collections.reverseOrder());
            for (int j = 0; j < cardsMustDropArray.length; j++) {
                discardPile.add(player.getCardsInHand().get((Integer.parseInt(cardsMustDropArray[j]) - 1)));
                player.getCardsInHand().remove((Integer.parseInt(cardsMustDropArray[j]) - 1));
            }
            player.showCards(player);
        }

    }

    private void checkPlayerHasDynamite(Player[] players, int playerIndex, Random rand, List<Cards> discardPile) {
        if (players[playerIndex].isHasDynamite()) {
            System.out.println(players[playerIndex].getRank() + ".player has the dynamite in front of him. He has 1/8 chance to explode");
            boolean[] dynamiteExplodePercentage = {false, false, false, false, false, false, false, true};
            if (dynamiteExplodePercentage[rand.nextInt(8)]) {
                System.out.println("the dynamite exploded in front of you, you lost 3 lives");
                players[playerIndex].setLives(players[playerIndex].getLives() - 3);
                players[playerIndex].setHasDynamite(false);
                for (int i = 0; i < players[playerIndex].getCardsInFrontOf().size(); i++) {
                    if (players[playerIndex].getCardsInFrontOf().get(i) instanceof Dynamite) {
                        discardPile.add(players[playerIndex].getCardsInFrontOf().get(i));
                        players[playerIndex].getCardsInFrontOf().remove(i);
                    }
                }

            } else {
                System.out.println("the dynamite didn't explode in front of you, it will be passed to the previous person");
                players[playerIndex].setHasDynamite(false);
                if (playerIndex == 0) {
                    players[(players.length - 1)].setHasDynamite(true);
                } else {
                    players[playerIndex - 1].setHasDynamite(true);
                }
                for (int i = 0; i < players[playerIndex].getCardsInFrontOf().size(); i++) {
                    if (players[playerIndex].getCardsInFrontOf().get(i) instanceof Dynamite) {
                        discardPile.add(players[playerIndex].getCardsInFrontOf().get(i));
                        players[playerIndex].getCardsInFrontOf().remove(i);
                    }
                }
            }
        }
    }

    private void inicializeCardPile(List<Cards> pile) {
        fillPile(new Beer(), 8, pile);
        fillPile(new Barrel(), 2, pile);
        fillPile(new Bang(), 30, pile);
        fillPile(new Prison(), 3, pile);
        fillPile(new CatBalou(), 6, pile);
        fillPile(new Dynamite(), 1, pile);
        fillPile(new Stagecoach(), 4, pile);
        fillPile(new Missed(), 15, pile);
        fillPile(new Indians(), 2, pile);
        Collections.shuffle(pile);
    }

    private void fillPile(Cards card, int limit, List<Cards> pile) {
        for (int i = 0; i < limit; i++) {
            pile.add(card);
        }
    }


}








