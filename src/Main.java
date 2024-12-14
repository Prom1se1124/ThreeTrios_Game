import cs3500.threetrios.controller.CommandLineHandler;
import cs3500.threetrios.controller.GameController;
import cs3500.threetrios.controller.GameCoordinator;
import cs3500.threetrios.model.hw5.AIPlayer;
import cs3500.threetrios.model.hw5.BoardConfigReader;
import cs3500.threetrios.model.hw5.CardColor;
import cs3500.threetrios.model.hw5.CornerAI;
import cs3500.threetrios.model.hw5.Grid;
import cs3500.threetrios.model.hw5.HumanPlayer;
import cs3500.threetrios.model.hw5.IGrid;
import cs3500.threetrios.model.hw5.IPlayer;
import cs3500.threetrios.model.hw5.MaxFlipAI;
import cs3500.threetrios.model.hw5.ThreeTriosModel;
import cs3500.threetrios.model.hw5.ViewModel;
import cs3500.threetrios.view.CardClickHandlerImpl;
import cs3500.threetrios.view.CellClickHandlerImpl;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.HandsPanel;
import cs3500.threetrios.view.BoardPanel;
import cs3500.threetrios.view.ProviderViewAdapter;


/**
 * Not used yet.
 */
public class Main {
  /**
   * Initialize the Game Configuration: Load the card and board configuration files, initialize
   * players, the grid, and the model.
   * <p>
   * Create the MVC Components:
   * Create the model.
   * Wrap the model in a ViewModel (if necessary).
   * Create the view.
   * Create controllers and register them with the model and view.
   * <p>
   * Pass Control to the Controller: Create a controller to manage the interactions between the
   * model and view. The controller is the main driver of the game flow after everything is set up.
   */

  public static void main(String[] args) {

    try {

      // Parse command-line arguments
      CommandLineHandler.CommandLineConfig config = CommandLineHandler.parseArgs(args);

      // Log the parsed players and rules for debugging
      System.out.println("Players: " + config.getPlayer1().getName() + " (RED), "
              + config.getPlayer2().getName() + " (BLUE)");
      System.out.println("Using BattleRule: " + config.getBattleRule().getClass().getSimpleName());


      // File paths for configuration files
      String cardFilePath = "threeTrios/largeCardConfig.txt";
      String boardFilePath = "threeTrios/largeBoardConfig.txt";


      // Create the grid using BoardConfigReader
      IGrid grid = new Grid();
      BoardConfigReader boardReader = new BoardConfigReader(boardFilePath);
      int[][] gridConfiguration = boardReader.convertFile();
      grid.initialize(gridConfiguration);


      // Create the model with the specified battle rule
      ThreeTriosModel model = new ThreeTriosModel(config.getBattleRule());


      IPlayer[] players = {config.getPlayer1(), config.getPlayer2()};

      model.initializeGame(grid, players, cardFilePath, boardFilePath);

      // Put the model in a read-only view model
      ViewModel viewModel = new ViewModel(model);

      // Create views for each player
      GameView redView = new GameView(viewModel, "red");
      GameView blueView = new GameView(viewModel, "blue");
      redView.setVisible(true);
      blueView.setVisible(true);

      // Add card click handlers for the hands panels for red view
      HandsPanel redHandPanel = redView.getRedHandPanel();
      HandsPanel blueHandPanelRedView = redView.getBlueHandPanel();
      redHandPanel.addCardClickHandler(new CardClickHandlerImpl(redHandPanel));
      blueHandPanelRedView.addCardClickHandler(new CardClickHandlerImpl(blueHandPanelRedView));

      // Add cell click handler for the board panel in red view
      BoardPanel redBoardPanel = redView.getBoardPanel();
      redBoardPanel.addCellClickHandler(new CellClickHandlerImpl(redBoardPanel));

      // Add card click handlers for the hands panels for blue view
      HandsPanel bluesRedHandPanel = blueView.getRedHandPanel();
      HandsPanel blueHandPanel = blueView.getBlueHandPanel();
      bluesRedHandPanel.addCardClickHandler(new CardClickHandlerImpl(bluesRedHandPanel));
      blueHandPanel.addCardClickHandler(new CardClickHandlerImpl(blueHandPanel));

      // Add cell click handler for the board panel in blue view
      BoardPanel blueBoardPanel = blueView.getBoardPanel();
      blueBoardPanel.addCellClickHandler(new CellClickHandlerImpl(blueBoardPanel));

      // Create controllers for each player
      GameController redController = new GameController(model, redView, config.getPlayer1(),
              players, model.getHandSize());
      GameController blueController = new GameController(model, blueView, config.getPlayer2(),
              players, model.getHandSize());

      // Create the centralized GameCoordinator
      GameCoordinator coordinator = new GameCoordinator(redController, blueController);

      // Register controllers as listeners for their respective views
      redView.getViewFeature().addListener(redController);
      blueView.getViewFeature().addListener(blueController);

      // Register model features as listeners to controllers
      redController.getModelFeature().addListener(redController);
      blueController.getModelFeature().addListener(blueController);

      model.getModelFeature().setCoordinator(coordinator);

      // Start the game for each player
      redController.playGame(cardFilePath, boardFilePath, model, grid);
      blueController.playGame(cardFilePath, boardFilePath, model, grid);

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }


//    //for hw8(adapting provider view)
//    // File paths for configuration files
//    String cardFilePath = "threeTrios/largeCardConfig.txt";
//    String boardFilePath = "threeTrios/validBoardConfig.txt";
//
//    // Create players
//    IPlayer redPlayer = null;
//    IPlayer bluePlayer = null;
//
//
//    // Create the grid using BoardConfigReader
//    IGrid grid = new Grid();
//    BoardConfigReader boardReader = new BoardConfigReader(boardFilePath);
//    int[][] gridConfiguration = boardReader.convertFile();
//    grid.initialize(gridConfiguration);
//
//    // Create the model
//    ThreeTriosModel model = new ThreeTriosModel();
//
//    if (args.length != 2) {
//      throw new IllegalArgumentException("Invalid input type");
//    }
//    if (args[0].equalsIgnoreCase("human")) {
//      redPlayer = new HumanPlayer("Human1", CardColor.RED);
//    } else if (args[0].equalsIgnoreCase("strategy1")) {
//      redPlayer = new AIPlayer("AI1", CardColor.RED, new MaxFlipAI(), model);
//    } else if (args[0].equalsIgnoreCase("strategy2")) {
//      redPlayer = new AIPlayer("AI1", CardColor.RED, new CornerAI(), model);
//    } else {
//      throw new IllegalArgumentException("Invalid player type for player 1.");
//    }
//
//    if (args[1].equalsIgnoreCase("human")) {
//      bluePlayer = new HumanPlayer("Human2", CardColor.BLUE);
//    } else if (args[1].equalsIgnoreCase("strategy1")) {
//      bluePlayer = new AIPlayer("AI2", CardColor.BLUE, new MaxFlipAI(), model);
//    } else if (args[1].equalsIgnoreCase("strategy2")) {
//      bluePlayer = new AIPlayer("AI2", CardColor.BLUE, new CornerAI(), model);
//    } else {
//      throw new IllegalArgumentException("Invalid player type for player 2.");
//    }
//
//    IPlayer[] players = {redPlayer, bluePlayer};
//
//    model.initializeGame(grid, players, cardFilePath, boardFilePath);
//
//    // Put the model in a read-only view model
//    ViewModel viewModel = new ViewModel(model);
//
//    // Create view for Player 1 (our view)
//    GameView redView = new GameView(viewModel, "red");
//    redView.setVisible(true);
//
//    // Create view for Player 2 (using provider's view wrapped in our adapter)
//    ProviderViewAdapter blueView = new ProviderViewAdapter(viewModel, "blue");
//    blueView.setVisible(true);
//
//    //Moved the refresh() in GameViw constructor to here
//    redView.refresh();
//    blueView.refresh();
//
//    // Add card click handlers for the hands panels for red view(only needed for our view)
//    HandsPanel redHandPanel = redView.getRedHandPanel();
//    HandsPanel blueHandPanelRedView = redView.getBlueHandPanel();
//    redHandPanel.addCardClickHandler(new CardClickHandlerImpl(redHandPanel));
//    blueHandPanelRedView.addCardClickHandler(new CardClickHandlerImpl(blueHandPanelRedView));
//
//    // Add cell click handler for the board panel in red view
//    BoardPanel redBoardPanel = redView.getBoardPanel();
//    redBoardPanel.addCellClickHandler(new CellClickHandlerImpl(redBoardPanel));
//
//
//    // Create controllers for each player
//    GameController redController = new GameController(model, redView, redPlayer,
//            players, model.getHandSize());
//    GameController blueController = new GameController(model, blueView, bluePlayer,
//            players, model.getHandSize());
//
//
//    // Set up the controller for the provider's view
//    blueView.setController(blueController);
//
//    // Create the centralized GameCoordinator
//    GameCoordinator coordinator = new GameCoordinator(redController, blueController);
//
//    // Register controllers as listeners for their respective views
//    redView.getViewFeature().addListener(redController);
//
//
//    // Register model features as listeners to controllers
//    redController.getModelFeature().addListener(redController);
//    blueController.getModelFeature().addListener(blueController);
//
//    model.getModelFeature().setCoordinator(coordinator);
//
//    // Start the game for each player
//    redController.playGame(cardFilePath, boardFilePath, model, grid);
//    blueController.playGame(cardFilePath, boardFilePath, model, grid);

  }


}