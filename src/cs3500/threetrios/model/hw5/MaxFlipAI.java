package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;

/**
 * The AI player that can play based on Max Flip Logic.
 */
public class MaxFlipAI implements IAi {
  @Override
  public int[] findMove(IModel model, IPlayer player) {
    List<int[]> bestMoves = evaluateAllMoves(model, player);

    if (bestMoves.isEmpty()) {
      System.out.println("No valid moves found for the AI player.");
      return noValidMoves(model);
    }

    int[] bestMove = selectBestMove(bestMoves);

    return new int[]{bestMove[0], bestMove[1], bestMove[2]};
  }

  //Evaluate all possible moves and collect the best moves
  private List<int[]> evaluateAllMoves(IModel model, IPlayer player) {
    int maxCardsFlipped = 0;
    List<int[]> bestMoves = new ArrayList<>();

    for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
      // For every cell on the board
      for (int row = 0; row < model.getBoard().length; row++) {
        for (int col = 0; col < model.getBoard()[0].length; col++) {

          if (model.getBoard()[row][col] == 0 && model.getBoardState()[row][col] == null) {

            int cardsFlipped = evaluateMove(model, cardIndex, row, col);

            if (cardsFlipped > maxCardsFlipped) {
              maxCardsFlipped = cardsFlipped;
              bestMoves.clear();
              bestMoves.add(new int[]{cardIndex, row, col, cardsFlipped});
            } else if (cardsFlipped == maxCardsFlipped) {

              bestMoves.add(new int[]{cardIndex, row, col, cardsFlipped});
            }
          }
        }
      }
    }

    return bestMoves;
  }

  // Evaluate a specific move for a given card and position
  private int evaluateMove(IModel model, int cardIndex, int row, int col) {
    // Create a new model to simulate
    IModel newModel = new ThreeTriosModel((ThreeTriosModel) model);
    newModel.placeCard(cardIndex, row, col);

    // Return the number of flipped cards
    return newModel.getFlippedCardsCount();
  }

  // Select the best move based on tie-breaking rules
  private int[] selectBestMove(List<int[]> bestMoves) {
    int[] bestMove = bestMoves.get(0);

    for (int[] move : bestMoves) {
      if (isBetterMove(move, bestMove)) {
        bestMove = move;
      }
    }

    return bestMove;
  }

  // Tie-breaking rules
  private boolean isBetterMove(int[] move, int[] bestMove) {
    return (move[1] < bestMove[1]) ||
            (move[1] == bestMove[1] && move[2] < bestMove[2]) ||
            (move[1] == bestMove[1] && move[2] == bestMove[2] && move[0] < bestMove[0]);
  }


}
