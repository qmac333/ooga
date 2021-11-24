package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.DrawFourCard;
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
    assertFalse(wildRule.canPlay(new ReverseCard("blue", null), new ReverseCard("yellow", null)));
    assertFalse(wildRule.canPlay(new SkipCard("red", null), new SkipCard("red", null)));
  }

  @Test
  public void wildCardsAlwaysWork(){
    assertTrue(wildRule.canPlay(new NumberCard("green", 5), new DrawFourCard("red", null)));
    assertTrue(wildRule.canPlay(new ReverseCard("green", null), new DrawFourCard("red", null)));
    assertTrue(wildRule.canPlay(new SkipCard("green", null), new DrawFourCard("red", null)));
    assertTrue(wildRule.canPlay(new NumberCard("green", 5), new WildCard("red", null)));
    assertTrue(wildRule.canPlay(new ReverseCard("green", null), new WildCard("red", null)));
    assertTrue(wildRule.canPlay(new SkipCard("green", null), new WildCard("red", null)));
  }
}
