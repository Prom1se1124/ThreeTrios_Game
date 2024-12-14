package cs3500.threetrios.view;

import cs3500.threetrios.controller.IController;
import cs3500.threetrios.model.hw5.PlayerAdapter;
import cs3500.threetrios.model.hw5.ReadOnlyTriosModel;
import cs3500.threetrios.provider.view.ViewFeatures;
import cs3500.threetrios.provider.model.Player;

public class CombinedViewFeatureAdapter extends ViewFeature implements ViewFeatures {

  private final ReadOnlyTriosModel model;
  private IController controller;

  public CombinedViewFeatureAdapter(GameView view, ReadOnlyTriosModel model) {
    super(view);
    this.model = model;
  }


  // Provider's ViewFeatures methods
  @Override
  public void handleCellClick(int x, int y, boolean redSelected, int selectedY) {
    if (!redSelected) {
      selectHandCard(selectedY);
    }
    selectGridCell(x, y);
  }

  @Override
  public Player getPlayer() {
    return new PlayerAdapter(super.listener.getPlayer());
  }

  @Override
  public void refreshView() {
    if (super.listener != null) {
      super.listener.update();
    }
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }


  // Method to set controller
  public void setController(IController controller) {
    super.addListener(controller);
    this.controller = controller;
  }


}
