package cs3500.threetrios.controller;

import cs3500.threetrios.model.hw5.AIPlayer;
import cs3500.threetrios.model.hw5.Grid;
import cs3500.threetrios.model.hw5.IAi;
import cs3500.threetrios.model.hw5.IGrid;
import cs3500.threetrios.model.hw5.IModel;
import cs3500.threetrios.model.hw5.IModelFeature;
import cs3500.threetrios.model.hw5.IPlayer;
import cs3500.threetrios.model.hw5.ModelFeature;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.IViewFeature;
import cs3500.threetrios.view.ViewFeature;

public class GameController implements IController {

  private GameView view;
  private IPlayer player;
  private IModel model;
  private int numCard;
  IPlayer[] players;
  private IModelFeature modelFeatures;
  private IViewFeature viewFeatures;
  private IGrid grid;
  private int selectedCardIndex = -1;

  public GameController(IModel model, GameView view,
                        IPlayer player, IPlayer[] players, int numCard) {
    this.view = view;
    this.player = player;
    this.model = model;
    this.numCard = numCard;
    this.players = players;
    this.grid = new Grid();

    // Create model and view features
    this.modelFeatures = new ModelFeature(model);
    this.viewFeatures = new ViewFeature(view);

  }


  @Override
  public void playGame(String cardPath, String boardPath, IModel model, IGrid grid) {
    if (cardPath == null || cardPath.isEmpty()) {
      throw new IllegalArgumentException("invalid card file path");
    }
    if (boardPath == null || boardPath.isEmpty()) {
      throw new IllegalArgumentException("invalid board file path");
    }
    model.initializeGame(grid, players, cardPath, boardPath);

    view.refresh();

    notifyPlayerTurn();
  }

  private void notifyPlayerTurn() {
    // Get the current player
    IPlayer currentPlayer = model.getCurrentTurnPlayer();
    System.out.println("notifyPlayerTurn() called for: " + currentPlayer.getName());

    // Update the view
    view.setCurrentPlayer(currentPlayer.getName());

    // If the current player is a machine, trigger an automated move
    if (currentPlayer instanceof AIPlayer) {
      handleAITurn((AIPlayer) currentPlayer);
    } else {
      System.out.println("It's " + currentPlayer.getName() + "'s turn.");
    }
  }

  private void handleAITurn(AIPlayer aiPlayer) {
    System.out.println("handleAITurn() called for: " + aiPlayer.getName());
    // Get the move fromAI
    int[] move = aiPlayer.choosePosition(model.getGrid().getAvailablePositions());

    System.out.println("AI selected move: CardIndex " + move[0] + ", Position: (" + move[1]
            + ", " + move[2] + ")");

    // Place the card in the model
    try {
      aiPlayer.executeMove();
      System.out.println(aiPlayer + " placed a card at (" + move[1] + ", " + move[2] + ")");
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorMessage("AI made an invalid move: " + e.getMessage());
    }

    // Switch turn after AI makes a move
    model.switchTurn();
    notifyPlayerTurn();
  }


  @Override
  public void update() {
    view.refresh();
  }

  @Override
  public void switchPlayer(IPlayer player) {
    model.switchTurn();
    notifyPlayerTurn();
  }

  @Override
  public void selectCard(int handIndex) {
    // Logic for selecting or dehighlighting a card in the hand (human player)
    IPlayer currentPlayer = model.getCurrentTurnPlayer();
    if (!(currentPlayer instanceof AIPlayer) && isCurrentPlayer()) {
      if (selectedCardIndex == handIndex) {
        // If the card is already selected, dehighlight it
        System.out.println("Card deselected at hand index: " + handIndex);
        selectedCardIndex = -1; // Reset the selected card index to indicate no card is selected
      } else {
        // Otherwise, select the new card
        selectedCardIndex = handIndex;
        System.out.println("Card selected at hand index: " + handIndex);
      }
    } else {
      System.out.println("It's not your turn or you're not allowed to make selections!");
    }
  }


  public int getSelectedCardIndex() {
    return selectedCardIndex;
  }

  @Override
  public void selectGridCell(int row, int col) {
    // Logic for selecting a grid cell to place a card
    IPlayer currentPlayer = model.getCurrentTurnPlayer();
    if (!(currentPlayer instanceof AIPlayer) && isCurrentPlayer()) {
      if (selectedCardIndex == -1) {
        // No card has been selected
        view.showErrorMessage("Please select a card before selecting a grid cell.");
        return;
      }
      try {
        // Place the selected card at the specified grid cell
        model.placeCard(selectedCardIndex, row, col);

        // Reset the selected card index after placing the card
        selectedCardIndex = -1;

        // Switch turn to the other player
        model.switchTurn();
        view.refresh();
        notifyPlayerTurn();
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.showErrorMessage("Invalid move: " + e.getMessage());
      }
    } else {
      System.out.println("It's not your turn or you're not allowed to make selections!");
    }
  }

  @Override
  public boolean isCurrentPlayer() {

    boolean isCurrent = model.getCurrentTurnPlayer().equals(player);
    System.out.println("isCurrentPlayer check: " + player.getName()
            + " (Expected: " + model.getCurrentTurnPlayer().getName() + ") -> " + isCurrent);
    return isCurrent;
  }

  @Override
  public IPlayer getPlayer() {
    return player;
  }

  @Override
  public IModelFeature getModelFeature() {
    return this.modelFeatures;
  }


  @Override
  public void displayEndMessage(String message) {
    view.showEndMessage(message);
  }

  @Override
  public void setHintsEnabled(boolean enabled) {

    ((IModel)model).setHintsEnabled(player, enabled);
    view.refresh();  // Refresh to show/hide hints
  }

}

