package cs3500.threetrios.view;

/**
 * Implementation of the cell click handler.
 */
public class CellClickHandlerImpl implements CellClickHandler {

  /**
   * Constructor to create a new click handler.
   *
   * @param boardPanel the panel of the game board.
   */
  public CellClickHandlerImpl(BoardPanel boardPanel) {
    // nothing to do.
  }

  @Override
  public void handleCellClick(int row, int col) {
    System.out.println("Cell clicked at row: " + row + ", column: " + col);
  }

}
