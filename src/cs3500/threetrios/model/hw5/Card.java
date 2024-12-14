package cs3500.threetrios.model.hw5;

/**
 * The Card used to play in the ThreeTrio Game. It has the name and four directions values.
 */
public class Card implements ICard {

  private final String name; //Same as an id for this card
  private final int northValue;
  private final int southValue;
  private final int eastValue;
  private final int westValue;
  private CardColor color;


  /**
   * The Card used to play in the ThreeTrio Game. It has the name and four directions values.
   *
   * @param name       name of the card
   * @param northValue north value of the card
   * @param southValue south value of the card
   * @param eastValue  east value of the card
   * @param westValue  west value of the card
   * @param color      color of the card
   */
  public Card(String name, int northValue, int southValue, int eastValue,
              int westValue, CardColor color) {
    if (name == null || color == null) {
      throw new IllegalArgumentException("Card name and color must not be null");
    }
    if (northValue < 1 || northValue > 10 ||
            southValue < 1 || southValue > 10 ||
            eastValue < 1 || eastValue > 10 ||
            westValue < 1 || westValue > 10) {
      throw new IllegalArgumentException("Side values of card must be between 1 and 10.");
    }

    this.name = name;
    this.northValue = northValue;
    this.southValue = southValue;
    this.eastValue = eastValue;
    this.westValue = westValue;
    this.color = color;
  }

  /**
   * The Card used to play in the ThreeTrio Game. It has the name and four directions values.
   *
   * @param name       name of the card
   * @param northValue north value of the card
   * @param southValue south value of the card
   * @param eastValue  east value of the card
   * @param westValue  west value of the card
   */
  public Card(String name, int northValue, int southValue, int eastValue, int westValue) {
    if (name == null) {
      throw new IllegalArgumentException("Card name and color must not be null");
    }
    if (northValue < 1 || northValue > 10
            || southValue < 1 || southValue > 10
            || eastValue < 1 || eastValue > 10
            || westValue < 1 || westValue > 10) {
      throw new IllegalArgumentException("Side values of card must be between 1 and 10.");
    }

    this.name = name;
    this.northValue = northValue;
    this.southValue = southValue;
    this.eastValue = eastValue;
    this.westValue = westValue;
    this.color = CardColor.RED;
  }

  @Override
  public boolean battle(ICard opponentCard, Direction direction) {
    if (opponentCard == null) {
      throw new IllegalArgumentException("Opponent card cannot be null");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    int thisCardValue;
    int opponentCardValue;

    switch (direction) {
      case NORTH:
        thisCardValue = this.northValue;
        opponentCardValue = opponentCard.getSouthValue();
        break;
      case SOUTH:
        thisCardValue = this.southValue;
        opponentCardValue = opponentCard.getNorthValue();
        break;
      case EAST:
        thisCardValue = this.eastValue;
        opponentCardValue = opponentCard.getWestValue();
        break;
      case WEST:
        thisCardValue = this.westValue;
        opponentCardValue = opponentCard.getEastValue();
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
    return thisCardValue > opponentCardValue;
  }

  @Override
  public void flip() {
    if (this.color == CardColor.RED) {
      this.color = CardColor.BLUE;
    } else if (this.color == CardColor.BLUE) {
      this.color = CardColor.RED;
    } else {
      throw new IllegalStateException("Cannot flip a card with an undefined color.");
    }
  }

  @Override
  public CardColor getColor() {
    return color;
  }

  @Override
  public void setColor(CardColor color) {
    this.color = color;
  }

  @Override
  public String toString() {
    String north = (northValue == 10) ? "A" : String.valueOf(northValue);
    String south = (southValue == 10) ? "A" : String.valueOf(southValue);
    String east = (eastValue == 10) ? "A" : String.valueOf(eastValue);
    String west = (westValue == 10) ? "A" : String.valueOf(westValue);

    return String.format("%s %s %s %s %s", name, north, south, east, west);
  }


  @Override
  public int getSouthValue() {
    return southValue;
  }

  @Override
  public int getNorthValue() {
    return northValue;
  }

  @Override
  public int getWestValue() {
    return westValue;
  }

  @Override
  public int getEastValue() {
    return eastValue;
  }

  @Override
  public ICard clone() {
    return new Card(this.name, this.northValue, this.southValue, this.eastValue, this.westValue,
            this.color);
  }
}
