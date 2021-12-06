package ooga.model.cards;

import ooga.model.cards.OneSidedCard;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class ReverseCard extends OneSidedCard {

  public ReverseCard(String color) {
    super(color, "Reverse", 20);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.reverseGamePlay();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.reverseGame();
  }
}
