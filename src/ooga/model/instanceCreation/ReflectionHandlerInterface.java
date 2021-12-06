package ooga.model.instanceCreation;

import java.util.ResourceBundle;
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

  static final String BUNDLE_PATH = "ooga.model.instanceCreation.resources.ReflectionResources";
  static final String PLAYER_BASE = "PlayerClassBase";
  static final String DRAW_RULE_BASE = "DrawRuleBase";
  static final String PLAY_RULE_BASE = "PlayRulesBase";
  static final String PLAYER_ERROR = "PlayerCreationError";
  static final String RULE_ERROR = "RuleCreationError";
  static final String DRAW_RULE_ERROR = "DrawRuleCreationError";

  static final ResourceBundle reflectionResources = ResourceBundle.getBundle(BUNDLE_PATH);

  /**
   * Creates a new instance of a player using reflection
   *
   * @param name The name of the player
   * @param group The player group the player belongs to
   * @param type The type of player this is (Human or Computer)
   * @return An instance of a player with these features
   */
  static Player getPlayer(String name, PlayerGroupPlayerInterface group, String type)
      throws ReflectionErrorException {
    Player player;
    try {
      Class<?> playerClass = Class.forName(
          String.format(reflectionResources.getString(PLAYER_BASE), type));
      player = (Player) playerClass.getDeclaredConstructor(
          String.class,
          PlayerGroupPlayerInterface.class).newInstance(name, group);
      return player;
    } catch (Exception e) {
      throw new ReflectionErrorException(
          String.format(reflectionResources.getString(PLAYER_ERROR), type));
    }
  }

  /**
   * Creates a new instance of a draw rule using reflection
   *
   * @param type The type of draw rule (Normal or Blaster)
   * @return An instance of the Draw Rule
   */
  static DrawRuleInterface getDrawRule(String type) throws ReflectionErrorException {
    try {
      Class<?> clazz = Class.forName(
          String.format(reflectionResources.getString(DRAW_RULE_BASE), type));
      return (DrawRuleInterface) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new ReflectionErrorException(
          String.format(reflectionResources.getString(DRAW_RULE_ERROR), type));
    }
  }

  /**
   * Creates a new instance of a Rule using reflection
   *
   * @param type The type of rule
   * @return An instance of this type of rule
   */
  static RuleInterface getRule(String type) throws ReflectionErrorException {
    try {
      Class<?> clazz = Class.forName(
          String.format(reflectionResources.getString(PLAY_RULE_BASE), type));
      return (RuleInterface) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
      throw new ReflectionErrorException(
          String.format(reflectionResources.getString(RULE_ERROR), type));
    }
  }
}
