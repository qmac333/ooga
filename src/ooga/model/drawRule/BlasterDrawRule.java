package ooga.model.drawRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.drawRule.blaster.Blaster;
import ooga.model.drawRule.blaster.BlasterInterface;
import ooga.model.gameState.GameStateDrawInterface;

public class BlasterDrawRule extends DrawRule {

  private static final String BUNDLE_PACKAGE = "ooga.model.drawRule.resources.DrawRuleResources";
  private static final String DEFAULT_BLASTER_PROBABILITY = "DefaultBlastProbability";

  private static final ResourceBundle blastResources = ResourceBundle.getBundle(BUNDLE_PACKAGE);

  private final BlasterInterface myBlaster;

  public BlasterDrawRule() {
    myBlaster = new Blaster(
        Double.parseDouble(blastResources.getString(DEFAULT_BLASTER_PROBABILITY)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> noPlayDraw(GameStateDrawInterface game) {
    return myBlaster.insert(List.of(game.getNextCard()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> forcedDraw(GameStateDrawInterface game, int amount) {
    List<CardInterface> cardsToInsert = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      cardsToInsert.add(game.getNextCard());
    }
    return myBlaster.insert(cardsToInsert);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> drawUntilBlast(GameStateDrawInterface game, String color) {
    Collection<CardInterface> ejected;
    do {
      ejected = myBlaster.insert(List.of(game.getNextCard()));
    } while (ejected.isEmpty());

    return ejected;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> drawUntilColor(GameStateDrawInterface game,
      String colorToMatch) {
    Collection<CardInterface> cardsToInsert = new ArrayList<>();
    CardInterface drawn;
    do {
      drawn = game.getNextCard();
      cardsToInsert.add(drawn);
    } while (!drawn.getMyColor().equals(colorToMatch));
    return myBlaster.insert(cardsToInsert);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ViewCardInterface> getBlasterCards() {
    return myBlaster.getCards();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CardInterface> getBlasterList() {
    return myBlaster.getCardList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadBlaster(List<CardInterface> cards) {
    myBlaster.setBlaster(cards);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBlastProbability(double probability) {
    myBlaster.setProbabilityOfBlast(probability);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean blasted() {
    return myBlaster.blasted();
  }
}
