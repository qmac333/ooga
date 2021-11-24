package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.DrawFourCard;
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
    assertFalse(sameColorRule.canPlay(new DrawFourCard("red", null), new DrawTwoCard("blue", null)));
    assertFalse(sameColorRule.canPlay(new SkipCard("red", null), new ReverseCard("blue", null)));
  }

  @Test
  public void otherwiseDifferentCardsWithSameColorCanWork(){
    assertTrue(sameColorRule.canPlay(new SkipCard("red", null), new DrawTwoCard("red", null)));
    assertTrue(sameColorRule.canPlay(new SkipCard("blue", null), new ReverseCard("blue", null)));
  }

  @Test
  public void sameCardsWithDifferentColorsDontWork(){
    assertFalse(sameColorRule.canPlay(new DrawTwoCard("red", null), new DrawTwoCard("blue", null)));
    assertFalse(sameColorRule.canPlay(new ReverseCard("red", null), new ReverseCard("blue", null)));
    assertFalse(sameColorRule.canPlay(new SkipCard("red", null), new SkipCard("blue", null)));
  }
}
