package ooga.model.rules;

import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;

public class SameActionRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.RulesInformation";
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
