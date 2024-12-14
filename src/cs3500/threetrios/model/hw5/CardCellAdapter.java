package cs3500.threetrios.model.hw5;

import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.Card;

public class CardCellAdapter implements Cell {

  private final ICard ourCard;  // might be null for empty card cells

  public CardCellAdapter(ICard ourCard) {
    this.ourCard = ourCard;
  }

  @Override
  public boolean isCardCell() {
    return true;
  }

  @Override
  public boolean isEmpty() {
    return ourCard == null;
  }

  @Override
  public Card getCard() {
    if (ourCard == null) {
      throw new IllegalStateException("No card in this cell");
    }
    return new CardAdapter(ourCard);
  }
}
