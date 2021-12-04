package ooga.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import ooga.model.gameState.GameState;
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
    assertEquals(0, group.getCurrentPlayer());
    // AND WHEN we take two turns
    group.playTurn();
    group.playTurn();
    // THEN the current player is 2
    assertEquals(2, group.getCurrentPlayer());
  }

  @Test
  public void changingDirectionOfGameIsReflectedInCurrentPlayer(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // WHEN we change direction
    group.reverseOrder();
    group.playTurn();
    // THEN the next player is two
    assertEquals(2, group.getCurrentPlayer());
  }

  @Test
  public void skippingPlayerWorks(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // WHEN we skip right away
    group.skipNextPlayer();
    group.playTurn();
    // THEN the next player is two
    assertEquals(2, group.getCurrentPlayer());
    // AND WHEN we skip again
    group.skipNextPlayer();
    group.playTurn();
    // THEN it's now to player 1
    assertEquals(1, group.getCurrentPlayer());
  }

  @Test
  public void skippingEveryoneWorks(){
    // GIVEN a game starts with 3 players
    group.addPlayer(new ComputerPlayer("Player1", group));
    group.addPlayer(new ComputerPlayer("Player2", group));
    group.addPlayer(new ComputerPlayer("Player3", group));
    // WHEN we skip everyone
    group.skipEveryone();
    group.playTurn();
    // THEN the next player is still 0
    assertEquals(0, group.getCurrentPlayer());
  }
}
