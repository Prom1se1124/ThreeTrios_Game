package cs3500.threetrios.model.hw5;


/**
 * Abstract decorator class for BattleRule. Allows combining multiple rules dynamically.
 */
public abstract class BattleRuleDecorator implements BattleRule {
  protected final BattleRule wrappedRule;

  public BattleRuleDecorator(BattleRule wrappedRule) {
    if (wrappedRule == null) {
      throw new IllegalArgumentException("Wrapped rule cannot be null");
    }
    this.wrappedRule = wrappedRule;
  }

  @Override
  public boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction) {
    return wrappedRule.determineBattle(attackingCard, defendingCard, direction);
  }

  /**
   * Getter for wrappedRule.
   */
  public BattleRule getWrappedRule() {
    return wrappedRule;
  }

}
