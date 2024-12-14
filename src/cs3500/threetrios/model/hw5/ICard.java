package cs3500.threetrios.model.hw5;

/**
 * Interface for a card used in the Three Trios game. Cards have attack values associated
 * with each of the four cardinal directions and are used in battles on the grid.
 * The card can also be assigned a color indicating its ownership.
 */
public interface ICard {




  /**
   * Battles an opponent's card. The outcome depends on the relative attack values of the cards.
   * @param opponentCard The opponent's card to battle against.
   * @param direction The direction from this card to the opponent's card.
   * @return true if this card wins the battle, false otherwise.
   *@throws IllegalArgumentException if opponent or direction of opponent is null.
   */
  boolean battle(ICard opponentCard, Direction direction);

  /**
   * Flips the ownership of the card to the opposite player.
   * @throws IllegalStateException if the card need to be flipped has undefined colors.
   */
  void flip() throws IllegalStateException;

  /**
   * Gets the current color (ownership) of the card.
   * @return The color of the card (RED or BLUE).
   */
  CardColor getColor();

  /**
   * Sets the color of the card.
   * @param color The new color of the card (RED or BLUE).
   */
  void setColor(CardColor color);

  /**
   * Provides a textual representation of the card for display purposes. Make sure to display A if
   * the card has value of 10.
   * @return A string representing the card and its values.
   */
  String toString();

  /**
   * get the south side value of a card.
   * @return An int from the south side.
   */
  int getSouthValue();

  /**
   * get the south side value of a card.
   * @return An int from the south side.
   */
  int getNorthValue();

  /**
   * get the south side value of a card.
   * @return An int from the south side.
   */
  int getWestValue();

  /**
   * get the south side value of a card.
   * @return An int from the south side.
   */
  int getEastValue();

  /**
   * Creates a deep copy of this card.
   * @return A new ICard instance with the same values and color as this card.
   */
  ICard clone();
}
