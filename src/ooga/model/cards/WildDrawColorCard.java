package ooga.model.cards;

import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card that lets the user pick a color and then have the next player draw until the draw a card of
 * the correct color
 *
 * @author Paul Truitt
 */
public class WildDrawColorCard extends OneSidedCard {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.resources.WildDrawColorResources";
  private static final String TYPE = "Type";
  private static final String NUMBER = "Number";
  private static final String DRAW_AMOUNT = "DrawAmount";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  public WildDrawColorCard(String color) {
    super(color, cardResources.getString(TYPE), Integer.parseInt(cardResources.getString(NUMBER)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    // Do Nothing, Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.enforceDraw(Integer.parseInt(cardResources.getString(DRAW_AMOUNT)));
    super.setCardColor(player.getColor());
  }
}
