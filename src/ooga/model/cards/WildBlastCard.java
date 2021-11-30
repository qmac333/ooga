package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

/**
 * Card that has the next player draw until they set off the blaster
 *
 * @author Paul Truitt
 */
public class WildBlastCard extends Card {

  private final int DRAW_AMOUNT = -1;

  public WildBlastCard(String color) {
    super("Black", "WildBlast", 50);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.addDraw(DRAW_AMOUNT);
    super.setCardColor("Red");
  }

  @Override
  public void executeAction(PlayerInterface player) {
    player.enforceDraw(DRAW_AMOUNT);
    super.setCardColor(player.getColor());
  }

  @Override
  public void flip() {
    // Do nothing
  }
}
