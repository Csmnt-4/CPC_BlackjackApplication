package manager;

import entity.Deck;
import entity.Player;
import ui.GameFrame;

import java.awt.*;
import java.util.Arrays;

public class PlayerInteractionManager {
    private Player user;
    private Player dealer;
    private Deck deck;
    private int bet;

    public PlayerInteractionManager() throws Exception {
        this.user = new Player(1000, true);
        this.dealer = new Player(1000, false);
        this.deck = new Deck();
        this.bet = 0;
    }

    public void gameLoop(GameFrame game) {
        game.getTableBetLabel().setText("Let's play some Blackjack! ");
        do {
            user.update(game);
            dealer.update(game);
            game.setInvisibleCardToTable(deck);
            if (game.isBet()) {
                game.setBet(false);
                if (!game.getPlayerBetTextField().getText().isEmpty()) {
                    try {
                        bet = Integer.parseInt(game.getPlayerBetTextField().getText());
                        if (user.getBalance() >= bet) {
                            if (dealer.getBalance() >= bet) {
                                game.getTableBetLabel().setText("Current bet: " + bet + "$!");
                                user.setBalance(user.getBalance() - bet);
                                dealer.setBalance(dealer.getBalance() - bet);
                            } else {
                                game.getTableBetLabel().setText("The dealer cannot accept the bet!");
                                Color defaultColor = game.getDealerBalancePane().getBackground();
                                game.getDealerBalancePane().setBackground(new Color(100, 0, 0));
                                game.getDealerBalancePane().setBackground(defaultColor);
                                game.getDealerBalancePane().setBackground(new Color(100, 0, 0));
                                game.getDealerBalancePane().setBackground(defaultColor);
                                game.getDealerBalancePane().setBackground(new Color(100, 0, 0));
                                game.getDealerBalancePane().setBackground(defaultColor);
                                game.getDealerBalancePane().setBackground(new Color(100, 0, 0));
                            }
                        } else {
                            game.getTableBetLabel().setText("Your balance is lower than the bet!");
                            Color defaultColor = game.getPlayerBalancePane().getBackground();
                            game.getPlayerBalancePane().setBackground(new Color(250, 0, 0));
                            Thread.sleep(100);
                            game.getPlayerBalancePane().setBackground(defaultColor);
                            Thread.sleep(100);
                            game.getPlayerBalancePane().setBackground(new Color(250, 0, 0));
                            Thread.sleep(100);
                            game.getPlayerBalancePane().setBackground(defaultColor);
                        }
                    } catch (Exception e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }

                }
            }
        } while (user.getBalance() > 0 && dealer.getBalance() > 0);
        user.update(game);
        dealer.update(game);
        game.setInvisibleCardToTable(deck);
        if (user.getBalance()==0) {
            game.getTableBetLabel().setText("Seems like you lost... Try your luck next time!");
        } else {
            game.getTableBetLabel().setText("Congratulations! You won in this nameless casino!");
        }

        // TODO: PlayerInteractionManager
        //  - compare values
        //  - main loop (should I put it right here instead of..?)
    }

    public Player getUser() {
        return user;
    }

    public void setUser(Player user) {
        this.user = user;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}