package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.view.ThreeTriosPanel;


/**
 * Represent the interface for three trios.
 */
public interface ThreeTriosView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(ViewFeatures listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * gets the JPanel for the view.
   *
   * @return the ThreeTriosPanel
   */
  ThreeTriosPanel getPanel();
}
