package cs3500.threetrios.model.hw5;

/**
 * Default battle rule: Attacking card wins if its value is greater than the defending card.
 */
public class DefaultBattleRule implements BattleRule {
  @Override
  public boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction) {
    int attackerValue = CardUtils.getValueInDirection(attackingCard, direction);
    int defenderValue = CardUtils.getValueInOppositeDirection(defendingCard, direction);
    return attackerValue > defenderValue;
  }


}
