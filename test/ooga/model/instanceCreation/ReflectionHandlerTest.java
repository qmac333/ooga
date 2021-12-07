package ooga.model.instanceCreation;

import ooga.model.cards.FlipCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.WildDrawFourCard;
import ooga.model.drawRule.BlasterDrawRule;
import ooga.model.drawRule.NormalDrawRule;
import ooga.model.player.player.ComputerPlayer;
import ooga.model.player.player.HumanPlayer;
import ooga.model.rules.individualRules.SameActionRule;
import ooga.model.rules.individualRules.StackRule;
import ooga.model.rules.individualRules.WildRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectionHandlerTest {
  @Test
  void correctDrawRuleReturned() throws ReflectionErrorException {
    Assertions.assertTrue(ReflectionHandlerInterface.getDrawRule("Normal") instanceof NormalDrawRule);
    Assertions.assertTrue(ReflectionHandlerInterface.getDrawRule("Blaster") instanceof BlasterDrawRule);
  }

  @Test
  void throwsReflectionErrorWhenNeeded() {
    Assertions.assertThrows(ReflectionErrorException.class, () -> ReflectionHandlerInterface.getDrawRule("Hello"));
  }

  @Test
  void correctPlayerTypeReturned() throws ReflectionErrorException {
    Assertions.assertTrue(ReflectionHandlerInterface.getPlayer("Normal", null, "Human") instanceof HumanPlayer);
    Assertions.assertTrue(ReflectionHandlerInterface.getPlayer("Normal", null, "Computer") instanceof ComputerPlayer);
  }

  @Test
  void reflectionErrorForIncorrectType() {
    Assertions.assertThrows(ReflectionErrorException.class, () -> ReflectionHandlerInterface.getPlayer("CPU", null, "CPU"));
  }

  @Test
  void getCorrectRuleType() throws ReflectionErrorException {
    Assertions.assertTrue(ReflectionHandlerInterface.getRule("Stack") instanceof StackRule);
    Assertions.assertTrue(ReflectionHandlerInterface.getRule("Wild") instanceof WildRule);
    Assertions.assertTrue(ReflectionHandlerInterface.getRule("SameAction") instanceof SameActionRule);
  }

  @Test
  void reflectionErrorForWrongRule() {
    Assertions.assertThrows(ReflectionErrorException.class, () -> ReflectionHandlerInterface.getRule("Satck"));
  }

  @Test
  void getCorrectActionCard() throws ReflectionErrorException {
    Assertions.assertTrue(ReflectionHandlerInterface.getActionCard("Flip", "Blue") instanceof FlipCard);
    Assertions.assertTrue(ReflectionHandlerInterface.getActionCard("Skip", "Red") instanceof SkipCard);
    Assertions.assertTrue(ReflectionHandlerInterface.getActionCard("WildDrawFour", "Black") instanceof WildDrawFourCard);
  }

  @Test
  void reflectionErrorForIncorrectCardType() {
    Assertions.assertThrows(ReflectionErrorException.class, () -> ReflectionHandlerInterface.getActionCard("Noh", "Black"));
  }
}
