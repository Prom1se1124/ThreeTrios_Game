package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.GameCoordinator;
import cs3500.threetrios.controller.IController;

public class ModelFeature implements IModelFeature {

  private GameCoordinator coordinator;
  private final List<IController> listeners = new ArrayList<>(); // Initialize to avoid null pointer
  private final IModel model;

  public ModelFeature(IModel model) {
    this.model = model;
  }

  // New setter for the coordinator
  public void setCoordinator(GameCoordinator coordinator) {
    this.coordinator = coordinator;
  }

  @Override
  public void switchPlayerTurn() {
    model.switchTurn(); // Switch turn in the model
    notifyListeners(); // Notify controllers
  }

  @Override
  public void addListener(IController listener) {
    this.listeners.add(listener);
  }

  @Override
  public void setThisAsListener() {

  }

  @Override
  public void notifyListeners() {
    if (coordinator != null) {
      coordinator.refreshAllViews();
    }
  }

  @Override
  public void notifyGameEnd(String message) {
    if (coordinator != null) {
      coordinator.handleGameEnd(message);
    }
  }


}
