package ooga.model.instanceCreation;

import java.util.ResourceBundle;
import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import ooga.model.rules.RuleInterface;

public class ReflectionHandler implements ReflectionHandlerInterface {

  private static final String BUNDLE_PATH = "ooga.model.instanceCreation.ReflectionResources";
  private static final String PLAYER_BASE = "PlayerClassBase";
  private static final String DRAW_RULE_BASE = "DrawRuleBase";
  private static final String PLAY_RULE_BASE = "PlayRulesBase";

  private static final ResourceBundle reflectionResources = ResourceBundle.getBundle(BUNDLE_PATH);

  @Override
  public Player getPlayer(String name, PlayerGroupPlayerInterface group, String type)
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
      throw new ReflectionErrorException("Reflection Error");
    }
  }

  @Override
  public DrawRuleInterface getDrawRule(String type) throws ReflectionErrorException {
    try {
      Class<?> clazz = Class.forName(
          String.format(reflectionResources.getString(DRAW_RULE_BASE), type));
      return (DrawRuleInterface) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new ReflectionErrorException("Reflection Error");
    }
  }

  @Override
  public RuleInterface getRule(String type) throws ReflectionErrorException {
    try {
      Class<?> clazz = Class.forName(
          String.format(reflectionResources.getString(PLAY_RULE_BASE), type));
      return (RuleInterface) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new ReflectionErrorException(String.format("Couldn't create Rule %s", type));
    }
  }
}
