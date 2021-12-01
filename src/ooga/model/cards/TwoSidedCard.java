package ooga.model.cards;

import java.util.List;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

/**
 * Card for UnoFlip that holds two different normal cards inside of it. When it flips, the card that
 * is being used switches
 *
 * @author Paul Truitt
 */
public class TwoSidedCard implements CardInterface, ViewCardInterface {

  private List<OneSidedCard> myCards;
  private int activeSide;

  public TwoSidedCard(OneSidedCard sideOne, OneSidedCard sideTwo) {
    myCards = List.of(sideOne, sideTwo);
    activeSide = 0;
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    myCards.get(activeSide).executeAction(game);
  }

  @Override
  public void executeAction(PlayerInterface player) {
    myCards.get(activeSide).executeAction(player);
  }

  @Override
  public String getType() {
    return myCards.get(activeSide).getType();
  }

  @Override
  public int getNum() {
    return myCards.get(activeSide).getNum();
  }

  @Override
  public String getMyColor() {
    return myCards.get(activeSide).getMyColor();
  }

  @Override
  public void flip() {
    activeSide++;
    activeSide = activeSide % 2;
  }
}
