package cs3500.threetrios.model.hw5;

import java.util.Collections;
import java.util.List;

/**
 * The model of the Three Trios Game. The Game model manages the game state,
 * including tracking turns, card placement, and determining winners.
 */
public class ThreeTriosModel implements IModel, ReadOnlyTriosModel {

  private IGrid grid;
  private IPlayer[] players;
  private int currentPlayerIndex; // 0 is RED player, 1 is BLUE player
  boolean redHint;
  boolean blueHint;
  private ModelFeature modelFeature;
  private BattleRule battleRule;


  /**
   * No-argument Constructor of the model.
   */
  public ThreeTriosModel() {
    // Initialize default fields here
    this.battleRule = new DefaultBattleRule(); // Use the default battle rule
  }

  /**
   * Copy Constructor of the model.
   */
  public ThreeTriosModel(ThreeTriosModel model) {


    this.grid = new Grid((Grid) model.grid);

    // Create a deep copy of players arrays
    this.players = new IPlayer[model.players.length];
    for (int i = 0; i < model.players.length; i++) {
      if (model.players[i] instanceof HumanPlayer) {
        this.players[i] = new HumanPlayer((HumanPlayer) model.players[i]);
      } else if (model.players[i] instanceof AIPlayer) {
        AIPlayer aiPlayer = (AIPlayer) model.players[i];
        this.players[i] = new AIPlayer(aiPlayer.getName(), aiPlayer.getColor(),
                aiPlayer.getStrategy(), this);
      } else {
        throw new IllegalArgumentException("Unknown player type.");
      }
    }


    this.currentPlayerIndex = model.currentPlayerIndex;
    this.battleRule = model.battleRule;
    this.modelFeature = new ModelFeature(this);
  }


  /**
   * Constructor allow specifying a custom BattleRule.
   */
  public ThreeTriosModel(BattleRule battleRule) {
    if (battleRule == null) {
      throw new IllegalArgumentException("Battle rule cannot be null.");
    }
    this.battleRule = battleRule;
  }

  @Override
  public void initializeGame(IGrid grid, IPlayer[] players, String cardFile,
                             String gridFile)
          throws IllegalArgumentException {
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null.");
    }
    if (players.length != 2) {
      throw new IllegalArgumentException("There must be exactly two players.");
    }
    if (players[0] == null || players[1] == null) {
      throw new IllegalArgumentException("Players cannot be null.");
    }

    BoardConfigReader boardReader = new BoardConfigReader(gridFile);
    int[][] gridBoard = boardReader.convertFile();
    this.grid = new Grid(this.battleRule);
    this.grid.initialize(gridBoard);


    int numCells = grid.getAvailablePositions().size();
    int requiredCards = numCells;

    CardConfigReader cardReader = new CardConfigReader(cardFile);
    List<Card> deck = cardReader.convertFile(requiredCards);


    this.players = players;
    int handSize = getHandSize();


    while (players[0].getHand().size() < handSize && !deck.isEmpty()) {
      Card redCard = deck.remove(0);
      redCard.setColor(CardColor.RED);
      players[0].addCard(redCard);
    }


    while (players[1].getHand().size() < handSize && !deck.isEmpty()) {
      Card blueCard = deck.remove(0);
      blueCard.setColor(CardColor.BLUE);
      players[1].addCard(blueCard);
    }

    this.currentPlayerIndex = 0;

    if (this.modelFeature == null) {
      this.modelFeature = new ModelFeature(this);
    }
    modelFeature.notifyListeners();
  }

  @Override
  public boolean placeCard(int cardIdx, int x, int y) throws IllegalArgumentException,
          IllegalStateException {

    boolean success = this.grid.placeCard(getCurrentTurnPlayer().getHand().get(cardIdx), x, y);

    if (success) {


      // Remove the card from the player's hand after placing it
      getCurrentTurnPlayer().removeCard(cardIdx);
      modelFeature.notifyListeners();

      if (isGameOver()) {
        endGame();
      }
    }
    return success;
  }


  @Override
  public boolean isGameOver() {

    // Get available positions from the grid
    List<int[]> availablePositions = grid.getAvailablePositions();

    // If there are no available positions, the game is over
    return availablePositions.isEmpty();
  }

  @Override
  public IPlayer getWinner() throws IllegalStateException {
    if (!isGameOver()) {
      throw new IllegalStateException("The game has not yet ended.");
    }

    // Get the scores of both players
    int[] scores = getScores(); // This method calculates the number of cards each player owns
    int playerOneScore = scores[0];
    int playerTwoScore = scores[1];

    // Determine the winner
    if (playerOneScore > playerTwoScore) {
      return players[0]; // Player 1 wins
    } else if (playerTwoScore > playerOneScore) {
      return players[1]; // Player 2 wins
    } else {
      return null; // It's a tie
    }

  }

  @Override
  public void endGame() throws IllegalStateException {

    if (grid == null || players == null) {
      throw new IllegalStateException("The game has not started yet.");
    }

    String winnerMessage;
    try {
      IPlayer winner = getWinner();
      if (winner == null) {
        winnerMessage = "The game is a tie!";
      } else {
        winnerMessage = "The winner is: " + winner.getName();
      }
    } catch (IllegalStateException e) {
      winnerMessage = "Error determining the winner.";
    }


    modelFeature.notifyGameEnd(winnerMessage);

  }

  @Override
  public IPlayer getCurrentTurnPlayer() {
    if (players == null) {
      throw new IllegalStateException("There is null players.");
    }

    if (players.length != 2) {
      throw new IllegalStateException("There should be exactly 2 players.");
    }

    System.out.println("Current turn index: " + currentPlayerIndex + ", Current player: "
            + players[currentPlayerIndex].getName());

    return players[currentPlayerIndex];
  }

  @Override
  public List<ICard> getHand(IPlayer player) {
    return getCurrentTurnPlayer().getHand();
  }

  @Override
  public List<ICard> getRedHand() {
    return players[0].getHand();

  }

  @Override
  public List<ICard> getBlueHand() {
    return players[1].getHand();

  }

  @Override
  public void switchTurn() {
    currentPlayerIndex = (currentPlayerIndex == 0) ? 1 : 0;

    if (isGameOver()) {
      endGame();
    } else {
      modelFeature.notifyListeners();
    }


  }

  @Override
  public int[] getScores() {
    int playerRedScore = 0;
    int playerBlueScore = 0;

    // Get grid dimensions
    int[] dimensions = grid.getDimensions();
    int rows = dimensions[0];
    int cols = dimensions[1];

    // Iterate through the entire grid to count the number of RED and BLUE cards
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        ICard card = grid.getCardPosition(x, y);
        if (card != null) {
          if (card.getColor() == CardColor.RED) {
            playerRedScore++;
          } else if (card.getColor() == CardColor.BLUE) {
            playerBlueScore++;
          }
        }
      }
    }

    return new int[]{playerRedScore, playerBlueScore};
  }

  @Override
  public int[][] getBoard() {
    return grid.getBoard();
  }

  @Override
  public ICard[][] getBoardState() {
    return grid.getBoardState();
  }


  @Override
  public String displayPlayer() {
    if (currentPlayerIndex == 0) {
      return "RED";
    } else if (currentPlayerIndex == 1) {
      return "BLUE";
    } else {
      throw new IllegalStateException("There should be exactly two players.");
    }
  }

  @Override
  public int getCol() {
    return this.grid.getCol();
  }

  @Override
  public int getRow() {
    return this.grid.getRow();
  }

  /**
   * Display all the card in current player's hand.
   *
   * @return Strings represents all the card in current player's hand.
   */
  public String displayPlayerHand() {
    StringBuilder sb = new StringBuilder();
    for (ICard card : getCurrentTurnPlayer().getHand()) {
      sb.append(card.toString()).append("\n");
    }
    return sb.toString();
  }

  public String display() {
    return this.grid.display();
  }


  public int getFlippedCardsCount() {
    return grid.getFlippedCardsCount();
  }

  public ModelFeature getModelFeature() {
    if (this.modelFeature == null) {
      this.modelFeature = new ModelFeature(this);
    }
    return this.modelFeature;
  }


  @Override
  public int getHandSize() {
    int numCells = grid.getAvailablePositions().size();
    return (numCells + 1) / 2;
  }


  @Override
  public IPlayer[] getPlayers() {
    return players;
  }

  @Override
  public IGrid getGrid() {
    return grid;
  }

  @Override
  public int getHint(int x, int y, ICard card) {
    return this.grid.getHint(x, y, card);
  }


  @Override
  public boolean isHintsEnabledForCurrentPlayer() {
    if (currentPlayerIndex == 0) {
      return redHint;
    }
    return blueHint;

  }

  @Override
  public void setHintsEnabled(IPlayer player, boolean enabled) {
    if (player.getColor().equals(CardColor.RED)) {
      this.redHint = enabled;
    } else {
      this.blueHint = enabled;
    }

  }

  @Override
  public BattleRule getBattleRule() {
    return this.battleRule;
  }


}
