package ooga.apiexamples;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.GameStateInterface;

/**
 * Class for mocking out the game state.
 */
public class DummyGameState implements GameStateInterface {

  Map<Integer, List<List<String>>> hand;

  public DummyGameState() {
    hand = new HashMap<>();
    hand.put(0, new ArrayList<>());
    hand.put(-1, new ArrayList<>());
    hand.get(0).add(new ArrayList<>(Arrays.asList("Red", "0")));
    hand.get(0).add(new ArrayList<>(Arrays.asList("Yellow", "0")));
    hand.get(-1).add(new ArrayList<>(Arrays.asList("Blue", "Reverse")));


  }

  @Override
  public void playTurn() {

  }

  @Override
  public int getCurrentPlayer() {
    return 0;
  }

  @Override
  public Map<Integer, List<List<String>>> getCurrentPlayerCards() {
    return hand;
  }
}
