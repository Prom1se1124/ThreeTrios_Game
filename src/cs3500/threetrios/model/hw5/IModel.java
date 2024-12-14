package cs3500.threetrios.model.hw5;

/**
 * Interface for the game in the Three Trios game. The Game interface manages the game state,
 * including tracking turns, card placement, and determining winners.
 */
public interface IModel extends ReadOnlyTriosModel {

  /**
   * Initializes the game with the provided players and grid.
   *
   * @param grid    The game grid to be used.
   * @param players An array of players participating in the game.
   * @throws IllegalArgumentException if the grid or players array is null,
   *                                  or if there are not exactly two players.
   */
  void initializeGame(IGrid grid, IPlayer[] players, String cardFile, String gridFile)
          throws IllegalArgumentException;

  /**
   * Attempts to place a card at the specified position on the grid.
   *
   * @param cardIdx The card index to place.
   * @param x    The x-coordinate of the cell.
   * @param y    The y-coordinate of the cell.
   * @return true if the card was successfully placed, false otherwise.
   * @throws IllegalArgumentException if the card is null, or if the coordinates are out of bounds.
   * @throws IllegalStateException    if the specified cell is a hole or already occupied by a card.
   */
  boolean placeCard(int cardIdx, int x, int y) throws IllegalArgumentException,
          IllegalStateException;


  /**
   * Ends the game and performs any cleanup operations if necessary.
   *
   * @throws IllegalStateException if the game has not been started yet.
   */
  void endGame() throws IllegalStateException;


  /**
   * Switches to the next player, updating whose turn it is.
   */
  void switchTurn();

  /**
   * Get the number of cards being flipped.
   */
   int getFlippedCardsCount();

  /**
   * Get the number of cards each player should have in their hand.
   *
   * @return the hand size for each player.
   */
  int getHandSize();

  /**
   * Get the players.
   *
   * @return players.
   */
  IPlayer[] getPlayers();

  /**
   * Get the grid.
   *
   * @return grid.
   */
  IGrid getGrid();

  /**
   * Get the model feature.
   *
   * @return the hand size for each player.
   */
  ModelFeature getModelFeature();

  /**
   * Get the hint.
   */
  int getHint(int x, int y, ICard card);

  /**
   * Showing if the hints is for current player.
   */
  boolean isHintsEnabledForCurrentPlayer();

  /**
   * Set the hint up.
   */
  void setHintsEnabled(IPlayer player, boolean enabled);


}
