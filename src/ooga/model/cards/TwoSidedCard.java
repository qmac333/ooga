package ooga.model.cards;

import java.util.List;
import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card for UnoFlip that holds two different normal cards inside it. When it flips, the card that
 * is being used switches
 *
 * @author Paul Truitt
 */
public class TwoSidedCard implements CardInterface, ViewCardInterface {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.resources.TwoSidedResources";
  private static final String SIDES = "NumberOfSides";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  private final List<OneSidedCard> myCards;
  private int activeSide;

  public TwoSidedCard(OneSidedCard sideOne, OneSidedCard sideTwo) {
    myCards = List.of(sideOne, sideTwo);
    activeSide = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    myCards.get(activeSide).executeAction(game);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    myCards.get(activeSide).executeAction(player);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {
    return myCards.get(activeSide).getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNum() {
    return myCards.get(activeSide).getNum();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMyColor() {
    return myCards.get(activeSide).getMyColor();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flip() {
    activeSide++;
    activeSide = activeSide % Integer.parseInt(cardResources.getString(SIDES));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {
    if (other == this){
      return true;
    }
    if (!(other instanceof TwoSidedCard)){
      return false;
    }
    TwoSidedCard o = (TwoSidedCard) other;
    return isSame(o);
  }

  private boolean isSame(TwoSidedCard card){
    if (!sidesSame(card)){
      return false;
    }
    this.flip();
    card.flip();
    if (!sidesSame(card)){
      return false;
    }
    this.flip();
    card.flip();
    return true;
  }

  private boolean sidesSame(TwoSidedCard card){
    return card.getMyColor().equals(this.getMyColor()) && card.getNum() == this.getNum() && card.getType().equals(this.getType());
  }

  @Override
  public void setColor(String color) {
    myCards.get(activeSide).setColor(color);
  }
}
