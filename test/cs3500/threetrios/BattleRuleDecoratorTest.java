package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.hw5.BattleRule;
import cs3500.threetrios.model.hw5.DefaultBattleRule;
import cs3500.threetrios.model.hw5.FallenAceBattleRule;
import cs3500.threetrios.model.hw5.ReverseBattleRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BattleRuleDecoratorTest {
  private BattleRule baseRule;
  private BattleRule reverseRule;

  @Before
  public void setUp() {
    baseRule = new DefaultBattleRule();
    reverseRule = new ReverseBattleRule(baseRule);
  }

  @Test
  public void testReverseDecorator() {
    assertTrue(reverseRule instanceof ReverseBattleRule);
    assertEquals(baseRule, ((ReverseBattleRule) reverseRule).getWrappedRule());
  }

  @Test
  public void testMultipleDecorators() {
    BattleRule combinedRule = new FallenAceBattleRule(reverseRule);

    assertTrue(combinedRule instanceof FallenAceBattleRule);
    assertTrue(((FallenAceBattleRule) combinedRule).getWrappedRule() instanceof ReverseBattleRule);
  }
}
