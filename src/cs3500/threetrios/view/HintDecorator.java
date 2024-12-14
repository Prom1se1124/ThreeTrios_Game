package cs3500.threetrios.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import cs3500.threetrios.model.hw5.ICard;
import cs3500.threetrios.model.hw5.ReadOnlyTriosModel;

/**
 * Decorator for BoardPanel that adds hints.
 */
public class HintDecorator extends BoardPanel {
  private final BoardPanel originalPanel;
  private final ReadOnlyTriosModel model;
  private ICard selectedCard;

  public HintDecorator(BoardPanel originalPanel, ReadOnlyTriosModel model) {
    super(originalPanel.getViewFeature());
    this.originalPanel = originalPanel;
    this.model = model;
    System.out.println("HintDecorator initialized");

    // Forward mouse listeners from original panel
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        System.out.println("HintDecorator: Mouse clicked at " + e.getX() + "," + e.getY());
        // Convert the coordinates and forward to original panel
        originalPanel.dispatchEvent(e);
      }
    });
  }


  @Override
  public void addCellClickHandler(CellClickHandler handler) {
    // Make sure both decorator and original panel get click handlers
    super.addCellClickHandler(handler);
    originalPanel.addCellClickHandler(handler);
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    System.out.println("HintDecorator painting, selectedCard: " + selectedCard +
            ", hints enabled: " + model.isHintsEnabledForCurrentPlayer());

    // Then add our hint overlay
    if (selectedCard != null && model.isHintsEnabledForCurrentPlayer()) {
      drawHintOverlay(g);
    }
  }

  private void drawHintOverlay(Graphics g) {
    int[][] grid = model.getBoard();
    ICard[][] boardState = model.getBoardState();

    int rows = grid.length;
    int cols = grid[0].length;
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        // Only show hints for empty playable cells
        if (grid[row][col] != -1 && boardState[row][col] == null) {
          int flips = model.getHint(row, col, selectedCard);
          if (flips > 0) {
            drawHintNumber(g, flips, row, col, cellWidth, cellHeight);
          }
        }
      }
    }
  }

  private void drawHintNumber(Graphics g, int flips, int row, int col,
                              int cellWidth, int cellHeight) {
    g.setColor(new Color(0, 255, 0, 128));
    g.setFont(new Font("Arial", Font.BOLD, 24));

    String hint = String.valueOf(flips);
    FontMetrics fm = g.getFontMetrics();

    int x = col * cellWidth + (cellWidth - fm.stringWidth(hint)) / 2;
    int y = row * cellHeight + (cellHeight + fm.getHeight()) / 2;

    g.drawString(hint, x, y);
  }

  @Override
  public void displayBoard(int[][] gridConfiguration, ICard[][] cardsOnBoard) {
    // Update both the original panel and hint
    originalPanel.displayBoard(gridConfiguration, cardsOnBoard);
    super.displayBoard(gridConfiguration, cardsOnBoard);
    repaint();
  }

  public void setSelectedCard(ICard card) {
    System.out.println("HintDecorator: Setting selected card to " + card);
    this.selectedCard = card;
    repaint();
  }
}