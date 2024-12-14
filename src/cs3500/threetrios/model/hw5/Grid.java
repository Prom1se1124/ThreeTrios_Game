package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;


/**
 * The Grid class represents the playing field for the Three Trios game.
 * Note on coordinates: In this class, the x-coordinate represents the row (up down),
 * while the y-coordinate represents the column (left right).
 */
public class Grid implements IGrid {


  private int[][] gridConfiguration; // The grid configuration: -1 for holes, 0 for empty card cells
  private ICard[][] cardsOnGrid; // The grid holding cards, null if a cell is empty
  private int flippedCardsCount;
  private BattleRule battleRule;

  public Grid() {
    // Empty constructor since grid is initialized using initialize() method
    this.battleRule = new DefaultBattleRule(); // Default battle rule
  }


  /**
   * The Grid class represents the playing field for the Three Trios game.
   * Note on coordinates: In this class, the x-coordinate represents the row (up down),
   * while the y-coordinate represents the column (left right).
   */
  public Grid(BattleRule battleRule) {
    if (battleRule == null) {
      throw new IllegalArgumentException("Battle rule cannot be null.");
    }
    this.battleRule = battleRule;
  }

  /**
   * This is the deep, copy constructor.
   */
  public Grid(Grid other) {
    if (other == null) {
      throw new IllegalArgumentException("Grid to be copied cannot be null.");
    }

    int rows = other.gridConfiguration.length;
    int cols = other.gridConfiguration[0].length;

    // Deep copy the grid configuration
    this.gridConfiguration = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(other.gridConfiguration[i], 0, this.gridConfiguration[i],
              0, cols);
    }

    // Deep copy the cards on the grid
    this.cardsOnGrid = new ICard[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (other.cardsOnGrid[i][j] != null) {
          // Assuming ICard has a clone() method or copy constructor to deep copy the card
          this.cardsOnGrid[i][j] = other.cardsOnGrid[i][j].clone();
        } else {
          this.cardsOnGrid[i][j] = null;
        }
      }
    }
    this.battleRule = other.battleRule;
  }

  @Override
  public void initialize(int[][] configuration) throws IllegalArgumentException {
    if (configuration == null) {
      throw new IllegalArgumentException("Configuration cannot be null.");
    }

    if (configuration.length == 0 || configuration[0].length == 0) {
      throw new IllegalArgumentException("Configuration must have at least one row/column.");
    }

    int rows = configuration.length;
    int cols = configuration[0].length;


    // Validate that all rows have the same length (rectangular grid)
    for (int[] row : configuration) {
      if (row.length != cols) {
        throw new IllegalArgumentException(
                "All rows in the configuration must have the same length.");
      }
    }

    // Initialize the internal grid configuration with the given configuration
    this.gridConfiguration = new int[rows][cols];
    this.cardsOnGrid = new ICard[rows][cols]; // Initialize card grid to match

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // Copy configuration values to gridConfiguration
        this.gridConfiguration[i][j] = configuration[i][j];
        // Initialize the cardsOnGrid to null (no cards placed initially)
        this.cardsOnGrid[i][j] = null;
      }
    }
  }

  @Override
  public boolean placeCard(ICard card, int x, int y) throws
          IllegalArgumentException, IllegalStateException {

    if (card == null) {
      throw new IllegalArgumentException("Card can not be null.");
    }

    if (x < 0 || x >= gridConfiguration.length || y < 0 || y >= gridConfiguration[0].length) {
      throw new IllegalArgumentException("Selected coordinates not in bound.");
    }

    if (gridConfiguration[x][y] == -1) {
      throw new IllegalStateException("Can not place a card in a hole.");
    }

    if (cardsOnGrid[x][y] != null) {
      throw new IllegalStateException(
              "This cell is already occupied by a card at coordinates: (" + x + ", " + y + "). " +
                      "Existing card: " + cardsOnGrid[x][y].toString() +
                      ". Card attempted to be placed: " + card.toString());
    }


    // Place the card on the cardsOnGrid, not on gridConfiguration
    cardsOnGrid[x][y] = card;
    flippedCardsCount = 0;

    // Before initiating the normal battles, apply the Level 2 variant rules if they exist
    if (battleRule instanceof SameBattleRule) {
      ((SameBattleRule) battleRule).applySameRule(card, cardsOnGrid, gridConfiguration, x, y);
    } else if (battleRule instanceof PlusBattleRule) {
      ((PlusBattleRule) battleRule).applyPlusRule(card, cardsOnGrid, gridConfiguration, x, y);
    }

    // Initiate battle with adjacent cards using a helper method
    processBattles(card, x, y);

    return true;
  }

  private void processBattles(ICard card, int x, int y) {
    // Get the adjacent cards
    ICard[] adjacentCards = getAdjacentCards(x, y);

    for (ICard opponentCard : adjacentCards) {
      // Use the helper method to determine opponent card coordinates
      int[] coordinates = getCoordinatesOfAdjacentCard(x, y, opponentCard);
      int opponentX = coordinates[0];
      int opponentY = coordinates[1];

      // If valid coordinates are found, determine the direction and perform battle
      if (opponentX != -1 && opponentY != -1) {
        Direction direction = getDirectionOfAdjacentCard(x, y, opponentX, opponentY);

        // Only flip if the battle is won against the opponent card
        if (opponentCard.getColor() != card.getColor() &&
                battleRule.determineBattle(card, opponentCard, direction)) {

          // Flip the opponent card if battle is won
          System.out.println("Flipping card at (" + opponentX + ", " + opponentY + ") from " +
                  opponentCard.getColor() + " to " + card.getColor());

          opponentCard.flip();

          // Increment the flipped cards counter
          flippedCardsCount++;

          // Recursively process battles for the flipped card (chain reaction)
          processBattles(opponentCard, opponentX, opponentY);
        }
      }
    }
  }


  private int[] getCoordinatesOfAdjacentCard(int x, int y, ICard opponentCard) {
    if (x - 1 >= 0 && cardsOnGrid[x - 1][y] == opponentCard) {
      return new int[]{x - 1, y}; // North
    } else if (x + 1 < gridConfiguration.length && cardsOnGrid[x + 1][y] == opponentCard) {
      return new int[]{x + 1, y}; // South
    } else if (y - 1 >= 0 && cardsOnGrid[x][y - 1] == opponentCard) {
      return new int[]{x, y - 1}; // West
    } else if (y + 1 < gridConfiguration[0].length && cardsOnGrid[x][y + 1] == opponentCard) {
      return new int[]{x, y + 1}; // East
    }
    return new int[]{-1, -1}; // Invalid coordinates
  }


  @Override
  public boolean isValidPosition(int x, int y) {
    // Check if the coordinates are within bounds
    if (x < 0 || x >= gridConfiguration.length || y < 0 || y >= gridConfiguration[0].length) {
      return false; // Out of grid bounds
    }

    // Check if the cell is a hole
    if (gridConfiguration[x][y] == -1) {
      return false; // It's a hole, so cannot place a card here
    }

    // Check if the cell is already occupied by a card
    return cardsOnGrid[x][y] == null; // The cell is occupied

    // All checks passed, it's a valid position
  }

  @Override
  public ICard[] getAdjacentCards(int x, int y) throws IllegalArgumentException {

    if (x < 0 || x >= gridConfiguration.length || y < 0 || y >= gridConfiguration[0].length) {
      throw new IllegalArgumentException("Selected coordinates are out of bounds.");
    }

    List<ICard> adjacentCards = new ArrayList<>();

    //x and y here are flipped from normal math xy position(x is up down, y is left right)

    // Check North side
    if (x - 1 >= 0 && gridConfiguration[x - 1][y] != -1 && cardsOnGrid[x - 1][y] != null) {
      adjacentCards.add(cardsOnGrid[x - 1][y]);
    }

    // Check South side
    if (x + 1 < gridConfiguration.length && gridConfiguration[x + 1][y] != -1
            && cardsOnGrid[x + 1][y] != null) {
      adjacentCards.add(cardsOnGrid[x + 1][y]);
    }

    // Check West side
    if (y - 1 >= 0 && gridConfiguration[x][y - 1] != -1 && cardsOnGrid[x][y - 1] != null) {
      adjacentCards.add(cardsOnGrid[x][y - 1]);
    }

    // Check East side
    if (y + 1 < gridConfiguration[0].length && gridConfiguration[x][y + 1] != -1
            && cardsOnGrid[x][y + 1] != null) {
      adjacentCards.add(cardsOnGrid[x][y + 1]);
    }

    // Convert list to an array and return
    return adjacentCards.toArray(new ICard[0]);
  }


  //helper method used to determine the direction of opponent cards
  private Direction getDirectionOfAdjacentCard(int x, int y, int adjacentX, int adjacentY) {
    if (adjacentX == x - 1 && adjacentY == y) {
      return Direction.NORTH;
    } else if (adjacentX == x + 1 && adjacentY == y) {
      return Direction.SOUTH;
    } else if (adjacentX == x && adjacentY == y - 1) {
      return Direction.WEST;
    } else if (adjacentX == x && adjacentY == y + 1) {
      return Direction.EAST;
    } else {
      throw new IllegalArgumentException(
              "Invalid adjacent coordinates relative to the given position.");
    }
  }

  @Override
  public List<int[]> getAvailablePositions() {
    List<int[]> availablePositions = new ArrayList<>();

    for (int x = 0; x < gridConfiguration.length; x++) {
      for (int y = 0; y < gridConfiguration[0].length; y++) {
        // Check if the cell is a card cell and is empty
        if (gridConfiguration[x][y] == 0 && cardsOnGrid[x][y] == null) {
          availablePositions.add(new int[]{x, y});
        }
      }
    }
    return availablePositions;

  }

  @Override
  public ICard getCardPosition(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= gridConfiguration.length || y < 0 || y >= gridConfiguration[0].length) {
      throw new IllegalArgumentException("Coordinates are out of bounds.");
    }
    return cardsOnGrid[x][y];
  }


  @Override
  public int[] getDimensions() {
    if (gridConfiguration == null) {
      throw new IllegalStateException("Grid has not been initialized.");
    }
    return new int[]{gridConfiguration.length, gridConfiguration[0].length};
  }


  @Override
  public String display() {
    if (gridConfiguration == null) {
      throw new IllegalStateException("Grid has not been initialized.");
    }

    StringBuilder gridRepresentation = new StringBuilder();

    int rows = gridConfiguration.length;
    int cols = gridConfiguration[0].length;

    // Loop over each cell in the grid
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (gridConfiguration[i][j] == -1) {
          // Hole in the grid
          gridRepresentation.append(" ");
        } else if (cardsOnGrid[i][j] == null) {
          // Empty card cell
          gridRepresentation.append("_");
        } else {
          // Card cell with a card, showing the color of card
          ICard card = cardsOnGrid[i][j];
          if (card.getColor() == CardColor.RED) {
            gridRepresentation.append("R");
          } else if (card.getColor() == CardColor.BLUE) {
            gridRepresentation.append("B");
          }
        }
      }
      // Move to the next line after each row
      gridRepresentation.append("\n");
    }
    return gridRepresentation.toString();

  }

  @Override
  public int[][] getBoard() {
    return gridConfiguration;
  }


  @Override
  public ICard[][] getBoardState() {
    return cardsOnGrid;
  }

  @Override
  public int getCol() {
    return gridConfiguration[0].length;
  }

  @Override
  public int getRow() {
    return gridConfiguration.length;
  }


  public int getFlippedCardsCount() {
    return flippedCardsCount;
  }

  @Override
  public int getHint(int x, int y, ICard card) {
    int numFlips = 0;

    ICard[] adjacentCards = getAdjacentCards(x, y);

    // Loop through each adjacent card
    for (ICard opponentCard : adjacentCards) {
      // Get the coordinates of the opponent card
      int[] coordinates = getCoordinatesOfAdjacentCard(x, y, opponentCard);
      int opponentX = coordinates[0];
      int opponentY = coordinates[1];

      // If valid coordinates found, determine the direction and check the battle
      if (opponentX != -1 && opponentY != -1) {
        Direction direction = getDirectionOfAdjacentCard(x, y, opponentX, opponentY);
        if (battleRule.determineBattle(card, opponentCard, direction)) {
          numFlips++;
        }
      }
    }
    return numFlips;
  }


}









