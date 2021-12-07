package ooga.model.drawRule;

import java.util.Collection;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStateDrawInterface;

public abstract class DrawRule implements DrawRuleInterface {

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Collection<CardInterface> drawUntilBlast(GameStateDrawInterface game, String color);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Collection<CardInterface> drawUntilColor(GameStateDrawInterface game, String color);

}
