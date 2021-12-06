package ooga.model.rules;

import java.util.Arrays;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;

public class WildRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.RulesInformation";
  private final static String WILD = "Wild";
  private final static String SPLIT = "Divider";

  private final ResourceBundle ruleResources = ResourceBundle.getBundle(BUNDLE_PATH);

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return Arrays.stream(ruleResources.getString(WILD).split(ruleResources.getString(SPLIT)))
        .anyMatch(c -> c.equals(cardToPlay.getType()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0) {
      return false;
    }

    return Arrays.stream(ruleResources.getString(WILD).split(ruleResources.getString(SPLIT)))
        .anyMatch(c -> c.equals(cardToPlay.getType()));
  }
}
