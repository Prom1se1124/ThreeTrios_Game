package cs3500.threetrios.view;


import cs3500.threetrios.model.hw5.IPlayer;

/**
 * Interface representing the overall game view.
 */
public interface IGameView {


  /**
   * Updates the display to reflect the current game state.
   */
  void refresh();

  /**
   * Highlights the currently selected card or cell based on user interaction.
   *
   * @param x the x-coordinate of the selected cell (or -1 if no cell is selected).
   * @param y the y-coordinate of the selected cell (or -1 if no cell is selected).
   */
  void highlightSelection(int x, int y);

  /**
   * Registers a click handler for the overall view.
   *
   * @param handler the handler that will manage click events on the view.
   */
  void addClickHandler(ViewClickHandler handler);

  /**
   * Sets the current player label based on whose turn it is.
   *
   * @param playerName the name of the current player.
   */
  void setCurrentPlayer(String playerName);

  /**
   * Show the present error.
   *
   * @param message the detail message to show.
   */
  void showErrorMessage(String message);

  /**
   * Return the player that is on current turn.
   */
  IPlayer getCurrentTurnPlayer();


  /**
   * Return the ViewFeature.
   */
  ViewFeature getViewFeature();

  /**
   * Show the ending message, telling the game players that the game ended.
   *
   * @param message the detail message.
   */
  void showEndMessage(String message);

  /**
   * Return red player's hand panel.
   */
  HandsPanel getRedHandPanel();

  /**
   * Return blue player's hand panel.
   */
  HandsPanel getBlueHandPanel();


  /**
   * Return the board panel.
   */
  BoardPanel getBoardPanel();


}
