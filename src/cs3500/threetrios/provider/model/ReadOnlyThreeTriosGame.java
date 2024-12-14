package cs3500.threetrios.provider.model;

import java.io.IOException;
import java.util.List;

/**
 * Interface for the model that contains no functionality, only the ability to read the state
 * of the game.
 */
public interface ReadOnlyThreeTriosGame {

  /**
   * Starts the game of ThreeTrios by setting up the board and hands according to the
   * given configuration files.
   *
   * @param grid the grid configuration file
   * @param allCards the card configuration file
   * @param rows the integer rows
   * @param cols the integer columns
   * @throws IllegalStateException if the game is already started.
   * @throws IllegalStateException when card size is greater than grid size (INVARIANT holds)
   */
  public void startGame(Grid grid, List<Card> allCards, int rows, int cols,
                        Player redPlayer, Player bluePlayer);

  /**
   * gets the card at the given coordinates.
   * @param row the row of the card
   * @param col the column of the card
   * @return the card itself
   * @throws IllegalArgumentException if there is no card at the given interface
   */
  public Card getCardAtIdx(int row, int col);

  /**
   * Outputs the game over state. If all spots on the grid are filled, the game is over.
   * @return true iff the game is over, false if not
   * @throws IllegalStateException if the game has not yet started.
   */
  public boolean isGameOver();
  //is the game over

  /**
   * gets the string winner for the game.
   * @return the string form of the winner
   */
  public String getWinner();

  /**
   * Gets the current grid state from the model.
   * @return the Grid.
   */
  public Grid getGrid();

  /**
   * Gets the current state of player1's hand.
   * @return the Hand of p1.
   */
  public Hand getRedPlayerHand();

  /**
   * Gets the current state of player2's hand.
   * @return the Hand of p2.
   */
  public Hand getBluePlayerHand();

  /**
   * gets the score of the player by adding the number of cards in their hand
   * and the number of cards of their color on the board.
   * @param color the color of the player who's score the user would like to get
   * @return an integer score
   */
  public int getPlayerScore(CardColor color);

  /**
   * get the player whose turn it currently is.
   * @return the player
   */
  public Player getCurrentPlayer();

  /**
   * gets the potential score when placing down the card,
   * uses a copy of model and grid to simulate the game
   * without mutating it.
   * @param card the card to place down
   * @param row the row to place
   * @param col the col to place
   * @return the integer potential score for the player
   */
  public int getPotentialScore(Card card, int row, int col, Player redPlayer, Player bluePlayer)
          throws IOException;



}
