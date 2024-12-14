package cs3500.threetrios.view;


import cs3500.threetrios.model.hw5.ICard;

/**
 * Interface representing the game board panel.
 */
public interface IBoardPanel {


  /**
   * Displays the current state of the game board.
   *
   * @param cardsOnBoard a 2D array representing the cards on the board,
   *                     where null represents an empty cell,
   *                     and cells with cards have their ICard objects.
   */
  void displayBoard(int[][] gridConfiguration, ICard[][] cardsOnBoard);

  /**
   * Highlights a specific cell on the board， also print the coordinates for now.
   *
   * @param x the x-coordinate of the cell to be highlighted.
   * @param y the y-coordinate of the cell to be highlighted.
   */
  void highlightCell(int x, int y);

  /**
   * Registers a click handler for individual cells on the board.
   *
   * @param handler the handler that will manage click events on cells.
   */
  void addCellClickHandler(CellClickHandler handler);

  /**
   * A getter for ViewFeature
   */
  ViewFeature getViewFeature();

}
