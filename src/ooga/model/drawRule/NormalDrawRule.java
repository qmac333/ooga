package ooga.model.drawRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateDrawInterface;

public class NormalDrawRule implements DrawRuleInterface {

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> noPlayDraw(GameStateDrawInterface game) {
    return List.of(game.getNextCard());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> forcedDraw(GameStateDrawInterface game, int amount) {
    List<CardInterface> drawn = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      drawn.add(game.getNextCard());
    }
    return drawn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> drawUntilBlast(GameStateDrawInterface game) {
    return List.of(game.getNextCard());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> drawUntilColor(GameStateDrawInterface game, String color) {
    Collection<CardInterface> cardsDrawn = new ArrayList<>();
    CardInterface drawn;
    do {
      drawn = game.getNextCard();
      cardsDrawn.add(drawn);
    } while (!drawn.getMyColor().equals(color));
    return cardsDrawn;
  }

  /**
   * @return Null because there is no blaster
   */
  @Override
  public Collection<ViewCardInterface> getBlasterCards() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBlastProbability(double probability) {
    // Do nothing because there is no blaster involved
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean blasted() {
    return false; // Blaster never goes off in Normal Draw Rule
  }
}
