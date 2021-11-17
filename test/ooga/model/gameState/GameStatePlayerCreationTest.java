package ooga.model.gameState;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class GameStatePlayerCreationTest {
  GameState gameState;

  @Test
  public void puttingInAnEmptyMapMeansNoPlayers(){
    // GIVEN we start a game with an empty map
    gameState = new GameState("Original", new HashMap<>(), 100, false);
    // THEN we have an empty set of names
    assertEquals(0, gameState.getPlayerNames().size());
  }

  @Test
  public void puttingAComputerPlayerInResultsInASizeOfOne(){
    // GIVEN we start the game with one computer player
    Map<String, String> map = new HashMap<>();
    map.put("Paul", "CPU");
    gameState = new GameState("Original", map, 100, false);
    // THEN we have one name
    assertEquals(1, gameState.getPlayerNames().size());
  }

  @Test
  public void puttingMultiplePlayersInWorks(){
    // GIVEN we have 3 Players with correct types
    Map<String, String> map = new HashMap<>();
    map.put("Paul", "CPU");
    map.put("Will", "Human");
    map.put("Drew", "Human");
    gameState = new GameState("Original", map, 100, false);
    // THEN we will have three names returned with correct names
    assertEquals(3, gameState.getPlayerNames().size());
    assertTrue(gameState.getPlayerNames().contains("Paul"));
    assertTrue(gameState.getPlayerNames().contains("Will"));
    assertTrue(gameState.getPlayerNames().contains("Drew"));
  }
}
