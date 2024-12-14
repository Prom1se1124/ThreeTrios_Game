package cs3500.threetrios.provider.model;

//import java.io.File;
//import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;

/**
 * An interface that represents the model of a game of Three Trios.
 */
public interface ThreeTriosGame extends ReadOnlyThreeTriosGame {

  /**
   * Places a given card at the row and column index provided.
   *
   * @param rowIdx the 0-indexed row
   * @param colIdx the 0-indexed column
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game has already ended
   * @throws IllegalArgumentException if the row or column idx is out of bounds
   * @throws IllegalArgumentException if the row and column idx indicates a hole cell
   *     (cannot place card)
   * @throws IllegalArgumentException if the row and column idx indicates a cell
   *     where there is already a card.
   */
  public void placeCard(Card card, int rowIdx, int colIdx);


  /**
   * For the given cell, get which directions aren't hole cells
   * or off the grid.
   * @param row the int row of the card
   * @param col the int column of the card
   * @return list of directions to check
   */
  public ArrayList<Direction> directionsOpen(int row, int col);


  /**
   * starts the ThreeTriosGame.
   * @param gridFile the grid file to read
   * @param cardFile the card file to read
   * @param redPlayer the red player
   * @param bluePlayer the blue player
   */
  void startGame(File gridFile, File cardFile, Player redPlayer, Player bluePlayer);

  /**
   * starts up the model with controlled cards and grid set up so it
   * doesn't rely on logic of model and placing cards.
   * @param grid the grid
   * @param redHand the red player's hand
   * @param blueHand the blue player's hand
   */
  public void startMidGame(Grid grid, Hand redHand, Hand blueHand,
                           Player redPlayer, Player bluePlayer);

  /**
   * Returns the player corresponding to the color.
   * @param color the color of the player and their cards
   * @return the player
   */
  public Player getPlayer(CardColor color);

  /**
   * deep copies the grid and player hands to create a new model with the same
   * game state. Returns the new model alreayd started up.
   * @param model the model to deep copy
   * @return the deep copied model
   */
  public ThreeTriosGame newModel(ThreeTriosGame model, Player redPlayer, Player bluePlayer);


  /**
   * adds listeners to this model's list of listeners.
   * @param listener the Feature listener
   */
  public void addListeners(ModelFeatures listener);


}
