package cs3500.threetrios.model.hw5;

import java.util.List;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.CardColor;
import cs3500.threetrios.provider.model.Hand;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ThreeTriosStrategy;

public class PlayerAdapter implements Player {

  private final IPlayer ourPlayer;

  public PlayerAdapter(IPlayer ourPlayer) {
    this.ourPlayer = ourPlayer;
  }

  @Override
  public Hand getHand() {
    return new HandAdapter(ourPlayer.getHand());
  }

  @Override
  public CardColor getColor() {

    return ourPlayer.getColor() == cs3500.threetrios.model.hw5.CardColor.RED ?
            CardColor.Red : CardColor.Blue;
  }

  @Override
  public boolean isTurn() {
    return false;//这个要根据我们的model logic来写，
  }

  @Override
  public void changeTurn() {
//这个要根据我们的model logic来写，
  }

  @Override
  public int getHandSize() {
    return ourPlayer.getHand().size();
  }

  @Override
  public Card playFromHand(int idx) {
    cs3500.threetrios.model.hw5.ICard ourCard = ourPlayer.getHand().get(idx);
    return new CardAdapter(ourCard);
  }

  @Override
  public void makeHand(List<Card> cards) {
  //这个也许，可能不需要， since our model manages hand creation
  }

  @Override
  public boolean isAIPlayer() {
    return ourPlayer instanceof cs3500.threetrios.model.hw5.AIPlayer;
  }

  @Override
  public ThreeTriosStrategy getStrategy() {
    return null;
  }

  @Override
  public Player copyPlayer() {
    return new PlayerAdapter(ourPlayer);
  }
}
