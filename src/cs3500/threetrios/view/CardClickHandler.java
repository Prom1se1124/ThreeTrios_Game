package cs3500.threetrios.view;


/**
 * Interface representing the click handler for cards in the player's hand.
 */
public interface CardClickHandler {


  /**
   * Handles a click event on a specific card in the player's hand.
   *
   * @param cardIndex The index of the card clicked in the hand.
   */
  void handleCardClick(int cardIndex);
}
