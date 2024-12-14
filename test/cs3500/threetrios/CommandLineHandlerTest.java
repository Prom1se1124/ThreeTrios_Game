package cs3500.threetrios;


import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.controller.CommandLineHandler;
import cs3500.threetrios.model.hw5.CardColor;
import cs3500.threetrios.model.hw5.DefaultBattleRule;
import cs3500.threetrios.model.hw5.FallenAceBattleRule;
import cs3500.threetrios.model.hw5.ReverseBattleRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineHandlerTest {

  private String[] defaultArgs;
  private String[] customRuleArgs;


  @Before
  public void setUp() {
    defaultArgs = new String[]{"human", "human"};
    customRuleArgs = new String[]{"human", "strategy1", "--rule", "reverse,fallen-ace"};
  }

  @Test
  public void testParseDefaultArgs() {
    CommandLineHandler.CommandLineConfig config = CommandLineHandler.parseArgs(defaultArgs);

    assertEquals("Player 1", config.getPlayer1().getName());
    assertEquals(CardColor.RED, config.getPlayer1().getColor());

    assertEquals("Player 2", config.getPlayer2().getName());
    assertEquals(CardColor.BLUE, config.getPlayer2().getColor());

    assertTrue(config.getBattleRule() instanceof DefaultBattleRule);
  }

  @Test
  public void testParseCustomRuleArgs() {
    CommandLineHandler.CommandLineConfig config = CommandLineHandler.parseArgs(customRuleArgs);

    assertTrue(config.getBattleRule() instanceof FallenAceBattleRule);
    assertTrue(((FallenAceBattleRule) config.getBattleRule())
            .getWrappedRule() instanceof ReverseBattleRule);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidArgs() {
    String[] invalidArgs = {"unknownPlayerType", "human"};
    CommandLineHandler.parseArgs(invalidArgs);
  }

}
