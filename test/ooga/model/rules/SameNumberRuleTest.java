package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.WildDrawFourCard;
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
    assertFalse(sameNumberRule.canPlay(new NumberCard("red", 5), new NumberCard("red", 6), 0));
    assertFalse(sameNumberRule.canPlay(new NumberCard("blue", 0), new NumberCard("blue", 2), 0));
  }

  @Test
  public void nonNumberCardsNeverWork(){
    assertFalse(sameNumberRule.canPlay(new ReverseCard("red"), new ReverseCard("red"), 0));
    assertFalse(sameNumberRule.canPlay(new DrawTwoCard("red"), new DrawTwoCard("red"), 0));
    assertFalse(sameNumberRule.canPlay(new WildDrawFourCard("red"), new WildDrawFourCard("red"), 0));
    assertFalse(sameNumberRule.canPlay(new SkipCard("red"), new SkipCard("red"), 0));
  }

  @Test
  public void cardsWithTheSameNumbersShouldWork(){
    assertTrue(sameNumberRule.canPlay(new NumberCard("red", 5), new NumberCard("green", 5), 0));
    assertTrue(sameNumberRule.canPlay(new NumberCard("blue", 0), new NumberCard("yellow", 0), 0));
  }

  @Test
  public void ifTheresAnImpendingDrawItWontWork(){
    assertFalse(sameNumberRule.canPlay(new NumberCard("red", 5), new NumberCard("green", 5), 1));
    assertFalse(sameNumberRule.canPlay(new NumberCard("blue", 0), new NumberCard("yellow", 0), -1));
  }
}
