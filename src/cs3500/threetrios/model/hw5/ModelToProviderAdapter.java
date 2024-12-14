package cs3500.threetrios.model.hw5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.CardColor;
import cs3500.threetrios.provider.model.Grid;
import cs3500.threetrios.provider.model.Hand;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ReadOnlyThreeTriosGame;

public class ModelToProviderAdapter implements ReadOnlyThreeTriosGame {
  private final ReadOnlyTriosModel ourModel;

  public ModelToProviderAdapter(ReadOnlyTriosModel ourModel) {
    this.ourModel = ourModel;
  }

  @Override
  public void startGame(Grid grid, List<Card> allCards, int rows, int cols, Player redPlayer,
                        Player bluePlayer) {
    // This method likely won't be called since we're using our model's initialization,
    // but we will throw an exception to be clear
    throw new UnsupportedOperationException(
            "Game initialization should be handled by our model, not provider");
  }

  @Override
  public Card getCardAtIdx(int row, int col) {
    ICard card = ourModel.getBoardState()[row][col];
    if (card == null) {
      throw new IllegalArgumentException("No card at position " + row + "," + col);
    }
    return new CardAdapter(card);
  }

  @Override
  public boolean isGameOver() {
    return ourModel.isGameOver();
  }

  @Override
  public String getWinner() {

    if (!ourModel.isGameOver()) {
      return "Game is not over";
    }
    IPlayer winner = ourModel.getWinner();
    if (winner == null) {
      return "Tie Game";
    }
    return winner.getName() + " Wins";
  }

  @Override
  public Grid getGrid() {

    return new GridAdapter(ourModel);
  }

  @Override
  public Hand getRedPlayerHand() {
    // Defensive null check
    List<ICard> redHand = ourModel.getRedHand();
    if (redHand == null) {
      throw new IllegalStateException("Red hand is null");
    }
    return new HandAdapter(new ArrayList<>(redHand));
  }

  @Override
  public Hand getBluePlayerHand() {
    List<ICard> blueHand = ourModel.getBlueHand();
    if (blueHand == null) {
      throw new IllegalStateException("Blue hand is null");
    }
    return new HandAdapter(new ArrayList<>(blueHand));
  }

  @Override
  public int getPlayerScore(CardColor color) {
    int[] scores = ourModel.getScores();
    return (color == CardColor.Red) ? scores[0] : scores[1];
  }

  @Override
  public Player getCurrentPlayer() {
    IPlayer currentPlayer = ourModel.getCurrentTurnPlayer();

    // Log the state transition for debugging
    System.out.println("ModelToProviderAdapter: Current player is " + currentPlayer.getName());
    System.out.println("ModelToProviderAdapter: Player color is " + currentPlayer.getColor());

    return new PlayerAdapter(currentPlayer);

  }

  @Override
  public int getPotentialScore(Card card, int row, int col, Player redPlayer,
                               Player bluePlayer) throws IOException {
    // This is likely used for AI strategies which we're not implementing
    // So, return current score as we're not predicting potential scores
    return getPlayerScore(card.getColor());
  }


  //Helper method to validate model state
  public void verifyState() {
    System.out.println("\n=== Model Adapter State Verification ===");
    System.out.println("Current Player: " + ourModel.getCurrentTurnPlayer().getName());
    System.out.println("Red Hand Size: " + ourModel.getRedHand().size());
    System.out.println("Blue Hand Size: " + ourModel.getBlueHand().size());
    System.out.println("Game Over: " + ourModel.isGameOver());
    System.out.println("====================================\n");
  }


}
