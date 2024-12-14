package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * The interface and required methods for Grid object (what the user places cards on)
 * in the game ThreeTrios. the Grid has a list of rows of cards which means that the method call:
 * grid.get(row).get(col) would first get the row and then retrieve the element at the column.
 * The coordinate system is 0-indexed row-column order.
 */
public interface Grid {
  /**
   * Gets the current grid.
   *
   * @return the list of list of cells
   */
  List<List<Cell>> getCards();
}
