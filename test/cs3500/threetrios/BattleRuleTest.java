package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.hw5.BattleRule;
import cs3500.threetrios.model.hw5.Card;
import cs3500.threetrios.model.hw5.CardColor;
import cs3500.threetrios.model.hw5.DefaultBattleRule;
import cs3500.threetrios.model.hw5.Direction;
import cs3500.threetrios.model.hw5.FallenAceBattleRule;
import cs3500.threetrios.model.hw5.PlusBattleRule;
import cs3500.threetrios.model.hw5.ReverseBattleRule;
import cs3500.threetrios.model.hw5.SameBattleRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleRuleTest {
  private BattleRule defaultRule;
  private BattleRule reverseRule;
  private BattleRule fallenAceRule;

  @Before
  public void setUp() {
    defaultRule = new DefaultBattleRule();
    reverseRule = new ReverseBattleRule(defaultRule);
    fallenAceRule = new FallenAceBattleRule(defaultRule);
  }

  @Test
  public void testDefaultBattleRule() {
    Card card1 = new Card("Card1", 8, 4, 3, 6,
            CardColor.RED);
    Card card2 = new Card("Card2", 4, 6, 2, 7,
            CardColor.BLUE);

    assertTrue(defaultRule.determineBattle(card1, card2, Direction.NORTH)); // Card1 wins

  }

  @Test
  public void testReverseBattleRule() {
    Card card1 = new Card("Card1", 5, 4, 3, 6,
            CardColor.RED);
    Card card2 = new Card("Card2", 4, 6, 2, 7,
            CardColor.BLUE);

    assertTrue(reverseRule.determineBattle(card1, card2, Direction.NORTH)); // Card1 wins
  }

  @Test
  public void testFallenAceBattleRule() {
    Card card1 = new Card("Card1", 10, 4, 3, 6,
            CardColor.RED);
    Card card2 = new Card("Card2", 4, 1, 2, 7,
            CardColor.BLUE);// Ace

    assertTrue(fallenAceRule.determineBattle(card2, card1, Direction.SOUTH));
  }

  @Test
  public void testCombinedRules() {
    BattleRule combinedRule = new FallenAceBattleRule(reverseRule);
    Card card1 = new Card("Card1", 10, 4, 3, 6,
            CardColor.RED);
    Card card2 = new Card("Card2", 4, 1, 2, 7,
            CardColor.BLUE);// Ace

    assertTrue(combinedRule.determineBattle(card1, card2, Direction.EAST));
    assertFalse(combinedRule.determineBattle(card2, card1, Direction.SOUTH));
  }

  @Test
  public void testSameBattleRuleFlipOccurred() {

    BattleRule sameRule = new SameBattleRule(defaultRule);

    int[][] gridConfig = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };
    Card[][] board = new Card[3][3];


    Card A = new Card("A", 5, 1, 3, 10, CardColor.RED);
    board[1][1] = A;


    Card B = new Card("B", 8, 6, 9, 4, CardColor.BLUE);
    board[0][1] = B;

    // N=1 same as A.S=1 => (1,1)
    Card C = new Card("C", 1, 7, 5, 4, CardColor.RED);
    board[2][1] = C;

    // W=3 same as A.E=3 => (3,3)

    Card D = new Card("D", 2, 4, 8, 3, CardColor.BLUE);
    board[1][2] = D;

    // E=10 same as A.W=10 => (10,10)
    Card E = new Card("E", 4, 4, 10, 2, CardColor.BLUE);
    board[1][0] = E;


    ((SameBattleRule) sameRule).applySameRule(A, board, gridConfig, 1, 1);

    // Check flips
    assertEquals(CardColor.BLUE, B.getColor()); // not a match pair, stays blue
    assertEquals(CardColor.RED, C.getColor()); // already red, stays red
    assertEquals(CardColor.RED, D.getColor()); // flipped from blue to red
    assertEquals(CardColor.RED, E.getColor()); // flipped from blue to red
  }



  @Test
  public void testPlusBattleRuleScenario() {

    BattleRule plusRule = new PlusBattleRule(defaultRule);

    int[][] gridConfig = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
    };
    Card[][] board = new Card[3][3];

    Card A = new Card("A", 5, 4, 3, 10, CardColor.RED);
    board[1][1] = A;

    Card B = new Card("B", 8, 5, 4, 2, CardColor.BLUE);
    board[0][1] = B;

    Card C = new Card("C", 6, 2, 2, 9, CardColor.RED);
    board[2][1] = C;

    Card D = new Card("D", 3, 5, 2, 7, CardColor.RED);
    board[1][2] = D;

    Card E = new Card("E", 9, 3, 1, 8, CardColor.BLUE);
    board[1][0] = E;

    ((PlusBattleRule) plusRule).applyPlusRule(A, board, gridConfig, 1, 1);


    assertEquals(CardColor.RED, A.getColor()); // still RED
    assertEquals(CardColor.RED, B.getColor()); // flipped from BLUE to RED
    assertEquals(CardColor.RED, C.getColor()); // stays RED
    assertEquals(CardColor.RED, D.getColor()); // stays RED
    assertEquals(CardColor.BLUE, E.getColor()); // remains BLUE, since we are not applying "regular"
    //rule for this test
  }


}

