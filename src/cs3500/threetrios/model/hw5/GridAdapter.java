package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.Grid;


public class GridAdapter implements Grid {
  private final int[][] board;
  private final ICard[][] boardState;

  public GridAdapter(ReadOnlyTriosModel model) {
    this.board = model.getBoard();
    this.boardState = model.getBoardState();
  }

  @Override
  public List<List<Cell>> getCards() {
    List<List<Cell>> theirGrid = new ArrayList<>();

    for (int i = 0; i < board.length; i++) {
      List<Cell> row = new ArrayList<>();
      for (int j = 0; j < board[0].length; j++) {
        if (board[i][j] == -1) {
          // This is a hole
          row.add(new HoleCellAdapter());
        } else {
          // This is a card cell (either empty or with card)
          row.add(new CardCellAdapter(boardState[i][j]));
        }
      }
      theirGrid.add(row);
    }
    return theirGrid;
  }
}
