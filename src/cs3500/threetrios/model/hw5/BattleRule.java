package cs3500.threetrios.model.hw5;


/**
 * Interface for defining battle rules in the Three Trios game.
 */
public interface BattleRule {
  /**
   * Determines the outcome of a battle between two cards.
   *
   * @param attackingCard The card initiating the battle.
   * @param defendingCard The card being attacked.
   * @param direction The direction of the battle.
   * @return true if the attacking card wins, false otherwise.
   */
  boolean determineBattle(ICard attackingCard, ICard defendingCard, Direction direction);
}
