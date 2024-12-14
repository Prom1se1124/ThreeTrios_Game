# Three Trios Game

## Overview

Three Trios is the card game we are implementing for hw5-8, this is a card game that involves 2
players, who need to place cards on a grid and battle adjacent opponent's cards. This game is
exciting due to the flip rules involved, which create sudden game phase turns and dramatic endings.
The codebase is designed to implement and manage all game logic, including card placement, battles,
turn management, etc...

## Changes for hw8

Summary of Working and Non-Working Features in the Final Submission
In the final version, most features function as intended. After successfully adapting the provider's
view, the game can be played smoothly. This includes supporting legal moves, providing warnings for
illegal moves, and maintaining consistent game logic. The game correctly handles actions like card
flipping and accurately determines the winner at the end of the game.

However, the AI players are not functioning correctly. This issue has persisted since Assignment 7,
likely due to incorrect logic handling in the controller. As changes to the controller or model were
not permitted in this assignment, the problem remains unresolved in Assignment 8. Despite this, the
provider's view integration works well, allowing for a successful gameplay experience for human
players.

## Changes for costumers

We had to change addCellClickHandler() method. The current implementation assumes a square grid,
which the costumer uses 3x2 grid, this cause bugs and errors, so we had to change calculating
cellSize to
int cellWidth = getWidth() / cardsOnBoard[0].length;
int cellHeight = getHeight() / cardsOnBoard.length;

Another change we had to make is TextView class, this included small bugs. However, I think I
shouldn't even send cosutmer this class, since it's the view we used to test hw5's model. 

## Changes for part3

In Part 3 of this project, several new classes were added to improve the overall architecture,
enhance the player interaction, and make the game logic more dynamic. Below is a summary of the key
changes:

### New Classes Added

**GameController**
Acts as the bridge between the game view and the model. It controls the flow of the game, including
player interactions, turn switching, and managing AI moves. It invokes methods from both the model
and the view to keep them synchronized.

Handles player input (either from a human or an AI player) and triggers appropriate actions within
the model.

Manages the gameplay loop by notifying players when it's their turn and updating the view.

**IController**
This is the Interface for the controller in the game. It provides abstraction for the core methods
required by a game controller, such as playGame, update, and player actions (selectCard,
selectGridCell).

**GameCoordinator**
Coordinates the interactions between multiple controllers (e.g., for AI and human players).

This ensures that all game views are updated consistently by managing calls to refresh different
controllers' views.

**ModelFeature**
It acts as a "listener" interface for the model, enabling the game model to notify controllers of
important changes.

It provides the notifyListeners() method to update the controllers when a game state changes,
ensuring that all parts of the MVC framework remain in sync.

It deals endgame notifications through notifyGameEnd(), passing important messages like winner
announcements to the controller(showing the winner's name, not their color).

**ViewFeature**
Represents the interactive features from the view perspective.

Handles user interactions, such as selecting a card or clicking on a grid cell, and forwards them to
the appropriate controller.

Allows adding a listener to connect the game view with a controller, which facilitates a reactive
interface.

### Updates to Game Flow

Endgame Logic: The endgame logic has been refined, such that the game automatically detects when the
board is full. The endGame() method in the ThreeTriosModel class now communicates the final result
to the GameCoordinator, which then passes the result to the view.

### Architectural Improvements

The new classes added in Part 3 have helped to:

Enhance Modularization: By creating GameController, ModelFeature, and ViewFeature, we made an
observer pattern, each component of
the MVC pattern can now be developed and tested independently. This makes debugging easier and helps
identify issues in specific parts of the code.

Improve AI and Human Interaction: The GameCoordinator and the enhanced GameController together
provide a robust framework for managing the game’s flow, especially when handling both human and AI
players in the same game.

Separation of Concerns: ModelFeature and ViewFeature help in clearly separating the model's
responsibilities from the view's interactive functionalities, making it easier to manage updates and
events in a clean and organized manner.

## Changes for part2

### Changes for Part 2 in Models

**Deep Copy Constructors**

Adding deep copy constructors in ThreeTriosModel, Grid, and Player ensure independence of copies,
which is particularly useful for AI simulations!

**Counting number of cards flipped while flipping**

getFlippedCardsCount provides key insights for deciding the best possible move, especially for AI,
since it counts the number of opponent cards flipped with the selected card played

### New View Interfaces and Implementations

To enhance the player experience and make the game, we have added several new interfaces and view
components in part 2, focusing on a graphical representation of the game state:

1. **IGameView Interface**:
    - Defines the primary methods for interacting with the game view, such as displaying the board,
      highlighting selections, and updating the view based on user actions.

2. **GameView Class**:
    - Implements the `IGameView` interface to provide a GUI-based view for the Three Trios game.
    - Uses "JSwing` components to visually represent the game state, including the board, players'
      hands, and current player's turn.

3. **IBoardPanel and BoardPanel Class**:
    - The `IBoardPanel` interface defines methods for displaying the gameboard, handling click
      events, and highlighting(print location of)cells.
    - `BoardPanel` provides the graphical representation of the playing grid. It shows playable
      cells and holes, draws cards on the board, and prints location of selected cells when clicked.
    - Battles and card placements are visually represented by changing the color of cells, with each
      card's directional values displayed for more informed decision-making.

4. **IHandsPanel and HandsPanel Class**:
    - The `IHandsPanel` interface is responsible for managing and displaying the player's hand in
      the GUI.
    - `HandsPanel` shows each player's current cards. It can highlight cards when it isclicked, and
      deselect cards when clicked again.
    - Users can interact with the cards to choose which one to play, and the visual also shows
      card's north, south, east, and west values.

5. **Click Handlers**:
    - **CardClickHandlerImpl**: Handles mouse clicks on a player's hand to select or deselect cards.
    - **CellClickHandlerImpl**: Handles clicks on board cells for card placement(with the upcoming
      controller).

### New Strategy (AI)Player Implementations

Part 2 also introduces different types of players to enhance the strategic depth of the game. These
players are implemented using new classes, extending the base functionality of `IPlayer`:

1. **MaxFlipAI Player**:
    - This strategy player is an AI that places cards in order to maximize the number of opponent
      cards it can flip.
    - It has a method called `findMove()` which choose the optimal position based on evaluating all
      available moves and simulates potential flips.

2. **CornerAI Player**:
    - The **RandomAI** chooses corners first to place cards, which perform a more defenseive playing
      style compared to MaxFlipAI.
    - It is intended to provide different style of playing to the user, which adds verieties and
      funs when playing against an AI.

### Summary of Changes in Part 2

- Introduced new graphical interfaces (`IGameView`, `IBoardPanel`, `IHandsPanel`) to standardize
  view behaviors.
- Added a Swing-based **GameView** class for a visual game interface.
- Developed **MaxFlipAI** and **CornerAI** players for different levels of strategic gameplay.
- Added click handler implementations to handle card selection and placement.
- Enhanced game logic to support dynamic updates and refreshes of the game view, providing a
  seamless user experience.

### Notes

Due to the lack of controller, the game now is still not playable. The main method now manually
adds one card from each side(red and blue) into the view, which can be displayed in the view, but
you can't add cards, performs battles and other actions by clicking the mouse yet, because WE ARE
MISSING A CONTROLLER!!!

### Documentation of invariants

Since we did not include a documentation of invariants in part 1, we will add it here!

### 1. Valid Coordinate Ranges

- **Grid Coordinates**:
    - The `Grid` is represented as a 2D matrix, where rows and columns are indexed starting from
      zero.
    - The coordinates `(row, col)` must always be in the range defined by the grid size,
      specifically:
        - `0 <= row < gridHeight`
        - `0 <= col < gridWidth`
    - Attempting to access or place a card outside this range results in
      an `IllegalArgumentException`.

- **Non-playable Cells**:
    - Some cells are designated as non-playable ("holes") during grid initialization.
    - These cells are represented with a specific marker (`-1`) in the grid configuration, and no
      card may be placed on such cells.

### 2. Valid Card Values

- **Directional Values**:
    - Each card has four directional values: North, South, East, and West.
    - Directional values must be positive integers in a range of 1-10,
      which represent the strength of the card on that side.
    - Any card created must have directional values within this valid range to be considered
      usable.

- **Card Color**:
    - Each card is assigned a color, either `RED` or `BLUE`, representing which player owns the
      card.
    - Once a card is placed on the grid, its color may change due to battles, but only based on the
      game’s flipping rules.

## Part 1

## Key components

ThreeTriosModel (implements IModel): This model represents the main logic of the game, taking care
of the grid(map or board in other words), players, and the overall game state. It initializes the
game, takes care of placing cards (by calling the grid), while determining game conditions, also,
switching player turns.

Grid (implements IGrid): The Grid class represents the playing board used for the game. It manages
cells available for placing cards, and holes that can not be placed with cards. Grid also handles
placing cards while initiating battles between cards.

HumanPlayer (implements IPlayer): Represents a real human player in the game(we will have AI player
available soon!). Each player has a name, a color(red or blue), a hand of cards, and a score.
Players can choose cards and decide where to place them.

Card (implements ICard): Represents a card in the game. Each card has four directional values (
north, south, east, and west) and a color representing ownership. Cards can be flipped when a battle
is won or lost.

TextView: This provides a textual representation of the game, it is mainly used during the game
development stage, and the final product will be using advanced view with visual representations.

BoardConfigReader & CardConfigReader: Those are the utility classes for reading configuration files
for the grid and cards, respectively.

## Key Subcomponents

Key Subcomponents

ThreeTriosModel

1. initializeGame: Initializes the game based on the configuration files received.

2. placeCard: Places a card on the grid and initiates a chain reaction of battles (depends on card
   values), by calling gird's placeCard method mentioned below.

3. switchTurn: Switches the active player between RED and BLUE.

4. displayPlayer and displayPlayerHand: Helper methods for getting the current player and displaying
   their hand.

Grid

1. initialize: Sets up the grid with holes(if any) and card cells based on a configuration received.

2. placeCard: Places a card on the specified position on the grid and initiates battles if
   applicable.

3. processBattles: Handles the chain reaction of card flips when battles are won.

HumanPlayer

1. chooseCard: Allows a player to choose a card to be played.

2. choosePosition: Determines the coordinates on the grid where a card should be placed.

## Source Organization

### model.hw5: Contains the core game components, including classes for ThreeTriosModel, Grid, Card,

HumanPlayer, configuration readers, and related interfaces:

Card.java: Implements the logic for each card, including battling and flipping mechanics.

Grid.java: Implements the playing grid, with card placement, validation, and battles.

HumanPlayer.java: Implements the human player with hand management and scoring.

ThreeTriosModel.java: Manages the game state, players, and grid interactions.

BoardConfigReader.java & CardConfigReader.java: Read configuration files to set up the game.

### view: Contains class for displaying the game state:

TextView.java: Provides a textual representation of the game for game development.

This layout of files helps readers/developers easily locate the relevant parts of the codebase. For
example, anything related to the core game logic can be found in the model.hw5 package.

## Additional Notes

The game now is not complete, lacking a controller to link everything up, while we as developers
will provide a better view with visual representations. Further enhancement of the game, such as
new rules, new type of cards, new kind of players are not yet known by the developers. Hence,  
new coming enhancement might result in change of code logic, or adding additional
classes/interfaces. For example, factory classes are not provided now due to the game currently
receives cards and grid only through configuration files.

Configuration Files: The grid configuration uses a simple textual format (X for holes, C for empty
cells), while cards are represented by their attributes in a similar text file format.
