package ooga.model.rules.individualRules;

import java.util.List;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.rules.RuleInterface;

public class StackRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.resources.RulesInformation";
  private final static String STACKER = "Stacker";
  private final static String SPLIT = "Divider";

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(BUNDLE_PATH);

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
    if (draw <= 0) {
      return false;
    }

    List<String> stackers = List.of(
        ruleResources.getString(STACKER).split(ruleResources.getString(SPLIT)));
    return stackers.contains(cardToPlay.getType());
  }
}