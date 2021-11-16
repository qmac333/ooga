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
    assertFalse(wildRule.canPlay(new NumberCard(null, "red", 5), new NumberCard(null, "green", 5)));
    assertFalse(wildRule.canPlay(new NumberCard(null, "blue", 0), new NumberCard(null, "yellow", 0)));
    assertFalse(wildRule.canPlay(new ReverseCard(null, "blue"), new ReverseCard(null, "yellow")));
    assertFalse(wildRule.canPlay(new SkipCard(null, "red"), new SkipCard(null, "red")));
  }

  @Test
  public void wildCardsAlwaysWork(){
    assertTrue(wildRule.canPlay(new NumberCard(null, "green", 5), new DrawFourCard(null, "red")));
    assertTrue(wildRule.canPlay(new ReverseCard(null, "green"), new DrawFourCard(null, "red")));
    assertTrue(wildRule.canPlay(new SkipCard(null, "green"), new DrawFourCard(null, "red")));
    assertTrue(wildRule.canPlay(new NumberCard(null, "green", 5), new WildCard(null, "red")));
    assertTrue(wildRule.canPlay(new ReverseCard(null, "green"), new WildCard(null, "red")));
    assertTrue(wildRule.canPlay(new SkipCard(null, "green"), new WildCard(null, "red")));
  }
}
