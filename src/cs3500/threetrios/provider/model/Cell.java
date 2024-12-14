package cs3500.threetrios.provider.model;

/**
 * An abstract class representing cells, used as the components of a grid.
 * Cells can be holes, or hold cards, but those that hold cards can be empty (no card within it).
 */
public interface Cell {

  /**
   * Only the CardCell class will override this method and return true.
   * @return false if not a card cell.
   */
  public boolean isCardCell();

  /**
   * Only the CardCell class will override this method, and will return
   * false if it does indeed contain a card.
   * @return true unless it is a card cell that contains a card.
   */
  public boolean isEmpty();

  /**
   * Gets the card within a given card cell, unable to access if it is not a card cell.
   * @return the Card within the card cell.
   * @throws IllegalStateException if the cell is not a card cell.
   */
  public Card getCard();

}
