package cs3500.threetrios.view;


/**
 * Implementation of card click handler.
 */
public class CardClickHandlerImpl implements CardClickHandler {

  /**
   * Constructor to create a new click handler.
   *
   * @param handsPanel the panel of hand of cards.
   */
  public CardClickHandlerImpl(HandsPanel handsPanel) {
    // nothing to do.
  }

  @Override
  public void handleCardClick(int cardIndex) {

    System.out.println("Clicked card at index: " + cardIndex);
  }
}
