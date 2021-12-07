package ooga.model.cards;

import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * Card to make every Player flip their hands
 *
 * @author Paul Truitt
 */
public class FlipCard extends OneSidedCard {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.resources.FlipResources";
  private static final String TYPE = "Type";
  private static final String NUMBER = "Number";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  public FlipCard(String color) {
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
    player.flipGame();
  }
}
