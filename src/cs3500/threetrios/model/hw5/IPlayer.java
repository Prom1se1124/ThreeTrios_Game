package cs3500.threetrios.model.hw5;


import java.util.List;

/**
 * Interface for players in the Three Trios game.
 * This interface allows for multiple types of players, such as human or AI,
 * to interact with the game model.
 */
public interface IPlayer {

  /**
   * Gets the name of the player.
   *
   * @return The player's name.
   */
  String getName();


  /**
   * Sets the player's hand with a list of cards.
   *
   * @param cards The list of cards dealt to the player.
   * @throws IllegalArgumentException if the cards list is null or empty.
   */
  void setHand(List<ICard> cards) throws IllegalArgumentException;


  /**
   * Gets the list of cards in the player's hand.
   *
   * @return A list of cards in the player's hand.
   */
  List<ICard> getHand();


  /**
   * Chooses a card from the player's hand to be played on the grid.
   * This could involve prompting a human user or selecting a card via AI logic.
   *
   * @return The card chosen by the player.
   * @throws IllegalStateException if the player's hand is empty and no card can be chosen.
   */
  ICard chooseCard(int cardIndex) throws IllegalStateException;


  /**
   * Chooses the coordinates (x, y) to place the chosen card.
   * This could involve user input for a human or logic for an AI.
   *
   * @param availablePositions A list of valid positions where the card can be placed.
   * @return The coordinates (x, y) selected by the player.
   * @throws IllegalArgumentException if the list of available positions is empty or null.
   * @throws IllegalStateException    if the player is unable to choose a position.
   */
  int[] choosePosition(List<int[]> availablePositions) throws IllegalArgumentException,
          IllegalStateException;


  /**
   * Updates the player's score.
   *
   * @param score The new score for the player.
   * @throws IllegalArgumentException if the provided score is negative.
   */
  void updateScore(int score) throws IllegalArgumentException;


  /**
   * Gets the player's current score.
   *
   * @return The current score of the player.
   */
  int getScore();

  /**
   * Gets the player's corresponding color.
   *
   * @return The color of the player.
   */
  public CardColor getColor();

  /**
   * Add Card to hand.
   */
  void addCard(ICard card);

  /**
   * Removes a card from the player's hand.
   *
   * @param cardIndex The index of the card to be removed.
   * @throws IllegalArgumentException if the card index is out of bounds.
   */
  void removeCard(int cardIndex) throws IllegalArgumentException;


}

