package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * The interface and required methods for a Hand object in the game ThreeTrios.
 * This is what each player has, and contains the cards they can play.
 */
public interface Hand {
  List<Card> getCards();

  /**
   * Used by the model to produce a new hand, removing a card
   * at a specific index. This will be used when a card is played from the hand.
   *
   * @param idx the index of the card to be removed.
   * @return a new hand without the card at the given index.
   */
  Hand removeFromHand(int idx);

  /**
   * selects the card at this index.
   * @param idx the integer index
   */
  void selectCard(int idx);
}
