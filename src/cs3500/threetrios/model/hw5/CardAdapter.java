package cs3500.threetrios.model.hw5;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.CardColor;

public class CardAdapter implements Card {
  private final ICard ourCard;

  public CardAdapter(ICard ourCard) {
    this.ourCard = ourCard;
  }

  @Override
  public boolean higherCard(Card card, Direction direction) {
    int ourValue = getValueForDirection(direction);
    int theirValue = ((CardAdapter) card).getValueForDirection(getOppositeDirection(direction));
    return ourValue > theirValue;
  }

  private int getValueForDirection(Direction direction) {
    switch (direction) {
      case NORTH: return ourCard.getNorthValue();
      case SOUTH: return ourCard.getSouthValue();
      case EAST: return ourCard.getEastValue();
      case WEST: return ourCard.getWestValue();
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

  private Direction getOppositeDirection(Direction dir) {
    switch (dir) {
      case NORTH: return Direction.SOUTH;
      case SOUTH: return Direction.NORTH;
      case EAST: return Direction.WEST;
      case WEST: return Direction.EAST;
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

  @Override
  public CardColor getColor() {
    return ourCard.getColor() == cs3500.threetrios.model.hw5.CardColor.RED ?
            CardColor.Red : CardColor.Blue;
  }

  @Override
  public int getDirNum(Direction direction) {
    return getValueForDirection(direction);
  }

  @Override
  public String getDirStr(Direction direction) {
    int value = getValueForDirection(direction);
    return value == 10 ? "A" : String.valueOf(value);
  }

  @Override
  public void changeColor(CardColor newColor) {
    // Convert their CardColor to our CardColor and change
    cs3500.threetrios.model.hw5.CardColor ourColor =
            newColor == CardColor.Red ?
                    cs3500.threetrios.model.hw5.CardColor.RED :
                    cs3500.threetrios.model.hw5.CardColor.BLUE;
    ourCard.setColor(ourColor);
  }

  @Override
  public String getName() {
    return ourCard.toString();
  }

  @Override
  public boolean isSelected() {
    // If our card system doesn't track selection state,
    // we could maintain this in the adapter
    return false;  // or implement selection tracking if needed
  }

  @Override
  public void selectCard() {
// Implement if needed
  }
}