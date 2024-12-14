package cs3500.threetrios.model.hw5;

import java.util.List;

/**
 * Read Only Model of Tree Trios Game.
 */
public interface ReadOnlyTriosModel {
  /**
   * Gets the current player whose turn it is.
   *
   * @return The current player.
   */
  IPlayer getCurrentTurnPlayer();

  /**
   * Returns the hand based on the inputted player.
   *
   * @param player player whose hand is being returned.
   * @return a list of c representing the hand.
   */
  List<ICard> getHand(IPlayer player);

  /**
   * Retrieves the number of cards owned by each player for scoring purposes.
   *
   * @return An array of scores, where the first value is player one's score.
   */
  int[] getScores();

  /**
   * Returns the board as a 2D array.
   * -1 for holes, 0 for empty card cells
   *
   * @return a 2D array of Statuses representing the board of statuses.
   */
  int[][] getBoard();

  /**
   * Returns the current board with cards placed info as a 2D array.
   * * @return a 2D array of Statuses representing the cards on board.
   */
  ICard[][] getBoardState();

  /**
   * Returns a Player representing the winner of the game.
   *
   * @return a Player representing the winner.
   */
  IPlayer getWinner();

  /**
   * Checks if the game has ended.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();


  /**
   * Display the name of player.
   *
   * @return name of player
   */
  String displayPlayer();


  /**
   * Get the cols of the grid.
   */
  int getCol();

  /**
   * Get the rows of the grid.
   */
  int getRow();

  /**
   * Get the red player's hand.
   */
  List<ICard> getRedHand();

  /**
   * Get the blue player's hand.
   */
  List<ICard> getBlueHand();

  /**
   * Get boolean of if the hint is enabled for the current player.
   */
  boolean isHintsEnabledForCurrentPlayer();

  /**
   * Gets the number of cards that would be flipped if the given card was placed at the
   * specified position.
   * @param x the row coordinate
   * @param y the column coordinate
   * @param card the card to be placed
   * @return the number of cards that would be flipped
   */
  int getHint(int x, int y, ICard card);

  /**
   * Getter for Battle rule.
   */
  BattleRule getBattleRule();
}
