package entity;

import ui.GameFrame;

import java.awt.*;
import java.util.Objects;

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
                for (int i = game.getPlayerTableModel().getCards().size(); i > 0; i--) {
                    Card gc = game.getPlayerTableModel().getCards().get(i);
                    for (Card dc : deck.getCards()) {
                        if (Objects.equals(gc.getCardName(), dc.getCardName())) {
                            break;
                        }
                        game.getPlayerTableModel().getCards().remove(i);
                        game.getPlayerTableModel().getIcons().remove(i);
                    }
                }
                for (Card dc : deck.getCards()) {
                    for (Card gc : game.getPlayerTableModel().getCards()) {
                        if (Objects.equals(dc.getCardName(), gc.getCardName())) {
                            break;
                        }
                        game.getPlayerTableModel().getCards().add(dc);
                        game.getPlayerTableModel().add(deck.getCardIcon(dc));
                    }
                }
                game.getPlayerCardsTable().setModel(game.getPlayerTableModel());
                game.getPlayerCardsTable().setMaximumSize(new Dimension(90 * deck.getDeckSize(), 100));
            }
        } else {
            game.getDealerCardsValueLabel().setText(Integer.toString(cardsValue));
            game.getDealerBalanceValueLabel().setText(Integer.toString(balance));
            if (deck.getDeckSize() > 0) {
                for (int i = game.getDealerTableModel().getCards().size(); i > 0; i--) {
                    Card gc = game.getDealerTableModel().getCards().get(i);
                    for (Card dc : deck.getCards()) {
                        if (Objects.equals(gc.getCardName(), dc.getCardName())) {
                            break;
                        }
                        game.getDealerTableModel().getCards().remove(i);
                        game.getDealerTableModel().getIcons().remove(i);
                    }
                }
                for (Card dc : deck.getCards()) {
                    for (Card gc : game.getDealerTableModel().getCards()) {
                        if (Objects.equals(dc.getCardName(), gc.getCardName())) {
                            break;
                        }
                        game.getDealerTableModel().getCards().add(dc);
                        if (dc.isVisible()) {
                            game.getDealerTableModel().add(deck.getCardIcon(dc));
                        } else {
                            game.getDealerTableModel().add(deck.getCardIcon("Back"));
                        }
                    }
                }
                game.getDealerCardsTable().setModel(game.getDealerTableModel());
                game.getDealerCardsTable().setMaximumSize(new Dimension(90 * deck.getDeckSize(), 100));
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
}
