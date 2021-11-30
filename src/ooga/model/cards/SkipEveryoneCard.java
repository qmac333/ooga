package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class SkipEveryoneCard extends OneSidedCard {

  public SkipEveryoneCard(String color) {
    super(color, "SkipEveryone", 30);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.skipEveryone();
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.skipEveryone();
  }
}
