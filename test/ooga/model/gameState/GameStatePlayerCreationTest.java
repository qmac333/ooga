package ooga.model.gameState;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.player.ViewPlayerInterface;
import org.junit.jupiter.api.Test;

public class GameStatePlayerCreationTest {
  GameState gameState;

  @Test
  public void puttingInAnEmptyMapMeansNoPlayers(){
    // GIVEN we start a game with an empty map
    gameState = new GameState("Basic", new HashMap<>(), 100, false);
    // THEN we have an empty set of names
    assertEquals(0, gameState.getPlayers().size());
  }

  @Test
  public void puttingAComputerPlayerInResultsInASizeOfOne()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    // GIVEN we start the game with one computer player
    Map<String, String> map = new HashMap<>();
    map.put("Paul", "CPU");
    gameState = new GameState("Basic", map, 100, false);
    // THEN we have one name
    assertEquals(1, gameState.getPlayers().size());
  }

  @Test
  public void puttingMultiplePlayersInWorks()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    // GIVEN we have 3 Players with correct types
    Map<String, String> map = new HashMap<>();
    map.put("Paul", "CPU");
    map.put("Will", "Human");
    map.put("Drew", "Human");
    gameState = new GameState("Basic", map, 100, false);
    List<ViewPlayerInterface> players = gameState.getPlayers();
    List<String> names = new ArrayList<>();
    for (ViewPlayerInterface player : players){
      names.add(player.getName());
    }
    // THEN we will have three names returned with correct names
    assertEquals(3, gameState.getPlayers().size());
    assertTrue(names.contains("Paul"));
    assertTrue(names.contains("Will"));
    assertTrue(names.contains("Drew"));
  }
}
