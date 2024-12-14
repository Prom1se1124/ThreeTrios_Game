package cs3500.threetrios.provider.model;

/**
 * features class that keeps track of the observers.
 * It should represent what the user requests of the game.
 */
public interface ModelFeatures {

  // Should have a list of players as input

  /**
   * Notifies the user when it is their turn and executes that player's move.
   */
  public void playerTurn();

  /**
   * refreshes the view.
   */
  public void refreshView();

}
