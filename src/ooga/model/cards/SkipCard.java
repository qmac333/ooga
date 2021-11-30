package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class SkipCard extends Card {

  public SkipCard(String color) {
    super(color, "Skip", 20);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.skipNextPlayer();
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.skipNextPlayer();
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
