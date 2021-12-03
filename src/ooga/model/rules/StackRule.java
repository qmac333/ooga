package ooga.model.rules;

import java.util.List;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;

public class StackRule implements RuleInterface {

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(
      "ooga.model.rules.RulesInformation");

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw <= 0)
      return false;

    List<String> stackers = List.of(ruleResources.getString("Stacker").split(","));
    return stackers.contains(cardToPlay.getType());
  }
}