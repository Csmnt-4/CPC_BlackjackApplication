package entity;

enum Suit {Clubs, Diamonds, Hearts, Spades}

enum Rank {Jack, Queen, King, Ace}

public class Card {
    private String cardName;
    private int rankValue;
    private Rank rank;
    private Suit suit;
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
            case 0 -> {
                this.suit = Suit.Clubs;
            }
            case 1 -> {
                this.suit = Suit.Diamonds;
            }
            case 2 -> {
                this.suit = Suit.Hearts;
            }
            case 3 -> {
                this.suit = Suit.Spades;
            }
            default -> throw new Exception("Not a valid card value.");
        }
        if (rank != null) {
            this.cardName = this.rank + " of " + this.suit;
        } else {
            this.cardName = this.rankValue + " of " + this.suit;
        }
        this.isVisible = true;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardName() {
        if (this.rank != null) {
            this.cardName = this.rank + " of " + this.suit.toString();
        } else {
            this.cardName = this.rankValue + " of " + this.suit.toString();

        }
    }

    public int getValue() {
        return rankValue;
    }

    public void setValue(int value) throws Exception {
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
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setRank(int value) throws Exception {
        switch (value) {
            case 0 -> this.rank = Rank.Jack;
            case 1 -> this.rank = Rank.Queen;
            case 2 -> this.rank = Rank.King;
            case 3 -> this.rank = Rank.Ace;
            default -> throw new Exception("Not a valid rank value.");
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) throws Exception {
        this.suit = suit;
    }

    public void setSuit(int value) throws Exception {
        switch (value) {
            case 1 -> this.suit = Suit.Clubs;
            case 2 -> this.suit = Suit.Diamonds;
            case 3 -> this.suit = Suit.Hearts;
            case 4 -> this.suit = Suit.Spades;
            default -> throw new Exception("Not a valid card value.");
        }
    }

    public int getRankValue() {
        return rankValue;
    }

    public void setRankValue(int rankValue) {
        this.rankValue = rankValue;
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
        } else if (rankValue == 10) {
            path += rankValue;
        } else {
            path += rank.toString().substring(0, 1);
        }
        path += ".png";
        return path;
    }
}


