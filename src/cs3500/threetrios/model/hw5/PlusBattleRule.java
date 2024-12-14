package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlusBattleRule extends BattleRuleDecorator {

  public PlusBattleRule(BattleRule wrappedRule) {
    super(wrappedRule);
  }

  @Override
  public boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction) {
    // For direct card-to-card battles, we defer to the wrapped rule.
    return super.determineBattle(attackingCard, defendingCard, direction);
  }


  /**
   * Apply the "Plus" rule. This should be called after placing a card and before the combo step.
   *
   * @param placedCard the card just placed by the current player.
   * @param board      the 2D array of cards representing the game board.
   * @param gridConfig the configuration of the grid.
   * @param x          the x-position (row) of the placed card.
   * @param y          the y-position (column) of the placed card.
   */
  public void applyPlusRule(ICard placedCard, ICard[][] board, int[][] gridConfig, int x, int y) {
    List<SumInfo> sums = gatherSums(placedCard, board, gridConfig, x, y);

    // Group cards by their sum
    Map<Integer, List<ICard>> sumToCardsMap = new HashMap<>();
    for (SumInfo si : sums) {
      sumToCardsMap.computeIfAbsent(si.sum, k -> new ArrayList<>()).add(si.opponentCard);
    }

    // Check if any sum appears twice or more
    for (Map.Entry<Integer, List<ICard>> entry : sumToCardsMap.entrySet()) {
      if (entry.getValue().size() > 1) {
        // We have at least two cards sharing this sum
        // Flip all opponent-owned cards that contributed to this sum
        for (ICard c : entry.getValue()) {
          if (c.getColor() != placedCard.getColor()) {
            c.flip();
          }
        }
      }
    }
  }

  /**
   * Helper method to gather the sums for each adjacent card.
   * It returns a list of SumInfo objects that store the sum and the opponent card.
   */
  private List<SumInfo> gatherSums(ICard placedCard, ICard[][] board,
                                   int[][] config, int x, int y) {
    List<SumInfo> result = new ArrayList<>();

    int rows = config.length;
    int cols = config[0].length;

    // North
    if (x - 1 >= 0 && config[x - 1][y] != -1 && board[x - 1][y] != null) {
      ICard northCard = board[x - 1][y];
      int sum = CardUtils.getValueInDirection(placedCard, Direction.NORTH)
              + CardUtils.getValueInOppositeDirection(northCard, Direction.NORTH);
      result.add(new SumInfo(sum, northCard));
    }

    // South
    if (x + 1 < rows && config[x + 1][y] != -1 && board[x + 1][y] != null) {
      ICard southCard = board[x + 1][y];
      int sum = CardUtils.getValueInDirection(placedCard, Direction.SOUTH)
              + CardUtils.getValueInOppositeDirection(southCard, Direction.SOUTH);
      result.add(new SumInfo(sum, southCard));
    }

    // East
    if (y + 1 < cols && config[x][y + 1] != -1 && board[x][y + 1] != null) {
      ICard eastCard = board[x][y + 1];
      int sum = CardUtils.getValueInDirection(placedCard, Direction.EAST)
              + CardUtils.getValueInOppositeDirection(eastCard, Direction.EAST);
      result.add(new SumInfo(sum, eastCard));
    }

    // West
    if (y - 1 >= 0 && config[x][y - 1] != -1 && board[x][y - 1] != null) {
      ICard westCard = board[x][y - 1];
      int sum = CardUtils.getValueInDirection(placedCard, Direction.WEST)
              + CardUtils.getValueInOppositeDirection(westCard, Direction.WEST);
      result.add(new SumInfo(sum, westCard));
    }

    return result;
  }

  /**
   * Private class to hold sum information between card just placed and an adjacent card.
   */
  private static class SumInfo {
    final int sum;
    final ICard opponentCard;

    SumInfo(int sum, ICard opponentCard) {
      this.sum = sum;
      this.opponentCard = opponentCard;
    }
  }

}
