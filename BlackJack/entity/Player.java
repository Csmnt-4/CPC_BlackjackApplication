package entity;

import ui.GameFrame;

import java.awt.*;

/** Player class for Players. Manages player's deck, balance and renders deck updates.
 *
 * @since 3/26/2023                                                                         <p></p>
 * Revision history:                                                                        <p>
 *  3/26/2023 - Create the class. Add update(GameFrame) function, add generic getters and
 *  setters                                                                                 <p>
 *  3/26/2023 - Remake update function, add openAllCards(), addCard(GameFrame, Deck, int)
 *  and its override functions.                                                             <p>
 *  3/27/2023 - Bug fixes in update(GameFrame) and changes in getCardsValue() functions.    <p>
 *  3/27/2023 - Formatting changes in update(GameFrame) and addCard(GameFrame, Deck, int)
 *  functions.                                                                              <p>
 *  3/31/2023 - Add comments.                                                               <p>
 * @author Victor Anisimov
 */
public class Player {
    private final boolean isUser;
    private int balance;
    private int cardsValue;
    private Deck deck;

    /** Player(int balance, boolean user) constructor, creates a new object based on balanse and whether
     * the player is controlled by the user.
     *
     * @param balance for setting the initial amount of "money" in the balance.
     * @param isUser for setting whether the user controls the actions and, most importantly, where
     *               does the program renders the deck.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Creating the constructor.                                                   <p>
     *  3/26/2023 - Edits in the class parameters.                                              <p>
     *  3/31/2023 - Add comments.                                                               <p>
     *  3/31/2023 - Minor formatting changes.                                                   <p>
     * @author Victor Anisimov
     */
    public Player(int balance, boolean isUser) {
        this.isUser = isUser;
        this.balance = balance;
        this.cardsValue = 0;
        try {
            this.deck = new Deck(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** update(GameFrame game) function for rendering the deck.
     *
     * @param game receiving a reference to the running instance of the game window.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/26/2023 - Remake the function, remove overcomplicated table management mechanic.      <p>
     *  3/27/2023 - Render bug fixes, minor formatting.                                         <p>
     *  3/31/2023 - Add comments.                                                               <p>
     * @author Victor Anisimov
     */
    public void update(GameFrame game) throws Exception{
        this.cardsValue = deck.getCardsSummary();
        if (isUser) {
            game.getPlayerCardsTable().setMinimumSize(new Dimension(80 * (deck.getDeckSize()), 100));
            game.getPlayerCardsValueLabel().setText(Integer.toString(cardsValue));
            game.getPlayerBalanceValueLabel().setText(Integer.toString(balance));
            if (deck.getDeckSize() > 0) {
                game.getPlayerTableModel().getIcons().clear();
                for (Card dc : deck.getCards()) {
                    game.getPlayerTableModel().add(deck.getCardIcon(dc));
                }
                game.getPlayerCardsTable().setMaximumSize(new Dimension(80 * (deck.getDeckSize()), 100));
                game.getPlayerCardsTable().setModel(game.getPlayerTableModel());
            }
        } else {
            boolean scoreIsVisible = true;
            game.getDealerBalanceValueLabel().setText(Integer.toString(balance));
            if (deck.getDeckSize() > 0) {
                game.getDealerCardsTable().setMaximumSize(new Dimension(80 * (deck.getDeckSize()), 100));
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
            }
        }
    }

    /** getBalance() function for returning player's balance.
     *
     * @return Player's balance as an integer.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     * @author Victor Anisimov
     */
    public int getBalance() {
        return balance;
    }

    /** setBalance(int balance) function to set player's balance.
     *
     * @param balance receiving a whole number of "dollars" to assign to player.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     * @author Victor Anisimov
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /** getCardsValue() function for returning player's cards summary.
     *
     * @return Player's card sum as an integer.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     * @author Victor Anisimov
     */
    public int getCardsValue() {
        this.cardsValue = deck.getCardsSummary();
        return cardsValue;
    }

    /** getDeck() function for returning player's deck.
     *
     * @return Player's deck as an object of Deck class.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     * @author Victor Anisimov
     */
    public Deck getDeck() {
        return deck;
    }

    /** setDeck(Deck deck) function to set player's card deck.
     *
     * @param deck receiving an object of class Deck to assign to player.
     * @since 3/26/2023                                                                         <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     * @author Victor Anisimov
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /** openAllCards(GameFrame game) function for updating every cards' in the deck property 'isVisible' to true and
     * re-rendering the deck via internal update method.
     *
     * @author Victor Anisimov
     * @param game receiving a reference to the running instance of the game window.
     * @since 3/8/2023                                                                          <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     */
    public void openAllCards(GameFrame game) throws Exception {
        for (Card c : this.deck.getCards()) {
            c.setVisible(true);
        }
        update(game);
    }

    /** addCard(GameFrame game, Deck deck, int cardPosition) function for adding a new card in the deck and
     * re-rendering the deck via internal update method.
     *
     * @author Victor Anisimov
     * @param game receiving a reference to the running instance of the game window.
     * @param deck receiving a reference to the instance of 'original' deck (so-called hand).
     *             to access its hashmap containing game cards pictures and card objects.
     * @param cardPosition cards' position necessary to access deck hashmap containing game cards information.
     * @since 3/8/2023                                                                          <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     */
    public void addCard(GameFrame game, Deck deck, int cardPosition) throws Exception {
        getDeck().addCard(deck, deck.getCards().get(cardPosition));
        update(game);
        Thread.sleep(500);
    }

    /** addCard(GameFrame game, Deck deck, int cardPosition) override function with addition of specifying whether the card picture
     * should be displayed, i.e. whether the card is "open".
     *
     * @author Victor Anisimov
     * @param game receiving a reference to the running instance of the game window.
     * @param deck receiving a reference to the instance of 'original' deck (so-called hand).
     *             to access its hashmap containing game cards pictures and card objects.
     * @param cardPosition cards' position necessary to access deck hashmap containing game cards information.
     * @param isCardOpen extra specification whether the card value should be displayed.
     * @since 3/8/2023                                                                          <p></p>
     * Revision history:                                                                        <p>
     *  3/26/2023 - Create the function.                                                        <p>
     *  3/31/2023 - Add comments.                                                               <p>
     */
    public void addCard(GameFrame game, Deck deck, int cardPosition, boolean isCardOpen) throws Exception {
        getDeck().addCard(deck, deck.getCards().get(cardPosition), isCardOpen);
        update(game);
        Thread.sleep(500);
    }
}
