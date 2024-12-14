package cs3500.threetrios.view;

import javax.swing.*;

import cs3500.threetrios.controller.IController;
import cs3500.threetrios.model.hw5.IPlayer;
import cs3500.threetrios.model.hw5.ReadOnlyTriosModel;
import cs3500.threetrios.provider.model.CardColor;
import cs3500.threetrios.provider.view.ThreeTriosGameView;
import cs3500.threetrios.model.hw5.ModelToProviderAdapter;

public class ProviderViewAdapter extends GameView {
  private final ThreeTriosGameView providerView;
  private final CombinedViewFeatureAdapter viewFeatureAdapter;
  private final ReadOnlyTriosModel model;

  public ProviderViewAdapter(ReadOnlyTriosModel model, String perspective) {
    super(model, perspective);  // Call super to maintain GameView contract
    this.model = model;

    // Set up provider's view with adapted model
    ModelToProviderAdapter providerModel = new ModelToProviderAdapter(model);
    CardColor color = perspective.equalsIgnoreCase("blue") ?
            CardColor.Blue : CardColor.Red;
    this.providerView = new ThreeTriosGameView(providerModel, color);

    // Create features adapter
    this.viewFeatureAdapter = new CombinedViewFeatureAdapter(this, model);
  }

  @Override
  public void setVisible(boolean visible) {
    providerView.makeVisible();
  }

  @Override
  public void refresh() {
    setCurrentPlayer(model.getCurrentTurnPlayer().getName());
    providerView.refresh();
  }

  @Override
  public void highlightSelection(int x, int y) {
    // Provider view handles highlighting internally
  }

  @Override
  public void addClickHandler(ViewClickHandler handler) {
    // Provider view uses its own click handling system
  }

  @Override
  public void setCurrentPlayer(String playerName) {
    providerView.setTitle(playerName + "'s Turn");
  }

  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(providerView, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public IPlayer getCurrentTurnPlayer() {
    return model.getCurrentTurnPlayer();
  }

  @Override
  public ViewFeature getViewFeature() {
    return viewFeatureAdapter;
  }

  @Override
  public HandsPanel getRedHandPanel() {
    return null; // Provider view doesn't expose these panels
  }

  @Override
  public HandsPanel getBlueHandPanel() {
    return null; // Provider view doesn't expose these panels
  }

  @Override
  public BoardPanel getBoardPanel() {
    return null; // Provider view doesn't expose these panels
  }

  @Override
  public void showEndMessage(String message) {
    JOptionPane.showMessageDialog(providerView, message, "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
  }

  public void setController(IController controller) {
    // Can use same adapter since it implements ViewFeatures
    viewFeatureAdapter.setController(controller);
    providerView.addClickListener(viewFeatureAdapter);
  }


}
