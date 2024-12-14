package cs3500.threetrios.provider.view;


import java.util.Objects;

import javax.swing.*;

import cs3500.threetrios.provider.model.CardColor;
import cs3500.threetrios.provider.model.ReadOnlyThreeTriosGame;

// extends JFrame because we need a window (not just a panel).
/**
 * GUI view for three trios.
 */
public class ThreeTriosGameView extends JFrame implements ThreeTriosView {

  private ThreeTriosPanel ttpanel;

  /**
   * Constructs the view for the GUI.
   * @param model the model
   */
  public ThreeTriosGameView(ReadOnlyThreeTriosGame model, CardColor cardColor) {
    ReadOnlyThreeTriosGame model1 = Objects.requireNonNull(model);
    this.ttpanel = new ThreeTriosPanel(model1); // needs states from game

    this.setSize(400 + 200 * model1.getGrid().getCards().get(0).size(),
            200 * model1.getGrid().getCards().size());
    this.setTitle("Three Trios");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.add(ttpanel);

    if (cardColor == CardColor.Red) {
      setTitle("Red Player");
    }
    else {
      setTitle("Blue Player");
    }
  }


  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */


  public void addClickListener(ViewFeatures listener) {
    ttpanel.addClickListener(listener);
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public void displayErrorMessage(String message) {
    //
  }

  /**
   * returns the JPanel.
   * @return the ThreeTriosPanel
   */
  @Override
  public ThreeTriosPanel getPanel() {
    return this.ttpanel;
  }

}
