package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.DrawFourCard;
import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SameActionRuleTest {

  SameActionRule sameActionRule;

  @BeforeEach
  public void start() {
    sameActionRule = new SameActionRule();
  }

  @Test
  public void twoNumberCardsCantPlay() {
    assertFalse(
        sameActionRule.canPlay(new NumberCard("Red", 9), new NumberCard("Red", 9)));
    assertFalse(
        sameActionRule.canPlay(new NumberCard("Red", 6), new NumberCard("Red", 9)));
  }

  @Test
  void twoDifferentActionCardsCantPlay() {
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new DrawTwoCard("Red")));
    assertFalse(
        sameActionRule.canPlay(new DrawTwoCard("Red"), new DrawFourCard("Red")));
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new DrawFourCard("Red")));
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new ReverseCard("Red")));
  }

  @Test
  void twoOfTheSameActionTypeCanPlay() {
    assertTrue(sameActionRule.canPlay(new SkipCard("Red"), new SkipCard("Green")));
    assertTrue(sameActionRule.canPlay(new ReverseCard("Red"), new ReverseCard("Green")));
    assertTrue(sameActionRule.canPlay(new DrawTwoCard("Red"), new DrawTwoCard("Green")));
    assertTrue(sameActionRule.canPlay(new DrawFourCard("Red"), new DrawFourCard("Green")));
  }
}
