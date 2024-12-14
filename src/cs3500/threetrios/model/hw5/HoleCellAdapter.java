package cs3500.threetrios.model.hw5;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;

public class HoleCellAdapter implements Cell {


  @Override
  public boolean isCardCell() {
    return false; // This is a hole, not a card cell
  }

  @Override
  public boolean isEmpty() {
    return true; // Holes are always considered empty
  }

  @Override
  public Card getCard() {
    return null;
  }
}
