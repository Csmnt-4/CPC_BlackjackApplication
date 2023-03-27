package manager;

import java.awt.Color;

import javax.swing.JPanel;

import entity.Deck;
import entity.Player;
import ui.GameFrame;

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

	static boolean isValidNumber(String value) {
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isDigit(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public void gameLoop(GameFrame game) throws Exception {
		game.getTableBetLabel().setText("Let's play some Blackjack! ");
		game.setInvisibleCardToTable(deck);
		user.update(game);
		dealer.update(game);
		do {
			game.getPlayerBetButton().setEnabled(true);
			game.getPassButton().setEnabled(false);
			game.getHitButton().setEnabled(false);
			deck.shuffleCards();
			if (game.isBet()) {
				try {
					user.setDeck(new Deck(true));
					dealer.setDeck(new Deck(true));
					user.update(game);
					dealer.update(game);
					game.setBet(false);
					if (!game.getPlayerBetTextField().getText().isEmpty()) {
						if (isValidNumber(game.getPlayerBetTextField().getText())) {
							bet = Integer.parseInt(game.getPlayerBetTextField().getText());
							if (user.getBalance() >= bet) {
								if (dealer.getBalance() >= bet) {
									game.getPlayerBetButton().setEnabled(false);
									int i = 4;
									boolean instantWin = false;
									boolean bothPlayersBusted = false;
									String additionalMessage = "";
									game.getTableBetLabel().setText("Current bet: " + bet + "$!");
									user.setBalance(user.getBalance() - bet);
									dealer.setBalance(dealer.getBalance() - bet);

									dealer.addCard(game, deck, 0);
									dealer.addCard(game, deck, 1, false);
									user.addCard(game, deck, 2);
									user.addCard(game, deck, 3);

									if (dealer.getCardsValue() == 21) {
										dealer.openAllCards();
										dealer.update(game);
										game.getTableBetLabel().setText("Dealer has a Blackjack!");
										game.setPass(true);
										Thread.sleep(1200);
									} else if (user.getCardsValue() == 21) {
										game.getTableBetLabel().setText("You have a Blackjack!");
										instantWin = true;
										Thread.sleep(1200);
									}
									while (user.getCardsValue() < 21 && !game.isPass() && !instantWin) {
										game.getPassButton().setEnabled(true);
										game.getHitButton().setEnabled(true);
										if (game.isHit()) {
											user.addCard(game, deck, i);
											if (user.getCardsValue() > 21) {
												game.getTableBetLabel().setText("You have busted.");
												Thread.sleep(1200);
											} else if (user.getCardsValue() == 21) {
												game.getTableBetLabel().setText("You have a Blackjack!");
												Thread.sleep(1200);
											} else if (user.getDeck().getDeckSize() >= 5) {
												additionalMessage = " You have a monty!";
												instantWin = true;
												Thread.sleep(1200);
											}
											game.setHit(false);
											i++;
										}
									}
									game.getPassButton().setEnabled(false);
									game.getHitButton().setEnabled(false);
									game.setPass(false);

									dealer.openAllCards();
									dealer.update(game);
									Thread.sleep(1200);

									while (dealer.getCardsValue() < 17 && !instantWin) {
										dealer.addCard(game, deck, i);
										if (dealer.getCardsValue() > 21 && user.getCardsValue() <= 21) {
											additionalMessage = " Dealer has busted.";
											instantWin = true;
											Thread.sleep(1200);
										} else if (dealer.getCardsValue() > 21 && user.getCardsValue() > 21) {
											bothPlayersBusted = true;
											additionalMessage = " Both players have busted.";
											Thread.sleep(1200);
										}
										i++;
									}
									if ((user.getCardsValue() > dealer.getCardsValue() && user.getCardsValue() <= 21)
											|| instantWin) {
										game.getTableBetLabel().setText("You won!" + additionalMessage);
										user.setBalance(user.getBalance() + bet * 2);
									} else if (user.getCardsValue() == dealer.getCardsValue() || bothPlayersBusted) {
										game.getTableBetLabel().setText("Push." + additionalMessage);
										user.setBalance(user.getBalance() + bet);
										dealer.setBalance(dealer.getBalance() + bet);
									} else {
										game.getTableBetLabel()
												.setText(additionalMessage + "Dealer has won this round.");
										dealer.setBalance(dealer.getBalance() + bet * 2);
									}

									Thread.sleep(3000);
									dealer.setDeck(new Deck(false));
									user.setDeck(new Deck(false));
									if (!(user.getBalance() > 0) && !(dealer.getBalance() > 0)) {
										game.getTableBetLabel().setText("Make your bets!");
										dealer.update(game);
										user.update(game);
									}
								} else {
									game.getTableBetLabel().setText("The dealer cannot accept the bet!");
									flashPaneBackground(game.getDealerBalancePane(), new Color(250, 0, 0));
								}
							} else {
								game.getTableBetLabel().setText("Your balance is lower than the bet!");
								flashPaneBackground(game.getPlayerBalancePane(), new Color(250, 0, 0));
							}
						} else {
							game.getTableBetLabel().setText("Your bet should be a number value!");
							game.getPlayerBetTextField().setText("");
						}
					}
				} catch (Exception e) {
					throw e;
				}
			}
		} while (user.getBalance() > 0 && dealer.getBalance() > 0);
		game.setInvisibleCardToTable(deck);
		user.update(game);
		dealer.update(game);
		if (user.getBalance() == 0) {
			game.getTableBetLabel().setText("Seems like you lost... Try your luck next time!");
		} else {
			game.getTableBetLabel().setText("Congratulations! You won in this nameless casino!");
		}
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