package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;

public class WildCard extends OneSidedCard {

  public WildCard(String color) {
    super("Black", "Wild", 50);
  }

  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    // FIXME: Once we have the interface figured out
    super.setCardColor("Red");
  }

  @Override
  public void executeAction(PlayerInterface player) {
    super.setCardColor(player.getColor());
  }
}
