package ooga.model.cards;

import java.util.ResourceBundle;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public class SkipEveryoneCard extends OneSidedCard {

  private static final String BUNDLE_PACKAGE = "ooga.model.cards.resources.SkipEveryoneResources";
  private static final String TYPE = "Type";
  private static final String NUMBER = "Number";

  private static final ResourceBundle cardResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  public SkipEveryoneCard(String color) {
    super(color, cardResources.getString(TYPE), Integer.parseInt(cardResources.getString(NUMBER)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void executeAction(GameStatePlayerInterface game) {
    game.skipEveryone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeAction(PlayerCardInterface player) {
    player.skipEveryone();
  }
}
