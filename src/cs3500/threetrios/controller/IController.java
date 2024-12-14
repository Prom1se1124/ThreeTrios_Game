package cs3500.threetrios.controller;

import cs3500.threetrios.model.hw5.IGrid;
import cs3500.threetrios.model.hw5.IModel;
import cs3500.threetrios.model.hw5.IModelFeature;
import cs3500.threetrios.model.hw5.IPlayer;

public interface IController {

  void playGame(String cardPath, String boardPath, IModel model, IGrid grid);

  void update(); // Called when the model changes state

  void switchPlayer(IPlayer player); // Handle player switching

  void selectCard(int handIndex); // New: Handle card selection from hand

  void selectGridCell(int row, int col);

  boolean isCurrentPlayer();

  IPlayer getPlayer();

  IModelFeature getModelFeature();

  void displayEndMessage(String message);

  void setHintsEnabled(boolean enabled);

}
