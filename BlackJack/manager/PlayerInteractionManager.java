package manager;

import java.awt.Color;

import javax.swing.JPanel;

import entity.Deck;
import entity.Player;
import ui.GameFrame;

public class PlayerInteractionManager {
	private final Player user;
	private final Player dealer;
	private final Deck deck;
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
			game.getStandButton().setEnabled(false);
			game.getHitButton().setEnabled(false);
			deck.shuffleCards();
			if (game.isBet()) {
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
								int i = 0;
								boolean instantWin = false;
								boolean bothPlayersBusted = false;
								String additionalMessage = "";
								game.getTableBetLabel().setText("Current bet: " + bet + "$!");
								user.setBalance(user.getBalance() - bet);
								dealer.setBalance(dealer.getBalance() - bet);
								System.out.println("_____________________________");
								deck.getCards().forEach(card -> System.out.println(card.getCardName()));
								dealer.addCard(game, deck, i);
								i++;
								dealer.addCard(game, deck, i, false);
								i++;
								user.addCard(game, deck, i);
								i++;
								user.addCard(game, deck, i);
								i++;

								if (user.getCardsValue() == 21) {
									game.getTableBetLabel().setText("You have a Blackjack!");
									instantWin = true;
									Thread.sleep(1200);
								}
								if (dealer.getCardsValue() == 21) {
									dealer.openAllCards();
									dealer.update(game);
									game.getTableBetLabel().setText("Dealer has a Blackjack!");
									game.setPass(true);
									Thread.sleep(1200);
								} else {
									while (user.getCardsValue() < 21 && !game.isPass() && !instantWin) {
										game.getStandButton().setEnabled(true);
										game.getHitButton().setEnabled(true);
										if (game.isHit()) {
											user.addCard(game, deck, i);
											if (user.getCardsValue() > 21) {
												game.getTableBetLabel().setText("You have busted.");
												Thread.sleep(1200);
											} else if (user.getCardsValue() == 21) {
												game.getTableBetLabel().setText("Twenty-one!");
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
									game.getStandButton().setEnabled(false);
									game.getHitButton().setEnabled(false);
									game.setPass(false);

									if (!instantWin) {
										dealer.openAllCards();
										dealer.update(game);
										Thread.sleep(1200);

										while (dealer.getCardsValue() < 17) {
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
									}
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
								dealer.setDeck(new Deck(true));
								user.setDeck(new Deck(true));
								if (user.getBalance() > 0 && dealer.getBalance() > 0) {
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
			}
		} while (user.getBalance() > 0 && dealer.getBalance() > 0);
		if (user.getBalance() == 0) {
			game.getTableBetLabel().setText("Seems like you lost... Try your luck next time!");
		} else if (dealer.getBalance() == 0) {
			game.getTableBetLabel().setText("Congratulations! You won in this nameless casino!");
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
}