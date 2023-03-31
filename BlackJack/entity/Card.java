package entity;

import java.util.Objects;

enum Suit {Clubs, Diamonds, Hearts, Spades}

enum Rank {Jack, Queen, King, Ace}
/** Card class is used to store the cards' information, such as rank, value and suit.
 * You can also get a proper formatted cards' name, which I may try and add in the tooltip later on.
 * Card is being created based off suit (1-4) and value (2-14), value is set automatically.
 *
 * @since 3/23/2023
 * @author Victor Anisimov
 */
public class Card {
    private final String cardName;
    private final int rankValue;
    private final Suit suit;
    private Rank rank = null;
    private boolean isVisible;

    public Card(int suit, int value) throws Exception {
        switch (value) {
            case 2, 3, 4, 5, 6, 7, 8, 9, 10 -> this.rankValue = value;
            case 11 -> {
                this.rank = Rank.Jack;
                this.rankValue = 10;
            }
            case 12 -> {
                this.rank = Rank.Queen;
                this.rankValue = 10;
            }
            case 13 -> {
                this.rank = Rank.King;
                this.rankValue = 10;
            }
            case 14 -> {
                this.rank = Rank.Ace;
                this.rankValue = 11;
            }
            default -> throw new Exception("Not a valid card value.");
        }
        switch (suit) {
            case 0 -> this.suit = Suit.Clubs;
            case 1 -> this.suit = Suit.Diamonds;
            case 2 -> this.suit = Suit.Hearts;
            case 3 -> this.suit = Suit.Spades;

            default -> throw new Exception("Not a valid card value.");
        }
        this.cardName = Objects.requireNonNullElse(rank, this.rankValue) + " of " + this.suit;
        this.isVisible = true;
    }

    public String getCardName() {
        return cardName;
    }

    public int getValue() {
        return rankValue;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getCardIconPath() {
        String path = "card_" + suit.toString().toLowerCase() + "_";
        if (rankValue < 10) {
            path += "0" + rankValue;
        } else if (rank == null) {
            path += rankValue;
        } else {
            path += rank.toString().substring(0, 1);
        }
        path += ".png";
        return path;
    }
}


