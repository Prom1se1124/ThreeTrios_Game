package cs3500.threetrios.controller;

/**
 * Interface for coordinating game activities between multiple controllers.
 * This interface is designed to manage and coordinate game interactions,
 * including updating views and handling game-end scenarios.
 */
public interface IGameCoordinator {

  /**
   * Refreshes all views managed by the game coordinator.
   * This method ensures that the visual representation of both players' views is up-to-date.
   */
  void refreshAllViews();

  /**
   * Handles the game-end scenario by displaying a provided message in all managed views.
   *
   * @param message the message to be displayed, indicating the outcome of the game.
   */
  void handleGameEnd(String message);
}
