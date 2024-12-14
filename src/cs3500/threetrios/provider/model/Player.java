package cs3500.threetrios.provider.model;

import cs3500.threetrios.provider.model.ThreeTriosStrategy;

//import java.util.ArrayList;
//import java.util.List;

//import java.lang.reflect.Array;
import java.util.List;

/**
 * Represents the player interface for three trios. This serves to
 * allows Players and A.I. players to interact with the three trios
 * game.
 *
 * <p>Human players will have a class that implements this interface, as well as a color field
 * that represents which player they are for determining turns and color of the cards
 * being placed and flipped.
 *
 * <p>A.I. players will also have a class that implements this interface as well as a color field
 * that denotes which player they are.
 *
 * <p>There should also be fields for the list of cards in their hand so they can play and place
 * those cards down.
 *
 * <p>It can be assumed that the players know what color they are (which player they are)
 * and what is in their hand.
 */
public interface Player {

  // javadocs above explain design plans

  /**
   * gets the current hand, the list of cards, from this player.
   * @return Hand the list of cards
   */
  public Hand getHand();

  /**
   * gets the color of the player, whether they are the red or blue player.
   * @return CardColor the player's color
   */
  public CardColor getColor();

  /**
   * returns whether it is the player's turn or not.
   * @return true iff it is this player's turn
   */
  public boolean isTurn();

  /**
   * changes this turn so if it's this player's turn, then
   * it is not, and if it's not then it turns into this turn.
   */
  public void changeTurn();

  /**
   * returns the size of the hand, the number of cards in the hand.
   * @return int of the number of cards
   */
  public int getHandSize();

  /**
   * Returns the card from the hand that was played and
   * removes that card from the hand.
   * @param idx the index of the card in hand to play
   * @return the Card to play from hand
   */
  public Card playFromHand(int idx);


  /**
   * sets the hand of the player to the list of cards.
   * @param cards the list of cards to add
   */
  public void makeHand(List<Card> cards);

  /**
   * returns true iff the player is an AI.
   * @return true if AI false if not
   */
  public boolean isAIPlayer();

  /**
   * gets the strategy for the AI player, null for human players.
   * @return ThreeTriosStrategy or null
   */
  public ThreeTriosStrategy getStrategy();

  /**
   * deep copies the player into a new player.
   * @return the new Player
   */
  public Player copyPlayer();

}
