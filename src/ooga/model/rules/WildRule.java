package ooga.model.rules;

import java.util.Arrays;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;

public class WildRule implements RuleInterface {

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(
      "ooga.model.rules.RulesInformation");

  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return Arrays.stream(ruleResources.getString("Wild").split(","))
        .anyMatch(c -> c.equals(cardToPlay.getType()));
  }

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0)
      return false;

    return Arrays.stream(ruleResources.getString("Wild").split(","))
        .anyMatch(c -> c.equals(cardToPlay.getType()));
  }
}
