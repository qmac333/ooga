package ooga.model.drawRule;

import java.util.ArrayList;
import java.util.List;
import ooga.model.cards.Card;
import ooga.model.gameState.GameStateDrawInterface;

public class NormalDrawRule implements DrawRuleInterface{

  @Override
  public List<Card> noPlayDraw(GameStateDrawInterface game) {
    return List.of(game.getNextCard());
  }

  @Override
  public List<Card> forcedDraw(GameStateDrawInterface game, int amount) {
    List<Card> drawn = new ArrayList<>();
    for (int i = 0; i < amount; i++){
      drawn.add(game.getNextCard());
    }
    return drawn;
  }
}
