package cs3500.threetrios.model.hw5;

import cs3500.threetrios.model.hw5.Direction;
import cs3500.threetrios.model.hw5.ICard;

/**
 * Utility class for handling directional card value calculations.
 */
public class CardUtils {

  /**
   * Gets the value of a card in the specified direction.
   *
   * @param card The card whose value is needed.
   * @param direction The direction to check.
   * @return The value of the card in the given direction.
   */
  public static int getValueInDirection(ICard card, Direction direction) {
    switch (direction) {
      case NORTH: return card.getNorthValue();
      case SOUTH: return card.getSouthValue();
      case EAST: return card.getEastValue();
      case WEST: return card.getWestValue();
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

  /**
   * Gets the value of a card in the direction opposite to the specified direction.
   *
   * @param card The card whose value is needed.
   * @param direction The direction to check.
   * @return The value of the card in the opposite direction.
   */
  public static int getValueInOppositeDirection(ICard card, Direction direction) {
    switch (direction) {
      case NORTH: return card.getSouthValue();
      case SOUTH: return card.getNorthValue();
      case EAST: return card.getWestValue();
      case WEST: return card.getEastValue();
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }
}
