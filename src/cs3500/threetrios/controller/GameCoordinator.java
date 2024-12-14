package cs3500.threetrios.controller;

public class GameCoordinator {
  private final GameController redController;
  private final GameController blueController;

  public GameCoordinator(GameController redController, GameController blueController) {
    this.redController = redController;
    this.blueController = blueController;

    // Register both controllers with the model to receive updates
    this.redController.getModelFeature().addListener(this.redController);
    this.blueController.getModelFeature().addListener(this.blueController);
  }

  public void refreshAllViews() {
    redController.update();  // This will internally call redView.refresh()
    blueController.update(); // This will internally call blueView.refresh()
  }


  public void handleGameEnd(String message) {
    redController.displayEndMessage(message);
    blueController.displayEndMessage(message);
  }


  // Other game coordination logic can go here if necessary
}

