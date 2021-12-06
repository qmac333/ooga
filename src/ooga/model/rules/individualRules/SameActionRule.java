package ooga.model.rules.individualRules;

import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.rules.RuleInterface;

public class SameActionRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.resources.RulesInformation";
  private final static String NUMBER = "Number";

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(BUNDLE_PATH);

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getType().equals(cardToPlay.getType()) && !cardToMatch.getType()
        .equals(ruleResources.getString(NUMBER));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0) {
      return false;
    }

    return cardToMatch.getType().equals(cardToPlay.getType()) && !cardToMatch.getType()
        .equals(ruleResources.getString(NUMBER));
  }
}
