package cs3500.threetrios.provider.model;

/**
 * The interface and required methods for a Card object (what users place) in the game ThreeTrios.
 */
public interface Card {
  /**
   * returns true if this card's number at the direction is larger
   * than the given card's number in the other direction.
   *
   * @param card      the card to compare against this card
   * @param direction the direction of this card
   * @return true iff this card is strictly beating the given card
   */
  boolean higherCard(Card card, Direction direction);

  /**
   * returns the color of the card: either red or blue.
   *
   * @return the CardColor
   */
  CardColor getColor();

  /**
   * returns the integer number corresonding to the direction.
   *
   * @param direction the cardinal direction needed
   * @return integer of the number in that direction
   */
  int getDirNum(Direction direction);

  /**
   * gets the string direction.
   * @param direction the Direction
   * @return the string form
   */
  String getDirStr(Direction direction);

  /**
   * Switches the current color of this card to the new color provided.
   *
   * @param newColor the new color for this card
   */
  void changeColor(CardColor newColor);

  /**
   * returns the name of a given card.
   *
   * @return the string name.
   */
  String getName();

  /**
   * whether or not this card is selected.
   * @return true iff this card is selected
   */
  boolean isSelected();

  /**
   * selects this card.
   */
  void selectCard();
}
