package ooga.model;

import java.util.List;
import java.util.Map;

public class GameState implements GameStateInterface, GameStateViewInterface {

  @Override
  public List<String> getPlayerNames() {
    return null;
  }

  @Override
  public List<Integer> getCardCounts() {
    return null;
  }

  @Override
  public void playTurn() {

  }

  @Override
  public int getGameplayDirection() {
    return 0;
  }

  @Override
  public void addPlayer(Player p) {

  }

  @Override
  public int getCurrentPlayer() {
    return 0;
  }

  @Override
  public Map<Integer, List<List<String>>> getCurrentPlayerCards() {
    return null;
  }
}
