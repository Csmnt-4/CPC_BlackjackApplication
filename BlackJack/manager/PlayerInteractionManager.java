package manager;

import java.awt.Color;

import javax.swing.JPanel;

import entity.Deck;
import entity.Player;
import ui.GameFrame;

/** PlayerInteractionManager class is used to create instances of Player's classes and run the main game logic.
 * Updates the tables with the cards, writes outputs to the user and sleeps where necessary for a subjectively
 * pleasant game flow. Can alternate between default "hard 17" ruleset and "chase the player" logic.
 *
 * @since 3/23/2023
 * @author Victor Anisimov
 */
public class PlayerInteractionManager {
    private final Player user;
    private final Player dealer;
    private final Deck deck;
    private final boolean useDefaultGameRules;
    private int bet;

    public PlayerInteractionManager(boolean useDefaultGameRules) throws Exception {
        this.user = new Player(1000, true);
        this.dealer = new Player(1000, false);
        this.deck = new Deck();
        this.bet = 0;
        this.useDefaultGameRules = useDefaultGameRules;
    }

    static boolean isValidNumber(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void gameLoop(GameFrame game) throws Exception {
        game.setTableBetLabelText("Let's play some Blackjack! ");
        game.setInvisibleCardToTable(deck);
        update(game);
        do {
            try {
                game.setGameButtons(false);
                deck.shuffleCards();
                if (game.isBet()) {
                    user.setDeck(new Deck(true));
                    dealer.setDeck(new Deck(true));
                    update(game);
                    game.setBet(false);
                    game.setPass(false);
                    if (!game.getPlayerBetTextField().getText().isEmpty()) {
                        if (isValidNumber(game.getPlayerBetTextField().getText()) && Integer.parseInt(game.getPlayerBetTextField().getText()) > 0) {
                            bet = Integer.parseInt(game.getPlayerBetTextField().getText());
                            if (user.getBalance() >= bet) {
                                if (dealer.getBalance() >= bet) {
                                    boolean instantWin = false;
                                    boolean playerHasBusted = false;
                                    boolean bothPlayersBusted = false;
                                    String additionalMessage = "";
                                    game.setTableBetLabelText("Current bet: " + bet + "$!");

                                    user.setBalance(user.getBalance() - bet);
                                    dealer.setBalance(dealer.getBalance() - bet);
                                    dealer.addCard(game, deck, 0);
                                    dealer.addCard(game, deck, 1, false);
                                    user.addCard(game, deck, 2);
                                    user.addCard(game, deck, 3);
                                    int i = 4;

                                    if (user.getCardsValue() == 21) {
                                        game.setTableBetLabelText("You have a Blackjack!");
                                        instantWin = true;
                                        sleep(false);
                                    }
                                    if (dealer.getCardsValue() == 21) {
                                        dealer.openAllCards(game);
                                        game.setTableBetLabelText("Dealer has a Blackjack!");
                                        instantWin = false;
                                        sleep(false);
                                    } else if (instantWin) {
                                        game.setTableBetLabelText("Dealer doesn't have a Blackjack!");
                                    } else {
                                        game.setGameButtons(true);
                                        while (user.getCardsValue() < 21 && !game.isPass() && !instantWin) {
                                            if (game.isHit()) {
                                                user.addCard(game, deck, i);
                                                if (user.getCardsValue() > 21) {
                                                    playerHasBusted = true;
                                                    game.setTableBetLabelText("You have busted.");
                                                } else if (user.getCardsValue() == 21) {
                                                    game.setTableBetLabelText("Twenty-one!");
                                                } else if (user.getDeck().getDeckSize() >= 5) {
                                                    instantWin = true;
                                                    additionalMessage = "You have a monty!";
                                                }
                                                game.setHit(false);
                                                i++;
                                            }
                                        }
                                        game.setGameButtons(false);
                                        if (!instantWin) {
                                            dealer.openAllCards(game);
                                            sleep(false);
                                            // Dealer follows default Blackjack ruleset and stops at 17
                                            while ((dealer.getCardsValue() < 17 && useDefaultGameRules)
                                                    // Dealer doesn't follow default Blackjack ruleset and tries to beat player's hand
                                                    || (dealer.getCardsValue() < user.getCardsValue() && !playerHasBusted && !useDefaultGameRules)
                                                    // Player has busted and Dealer follows default Blackjack ruleset
                                                    || (playerHasBusted && dealer.getCardsValue() < 17)) {
                                                dealer.addCard(game, deck, i);
                                                if (dealer.getCardsValue() > 21 && user.getCardsValue() <= 21) {
                                                    additionalMessage = "Dealer has busted.";
                                                    instantWin = true;
                                                } else if (dealer.getCardsValue() > 21 && user.getCardsValue() > 21) {
                                                    bothPlayersBusted = true;
                                                    additionalMessage = "Both players have busted.";
                                                }
                                                i++;
                                            }
                                        }
                                    }
                                    sleep(false);
                                    if ((user.getCardsValue() > dealer.getCardsValue() && user.getCardsValue() <= 21) || instantWin) {
                                        game.setTableBetLabelText("You won! " + additionalMessage);
                                        user.setBalance(user.getBalance() + bet * 2);
                                    } else if (user.getCardsValue() == dealer.getCardsValue() && !bothPlayersBusted) {
                                        game.setTableBetLabelText("Push. " + additionalMessage);
                                        user.setBalance(user.getBalance() + bet);
                                        dealer.setBalance(dealer.getBalance() + bet);
                                    } else {
                                        game.setTableBetLabelText(additionalMessage + " Dealer has won this round.");
                                        dealer.setBalance(dealer.getBalance() + bet * 2);
                                    }
                                    sleep(true);
                                    dealer.setDeck(new Deck(true));
                                    user.setDeck(new Deck(true));
                                    if (user.getBalance() > 0 && dealer.getBalance() > 0) {
                                        game.setTableBetLabelText("Make your bets!");
                                        dealer.update(game);
                                        user.update(game);
                                    }
                                } else {
                                    game.setTableBetLabelText("The dealer cannot accept the bet!");
                                    flashPaneBackground(game.getDealerBalancePane(), new Color(250, 0, 0));
                                }
                            } else {
                                game.setTableBetLabelText("Your balance is lower than the bet!");
                                flashPaneBackground(game.getPlayerBalancePane(), new Color(250, 0, 0));
                            }
                        } else {
                            game.setTableBetLabelText("Your bet should be a whole number value (higher than zero)!");
                            game.getPlayerBetTextField().setText("");
                        }
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        } while (user.getBalance() > 0 && dealer.getBalance() > 0);
        if (user.getBalance() == 0) {
            game.setTableBetLabelText("Seems like you lost... Try your luck next time!");
        } else if (dealer.getBalance() == 0) {
            game.setTableBetLabelText("Congratulations! You won in this nameless casino!");
        }
        user.update(game);
        dealer.update(game);
        game.setInvisibleCardToTable(deck);
    }

    private void flashPaneBackground(JPanel pane, Color color) throws InterruptedException {
        Color defaultColor = pane.getBackground();
        pane.setBackground(color);
        Thread.sleep(100);
        pane.setBackground(defaultColor);
        Thread.sleep(100);
        pane.setBackground(color);
        Thread.sleep(100);
        pane.setBackground(defaultColor);
        Thread.sleep(100);
        pane.setBackground(color);
        Thread.sleep(100);
        pane.setBackground(defaultColor);
    }

    private void sleep(boolean longSleep) throws InterruptedException {
        if (longSleep)  {
            Thread.sleep(3000);
        } else {
            Thread.sleep(1200);
        }
    }

    private void  update(GameFrame game) throws Exception{
        user.update(game);
        dealer.update(game);
    }
}