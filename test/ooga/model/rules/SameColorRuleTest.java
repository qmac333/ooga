package ooga.model.rules;

import static org.junit.jupiter.api.Assertions.*;

import ooga.model.cards.DrawFourCard;
import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.NumberCard;
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
    assertFalse(sameColorRule.canPlay(new DrawFourCard(null, "red"), new DrawTwoCard(null, "blue")));
    assertFalse(sameColorRule.canPlay(new SkipCard(null, "red"), new ReverseCard(null, "blue")));
  }

  @Test
  public void otherwiseDifferentCardsWithSameColorCanWork(){
    assertTrue(sameColorRule.canPlay(new SkipCard(null, "red"), new DrawTwoCard(null, "red")));
    assertTrue(sameColorRule.canPlay(new SkipCard(null, "blue"), new ReverseCard(null, "blue")));
  }

  @Test
  public void sameCardsWithDifferentColorsDontWork(){
    assertFalse(sameColorRule.canPlay(new DrawTwoCard(null, "red"), new DrawTwoCard(null, "blue")));
    assertFalse(sameColorRule.canPlay(new ReverseCard(null, "red"), new ReverseCard(null, "blue")));
    assertFalse(sameColorRule.canPlay(new SkipCard(null, "red"), new SkipCard(null, "blue")));
  }
}
