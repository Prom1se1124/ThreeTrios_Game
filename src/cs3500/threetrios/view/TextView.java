package cs3500.threetrios.view;


import cs3500.threetrios.model.hw5.ThreeTriosModel;

/**
 * The class that can produce the text view of the game.
 */
public class TextView {
  private final ThreeTriosModel model;

  /**
   * Constructor of the class taking a model.
   * @param model the game model
   */
  public TextView(ThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Model.Player: ").append(model.displayPlayer()).append("\n");

    sb.append(model.display());

    sb.append("Hand: \n");
    sb.append(model.displayPlayerHand());
    return sb.toString();
  }

}
