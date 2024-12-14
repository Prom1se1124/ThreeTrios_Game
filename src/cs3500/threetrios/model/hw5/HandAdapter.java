package cs3500.threetrios.model.hw5;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Hand;

public class HandAdapter implements Hand {

  private final List<cs3500.threetrios.model.hw5.ICard> ourHand;

  public HandAdapter(List<cs3500.threetrios.model.hw5.ICard> ourHand) {
    this.ourHand = ourHand;
  }
  @Override
  public List<Card> getCards() {
    List<Card> theirCards = new ArrayList<>();
    for (cs3500.threetrios.model.hw5.ICard card : ourHand) {
      theirCards.add(new CardAdapter(card));
    }
    return theirCards;
  }

  @Override
  public Hand removeFromHand(int idx) {
    List<cs3500.threetrios.model.hw5.ICard> newHand = new ArrayList<>(ourHand);
    newHand.remove(idx);
    return new HandAdapter(newHand);
  }

  @Override
  public void selectCard(int idx) {
    // No implementation needed since selection is handled by our controller
  }
}
