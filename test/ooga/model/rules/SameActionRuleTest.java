package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.WildDrawFourCard;
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
        sameActionRule.canPlay(new NumberCard("Red", 9), new NumberCard("Red", 9), 0));
    assertFalse(
        sameActionRule.canPlay(new NumberCard("Red", 6), new NumberCard("Red", 9), 0));
  }

  @Test
  void twoDifferentActionCardsCantPlay() {
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new DrawTwoCard("Red"), 0));
    assertFalse(
        sameActionRule.canPlay(new DrawTwoCard("Red"), new WildDrawFourCard("Red"), 0));
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new WildDrawFourCard("Red"), 0));
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new ReverseCard("Red"), 0));
  }

  @Test
  void twoOfTheSameActionTypeCanPlay() {
    assertTrue(sameActionRule.canPlay(new SkipCard("Red"), new SkipCard("Green"), 0));
    assertTrue(sameActionRule.canPlay(new ReverseCard("Red"), new ReverseCard("Green"), 0));
    assertTrue(sameActionRule.canPlay(new DrawTwoCard("Red"), new DrawTwoCard("Green"), 0));
    assertTrue(sameActionRule.canPlay(new WildDrawFourCard("Red"), new WildDrawFourCard("Green"), 0));
  }

  @Test
  void ifThereIsAnyImpendingDrawYouCantPlay(){
    assertFalse(sameActionRule.canPlay(new SkipCard("Red"), new SkipCard("Green"), 1));
    assertFalse(sameActionRule.canPlay(new ReverseCard("Red"), new ReverseCard("Green"), -1));
    assertFalse(sameActionRule.canPlay(new DrawTwoCard("Red"), new DrawTwoCard("Green"), 3));
    assertFalse(sameActionRule.canPlay(new WildDrawFourCard("Red"), new WildDrawFourCard("Green"), 2));
  }
}
