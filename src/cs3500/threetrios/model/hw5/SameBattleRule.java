package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;

/**
 * The SameBattleRule class applies the "Same" rule:
 * <p>
 * A "match pair" occurs if both values in the pair are identical.
 */
public class SameBattleRule extends BattleRuleDecorator {

  public SameBattleRule(BattleRule wrappedRule) {
    super(wrappedRule);
  }

  @Override
  public boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction) {
    // For direct card-to-card battles, just delegate to the wrapped rule
    return super.determineBattle(attackingCard, defendingCard, direction);
  }

  /**
   * Apply the "Same" rule. This should be called after a card is placed and before combo steps.
   *
   * @param placedCard the card just placed (current player's card)
   * @param board      the 2D array of cards representing the board
   * @param gridConfig the grid configuration array.
   * @param x          the x-position (row) of the placed card
   * @param y          the y-position (column) of the placed card
   */
  public void applySameRule(ICard placedCard, ICard[][] board, int[][] gridConfig, int x, int y) {
    // Get all adjacent cards and form pairs
    List<MatchPairInfo> pairs = gatherPairs(placedCard, board, gridConfig, x, y);

    // Count how many pairs are "match pairs"
    int matchPairCount = 0;
    for (MatchPairInfo mpi : pairs) {
      if (mpi.isMatchPair()) {
        matchPairCount++;
      }
    }

    // If two or more match pairs, flip all opponent cards in those match pairs
    if (matchPairCount >= 2) {
      for (MatchPairInfo mpi : pairs) {
        if (mpi.isMatchPair() && mpi.opponentCard.getColor() != placedCard.getColor()) {
          mpi.opponentCard.flip();
        }
      }
    }
  }

  /**
   * Helper method that gathers the pairs formed by the placed card and each adjacent card.
   */
  private List<MatchPairInfo> gatherPairs(ICard placedCard, ICard[][] board,
                                          int[][] config, int x, int y) {
    List<MatchPairInfo> result = new ArrayList<>();

    int rows = config.length;
    int cols = config[0].length;

    // Check each of the four directions: N, S, E, W
    // North
    if (x - 1 >= 0 && config[x - 1][y] != -1 && board[x - 1][y] != null) {
      ICard northCard = board[x - 1][y];
      int aValue = CardUtils.getValueInDirection(placedCard, Direction.NORTH);
      int oppValue = CardUtils.getValueInOppositeDirection(northCard, Direction.NORTH);
      result.add(new MatchPairInfo(aValue, oppValue, northCard));
    }

    // South
    if (x + 1 < rows && config[x + 1][y] != -1 && board[x + 1][y] != null) {
      ICard southCard = board[x + 1][y];
      int aValue = CardUtils.getValueInDirection(placedCard, Direction.SOUTH);
      int oppValue = CardUtils.getValueInOppositeDirection(southCard, Direction.SOUTH);
      result.add(new MatchPairInfo(aValue, oppValue, southCard));
    }

    // East
    if (y + 1 < cols && config[x][y + 1] != -1 && board[x][y + 1] != null) {
      ICard eastCard = board[x][y + 1];
      int aValue = CardUtils.getValueInDirection(placedCard, Direction.EAST);
      int oppValue = CardUtils.getValueInOppositeDirection(eastCard, Direction.EAST);
      result.add(new MatchPairInfo(aValue, oppValue, eastCard));
    }

    // West
    if (y - 1 >= 0 && config[x][y - 1] != -1 && board[x][y - 1] != null) {
      ICard westCard = board[x][y - 1];
      int aValue = CardUtils.getValueInDirection(placedCard, Direction.WEST);
      int oppValue = CardUtils.getValueInOppositeDirection(westCard, Direction.WEST);
      result.add(new MatchPairInfo(aValue, oppValue, westCard));
    }

    return result;
  }

  /**
   * Private class to hold information about a pair formed between the placed card and one
   * adjacent card.
   */
  private static class MatchPairInfo {
    final int aValue; // Value from placed card’s relevant side
    final int oppValue; // Value from adjacent card’s opposite side
    final ICard opponentCard; // The adjacent card

    MatchPairInfo(int aValue, int oppValue, ICard opponentCard) {
      this.aValue = aValue;
      this.oppValue = oppValue;
      this.opponentCard = opponentCard;
    }

    boolean isMatchPair() {
      return aValue == oppValue;
    }
  }
}
