package entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.*;

public class Deck {
    private final List<Card> cards;
    private final Map<String, ImageIcon> cardIconsMap;

    public Deck() throws Exception {
        this.cards = new ArrayList<>();
        this.cardIconsMap = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                Card card = new Card(i, j);
                this.cards.add(card);
                this.cardIconsMap.put(card.getCardName(), new ImageIcon(ImageIO.read(this.getFileFromResourceAsStream(card.getCardIconPath())).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            }
        }
        this.cardIconsMap.put("Back", new ImageIcon(ImageIO.read(this.getFileFromResourceAsStream("card_back.png")).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
    }

    public Deck(boolean ignoredBoolean) throws Exception {
        this.cards = new ArrayList<>();
        this.cardIconsMap = new HashMap<>();
        this.cardIconsMap.put("Back", new ImageIcon(ImageIO.read(this.getFileFromResourceAsStream("card_back.png")).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Deck deck, Card card) {
        this.cards.add(card);
        this.cardIconsMap.put(card.getCardName(), deck.getCardIcon(card));
    }

    public void addCard(Deck deck, Card card, boolean open) {
        if (!open) {
            card.setVisible(false);
        }
        this.cards.add(card);
        this.cardIconsMap.put(card.getCardName(), deck.getCardIcon(card));
    }

    public void shuffleCards() {
        Collections.shuffle(this.cards);
    }

    public int getCardsSummary() {
        int summary = 0;
        int ace = 0;
        for (Card c : cards) {
            if (c.getRank() != Rank.Ace) summary += c.getValue();
            else ace += 11;
        }
        while (ace >= 11 && summary + ace > 21) {
            ace -= 10;
        }
        return summary + ace;
    }

    public int getDeckSize() {
        return cards.size();
    }

    public ImageIcon getCardIcon(Card card) {
        return cardIconsMap.get(card.getCardName());
    }

    public ImageIcon getCardIcon(String cardName) {
        return cardIconsMap.get(cardName);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
