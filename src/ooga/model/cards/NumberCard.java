package ooga.model.cards;

import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class NumberCard extends OneSidedCard {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.CardResources";
  private static final String TYPE = "NumberType";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  public NumberCard(String color, int num) {
    super(color, cardResources.getString(TYPE), num);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.discardCard(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    // Do nothing
  }
}
