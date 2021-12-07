package ooga.model.instanceCreation;

import java.util.Collection;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.drawRule.DrawRule;
import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.gameState.GameStateDrawInterface;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroup;
import ooga.model.player.playerGroup.PlayerGroupGameInterface;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import ooga.model.rules.RuleInterface;

/**
 * This gives our program a single place to perform reflection. This should clean up how our program
 * looks and should make handling reflection errors easier.
 *
 * @author Paul Truitt
 */
public interface ReflectionHandlerInterface {

  String BUNDLE_PATH = "ooga.model.instanceCreation.resources.ReflectionResources";
  String PLAYER_BASE = "PlayerClassBase";
  String DRAW_RULE_BASE = "DrawRuleBase";
  String PLAY_RULE_BASE = "PlayRulesBase";
  String PLAYER_ERROR = "PlayerCreationError";
  String RULE_ERROR = "RuleCreationError";
  String DRAW_RULE_ERROR = "DrawRuleCreationError";
  String CARD_BASE = "CardBase";
  String CARD_ERROR = "CardCreationError";
  String CHEAT_ERROR = "CheatMethodError";
  String SPECIAL_DRAW_ERROR = "SpecialDrawError";

  ResourceBundle reflectionResources = ResourceBundle.getBundle(BUNDLE_PATH);

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
      throw new ReflectionErrorException(
          String.format(reflectionResources.getString(RULE_ERROR), type));
    }
  }

  /**
   * Create the specified action card
   *
   * @param type Type of card
   * @param color Card's color
   * @return Instance of card
   * @throws ReflectionErrorException If type isn't valid
   */
  static CardInterface getActionCard(String type, String color) throws ReflectionErrorException {
    try {
      Class<?> clazz = Class.forName(
          String.format(reflectionResources.getString(CARD_BASE), type));
      return (CardInterface) clazz.getDeclaredConstructor(String.class).newInstance(color);
    } catch (Exception e) {
      throw new ReflectionErrorException(
          String.format(reflectionResources.getString(CARD_ERROR), type));
    }
  }

  /**
   * Executes the required cheat method
   *
   * @param method Method to execute
   * @param arg1 Argument One for the cheat
   * @param arg2 Argument Two for the cheat
   * @param group PlayerGroup to call on
   * @throws ReflectionErrorException If reflection doesn't work
   */
  static void performCheatMethod(String method, String arg1, String arg2, PlayerGroupGameInterface group)
      throws ReflectionErrorException {
    try {
      PlayerGroup.class.getDeclaredMethod(method, String.class, String.class)
          .invoke(group, arg1, arg2);
    } catch (Exception e){
      throw new ReflectionErrorException(String.format(reflectionResources.getString(CHEAT_ERROR), method));
    }
  }

  /**
   * Performs the specified special draw method
   *
   * @param method Method to call
   * @param game Associated Game
   * @param color Color to look for
   * @param draw Draw rule to use
   * @return Cards returned by this method
   * @throws ReflectionErrorException If reflection doesn't work
   */
  static Collection<CardInterface> performSpecialDraw(String method, GameStateDrawInterface game, String color, DrawRuleInterface draw)
      throws ReflectionErrorException {
    try {
      return (Collection<CardInterface>) DrawRule.class.getDeclaredMethod(method,
          GameStateDrawInterface.class, String.class).invoke(draw, game, color);
    } catch (Exception e){
      throw new ReflectionErrorException(String.format(reflectionResources.getString(SPECIAL_DRAW_ERROR), method));
    }
  }
}
