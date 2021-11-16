package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.DrawFourCard;
import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SameNumberRuleTest {
  SameNumberRule sameNumberRule;

  @BeforeEach
  public void start(){
    sameNumberRule = new SameNumberRule();
  }

  @Test
  public void otherwiseEqualCardsWithDifferentNumbersDontWork(){
    assertFalse(sameNumberRule.canPlay(new NumberCard(null, "red", 5), new NumberCard(null, "red", 6)));
    assertFalse(sameNumberRule.canPlay(new NumberCard(null, "blue", 0), new NumberCard(null, "blue", 2)));
  }

  @Test
  public void nonNumberCardsNeverWork(){
    assertFalse(sameNumberRule.canPlay(new ReverseCard(null, "red"), new ReverseCard(null, "red")));
    assertFalse(sameNumberRule.canPlay(new DrawTwoCard(null, "red"), new DrawTwoCard(null, "red")));
    assertFalse(sameNumberRule.canPlay(new DrawFourCard(null, "red"), new DrawFourCard(null, "red")));
    assertFalse(sameNumberRule.canPlay(new SkipCard(null, "red"), new SkipCard(null, "red")));
  }

  @Test
  public void cardsWithTheSameNumbersShouldWork(){
    assertTrue(sameNumberRule.canPlay(new NumberCard(null, "red", 5), new NumberCard(null, "green", 5)));
    assertTrue(sameNumberRule.canPlay(new NumberCard(null, "blue", 0), new NumberCard(null, "yellow", 0)));
  }
}
