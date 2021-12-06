package ooga.model.cards;

import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class DiscardColorCard extends OneSidedCard {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.CardResources";
  private static final String TYPE = "DiscardColorType";
  private static final String NUMBER = "DiscardColorNumber";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  public DiscardColorCard(String color) {
    super(color, cardResources.getString(TYPE), Integer.parseInt(cardResources.getString(NUMBER)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    // Do nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.discardColor(super.getMyColor());
  }
}
