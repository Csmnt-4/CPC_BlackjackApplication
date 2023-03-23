package entity;

enum Suit {Clubs, Diamonds, Hearts, Spades}

enum Rank {Jack, Queen, King, Ace}

public class Card {
    private String imagePath;   // TODO: Decide if I need the path right here
    private String cardName;
    private int rankValue;
    private Rank rank;
    private Suit suit;

    // TODO: Add more properties. Maybe.

    public Card(int value, int suit) throws Exception {
        switch (value) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.rankValue = value;
                break;
            case 11:
                this.rank = Rank.Jack;
            case 12:
                this.rank = Rank.Queen;
            case 13:
                this.rank = Rank.King;
                this.rankValue = 10;
                break;
            case 14:
                this.rankValue = 11;
                this.rank = Rank.Ace;
                break;
            default:
                throw new Exception("Not a valid card value.");
        }
        switch (suit) {
            case 1 -> {
                this.suit = Suit.Clubs;
            }
            case 2 -> {
                this.suit = Suit.Diamonds;
            }
            case 3 -> {
                this.suit = Suit.Hearts;
            }
            case 4 -> {
                this.suit = Suit.Spades;
            }
            default -> throw new Exception("Not a valid card value.");
        }
        if (rank != null) {
            this.cardName = this.rank.toString() + " of " + this.suit.toString();
            this.imagePath = this.rank.toString() + this.suit.toString();
        } else {
            this.cardName = this.rankValue + " of " + this.suit.toString();
            this.imagePath = this.rankValue + this.suit.toString();
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath() {
        String path = "card_";
        if (this.rank != null) {
            path += this.suit.toString().toLowerCase() + "_" + this.rank.toString().indexOf(0);
        } else if (this.rankValue < 10){
            path += this.suit.toString().toLowerCase() + "_0" + this.rankValue ;
        } else {
            path += this.suit.toString().toLowerCase() + "_" +  this.rankValue;
        }
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardName() {
        if (this.rank != null) {
            this.imagePath = this.rank.toString() + this.suit.toString();
        } else {
            this.imagePath = this.rankValue + this.suit.toString();

        }
    }

    public int getValue() {
        return rankValue;
    }

    public void setValue(int value) throws Exception {
        switch (value) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.rankValue = value;
                break;
            case 11:
                this.rank = Rank.Jack;
            case 12:
                this.rank = Rank.Queen;
            case 13:
                this.rank = Rank.King;
                this.rankValue = 10;
                break;
            case 14:
                this.rankValue = 11;
                this.rank = Rank.Ace;
                break;
            default:
                throw new Exception("Not a valid card value.");
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
            case 1 -> this.rank = Rank.Jack;
            case 2 -> this.rank = Rank.Queen;
            case 3 -> this.rank = Rank.King;
            case 4 -> this.rank = Rank.Ace;
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
            case 1 -> {
                this.suit = Suit.Clubs;
            }
            case 2 -> {
                this.suit = Suit.Diamonds;
            }
            case 3 -> {
                this.suit = Suit.Hearts;
            }
            case 4 -> {
                this.suit = Suit.Spades;
            }
            default -> throw new Exception("Not a valid card value.");
        }
    }
}


