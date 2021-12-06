package ooga.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import ooga.model.gameState.GameState;
import ooga.model.player.player.ComputerPlayer;
import ooga.model.player.playerGroup.PlayerGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerGroupTest {

  PlayerGroup group;

  @Mock
  GameState gameState;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    gameState = mock(GameState.class);
    group = new PlayerGroup(new HashMap<>(), gameState);
    when(gameState.noPlayDraw()).thenReturn(new ArrayList<>());
  }

  @Test
  public void gameStartsInCorrectDirection(){
    // GIVEN we have a new game
    // THEN the game's direction should be 1
    assertEquals(1, group.getMyOrder());
  }

  @Test
  public void reversingGameplayIsSuccessful(){
    // GIVEN game starts
    // WHEN we reverse the order
    group.reverseOrder();
    // THEN the game's direction should now be -1
    assertEquals(-1, group.getMyOrder());
  }

  @Test
  public void continuousReversingHandlesCorrectly(){
    // GIVEN game starts
    // WHEN we reverse the game 4 times
    group.reverseOrder();
    group.reverseOrder();
    group.reverseOrder();
    group.reverseOrder();
    // THEN we are back to the original direction
    assertEquals(1, group.getMyOrder());
  }

  @Test
  public void keepingTrackOfCurrentPlayerWorksInNormalPlay(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // THEN the current player is 0
    assertEquals(0, group.getCurrentPlayerIndex());
    // AND WHEN we take two turns
    group.loadNextPlayer();
    group.loadNextPlayer();
    // THEN the current player is 2
    assertEquals(2, group.getCurrentPlayerIndex());
  }

  @Test
  public void changingDirectionOfGameIsReflectedInCurrentPlayer(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // WHEN we change direction
    group.reverseOrder();
    group.loadNextPlayer();
    // THEN the next player is two
    assertEquals(2, group.getCurrentPlayerIndex());
  }

  @Test
  public void skippingPlayerWorks(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // WHEN we skip right away
    group.skipNextPlayer();
    group.loadNextPlayer();
    // THEN the next player is two
    assertEquals(2, group.getCurrentPlayerIndex());
    // AND WHEN we skip again
    group.skipNextPlayer();
    group.loadNextPlayer();
    // THEN it's now to player 1
    assertEquals(1, group.getCurrentPlayerIndex());
  }

  @Test
  public void skippingEveryoneWorks(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // WHEN we skip everyone
    group.skipEveryone();
    group.loadNextPlayer();
    // THEN the next player is still 0
    assertEquals(0, group.getCurrentPlayerIndex());
  }
}
