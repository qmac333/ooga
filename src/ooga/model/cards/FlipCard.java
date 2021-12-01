package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

/**
 * Card to make every Player flip their hands
 *
 * @author Paul Truitt
 */
public class FlipCard extends OneSidedCard {

  public FlipCard(String color) {
    super(color, "Flip", 20);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.flipCards();
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.flipGame();
  }
}
