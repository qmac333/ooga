package ooga.model.rules;

import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.WildDrawFourCard;
import ooga.model.instanceCreation.ReflectionErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RuleSetTest {

  @Test
  public void stackableTrueWillHaveInstanceOfStackable() throws ReflectionErrorException {
    RuleSet set = new RuleSet("Basic", true);
    Assertions.assertTrue(set.canPlay(new DrawTwoCard("Red"), new WildDrawFourCard("Black"), 2));
  }

  @Test
  public void stackableFalseWontHaveInstanceOfStackable() throws ReflectionErrorException {
    RuleSet set = new RuleSet("Basic", false);
    Assertions.assertFalse(set.canPlay(new DrawTwoCard("Red"), new WildDrawFourCard("Black"), 2));
  }
}
