package cs3500.threetrios.model.hw5;

/**
 * Reverse battle rule: Attacking card wins if its value is less than the defending card.
 */
public class ReverseBattleRule extends BattleRuleDecorator {

  public ReverseBattleRule(BattleRule wrappedRule) {
    super(wrappedRule);
  }

  @Override
  public boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction) {
    int attackerValue = CardUtils.getValueInDirection(attackingCard, direction);
    int defenderValue = CardUtils.getValueInOppositeDirection(defendingCard, direction);
    return attackerValue < defenderValue; // Reverse comparison
  }

  /**
   * Getter for wrappedRule.
   */
  public BattleRule getWrappedRule() {
    return wrappedRule;
  }
}
