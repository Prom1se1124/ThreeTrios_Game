package cs3500.threetrios.model.hw5;

/**
 * Fallen Ace battle rule: A card with value 1 can defeat an Ace (10).
 */
public class FallenAceBattleRule extends BattleRuleDecorator {
  public FallenAceBattleRule(BattleRule wrappedRule) {
    super(wrappedRule);
  }

  @Override
  public boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction) {
    int attackerValue = CardUtils.getValueInDirection(attackingCard, direction);
    int defenderValue = CardUtils.getValueInOppositeDirection(defendingCard, direction);

    // If both Fallen Ace and Reverse are present, handle accordingly
    if (wrappedRule instanceof ReverseBattleRule) {
      if (attackerValue == 10 && defenderValue == 1) {
        return true; // Reverse logic: 10 beats 1
      } else if (attackerValue == 1 && defenderValue == 10) {
        return false; // Reverse logic: 1 loses to 10
      }
    }

    // Fallen Ace logic: 1 beats 10
    if (attackerValue == 1 && defenderValue == 10) {
      return true;
    } else if (attackerValue == 10 && defenderValue == 1) {
      return false;
    }

    // Delegate to the wrapped rule for other cases
    return wrappedRule.determineBattle(attackingCard, defendingCard, direction);
  }


  /**
   * Getter for wrappedRule.
   */
  public BattleRule getWrappedRule() {
    return wrappedRule;
  }
}
