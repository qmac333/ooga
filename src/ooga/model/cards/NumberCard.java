package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class NumberCard extends Card {

  public NumberCard(String color, int num) {
    super(color, "Number", num);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.discardCard(this);
  }

  @Override
  public void executeAction(PlayerInterface player) {
    // Do nothing
  }

  @Override
  public void flip() {
    // Do Nothing
  }
}
