package ooga.model.rules.individualRules;

import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.rules.RuleInterface;

public class SameNumberRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.resources.RulesInformation";
  private final static String NUMBER = "Number";

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(BUNDLE_PATH);

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    if (cardToMatch.getType().equals(ruleResources.getString(NUMBER)) && cardToPlay.getType().equals(ruleResources.getString(NUMBER))) {
      return cardToMatch.getNum() == cardToPlay.getNum();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0)
      return false;

    if (cardToMatch.getType().equals(ruleResources.getString(NUMBER)) && cardToPlay.getType().equals(ruleResources.getString(NUMBER))) {
      return cardToMatch.getNum() == cardToPlay.getNum();
    }
    return false;
  }
}
