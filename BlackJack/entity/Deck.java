package entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Deck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                           
            }
        }
    }

    // TODO: Add a collection with cards images for speed/memory increase
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void shuffleCards() {
        Collections.shuffle(this.cards);
    }
}
