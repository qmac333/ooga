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
    assertFalse(sameColorRule.canPlay(new SkipCard("red"), new DrawTwoCard("blue"), 0));
    assertFalse(sameColorRule.canPlay(new SkipCard("red"), new ReverseCard("blue"), 0));
  }

  @Test
  public void otherwiseDifferentCardsWithSameColorCanWork(){
    assertTrue(sameColorRule.canPlay(new SkipCard("red"), new DrawTwoCard("red"), 0));
    assertTrue(sameColorRule.canPlay(new SkipCard("blue"), new ReverseCard("blue"), 0));
  }

  @Test
  public void sameCardsWithDifferentColorsDontWork(){
    assertFalse(sameColorRule.canPlay(new DrawTwoCard("red"), new DrawTwoCard("blue"), 0));
    assertFalse(sameColorRule.canPlay(new ReverseCard("red"), new ReverseCard("blue"), 0));
    assertFalse(sameColorRule.canPlay(new SkipCard("red"), new SkipCard("blue"), 0));
  }

  @Test
  void neverWorksWithImpendingDraw(){
    assertFalse(sameColorRule.canPlay(new SkipCard("red"), new DrawTwoCard("red"), 1));
    assertFalse(sameColorRule.canPlay(new SkipCard("blue"), new ReverseCard("blue"), -1));
  }
}
