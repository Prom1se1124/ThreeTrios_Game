package cs3500.threetrios.model.hw5;

/**
 * The AI based on the logic that  cards in corners only expose
 * two of their attack values instead of all 4, making them harder to flip.
 * Then consider which card is hardest to flip in that corner.
 */
public class CornerAI implements IAi {

  @Override
  public int[] findMove(IModel model, IPlayer player) {


    if (isCornerAvailable(model, 0, 0)) {
      return findBestCardForCorner(model, player, "SE", 0, 0);
    } else if (isCornerAvailable(model, 0, model.getCol() - 1)) {
      return findBestCardForCorner(model, player, "SW", 0, model.getCol() - 1);
    } else if (isCornerAvailable(model, model.getRow() - 1, 0)) {
      return findBestCardForCorner(model, player, "NE", model.getRow() - 1, 0);
    } else if (isCornerAvailable(model, model.getRow() - 1, model.getCol() - 1)) {
      return findBestCardForCorner(model, player, "NW", model.getRow() - 1,
              model.getCol() - 1);
    }

    return noValidMoves(model);
  }

  private boolean isCornerAvailable(IModel model, int row, int col) {
    return model.getBoard()[row][col] == 0 && model.getBoardState()[row][col] == null;
  }

  private int[] findBestCardForCorner(IModel model, IPlayer player, String direction,
                                      int row, int col) {
    int maxSum = 0;
    int bestCard = -1;

    for (int i = 0; i < model.getHand(player).size(); i++) {
      ICard currentCard = model.getHand(player).get(i);
      int currentSum = calculateCornerValue(currentCard, direction);

      if (currentSum > maxSum) {
        maxSum = currentSum;
        bestCard = i + 1; // Cards are 1-indexed
      }
    }

    if (bestCard != -1) {
      return new int[]{bestCard, row, col};
    }

    // If no valid card is found, return no valid moves
    return noValidMoves(model);
  }

  private int calculateCornerValue(ICard card, String direction) {
    switch (direction) {
      case "SE":
        return card.getSouthValue() + card.getEastValue();
      case "SW":
        return card.getSouthValue() + card.getWestValue();
      case "NE":
        return card.getNorthValue() + card.getEastValue();
      case "NW":
        return card.getNorthValue() + card.getWestValue();
      default:
        return 0;
    }
  }
}
