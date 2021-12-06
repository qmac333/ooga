package ooga.model.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.instanceCreation.ReflectionHandlerInterface;

public class RuleSet implements RuleInterface {

  private static final String BUNDLE_PATH = "ooga.model.rules.resources.RulesInformation";
  private static final String RULES = "%sPlayRules";
  private static final String DIVIDER = "Divider";
  private static final String STACK_RULE = "StackingRule";

  private static final ResourceBundle rulesResources = ResourceBundle.getBundle(BUNDLE_PATH);

  List<RuleInterface> rules;

  public RuleSet(String gameType, boolean stackable) throws ReflectionErrorException {
    rules = new ArrayList<>();
    for (String rule : rulesResources.getString(String.format(RULES, gameType)).split(
        rulesResources.getString(DIVIDER))) {
      rules.add(ReflectionHandlerInterface.getRule(rule));
    }
    if (stackable) {
      rules.add(ReflectionHandlerInterface.getRule(rulesResources.getString(STACK_RULE)));
    }
  }

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return false;
  }

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    return rules.stream().anyMatch((RuleInterface r) -> r.canPlay(cardToMatch, cardToPlay, draw));
  }
}
