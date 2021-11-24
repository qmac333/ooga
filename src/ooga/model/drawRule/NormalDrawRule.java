package ooga.model.drawRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStateDrawInterface;

public class NormalDrawRule implements DrawRuleInterface {

  @Override
  public Collection<CardInterface> noPlayDraw(GameStateDrawInterface game) {
    return List.of(game.getNextCard());
  }

  @Override
  public Collection<CardInterface> forcedDraw(GameStateDrawInterface game, int amount) {
    List<CardInterface> drawn = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      drawn.add(game.getNextCard());
    }
    return drawn;
  }

  @Override
  public void setBlastProbability(double probability) {
    // Do nothing
  }
}
