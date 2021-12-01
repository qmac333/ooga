package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.WildDrawFourCard;
import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SameColorRuleTest {
  SameColorRule sameColorRule;

  @BeforeEach
  public void start(){
    sameColorRule = new SameColorRule();
  }

  @Test
  public void completelyDifferentCardsDontWork(){
    assertFalse(sameColorRule.canPlay(new WildDrawFourCard("red"), new DrawTwoCard("blue")));
    assertFalse(sameColorRule.canPlay(new SkipCard("red"), new ReverseCard("blue")));
  }

  @Test
  public void otherwiseDifferentCardsWithSameColorCanWork(){
    assertTrue(sameColorRule.canPlay(new SkipCard("red"), new DrawTwoCard("red")));
    assertTrue(sameColorRule.canPlay(new SkipCard("blue"), new ReverseCard("blue")));
  }

  @Test
  public void sameCardsWithDifferentColorsDontWork(){
    assertFalse(sameColorRule.canPlay(new DrawTwoCard("red"), new DrawTwoCard("blue")));
    assertFalse(sameColorRule.canPlay(new ReverseCard("red"), new ReverseCard("blue")));
    assertFalse(sameColorRule.canPlay(new SkipCard("red"), new SkipCard("blue")));
  }
}
