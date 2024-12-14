package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.hw5.BoardConfigReader;
import cs3500.threetrios.model.hw5.Card;
import cs3500.threetrios.model.hw5.CardColor;
import cs3500.threetrios.model.hw5.CardConfigReader;
import cs3500.threetrios.model.hw5.Direction;
import cs3500.threetrios.model.hw5.Grid;
import cs3500.threetrios.model.hw5.HumanPlayer;
import cs3500.threetrios.model.hw5.ICard;
import cs3500.threetrios.model.hw5.IPlayer;
import cs3500.threetrios.model.hw5.ThreeTriosModel;
import cs3500.threetrios.view.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The test cases of all the class.
 */
public class ModelTest {
  private String validBoardFilePath = "validBoardConfig.txt";
  private String validCardFilePath = "validCardConfig.txt";
  private Grid grid;
  private Card testCard;
  private Card card;
  private HumanPlayer player;
  private ThreeTriosModel model;
  private IPlayer[] players;

  @Before
  public void setUp() throws IOException {
    grid = new Grid();
    int[][] configuration = new int[][]{
            {0, 0, -1},
            {0, -1, 0},
            {0, 0, 0}
    };
    grid.initialize(configuration);
    testCard = new Card("TestCard", 5, 5, 5, 5, CardColor.RED);
    card = new Card("TestCard", 5, 6, 7, 8, CardColor.RED);
    player = new HumanPlayer("TestPlayer", CardColor.RED);
    model = new ThreeTriosModel((ThreeTriosModel) model);
    players = new IPlayer[] {new HumanPlayer("Player1", CardColor.RED),
        new HumanPlayer("Player2", CardColor.BLUE)};
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitializeWithNullConfig() {
    grid.initialize(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitializeWithEmptyConfig() {
    grid.initialize(new int[][]{});
  }

  @Test
  public void testPlaceCard() {
    assertTrue(grid.placeCard(testCard, 0, 0));
    assertFalse(grid.isValidPosition(0, 0)); // Now occupied
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOutOfBounds() {
    grid.placeCard(testCard, -1, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardInHole() {
    grid.placeCard(testCard, 0, 2);
  }

  @Test
  public void testIsValidPosition() {
    assertTrue(grid.isValidPosition(0, 0));
    assertFalse(grid.isValidPosition(1, 1)); // Hole
  }

  @Test
  public void testGetAdjacentCards() {
    grid.placeCard(testCard, 0, 0);
    Card adjacentCard = new Card("AdjacentCard", 5, 5, 5, 5, CardColor.BLUE);
    grid.placeCard(adjacentCard, 1, 0);
    ICard[] adjacentCards = grid.getAdjacentCards(0, 0);
    assertEquals(1, adjacentCards.length);
    assertEquals(adjacentCard, adjacentCards[0]);
  }

  @Test
  public void testGetAvailablePositions() {
    List<int[]> availablePositions = grid.getAvailablePositions();
    assertEquals(7, availablePositions.size());
  }

  @Test
  public void testBattle() {
    ICard opponentCard = new Card("OpponentCard", 4, 5, 6, 7, CardColor.BLUE);
    assertFalse(card.battle(opponentCard, Direction.NORTH));
    assertTrue(card.battle(opponentCard, Direction.SOUTH));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBattleWithNullOpponent() {
    card.battle(null, Direction.NORTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBattleWithNullDirection() {
    ICard opponentCard = new Card("OpponentCard", 4, 5, 6, 7, CardColor.BLUE);
    card.battle(opponentCard, null);
  }

  @Test
  public void testFlip() {
    card.flip();
    assertEquals(CardColor.BLUE, card.getColor());
    card.flip();
    assertEquals(CardColor.RED, card.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullColorCard() {
    Card undefinedColorCard = new Card("UndefinedCard", 5, 6, 7, 8, null);
    undefinedColorCard.flip();
  }

  @Test
  public void testGetColor() {
    assertEquals(CardColor.RED, card.getColor());
  }

  @Test
  public void testSetColor() {
    card.setColor(CardColor.BLUE);
    assertEquals(CardColor.BLUE, card.getColor());
  }

  @Test
  public void testGetNorthValue() {
    assertEquals(5, card.getNorthValue());
  }

  @Test
  public void testGetSouthValue() {
    assertEquals(6, card.getSouthValue());
  }

  @Test
  public void testGetEastValue() {
    assertEquals(7, card.getEastValue());
  }

  @Test
  public void testGetWestValue() {
    assertEquals(8, card.getWestValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullName() {
    new HumanPlayer(null, CardColor.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyName() {
    new HumanPlayer("", CardColor.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullColor() {
    new HumanPlayer("Player", null);
  }

  @Test
  public void testGetName() {
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  public void testSetHand() {
    List<ICard> hand = new ArrayList<>();
    hand.add(card); // Assuming MockCard is a valid ICard implementation
    player.setHand(hand);
    assertEquals(hand, player.getHand());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetHandWithNull() {
    player.setHand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetHandWithEmptyList() {
    player.setHand(new ArrayList<>());
  }

  @Test
  public void testChooseCard() {
    List<ICard> hand = new ArrayList<>();
    hand.add(card);
    player.setHand(hand);
    assertEquals(hand.get(0), player.chooseCard(0));
  }

  @Test(expected = IllegalStateException.class)
  public void testChooseCardWithEmptyHand() {
    player.chooseCard(0);
  }

  @Test
  public void testUpdateScore() {
    player.updateScore(10);
    assertEquals(10, player.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateScoreWithNegative() {
    player.updateScore(-5);
  }

  @Test
  public void testPlayerGetColor() {
    assertEquals(CardColor.RED, player.getColor());
  }

  @Test
  public void testBoardConvertFileValid() {
    BoardConfigReader reader = new BoardConfigReader(validBoardFilePath);
    int[][] board = reader.convertFile();

    assertEquals(3, board.length);
    assertEquals(3, board[0].length);
    assertEquals(-1, board[0][0]); // X
    assertEquals(0, board[0][1]);  // C
  }

  @Test
  public void testCardConvertFileValid() {
    CardConfigReader reader = new CardConfigReader(validCardFilePath);
    List<Card> cards = reader.convertFile(2);

    assertEquals(2, cards.size());
    assertEquals(5, cards.get(0).getNorthValue());
    assertEquals(6, cards.get(0).getSouthValue());
    assertEquals(7, cards.get(0).getEastValue());
    assertEquals(8, cards.get(0).getWestValue());
    assertEquals(6, cards.get(1).getWestValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConvertFileWithInvalidNumberFormat() throws IOException {
    String filePath = "invalidCardConfig.txt";
    FileWriter writer = new FileWriter(filePath);
    writer.write("Card1 A B C D\n");
    writer.close();

    CardConfigReader reader = new CardConfigReader(filePath);
    reader.convertFile(2);
  }

  @Test
  public void testDisplay() {
    grid.placeCard(card, 0, 0);
    assertEquals(grid.display(), "R_ \n" +
            "_ _\n" +
            "___\n");
  }

  @Test
  public void testTextView() {
    model.initializeGame(grid, players, "largeCardConfig.txt", validBoardFilePath);
    model.placeCard(0, 1, 2);
    model.switchTurn();
    TextView view = new TextView(model);
    assertEquals(view.toString(), "Model.Player: BLUE\n" +
            " __\n" +
            "__R\n" +
            "__ \n" +
            "Hand: \n" +
            "Card2 1 3 4 8\n" +
            "Card4 5 2 6 3\n" +
            "Card6 5 9 6 8\n");
  }

  @Test
  public void testGameOver() {
    model.initializeGame(grid, players, "largeCardConfig.txt",
            "OnceCellBoard.txt");
    model.placeCard(0, 0, 0);
    assertTrue(model.isGameOver());
    assertEquals(model.getWinner(), model.getCurrentTurnPlayer());
  }


}