package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.ReadOnlyThreeTriosGame;
import cs3500.threetrios.provider.model.Grid;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.CardColor;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.view.ViewFeatures;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * Represents the panel for drawing purposes.
 */
public class ThreeTriosPanel extends JPanel {

  private ReadOnlyThreeTriosGame model;
  private int selectedY = -1;
  private boolean redSelected = false;
  private final List<ViewFeatures> listeners = new ArrayList<>();

  /**
   * Constructs the panel.
   *
   * @param model the model
   */
  public ThreeTriosPanel(ReadOnlyThreeTriosGame model) {
    this.model = Objects.requireNonNull(model);
    this.addMouseListener(new ThreeTriosClickListener());
  }

  /**
   * displays the error message.
   * @param msg the string message to display
   */
  public void displayErrorMessage(String msg) {
    //JOptionPane.showMessageDialog(null, "Invalid Move, please try again");
    JOptionPane.showMessageDialog(null, msg);
  }


  /**
   * Paints the component.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    Grid grid = model.getGrid();
    int rows = grid.getCards().size();
    int cols = grid.getCards().get(0).size();
    int width = this.getWidth();
    int height = this.getHeight();
    Graphics2D g2d = (Graphics2D) g.create();
    Stroke defaultStroke = g2d.getStroke();
    g2d.scale(1, 1);
    List<List<Cell>> allCells = this.model.getGrid().getCards();
    int cellWidth = width / (2 + cols);
    int cellHeight = height / rows;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        this.drawGrid(g2d, allCells, i, j, cellWidth, cellHeight);
      }
    }
    List<Card> redCards = model.getRedPlayerHand().getCards();
    List<Card> blueCards = model.getBluePlayerHand().getCards();

    for (int i = 0; i < redCards.size(); i++) {
      this.paintRedCards(redCards, g2d, i, cellWidth, defaultStroke);
    }

    for (int i = 0; i < blueCards.size(); i++) {
      this.paintBlueCards(blueCards, g2d, i, cellWidth, defaultStroke);
    }
    for (ViewFeatures listener : listeners) {
      if (listener.isGameOver()) {
        //g2d.fillRect(width, height, width, height);
        g2d.setFont(new Font("Serif", Font.PLAIN, 50));
        g2d.setColor(Color.GRAY);
        g2d.drawString("Game Over", width / 2, height / 2);
        g2d.drawString(model.getWinner(), width / 2, height / 3);
      }
    }


  }

  /**
   * paints the red cards in hand.
   * @param redCards the red cards to paint
   * @param g2d the graphics object
   * @param i the index
   * @param cellWidth the width of each cell
   * @param defaultStroke the Stroke
   */
  private void paintRedCards(List<Card> redCards, Graphics2D g2d,
                             int i, int cellWidth, Stroke defaultStroke) {
    int cardHeight = this.getHeight() / redCards.size();
    g2d.setColor(Color.red);
    g2d.fillRect(0, i * cardHeight, cellWidth, cardHeight);
    Card card = redCards.get(i);
    g2d.setColor(Color.black);
    //this.paintCard(g2d, card, cellWidth, cellHeight, i);

    g2d.drawRect(0, i * cardHeight, cellWidth, cardHeight);
    String northText = card.getDirStr(Direction.NORTH);
    String southText = card.getDirStr(Direction.SOUTH);
    String eastText = card.getDirStr(Direction.EAST);
    String westText = card.getDirStr(Direction.WEST);
    g2d.drawString(northText,
            cellWidth / 2, i * cardHeight + cardHeight / 8);
    g2d.drawString(southText,
            cellWidth / 2, i * cardHeight + cardHeight * 7 / 8);
    g2d.drawString(eastText,
            cellWidth - cellWidth / 8, i * cardHeight + cardHeight / 2);
    g2d.drawString(westText,
            cellWidth / 8, i * cardHeight + cardHeight / 2);


    if (selectedY == i && redSelected) {
      g2d.setColor(Color.green);
      g2d.setStroke(new BasicStroke(cellWidth / 25));
      g2d.drawRect(0, i * cardHeight, cellWidth, cardHeight);
      g2d.setStroke(defaultStroke);
    }
  }

  /**
   * paints the blue cards in hand.
   * @param blueCards the list of blue cards
   * @param g2d the graphics object
   * @param i the index
   * @param cellWidth the cell width
   * @param defaultStroke the Stroke
   */
  private void paintBlueCards(List<Card> blueCards, Graphics2D g2d,
                              int i, int cellWidth,
                              Stroke defaultStroke) {
    int cardHeight = this.getHeight() / blueCards.size();
    g2d.setColor(Color.blue);
    g2d.fillRect(this.getWidth() - cellWidth, i * cardHeight, cellWidth, cardHeight);
    Card card = blueCards.get(i);
    g2d.setColor(Color.black);

    g2d.drawRect(this.getWidth() - cellWidth, i * cardHeight, cellWidth, cardHeight);
    String northText = card.getDirStr(Direction.NORTH);
    String southText = card.getDirStr(Direction.SOUTH);
    String eastText = card.getDirStr(Direction.EAST);
    String westText = card.getDirStr(Direction.WEST);
    g2d.drawString(northText, this.getWidth() - cellWidth / 2,
            i * cardHeight + cardHeight / 8);
    g2d.drawString(southText, this.getWidth() - cellWidth / 2,
            i * cardHeight + cardHeight * 7 / 8);
    g2d.drawString(eastText, this.getWidth() - cellWidth / 8,
            i * cardHeight + cardHeight / 2);
    g2d.drawString(westText, this.getWidth() - cellWidth * 7 / 8,
            i * cardHeight + cardHeight / 2);

    if (i == selectedY && !redSelected) {
      g2d.setColor(Color.green);
      g2d.setStroke(new BasicStroke(cellWidth / 25));
      g2d.drawRect(this.getWidth() - cellWidth, i * cardHeight, cellWidth, cardHeight);
      g2d.setStroke(defaultStroke);
    }
  }

  /**
   * paints the whole grid.
   * @param g2d the graphics object
   * @param allCells all the cells in the grid
   * @param i the row index
   * @param j the column index
   * @param cellWidth the cell width
   * @param cellHeight the cell height
   */
  private void drawGrid(Graphics2D g2d, List<List<Cell>> allCells,
                        int i, int j, int cellWidth, int cellHeight) {
    if (!allCells.get(i).get(j).isCardCell()) {
      g2d.setColor(Color.gray);
    } else {
      g2d.setColor(Color.yellow);
    }
    g2d.fillRect(cellWidth * j + cellWidth, cellHeight * i, cellWidth, cellHeight);
    g2d.setColor(Color.red);
    g2d.drawRect(cellWidth * j + cellWidth, cellHeight * i, cellWidth, cellHeight);
    if (allCells.get(i).get(j).isCardCell() && !allCells.get(i).get(j).isEmpty()) {
      //g2d.setColor(Color.orange);
      Card card = allCells.get(i).get(j).getCard();
      if (card.getColor() == CardColor.Red) {
        g2d.setColor(Color.red);
      }
      else {
        g2d.setColor(Color.blue);
      }
      g2d.fillRect(cellWidth * j + cellWidth, cellHeight * i, cellWidth, cellHeight);
      //Card card = allCells.get(i).get(j).getCard();

      g2d.setColor(Color.black);
      String northText = card.getDirStr(Direction.NORTH);
      String southText = card.getDirStr(Direction.SOUTH);
      String eastText = card.getDirStr(Direction.EAST);
      String westText = card.getDirStr(Direction.WEST);
      //System.out.println(eastText);
      g2d.drawString(northText,
              cellWidth * j + cellWidth / 2 + cellWidth,
              cellHeight * i + cellHeight / 8);
      g2d.drawString(southText,
              cellWidth * j + cellWidth / 2 + cellWidth,
              cellHeight * i + cellHeight);
      g2d.drawString(eastText,
              cellWidth * j + cellWidth - cellWidth / 8 + cellWidth,
              cellHeight * i + cellHeight / 2);
      g2d.drawString(westText,
              cellWidth * j + cellWidth + cellWidth / 8,
              cellHeight * i + cellHeight / 2);
    }
  }

  /**
   * Adds a click listener.
   *
   * @param listener the controller listener
   */

  public void addClickListener(ViewFeatures listener) {
    //empty until controller implementation
    listeners.add(listener);
  }


  /**
   * Inner class for this panel's mouse listener. Only care
   * about mouseClicked.
   */
  class ThreeTriosClickListener implements MouseListener {

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */

    @Override
    public void mouseClicked(MouseEvent e) {
      // System.err.println("Point: " + e.getX() + ", " + e.getY());
      //features.handleCellClick(e.getX(), e.getY());
      int cellWidth = getWidth() / (model.getGrid().getCards().get(0).size() + 2);
      int x = e.getX();
      int y = e.getY();

      if (x < cellWidth) {
        for (ViewFeatures listener : listeners) {
          if (listener.getPlayer().getColor() == CardColor.Red) {
            y = y / (getHeight() / model.getRedPlayerHand().getCards().size());
            System.out.println("Red Player. Index: " + y);
            model.getRedPlayerHand().selectCard(y);
            ThreeTriosPanel.this.redSelected = true;
            ThreeTriosPanel.this.selectedY = y;
          }
        }
      }

      else if (x > cellWidth * (model.getGrid().getCards().size()) + cellWidth) {
        for (ViewFeatures listener : listeners) {
          if (listener.getPlayer().getColor() == CardColor.Blue) {
            y = y / (getHeight() / model.getBluePlayerHand().getCards().size());
            System.out.println("Blue Player. Index: " + y);
            ThreeTriosPanel.this.redSelected = false;
            ThreeTriosPanel.this.selectedY = y;
          }
        }
      }

      else {
        y = y / (getHeight() / model.getGrid().getCards().size());
        x = (x / cellWidth) - 1;
        System.out.println("Coordinates: " + y + ", " + x);

        for (ViewFeatures listener : listeners) {
          listener.handleCellClick(y, x, redSelected, selectedY);
        }

        selectedY = -1;

      }

      for (ViewFeatures listener : listeners) {
        listener.refreshView();
        //ThreeTriosPanel.this.repaint();
      }
      //ThreeTriosPanel.this.repaint();
    }


    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
      // empty
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      // empty
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
      // empty
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
      // empty
    }
  }
}
