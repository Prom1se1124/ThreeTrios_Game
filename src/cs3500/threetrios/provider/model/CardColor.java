package cs3500.threetrios.provider.model;

/**
 * Represents the colors for the cards.
 * A color is either Red or Blue.
 */
public enum CardColor {
  Red, Blue;

  /**
   * Turns a CardColor into a string.
   * @return the string representation of a color.
   */
  @Override
  public String toString() {
    if (this == Red) {
      return "RED";
    }
    return "BLUE";
  }
}
