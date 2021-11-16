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
        sameActionRule.canPlay(new NumberCard(null, "Red", 9), new NumberCard(null, "Red", 9)));
    assertFalse(
        sameActionRule.canPlay(new NumberCard(null, "Red", 6), new NumberCard(null, "Red", 9)));
  }

  @Test
  void twoDifferentActionCardsCantPlay() {
    assertFalse(sameActionRule.canPlay(new SkipCard(null, "Red"), new DrawTwoCard(null, "Red")));
    assertFalse(
        sameActionRule.canPlay(new DrawTwoCard(null, "Red"), new DrawFourCard(null, "Red")));
    assertFalse(sameActionRule.canPlay(new SkipCard(null, "Red"), new DrawFourCard(null, "Red")));
    assertFalse(sameActionRule.canPlay(new SkipCard(null, "Red"), new ReverseCard(null, "Red")));
  }

  @Test
  void twoOfTheSameActionTypeCanPlay() {
    assertTrue(sameActionRule.canPlay(new SkipCard(null, "Red"), new SkipCard(null, "Green")));
    assertTrue(sameActionRule.canPlay(new ReverseCard(null, "Red"), new ReverseCard(null, "Green")));
    assertTrue(sameActionRule.canPlay(new DrawTwoCard(null, "Red"), new DrawTwoCard(null, "Green")));
    assertTrue(sameActionRule.canPlay(new DrawFourCard(null, "Red"), new DrawFourCard(null, "Green")));
  }
}
