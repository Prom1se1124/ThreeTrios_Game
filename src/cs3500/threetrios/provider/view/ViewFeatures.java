package cs3500.threetrios.provider.view;


import cs3500.threetrios.provider.model.Player;

/**
 * represents the player actions in the view interface.
 */
public interface ViewFeatures {


  /**
   * handles cell click by sending notificatio to the controller.
   * @param x the x coordinate
   * @param y the y coordinate
   * @param redSelected if red is selected
   * @param selectedY the selected y
   */
  public void handleCellClick(int x, int y, boolean redSelected, int selectedY);

  /**
   * returns the player.
   * @return the Player
   */
  public Player getPlayer();

  /**
   * refreshes the view so everyhting is updated.
   */
  public void refreshView();

  /**
   * returns whether the game is over.
   * @return true iff the game is over
   */
  public boolean isGameOver();


}
