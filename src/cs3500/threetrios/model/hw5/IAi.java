package cs3500.threetrios.model.hw5;

/**
 * The AI player that can play based on specific logic.
 */
public interface IAi {

  /**
   * Finds the best move for the AI player based on maximizing card flips.
   *
   * @param model The game model containing the board and player state
   * @param player The current player making the move
   * @return An integer array {cardIndex, row, col} representing the best move
   */
  int[] findMove(IModel model, IPlayer player);

  /**
   * Finds the move when there are no best move.
   * @param model The game model containing the board and player state
   * @return An integer array {cardIndex, row, col} representing the best move
   */
  default int[] noValidMoves(IModel model) {
    for (int i = 0; i < model.getBoard().length; i++) {
      for (int j = 0; j < model.getBoard()[0].length; j++) {
        if (model.getBoard()[i][j] == 0) {
          return new int[]{0, i, j};
        }
      }
    }
    throw new IllegalStateException("No valid moves.");
  }
}
