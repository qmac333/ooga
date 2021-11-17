package ooga.model.rules;

import java.util.Arrays;
import java.util.ResourceBundle;
import ooga.model.cards.Card;

public class WildRule implements RuleInterface {

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(
      "ooga.model.rules.RulesInformation");

  @Override
  public boolean canPlay(Card cardToMatch, Card cardToPlay) {
    return Arrays.stream(ruleResources.getString("AlwaysPlayable").split(","))
        .anyMatch(c -> c.equals(cardToPlay.getType()));
  }
}
