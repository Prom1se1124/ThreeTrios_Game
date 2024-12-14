package cs3500.threetrios.view;


/**
 * Interface representing the click handler for cells on the game board.
 */
public interface CellClickHandler {

  /**
   * Handles a click event on a specific cell of the game board.
   *
   * @param row The row index of the cell clicked.
   * @param col The column index of the cell clicked.
   */
  void handleCellClick(int row, int col);

}
