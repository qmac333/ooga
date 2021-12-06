package ooga.model.instanceCreation;

import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import ooga.model.rules.RuleInterface;

/**
 * This gives our program a single place to perform reflection. This should clean up how our program
 * looks and should make handling reflection errors easier.
 *
 * @author Paul Truitt
 */
public interface ReflectionHandlerInterface {

  /**
   * Creates a new instance of a player using reflection
   *
   * @param name The name of the player
   * @param group The player group the player belongs to
   * @param type The type of player this is (Human or Computer)
   * @return An instance of a player with these features
   */
  Player getPlayer(String name, PlayerGroupPlayerInterface group, String type)
      throws ReflectionErrorException;

  /**
   * Creates a new instance of a draw rule using reflection
   *
   * @param type The type of draw rule (Normal or Blaster)
   * @return An instance of the Draw Rule
   */
  DrawRuleInterface getDrawRule(String type) throws ReflectionErrorException;

  /**
   * Creates a new instance of a Rule using reflection
   *
   * @param type The type of rule
   * @return An instance of this type of rule
   */
  RuleInterface getRule(String type) throws ReflectionErrorException;
}
