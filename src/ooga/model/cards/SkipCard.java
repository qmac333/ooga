package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class SkipCard extends OneSidedCard {

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
}
