package ooga.model.rules;

import ooga.model.cards.DrawFiveCard;
import ooga.model.cards.DrawOneCard;
import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.FlipCard;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.SkipEveryoneCard;
import ooga.model.cards.WildBlastCard;
import ooga.model.cards.WildCard;
import ooga.model.cards.WildDrawColorCard;
import ooga.model.cards.WildDrawFourCard;
import ooga.model.cards.WildDrawTwoCard;
import ooga.model.rules.individualRules.StackRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackRuleTest {
  StackRule sr;

  @BeforeEach
  void start(){
    sr = new StackRule();
  }

  @Test
  void ifNoDrawThenNothingWorks(){
    assertFalse(sr.canPlay(new WildDrawFourCard("Black"), new WildDrawFourCard("Black"), 0));
    assertFalse(sr.canPlay(new WildCard("Black"), new WildCard("Black"), 0));
    assertFalse(sr.canPlay(new NumberCard("red", 5), new WildCard("Black"), 0));
  }

  @Test
  void ifNotDefiniteDrawThenNothingWorks(){
    assertFalse(sr.canPlay(new WildDrawFourCard("Black"), new WildDrawFourCard("Black"), -1));
    assertFalse(sr.canPlay(new WildCard("Black"), new WildCard("Black"), -2));
    assertFalse(sr.canPlay(new NumberCard("red", 5), new WildCard("Black"), -1));
  }

  @Test
  void nonStackerCardsNeverWork(){
    assertFalse(sr.canPlay(new WildCard("Black"), new WildCard("Black"), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new FlipCard("Black"), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new ReverseCard("Black"), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new NumberCard("Black", 1), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new SkipCard("Black"), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new SkipEveryoneCard("Black"), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new WildBlastCard("Black"), 1));
    assertFalse(sr.canPlay(new WildCard("Black"), new WildDrawColorCard("Black"), 1));
  }

  @Test
  void allStackerCardsWork(){
    assertTrue(sr.canPlay(new WildCard("Black"), new WildDrawFourCard("Black"), 1));
    assertTrue(sr.canPlay(new WildCard("Black"), new WildDrawTwoCard("Black"), 1));
    assertTrue(sr.canPlay(new WildCard("Black"), new DrawOneCard("Black"), 1));
    assertTrue(sr.canPlay(new WildCard("Black"), new DrawTwoCard("Black"), 1));
    assertTrue(sr.canPlay(new WildCard("Black"), new DrawFiveCard("Black"), 1));
  }
}
