package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import ooga.model.gameState.GameState;
import ooga.model.player.HumanPlayer;
import ooga.model.player.Player;
import ooga.model.player.PlayerGroup;
import ooga.model.player.PlayerGroupInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiscardColorCardTest {

  Player player;
  DiscardColorCard dcc;
  GameState gameState;
  PlayerGroupInterface group;

  @BeforeEach
  void start()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    gameState = new GameState();
    group = new PlayerGroup(new HashMap<>(), gameState);
    player = new HumanPlayer("Paul", group);
    dcc = new DiscardColorCard("Red");
  }

  @Test
  void defaultsSetCorrectly() {
    assertEquals("DiscardColor", dcc.getType());
    assertEquals(20, dcc.getNum());
  }

  @Test
  void successfullyDiscards() {
    gameState.discardCard(dcc);
    assertEquals("DiscardColor", gameState.getLastCardThrownType());
  }

  @Test
  void removesCorrectCards() {
    player.addCards(List.of(new SkipCard("Red"), new SkipCard("Blue"), new ReverseCard("Red"),
        new ReverseCard("Yellow")));
    dcc.executeAction(player);
    assertEquals(2, player.getHandSize());
  }

  @Test
  void doesNotRemoveCardsWhenThereAreNoneToRemove() {
    player.addCards(List.of(new SkipCard("Green"), new SkipCard("Blue"), new ReverseCard("Green"),
        new ReverseCard("Yellow")));
    dcc.executeAction(player);
    assertEquals(4, player.getHandSize());
  }

  @Test
  void doesNotEffectEmptyHand(){
    dcc.executeAction(player);
    assertEquals(0, player.getHandSize());
  }

  @Test
  void discardsRemovedCards(){
    player.addCards(List.of(new SkipCard("Red"), new SkipCard("Blue"), new ReverseCard("Green"),
        new ReverseCard("Yellow")));
    dcc.executeAction(player);
    assertEquals("Skip", gameState.getLastCardThrownType());
  }
}
