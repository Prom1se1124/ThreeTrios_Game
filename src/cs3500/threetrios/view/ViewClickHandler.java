package cs3500.threetrios.view;


/**
 * Interface representing the click handler for the overall game view.
 */
public interface ViewClickHandler {


  /**
   * Handles a click on the game view.
   *
   * @param x           The x-coordinate of the click within the view.
   * @param y           The y-coordinate of the click within the view.
   * @param componentId A string identifier representing which component within the view was
   *                    clicked.
   *                    This could be used to distinguish between different parts of the game view.
   */
  void handleViewClick(int x, int y, String componentId);


}
