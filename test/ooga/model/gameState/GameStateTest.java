package ooga.model.gameState;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import ooga.model.player.ComputerPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStateTest {
  GameState game;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    game = new GameState("Basic", new HashMap<>(), 100, false);
    game.createDeck(new HashMap<>());
    game.createPlayers(null);
  }

  @Test
  public void gameStartsInCorrectDirection(){
    // GIVEN we have a new game
    // THEN the game's direction should be 1
    assertEquals(1, game.getGameplayDirection());
  }

  @Test
  public void reversingGameplayIsSuccessful(){
    // GIVEN game starts
    // WHEN we reverse the order
    game.reverseGamePlay();
    // THEN the game's direction should now be -1
    assertEquals(-1, game.getGameplayDirection());
  }

  @Test
  public void continuousReversingHandlesCorrectly(){
    // GIVEN game starts
    // WHEN we reverse the game 4 times
    game.reverseGamePlay();
    game.reverseGamePlay();
    game.reverseGamePlay();
    game.reverseGamePlay();
    // THEN we are back to the original direction
    assertEquals(1, game.getGameplayDirection());
  }

  @Test
  public void keepingTrackOfCurrentPlayerWorksInNormalPlay(){
    // GIVEN a game starts with 3 players
    game.addPlayer(new ComputerPlayer("Player1", game, null, null));
    game.addPlayer(new ComputerPlayer("Player2", game, null, null));
    game.addPlayer(new ComputerPlayer("Player3", game, null, null));
    // THEN the current player is 0
    assertEquals(0, game.getCurrentPlayer());
    // AND WHEN we take two turns
    game.playTurn();
    game.playTurn();
    // THEN the current player is 2
    assertEquals(2, game.getCurrentPlayer());
  }

  @Test
  public void changingDirectionOfGameIsReflectedInCurrentPlayer(){
    // GIVEN a game starts with 3 players
    game.addPlayer(new ComputerPlayer("Player1", game, null, null));
    game.addPlayer(new ComputerPlayer("Player2", game, null, null));
    game.addPlayer(new ComputerPlayer("Player3", game, null, null));
    // WHEN we change direction
    game.reverseGamePlay();
    game.playTurn();
    // THEN the next player is two
    assertEquals(2, game.getCurrentPlayer());
  }

  @Test
  public void skippingPlayerWorks(){
    // GIVEN a game starts with 3 players
    game.addPlayer(new ComputerPlayer("Player1", game, null, null));
    game.addPlayer(new ComputerPlayer("Player2", game, null, null));
    game.addPlayer(new ComputerPlayer("Player3", game, null, null));
    // WHEN we skip right away
    game.skipNextPlayer();
    game.playTurn();
    // THEN the next player is two
    assertEquals(2, game.getCurrentPlayer());
    // AND WHEN we skip again
    game.skipNextPlayer();
    game.playTurn();
    // THEN it's now to player 1
    assertEquals(1, game.getCurrentPlayer());
  }

  @Test
  public void skippingEveryoneWorks(){
    // GIVEN a game starts with 3 players
    game.addPlayer(new ComputerPlayer("Player1", game, null, null));
    game.addPlayer(new ComputerPlayer("Player2", game, null, null));
    game.addPlayer(new ComputerPlayer("Player3", game, null, null));
    // WHEN we skip everyone
    game.skipEveryone();
    game.playTurn();
    // THEN the next player is still 0
    assertEquals(0, game.getCurrentPlayer());
  }
}
