package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;

/**
 * The class representing the human player in the Three Trio Game.
 */
public class HumanPlayer implements IPlayer {

  private final String name;

  private final CardColor color;
  private List<ICard> hand;
  private int score;


  /**
   * Constructor of the  class representing the human player in the Three Trio Game.
   *
   * @param name name of the player
   * @param color color the player represents
   */
  public HumanPlayer(String name, CardColor color) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }

    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }

    this.name = name;
    this.hand = new ArrayList<>();
    this.color = color;
    this.score = 0; // Score starts at 0

  }

  /**
   * Copy constructor for deep copying.
   */
  public HumanPlayer(HumanPlayer other) {
    if (other == null) {
      throw new IllegalArgumentException("Player to be copied cannot be null.");
    }

    this.name = other.name;
    this.color = other.color;
    this.score = other.score;
    // Deep copy the hand
    this.hand = new ArrayList<>();
    for (ICard card : other.hand) {
      this.hand.add(card.clone());
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setHand(List<ICard> cards) throws IllegalArgumentException {
    if (cards == null || cards.isEmpty()) {
      throw new IllegalArgumentException("List of cards cannot be null or empty");
    }

    // Set the player's hand to a new list to avoid modifying the original reference
    this.hand = new ArrayList<>(cards);
  }

  @Override
  public List<ICard> getHand() {

    //return copy to protect the player's hand from external modification
    return new ArrayList<>(hand);
  }

  @Override
  public ICard chooseCard(int cardIndex) throws IllegalStateException {
    if (hand.isEmpty()) {
      throw new IllegalStateException("The player's hand is already empty");
    }
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index chosen.");
    }

    return hand.get(cardIndex);
  }

  @Override
  public int[] choosePosition(List<int[]> availablePositions) throws IllegalArgumentException,
          IllegalStateException {
    if (availablePositions == null || availablePositions.isEmpty()) {
      throw new IllegalArgumentException("The list of available positions can "
              + "not be null or empty.");
    }

    // TODO: This method requires the controller to prompt the user and pass the chosen index.
    // For now, will return a placeholder value until the interaction is fully defined.

    // Placeholder return value
    return availablePositions.get(0);

  }

  @Override
  public void updateScore(int score) throws IllegalArgumentException {
    if (score < 0) {
      throw new IllegalArgumentException("Score can not be negative.");
    }

    this.score = score;

  }

  @Override
  public int getScore() {
    return score;
  }

  @Override
  public CardColor getColor() {
    return color;
  }

  @Override
  public void addCard(ICard card) {
    this.hand.add(card);
  }

  @Override
  public void removeCard(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      throw new IllegalArgumentException("Card index is out of bounds.");
    }
    hand.remove(cardIndex);
  }

}
