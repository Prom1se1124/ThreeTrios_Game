package cs3500.threetrios.view;


import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import cs3500.threetrios.model.hw5.IPlayer;
import cs3500.threetrios.model.hw5.ReadOnlyTriosModel;

/**
 * The Game View of the Game.
 */
public class GameView extends JFrame implements IGameView {


  private final BoardPanel boardPanel;
  private final HandsPanel redHandPanel;
  private final HandsPanel blueHandPanel;
  private final JLabel currentPlayerLabel;
  private final ReadOnlyTriosModel model;
  private final ViewFeature viewFeature;


  /**
   * Constructor of the Game view built by a model.
   */
  public GameView(ReadOnlyTriosModel model, String playerPerspective) {
    super("Three Trios Game");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;
    this.viewFeature = new ViewFeature(this);

    // Set up frame
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());

    JLabel playerPerspectiveLabel = new JLabel("You are: " + playerPerspective + " Player",
            JLabel.CENTER);
    playerPerspectiveLabel.setFont(new Font("Arial", Font.BOLD, 18));
    add(playerPerspectiveLabel, BorderLayout.SOUTH);

    currentPlayerLabel = new JLabel("Current player:" + model.displayPlayer(), JLabel.CENTER);
    currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
    add(currentPlayerLabel, BorderLayout.NORTH);

    // Create and add board and hand panels
    boardPanel = new BoardPanel(viewFeature);
    redHandPanel = new HandsPanel(Color.PINK, viewFeature);
    blueHandPanel = new HandsPanel(Color.CYAN, viewFeature);
    add(redHandPanel, BorderLayout.WEST);
    add(boardPanel, BorderLayout.CENTER);
    add(blueHandPanel, BorderLayout.EAST);

    refresh();
  }


  @Override
  public void refresh() {

    System.out.println("Calling refresh()");

    setCurrentPlayer(model.getCurrentTurnPlayer().getName());

    redHandPanel.clearHand();
    blueHandPanel.clearHand();

    System.out.println("RED Player's Hand:");
    model.getRedHand().forEach(card -> System.out.println(card.toString()));


    System.out.println("BLUE Player's Hand:");
    model.getBlueHand().forEach(card -> System.out.println(card.toString()));

    redHandPanel.displayHand(model.getRedHand());
    blueHandPanel.displayHand(model.getBlueHand());

    boardPanel.displayBoard(model.getBoard(), model.getBoardState());

    repaint();

  }

  @Override
  public void highlightSelection(int x, int y) {
    boardPanel.highlightCell(x, y);
  }

  @Override
  public void addClickHandler(ViewClickHandler handler) {
    // No overall click handler for GameView currently needed.
    // BoardPanel and HandPanel will have click handlers for individual elements.
  }

  @Override
  public void setCurrentPlayer(String playerName) {
    currentPlayerLabel.setText("Current player: " + model.displayPlayer());
  }

  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public IPlayer getCurrentTurnPlayer() {
    return model.getCurrentTurnPlayer();
  }

  @Override
  public ViewFeature getViewFeature() {
    return this.viewFeature;
  }

  public HandsPanel getRedHandPanel() {
    return redHandPanel;
  }

  public HandsPanel getBlueHandPanel() {
    return blueHandPanel;
  }

  public BoardPanel getBoardPanel() {
    return boardPanel;
  }

  public void showEndMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }



//  private final BoardPanel boardPanel;
//  private final HandsPanel redHandPanel;
//  private final HandsPanel blueHandPanel;
//  private final JLabel currentPlayerLabel;
//  private final JLabel playerPerspectiveLabel;
//  private final ReadOnlyTriosModel model;
//  private final ViewFeature viewFeature;
//  private final HintDecorator hintDecorator;
//
//
//  /**
//   * Constructor of the Game view built by a model.
//   */
//  public GameView(ReadOnlyTriosModel model, String playerPerspective) {
//    super("Three Trios Game");
//    if (model == null) {
//      throw new IllegalArgumentException("Model cannot be null");
//    }
//
//    this.model = model;
//    this.viewFeature = new ViewFeature(this);
//
//    // Set up frame
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    setSize(800, 600);
//    setLayout(new BorderLayout());
//
//    playerPerspectiveLabel = new JLabel("You are: " + playerPerspective + " Player",
//            JLabel.CENTER);
//    playerPerspectiveLabel.setFont(new Font("Arial", Font.BOLD, 18));
//    add(playerPerspectiveLabel, BorderLayout.SOUTH);
//    currentPlayerLabel = new JLabel("Current player:" + model.displayPlayer(), JLabel.CENTER);
//    currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
//    add(currentPlayerLabel, BorderLayout.NORTH);
//
//    // Create board panel, wrap it with decorator
//    this.boardPanel = new BoardPanel(viewFeature);
//    this.hintDecorator = new HintDecorator(boardPanel, model);
//
//    // Create and add board and hand panels
//    //boardPanel = new BoardPanel(viewFeature);
//
//    redHandPanel = new HandsPanel(Color.PINK, viewFeature);
//    blueHandPanel = new HandsPanel(Color.CYAN, viewFeature);
//    add(redHandPanel, BorderLayout.WEST);
//    add(hintDecorator, BorderLayout.CENTER);  // Add decorator instead of boardPanel
//    //add(boardPanel, BorderLayout.CENTER);
//    add(blueHandPanel, BorderLayout.EAST);
//
//    // Add menu for hint toggle
//    JMenuBar menuBar = new JMenuBar();
//    JMenu optionsMenu = new JMenu("Options");
//    JCheckBoxMenuItem hintsMenuItem = new JCheckBoxMenuItem("Show Hints");
//    hintsMenuItem.addActionListener(e -> {
//      if (viewFeature.listener != null) {
//        viewFeature.listener.setHintsEnabled(hintsMenuItem.isSelected());
//        hintDecorator.repaint();
//      }
//    });
//    optionsMenu.add(hintsMenuItem);
//    menuBar.add(optionsMenu);
//    setJMenuBar(menuBar);
//  }
//
//
//  @Override
//  public void refresh() {
//
//    System.out.println("Calling refresh()");
//
//    setCurrentPlayer(model.getCurrentTurnPlayer().getName());
//
//
//    redHandPanel.clearHand();
//    blueHandPanel.clearHand();
//
//    System.out.println("RED Player's Hand:");
//    model.getRedHand().forEach(card -> System.out.println(card.toString()));
//
//
//    System.out.println("BLUE Player's Hand:");
//    model.getBlueHand().forEach(card -> System.out.println(card.toString()));
//
//    redHandPanel.displayHand(model.getRedHand());
//    blueHandPanel.displayHand(model.getBlueHand());
//
//    // Both the original board and decorator need to be updated
//    boardPanel.displayBoard(model.getBoard(), model.getBoardState());
//    hintDecorator.displayBoard(model.getBoard(), model.getBoardState());
//
//    repaint();
//
//  }
//
//  @Override
//  public void highlightSelection(int x, int y) {
//    boardPanel.highlightCell(x, y);
//  }
//
//  @Override
//  public void addClickHandler(ViewClickHandler handler) {
//    // No overall click handler for GameView currently needed.
//    // BoardPanel and HandPanel will have click handlers for individual elements.
//  }
//
//  @Override
//  public void setCurrentPlayer(String playerName) {
//    currentPlayerLabel.setText("Current player: " + model.displayPlayer());
//  }
//
//  @Override
//  public void showErrorMessage(String message) {
//    JOptionPane.showMessageDialog(this, message, "Error",
//            JOptionPane.ERROR_MESSAGE);
//  }
//
//  @Override
//  public IPlayer getCurrentTurnPlayer() {
//    return model.getCurrentTurnPlayer();
//  }
//
//  @Override
//  public ViewFeature getViewFeature() {
//    return this.viewFeature;
//  }
//
//  @Override
//  public HandsPanel getRedHandPanel() {
//    return redHandPanel;
//  }
//
//  @Override
//  public HandsPanel getBlueHandPanel() {
//    return blueHandPanel;
//  }
//
//  @Override
//  public BoardPanel getBoardPanel() {
//    return boardPanel;
//  }
//
//  @Override
//  public void showEndMessage(String message) {
//    JOptionPane.showMessageDialog(this, message, "Game Over",
//            JOptionPane.INFORMATION_MESSAGE);
//  }
//
//
//  public HintDecorator getHintDecorator() {
//    return hintDecorator;
//  }
}
