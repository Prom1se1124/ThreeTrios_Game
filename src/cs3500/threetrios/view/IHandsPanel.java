package cs3500.threetrios.view;


import java.util.List;

import cs3500.threetrios.model.hw5.ICard;

/**
 * Interface representing the player's hand panel.
 */
public interface IHandsPanel {


  /**
   * Displays the hand panel to reflect the current cards in the player's hand.
   *
   * @param cards the list of cards to be displayed in the hand.
   */
  void displayHand(List<ICard> cards);

  /**
   * Highlights a specific card in the player's hand, also print the card out in console for now.
   *
   * @param cardIndex the index of the card to be highlighted.
   */
  void highlightCard(int cardIndex);


  /**
   * Dehighlights a specific card in the player's hand, also print the card out in console for now.
   *
   * @param cardIndex the index of the card to be highlighted.
   */
  void dehighlightCard(int cardIndex);

  /**
   * Registers a click handler for individual cards in the hand.
   *
   * @param handler the handler that will manage click events on cards.
   */
  void addCardClickHandler(CardClickHandler handler);




}
