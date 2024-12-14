package cs3500.threetrios.model.hw5;

import java.util.List;

/**
 * Interface for the grid in the Three Trios game. The grid manages the cells,
 * which can either be a hole or a card cell, and handles card placement and interaction.
 */
public interface IGrid {


  /**
   * Initializes the grid with a predefined configuration.
   *
   * @param configuration A matrix representing the grid where each element
   *                      indicates the type of cell (-1 for holes, 0 for empty card cells).
   * @throws IllegalArgumentException if the configuration is null or the matrix dimensions
   *                                  are invalid.
   */
  void initialize(int[][] configuration) throws IllegalArgumentException;


  /**
   * Attempts to place a card at the specified position on the grid.
   *
   * @param card The card to place.
   * @param x    The x-coordinate of the cell.
   * @param y    The y-coordinate of the cell.
   * @return true if the card was successfully placed, false otherwise
   * @throws IllegalArgumentException if the card is null, or if the coordinates are out of bounds.
   * @throws IllegalStateException    if the specified cell is a hole or already occupied by a card.
   */
  boolean placeCard(ICard card, int x, int y) throws IllegalArgumentException,
          IllegalStateException;


  /**
   * Checks if a specified position in the grid is valid for placing a card.
   *
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   * @return true if the position is valid (not a hole and within grid bounds), false otherwise.
   */
  boolean isValidPosition(int x, int y);


  /**
   * Retrieves the cards adjacent to the specified cell.
   *
   * @param x The x-coordinate of the cell.
   * @param y The y-coordinate of the cell.
   * @return An array of cards adjacent to the specified cell.
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  ICard[] getAdjacentCards(int x, int y) throws IllegalArgumentException;


  /**
   * Get a list of available positions on the grid where cards can be placed.
   *
   * @return A list of int arrays representing available positions. Each int array contains
   * two elements: the x and y coordinates of an available position.
   */
  List<int[]> getAvailablePositions();


  /**
   * Get a card's specific position.
   *
   * @throws IllegalArgumentException if the coordinates are out of bounds.
   */
  ICard getCardPosition(int x, int y);

  /**
   * Gets the dimensions of the grid as an array where the first element is the number of rows
   * and the second element is the number of columns.
   *
   * @return An array of size 2, where [0] is the number of rows and [1] is the number of columns.
   */
  int[] getDimensions();


  /**
   * Displays the current state of the grid for debugging and visualization purposes.
   */
  String display();

  /**
   * Get the board of the grid.
   */
  int[][] getBoard();


  /**
   * Get the game board with cards played in cardsOnGrid.
   */
  ICard[][] getBoardState();

  /**
   * Get the cols of the grid.
   */
  int getCol();

  /**
   * Get the rows of the grid.
   */
  int getRow();

  /**
   * Get the number of cards being flipped.
   */
  int getFlippedCardsCount();

  /**
   * get the hint how many cards will be flipped if play one card to one cell.
   *
   * @param x    x-coordinate of target cell
   * @param y    y-coordinate of target cell
   * @param card selected card
   * @return number of cards flipped
   */
  int getHint(int x, int y, ICard card);
}
