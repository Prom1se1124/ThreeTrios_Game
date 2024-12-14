package cs3500.threetrios.view;

import cs3500.threetrios.controller.IController;
import cs3500.threetrios.model.hw5.PlayerAdapter;
import cs3500.threetrios.model.hw5.ReadOnlyTriosModel;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.view.ViewFeatures;


public class ViewFeaturesToControllerAdapter implements ViewFeatures {

  private final IController ourController;
  private final ReadOnlyTriosModel model;
  private int lastSelectedCard = -1;  // State tracking

  public ViewFeaturesToControllerAdapter(IController ourController, ReadOnlyTriosModel model) {
    this.ourController = ourController;
    this.model = model;
  }


  @Override
  public void handleCellClick(int x, int y, boolean redSelected, int selectedY) {

    // Add debug info
    System.out.println("Adapter: handleCellClick called with x=" + x + ", y=" + y
            + ", redSelected=" + redSelected + ", selectedY=" + selectedY);

    // If this is a card selection
    if (redSelected || !redSelected) {
      System.out.println("Adapter: Processing card selection: " + selectedY);
      lastSelectedCard = selectedY;
      ourController.selectCard(selectedY);
      return;  // Exit, don't try to place card yet
    }

    // If this is a grid cell click(with a card selected)
    if (lastSelectedCard != -1) {
      System.out.println("Adapter: Placing card " + lastSelectedCard + " at position "
              + x + "," + y);
      ourController.selectGridCell(x, y);
      lastSelectedCard = -1;  // Reset selection after placement attempt
    } else {
      System.out.println("Adapter: Attempted grid selection without card selected");
    }
  }

  @Override
  public Player getPlayer() {
    return new PlayerAdapter(ourController.getPlayer());
  }

  @Override
  public void refreshView() {
    ourController.update();
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

}
