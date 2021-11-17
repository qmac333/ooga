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
    assertFalse(sameNumberRule.canPlay(new NumberCard("red", 5), new NumberCard("red", 6)));
    assertFalse(sameNumberRule.canPlay(new NumberCard("blue", 0), new NumberCard("blue", 2)));
  }

  @Test
  public void nonNumberCardsNeverWork(){
    assertFalse(sameNumberRule.canPlay(new ReverseCard("red"), new ReverseCard("red")));
    assertFalse(sameNumberRule.canPlay(new DrawTwoCard("red"), new DrawTwoCard("red")));
    assertFalse(sameNumberRule.canPlay(new DrawFourCard("red"), new DrawFourCard("red")));
    assertFalse(sameNumberRule.canPlay(new SkipCard("red"), new SkipCard("red")));
  }

  @Test
  public void cardsWithTheSameNumbersShouldWork(){
    assertTrue(sameNumberRule.canPlay(new NumberCard("red", 5), new NumberCard("green", 5)));
    assertTrue(sameNumberRule.canPlay(new NumberCard("blue", 0), new NumberCard("yellow", 0)));
  }
}
