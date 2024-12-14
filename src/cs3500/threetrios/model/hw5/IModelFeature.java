package cs3500.threetrios.model.hw5;

import cs3500.threetrios.controller.IController;

public interface IModelFeature {
  void switchPlayerTurn();

  void addListener(IController listener);

  void setThisAsListener();

  void notifyListeners();

  void notifyGameEnd(String message);
}
