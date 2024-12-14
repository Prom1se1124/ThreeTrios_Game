package cs3500.threetrios.provider.model;

import java.util.ArrayList;

public interface ThreeTriosStrategy {

  //This is the ThreeTriosStrategy interface from the provider, which is requiered to be passed
  //in to their Player interface(even for human players to work, although human player returns null
  // for getStrategy based on their javadoc). So, in order to adapt their code, we as customers
  // needs their ThreeTriosStrategy interface.

  /**
   * Uses the given strategy to place down the card at the optimal
   * spot based on the player's current hand and the game board.
   * @param model the read only three trios model
   * @param player the player that the move is for
   * @return an arraylist of integers the card index of hand, row and col
   */
  ArrayList<Integer> chooseSpot(ThreeTriosGame model, Player player);

}
