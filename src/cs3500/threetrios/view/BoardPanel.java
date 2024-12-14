package cs3500.threetrios.view;


import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.JPanel;

import cs3500.threetrios.model.hw5.CardColor;
import cs3500.threetrios.model.hw5.ICard;


/**
 * Implementation of the game board panel.
 */
public class BoardPanel extends JPanel implements IBoardPanel {


  private int[][] gridConfiguration;  // Grid configuration (-1 for holes, 0 for playable cells)
  private ICard[][] cardsOnBoard;
  private final ViewFeature viewFeature;

  public BoardPanel(ViewFeature viewFeature) {
    this.viewFeature = viewFeature;
    this.setPreferredSize(new Dimension(400, 400)); // Set size of the board
    this.setBackground(Color.WHITE);
  }


  @Override
  public void displayBoard(int[][] gridConfiguration, ICard[][] cardsOnBoard) {
    this.gridConfiguration = gridConfiguration;
    this.cardsOnBoard = cardsOnBoard;
    repaint();
  }


  @Override
  public void highlightCell(int x, int y) {
    // Highlighting logic if needed, but seems like not needed right now??
    // For now, print the coordinates of the highlighted cell
    System.out.println("Highlighting cell at (" + x + ", " + y + ")");
    repaint();
  }

  @Override
  public void addCellClickHandler(CellClickHandler handler) {
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        int cellSize = getWidth() / cardsOnBoard.length;
        int row = e.getY() / cellSize;
        int col = e.getX() / cellSize;
        handler.handleCellClick(row, col);
        viewFeature.selectGridCell(row, col);
      }
    });
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (gridConfiguration != null && cardsOnBoard != null) {
      int rows = gridConfiguration.length;
      int cols = gridConfiguration[0].length;
      int cellWidth = getWidth() / cols;
      int cellHeight = getHeight() / rows;

      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          paintCellBackground(g, row, col, cellWidth, cellHeight);
          drawCardValues(g, row, col, cellWidth, cellHeight);
          drawCellBorder(g, row, col, cellWidth, cellHeight);
        }
      }
    }
  }

  private void paintCellBackground(Graphics g, int row, int col, int cellWidth, int cellHeight) {
    if (gridConfiguration[row][col] == -1) {
      // hole
      g.setColor(Color.GRAY);
    } else {
      if (cardsOnBoard[row][col] == null) {
        // Empty cell
        g.setColor(Color.YELLOW);
      } else {
        // Cell occupied by a card
        ICard card = cardsOnBoard[row][col];
        g.setColor(card.getColor() == CardColor.RED ? Color.RED : Color.BLUE);
      }
    }
    g.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
  }

  private void drawCardValues(Graphics g, int row, int col, int cellWidth, int cellHeight) {
    if (cardsOnBoard[row][col] != null) {
      ICard card = cardsOnBoard[row][col];
      g.setColor(Color.BLACK);
      FontMetrics fm = g.getFontMetrics();
      int textX = col * cellWidth + cellWidth / 2;
      int textY = row * cellHeight + cellHeight / 2;

      // Draw card values for North, South, East, and West
      g.drawString(String.valueOf(card.getNorthValue()),
              textX - fm.stringWidth(String.valueOf(card.getNorthValue())) / 2,
              row * cellHeight + fm.getAscent());

      g.drawString(String.valueOf(card.getSouthValue()),
              textX - fm.stringWidth(String.valueOf(card.getSouthValue())) / 2,
              (row + 1) * cellHeight - fm.getDescent());

      g.drawString(String.valueOf(card.getEastValue()),
              (col + 1) * cellWidth - fm.stringWidth(String.valueOf(card.getEastValue())),
              textY);

      g.drawString(String.valueOf(card.getWestValue()),
              col * cellWidth,
              textY);
    }
  }

  private void drawCellBorder(Graphics g, int row, int col, int cellWidth, int cellHeight) {
    g.setColor(Color.BLACK);
    ((Graphics2D) g).setStroke(new BasicStroke(1));
    g.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
  }

  @Override
  public ViewFeature getViewFeature() {
    return this.viewFeature;
  }
}
