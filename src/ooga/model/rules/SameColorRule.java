package ooga.model.rules;

import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;

public class SameColorRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.RulesInformation";
  private final static String WILD = "WildColor";

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(BUNDLE_PATH);

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0) {
      return false;
    }

    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor()) || cardToMatch.getMyColor()
        .equals(ruleResources.getString(WILD));
  }
}
