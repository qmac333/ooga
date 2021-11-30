package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class ReverseCard extends Card {

  public ReverseCard(String color) {
    super(color, "Reverse", 20);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.reverseGamePlay();
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.reverseGame();
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
