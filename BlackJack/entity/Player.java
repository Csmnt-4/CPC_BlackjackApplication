package entity;

import ui.GameFrame;

import java.awt.*;

public class Player {
    private boolean user;
    private int balance;
    private int cardsValue;
    private Deck deck;

    public Player(int balance, boolean user) {
        this.user = user;
        this.balance = balance;
        this.cardsValue = 0;
        try {
            this.deck = new Deck(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(GameFrame game) {
        this.cardsValue = deck.getCardsSummary();
        if (user) {
            game.getPlayerCardsValueLabel().setText(Integer.toString(cardsValue));
            game.getPlayerBalanceValueLabel().setText(Integer.toString(balance));
            if (deck.getDeckSize() > 0) {
                game.getPlayerTableModel().getIcons().clear();
                for (Card dc : deck.getCards()) {
                    game.getPlayerTableModel().add(deck.getCardIcon(dc));
                }
                game.getPlayerCardsTable().setModel(game.getPlayerTableModel());
                game.getPlayerCardsTable().setMaximumSize(new Dimension(80 * (deck.getDeckSize()), 100));
            }
        } else {
            boolean scoreIsVisible = true;
            game.getDealerBalanceValueLabel().setText(Integer.toString(balance));
            if (deck.getDeckSize() > 0) {
                game.getDealerTableModel().getIcons().clear();
                for (Card dc : deck.getCards()) {
                    if (dc.isVisible()) {
                        game.getDealerTableModel().add(deck.getCardIcon(dc));
                    } else {
                        game.getDealerTableModel().add(deck.getCardIcon("Back"));
                        scoreIsVisible = false;
                        cardsValue -= dc.getValue();
                    }
                }
                if (scoreIsVisible) {
                    game.getDealerCardsValueLabel().setText(Integer.toString(cardsValue));
                } else {
                    game.getDealerCardsValueLabel().setText(cardsValue + " + ?");
                }
                game.getDealerCardsTable().setModel(game.getDealerTableModel());
                game.getDealerCardsTable().setMaximumSize(new Dimension(80 * (deck.getDeckSize()), 100));
            }
        }
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCardsValue() {
        return cardsValue;
    }

    public void setCardsValue(int cardsValue) {
        this.cardsValue = cardsValue;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void openAllCards() {
        for (Card c : this.deck.getCards()) {
            c.setVisible(true);
        }
    }

    public void addCard (GameFrame game, Deck deck, int cardPosition) throws InterruptedException {
        getDeck().addCard(deck, deck.getCards().get(cardPosition));
        update(game);
        Thread.sleep(500);
    }

    public void addCard (GameFrame game, Deck deck, int cardPosition, boolean isCardOpen) throws InterruptedException {
        getDeck().addCard(deck, deck.getCards().get(cardPosition), isCardOpen);
        update(game);
        Thread.sleep(500);
    }
}
