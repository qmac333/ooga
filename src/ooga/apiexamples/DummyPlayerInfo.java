package ooga.apiexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.model.PlayersInfo;

/**
 * Dummy class that returns static player info. Used for mocking out data from the PlayersInfo API
 * calls.
 */
public class DummyPlayerInfo implements PlayersInfo {

  List<String> players;

  public DummyPlayerInfo() {
    players = new ArrayList<String>(Arrays.asList("Andrew", "Paul", "William"));

  }

  @Override
  public List<String> getPlayerNames() {
    // make copy
    List<String> newList = new ArrayList<String>();
    for (String s : players) {
      newList.add(s);
    }
    return newList;
  }

  @Override
  public int getCurrentPlayer() {
    return 1; // Paul is the current player
  }

  @Override
  public int getPlayDirection() {
    return PlayersInfo.COUNTERCLOCKWISE;
  }
}
