package ooga.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ooga.model.cards.NumberCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.ViewCardInterface;
import ooga.model.cards.WildCard;
import ooga.model.gameState.GameState;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.player.ComputerPlayer;
import ooga.model.player.player.HumanPlayer;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerGroupTest {

  PlayerGroup group;

  @Mock
  GameState gameState;

  @Mock
  Player mockPlayer;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ReflectionErrorException {
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

  @Test
  public void correctMapReturned() throws ReflectionErrorException {
    Map<String, String> playerMap = new HashMap<>();
    PlayerGroup ng = new PlayerGroup(playerMap, gameState);
    assertEquals(playerMap, ng.getPlayerMap());
  }

  @Test
  public void groupReturnsWhatGameStateDoesForCanPlay(){
    when(gameState.canPlayCard(any())).thenReturn(false);
    assertFalse(group.canPlayCard(new WildCard("Black")));
    when(gameState.canPlayCard(any())).thenReturn(true);
    assertTrue(group.canPlayCard(new WildCard("Black")));
  }

  @Test
  public void addingCardGoesToCurrentPlayer() throws ReflectionErrorException {
    Player p = new HumanPlayer("Name", group);
    group.addPlayer(p);
    group.addCard("Skip", "Red");
    Assertions.assertEquals(1, p.getHandSize());
    Assertions.assertEquals(20, p.getNumPoints());
  }

  @Test
  public void toUnoMakesHandCorrect() {
    Player p = new HumanPlayer("Name", group);
    group.addPlayer(p);
    group.toUno("Blue", "3");
    Assertions.assertEquals(2, p.getHandSize());
  }

  @Test
  public void toWinMakesHandCorrect() {
    Player p = new HumanPlayer("Name", group);
    group.addPlayer(p);
    group.toWin("Blue", "3");
    Assertions.assertEquals(1, p.getHandSize());
    Assertions.assertEquals(3, p.getNumPoints());
  }

  @Test
  public void toColorWorks(){
    Player p = new HumanPlayer("Name", group);
    p.addCards(List.of(new NumberCard("Blue", 5), new WildCard("Black"), new SkipCard("Green")));
    group.addPlayer(p);
    group.toColor("Red", "Black");
    Collection<ViewCardInterface> vcis = p.getViewCards();
    assertEquals(2,
        (int) vcis.stream().filter((ViewCardInterface c) -> c.getMyColor().equals("Red")).count());
  }

  @Test
  public void sevenWorks(){
    Player p = new HumanPlayer("Name", group);
    group.addPlayer(p);
    group.seven("Red", "5");
    Collection<ViewCardInterface> vcis = p.getViewCards();
    assertEquals(7,
        (int) vcis.stream().filter((ViewCardInterface c) -> c.getMyColor().equals("Red") && c.getNum() == 5).count());
  }

  @Test
  public void countAndAwardPointsGivesAllPointsToCurrentPlayer(){
    Player p = new HumanPlayer("Name", group);
    group.addPlayer(p);
    Player p1 = new HumanPlayer("Name", group);
    group.addPlayer(p1);
    p1.addCards(List.of(new NumberCard("Red", 5)));
    p.addCards(List.of(new SkipCard("Red")));
    group.countAndAwardPoints();
    assertEquals(p.getPoints(), 25);
  }

  @Test
  public void loadHandsWorks(){
    Hand hand = new Hand();
    Hand hand1 = new Hand();
    Player p = new HumanPlayer("Name", group);
    group.addPlayer(p);
    Player p1 = new HumanPlayer("Name", group);
    group.addPlayer(p1);
    group.loadHands(List.of(hand, hand1));
    assertEquals(hand, p.getMyHand());
    assertEquals(hand1, p1.getMyHand());
  }

  @Test
  public void userPicksTurnWorks() throws ReflectionErrorException {
    Map<String, String> map = new HashMap<>();
    map.put("Paul", "Human");
    group = new PlayerGroup(map, gameState);
    assertTrue(group.userPicksCard());
    map = new HashMap<>();
    map.put("Paul", "CPU");
    group = new PlayerGroup(map, gameState);
    assertFalse(group.userPicksCard());
  }

  @Test
  public void cannotPlayEmptyGame() {
    Assertions.assertThrows(Exception.class, () ->group.playTurn());
  }

  @Test
  public void playerWithMultipleCardsWontMissUno(){
    Player p1 = new ComputerPlayer("Paul", group);
    group.addPlayer(p1);
    p1.addCards(List.of(new NumberCard("Green", 2), new NumberCard("Green", 5)));
    assertFalse(group.missedUno());
  }

  @Test
  public void playerWithOneCardMissesUno(){
    Player p1 = new ComputerPlayer("Paul", group);
    group.addPlayer(p1);
    p1.addCards(List.of(new NumberCard("Green", 2)));
    assertTrue(group.missedUno());
  }

  @Test
  public void callingUnoNegatesThis(){
    Player p1 = new ComputerPlayer("Paul", group);
    group.addPlayer(p1);
    p1.addCards(List.of(new NumberCard("Green", 2)));
    group.setUnoCalled(true);
    assertFalse(group.missedUno());
  }

  @Test
  public void onePlayerCallingItDoesntMessUpTheNext(){
    Player p1 = new ComputerPlayer("Paul", group);
    group.addPlayer(p1);
    Player p2 = new ComputerPlayer("Monty", group);
    group.addPlayer(p2);
    p2.addCards(List.of(new NumberCard("Green", 2)));
    p1.addCards(List.of(new NumberCard("Green", 2)));
    group.setUnoCalled(true);
    assertFalse(group.missedUno());
    group.loadNextPlayer();
    assertTrue(group.missedUno());
  }
}
