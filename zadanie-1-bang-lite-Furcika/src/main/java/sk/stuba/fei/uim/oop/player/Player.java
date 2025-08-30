package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.cards.Cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private int lives;
    private final List<Cards> cardsInHand = new ArrayList<Cards>();
    private final List<Cards> cardsInFrontOf = new ArrayList<Cards>();
    private int rank;
    private boolean inPrison;
    private boolean hasDynamite;
    private boolean hasBarrel;

    private boolean dead;

    public Player(int rank) {
        this.rank = rank;
        this.lives = 4;
    }

    public List<Cards> getCardsInHand() {
        return cardsInHand;
    }

    public List<Cards> getCardsInFrontOf() {
        return cardsInFrontOf;
    }

    public boolean isInPrison() {
        return inPrison;
    }

    public void setInPrison(boolean inPrison) {
        this.inPrison = inPrison;
    }

    public boolean isHasDynamite() {
        return hasDynamite;
    }

    public void setHasDynamite(boolean hasDynamite) {
        this.hasDynamite = hasDynamite;
    }

    public boolean isHasBarrel() {
        return hasBarrel;
    }

    public void setHasBarrel(boolean hasBarrel) {
        this.hasBarrel = hasBarrel;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void drawCard(List<Cards> pile, List<Cards> discardPile) {
        if (pile.size() < 2) {
            pile.addAll(discardPile);
            discardPile.clear();
            Collections.shuffle(pile);
        }
        for (int i = 0; i < 2; i++) {
            this.cardsInHand.add(pile.get(pile.size() - 1));
            pile.remove(pile.size() - 1);
        }
        System.out.println(this.getRank() + ".player drew 2 cards from the deck");


    }

    public void showCards(Player asd) {
        System.out.print("cards in hand: ");
        for (int j = 0; j < asd.getCardsInHand().size(); j++) {
            System.out.print((j + 1) + "." + asd.getCardsInHand().get(j).toString() + "\t");
        }
        System.out.print("\ncards in front of:");
        for (int j = 0; j < asd.getCardsInFrontOf().size(); j++) {
            System.out.print((j + 1) + "." + asd.getCardsInFrontOf().get(j).toString() + "\t");
        }
    }


}




































   /* int lives;
    List<Card> cardsInHand = new ArrayList<Card>();
    List<Card> cardsInFrontOf = new ArrayList<Card>();
    int rank;
    boolean inPrison;
    boolean hasDynamite;
    boolean hasBarrel;

    public Player(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void DrawCard(List<Card> pile){
        for (int i=0;i<2;i++){
            this.cardsInHand.add(pile.get(pile.size()-1));
            pile.remove(pile.size()-1);
        }
}

public void PlayCard(){

}
public void DiscardExcessCard(){

}*/


