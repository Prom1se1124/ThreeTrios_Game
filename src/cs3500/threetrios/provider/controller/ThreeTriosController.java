package cs3500.threetrios.provider.controller;

import cs3500.threetrios.provider.model.ModelFeatures;
import cs3500.threetrios.provider.model.ThreeTriosGame;
import cs3500.threetrios.provider.view.ViewFeatures;
import java.io.IOException;

/**
 * The interface of the required methods of a controller for the game ThreeTrios.
 */
public interface ThreeTriosController extends ViewFeatures, ModelFeatures {

  /**
   * Execute a single game of three trios given a three trios Model. When the game is over,
   * the playGame method ends.
   *
   */
  //void playGame(ReadOnlyThreeTriosGame model, File cardFile, File gridFile) throws IOException;

  void playGame(ThreeTriosGame model) throws IOException;

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   */
  //void handleCellClick(int x, int y);

  void handleCellClick(int x, int y, boolean redSelected, int selectedY);

  //void handleCellClick(MouseEvent e);
}
