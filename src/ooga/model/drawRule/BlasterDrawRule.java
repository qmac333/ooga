package ooga.model.drawRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.drawRule.blaster.Blaster;
import ooga.model.drawRule.blaster.BlasterInterface;
import ooga.model.gameState.GameStateDrawInterface;

public class BlasterDrawRule implements DrawRuleInterface{
  private final double DEFAULT_BLASTER_PROBABILITY = 0.5;
  private final BlasterInterface myBlaster;

  public BlasterDrawRule(){
    myBlaster = new Blaster(DEFAULT_BLASTER_PROBABILITY);
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
    for (int i = 0; i < amount; i++){
      cardsToInsert.add(game.getNextCard());
    }
    return myBlaster.insert(cardsToInsert);
  }

  @Override
  public Collection<CardInterface> drawUntilBlast(GameStateDrawInterface game) {
    Collection<CardInterface> ejected;
    do {
      ejected = myBlaster.insert(List.of(game.getNextCard()));
    } while (ejected.isEmpty());

    return ejected;
  }

  @Override
  public Collection<CardInterface> drawUntilColor(GameStateDrawInterface game, String colorToMatch) {
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
  public void setBlastProbability(double probability) {
    myBlaster.setProbabilityOfBlast(probability);
  }
}
