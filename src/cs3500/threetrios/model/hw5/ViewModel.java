package cs3500.threetrios.model.hw5;

import java.util.List;


/**
 * This is a ViewModel, which act as an adapter between the model and the view, providing read-only
 * access to the underlying model's state.
 */
public class ViewModel implements ReadOnlyTriosModel {
  private final ThreeTriosModel model;


  /**
   * Constructor to create a ViewModel from a given model.
   *
   * @param model the ThreeTriosModel to be used for creating the ViewModel.
   * @throws IllegalArgumentException if the model is null.
   */
  public ViewModel(ThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }


  @Override
  public IPlayer getCurrentTurnPlayer() {
    return model.getCurrentTurnPlayer();
  }

  @Override
  public List<ICard> getHand(IPlayer player) {
    return model.getHand(player);
  }

  @Override
  public int[] getScores() {
    return model.getScores();
  }

  @Override
  public int[][] getBoard() {
    return model.getBoard();
  }

  @Override
  public ICard[][] getBoardState() {
    return model.getBoardState();
  }

  @Override
  public IPlayer getWinner() {
    return model.getWinner();
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public String displayPlayer() {
    return model.displayPlayer();
  }

  @Override
  public int getCol() {
    return model.getCol();
  }

  @Override
  public int getRow() {
    return model.getRow();
  }

  @Override
  public List<ICard> getRedHand() {
    return model.getRedHand();
  }

  @Override
  public List<ICard> getBlueHand() {
    return model.getBlueHand();
  }

  @Override
  public boolean isHintsEnabledForCurrentPlayer() {
    return model.isHintsEnabledForCurrentPlayer();
  }

  @Override
  public int getHint(int x, int y, ICard card){
    return model.getHint(x, y, card);
  }

  @Override
  public BattleRule getBattleRule() {
    return model.getBattleRule();
  }
}
