package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.WildDrawFourCard;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.WildCard;
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
    assertFalse(wildRule.canPlay(new NumberCard("red", 5), new NumberCard("green", 5)));
    assertFalse(wildRule.canPlay(new NumberCard("blue", 0), new NumberCard("yellow", 0)));
    assertFalse(wildRule.canPlay(new ReverseCard("blue"), new ReverseCard("yellow")));
    assertFalse(wildRule.canPlay(new SkipCard("red"), new SkipCard("red")));
  }

  @Test
  public void wildCardsAlwaysWork(){
    assertTrue(wildRule.canPlay(new NumberCard("green", 5), new WildDrawFourCard("red")));
    assertTrue(wildRule.canPlay(new ReverseCard("green"), new WildDrawFourCard("red")));
    assertTrue(wildRule.canPlay(new SkipCard("green"), new WildDrawFourCard("red")));
    assertTrue(wildRule.canPlay(new NumberCard("green", 5), new WildCard("red")));
    assertTrue(wildRule.canPlay(new ReverseCard("green"), new WildCard("red")));
    assertTrue(wildRule.canPlay(new SkipCard("green"), new WildCard("red")));
  }
}
