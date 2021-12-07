package ooga.model.rules.individualRules;

import java.util.Arrays;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.rules.RuleInterface;

public class WildRule implements RuleInterface {

  private final static String BUNDLE_PATH = "ooga.model.rules.resources.RulesInformation";
  private final static String WILD = "Wild";
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
    if (draw != 0) {
      return false;
    }

    return Arrays.stream(ruleResources.getString(WILD).split(ruleResources.getString(SPLIT)))
        .anyMatch(c -> c.equals(cardToPlay.getType()));
  }
}
