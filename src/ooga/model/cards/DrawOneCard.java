package ooga.model.cards;

import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card to force next player to draw one card
 *
 * @author Paul Truitt
 */
public class DrawOneCard extends OneSidedCard {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.resources.DrawOneResources";
  private static final String TYPE = "Type";
  private static final String NUMBER = "Number";
  private static final String DRAW_AMOUNT = "DrawAmount";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  public DrawOneCard(String color) {
    super(color, cardResources.getString(TYPE), Integer.parseInt(cardResources.getString(NUMBER)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    // Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.enforceDraw(Integer.parseInt(cardResources.getString(DRAW_AMOUNT)));
  }
}
