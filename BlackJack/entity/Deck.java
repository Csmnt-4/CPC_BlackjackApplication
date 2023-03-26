package entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private Map<String, ImageIcon> cardIconsMap;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Deck() throws Exception {
        this.cards = new ArrayList<>();
        this.cardIconsMap = new HashMap<String, ImageIcon>();
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                Card card = new Card(i, j);
                this.cards.add(card);
//                System.out.println(card.getCardName()); TODO: Remove
                this.cardIconsMap.put(card.getCardName(), new ImageIcon(ImageIO.read(new File(card.getCardIconPath())).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                //TODO: Repeating code
            }
        }
        this.cardIconsMap.put("Back", new ImageIcon(ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_back.png")).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
    }
    public Deck(boolean b) throws Exception {
        this.cards = new ArrayList<>();
        this.cardIconsMap = new HashMap<String, ImageIcon>();
        this.cardIconsMap.put("Back", new ImageIcon(ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_back.png")).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
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

    public int getCardsSummary() {
        int summary = 0;
        int ace = 0;
        for (Card c : cards) {
            if (c.getRank() != Rank.Ace) summary += c.getValue();
            else ace += 11;
        }
        while (ace >= 11 && summary + ace > 21)
        {
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
}
