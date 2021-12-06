package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.individualCards.WildDrawFourCard;
import ooga.model.cards.individualCards.NumberCard;
import ooga.model.cards.individualCards.ReverseCard;
import ooga.model.cards.individualCards.SkipCard;
import ooga.model.cards.individualCards.WildCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WildRuleTest {
  WildRule wildRule;

  @BeforeEach
  public void start(){
    wildRule = new WildRule();
  }

  @Test
  public void nonWildCardsNeverFit(){
    assertFalse(wildRule.canPlay(new NumberCard("red", 5), new NumberCard("green", 5), 0));
    assertFalse(wildRule.canPlay(new NumberCard("blue", 0), new NumberCard("yellow", 0), 0));
    assertFalse(wildRule.canPlay(new ReverseCard("blue"), new ReverseCard("yellow"), 0));
    assertFalse(wildRule.canPlay(new SkipCard("red"), new SkipCard("red"), 0));
  }

  @Test
  public void wildCardsAlwaysWork(){
    assertTrue(wildRule.canPlay(new NumberCard("green", 5), new WildDrawFourCard("red"), 0));
    assertTrue(wildRule.canPlay(new ReverseCard("green"), new WildDrawFourCard("red"), 0));
    assertTrue(wildRule.canPlay(new SkipCard("green"), new WildDrawFourCard("red"), 0));
    assertTrue(wildRule.canPlay(new NumberCard("green", 5), new WildCard("red"), 0));
    assertTrue(wildRule.canPlay(new ReverseCard("green"), new WildCard("red"), 0));
    assertTrue(wildRule.canPlay(new SkipCard("green"), new WildCard("red"), 0));
  }

  @Test
  void ruleClassDoesntWorkWhenDraw(){
    assertFalse(wildRule.canPlay(new NumberCard("green", 5), new WildDrawFourCard("red"), 1));
    assertFalse(wildRule.canPlay(new ReverseCard("green"), new WildDrawFourCard("red"), 1));
    assertFalse(wildRule.canPlay(new SkipCard("green"), new WildDrawFourCard("red"), 1));
    assertFalse(wildRule.canPlay(new NumberCard("green", 5), new WildCard("red"), 2));
    assertFalse(wildRule.canPlay(new ReverseCard("green"), new WildCard("red"), 7));
    assertFalse(wildRule.canPlay(new SkipCard("green"), new WildCard("red"), 3));
  }
}
