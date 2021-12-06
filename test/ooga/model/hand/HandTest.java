package ooga.model.hand;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.cards.DrawOneCard;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.WildCard;
import ooga.model.gameState.GameState;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.player.ComputerPlayer;
import ooga.model.player.player.Player;
import ooga.model.player.player.PlayerGameInterface;
import ooga.model.player.playerGroup.PlayerGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HandTest {

  Hand myHand;

  Player p;
  PlayerGroup pg;

  @Mock
  GameState myGame;

  @Mock
  WildCard card1;

  @Mock
  WildCard card2;

  @BeforeEach
  public void start() throws ReflectionErrorException {
    myHand = new Hand();
    myGame = Mockito.mock(GameState.class);
    pg = new PlayerGroup(new HashMap<>(), myGame);
    p = new ComputerPlayer("Paul", pg);
  }

  @Test
  public void handSizeAdjustsCorrectly() {
    myHand.add(List.of(new SkipCard("red")));
    assertEquals(1, myHand.size());
    myHand.add(List.of(new ReverseCard("red"), new NumberCard("red", 5)));
    assertEquals(3, myHand.size());
  }

  @Test
  public void handSizeDecreasesOnPlay() throws InvalidCardSelectionException {
    myHand.add(List.of(new ReverseCard("red"), new NumberCard("red", 5)));
    myHand.play(1, pg, p);
    assertEquals(1, myHand.size());
  }

  @Test
  public void correctMethodCalledOnTheGame() throws InvalidCardSelectionException {
    myHand.add(List.of(new DrawOneCard("red")));
    myHand.play(0, pg, p);
    verify(myGame, times(1)).addDraw(1);
  }

  @Test
  public void throwsExceptionsWhenHandTooSmall() {
    assertThrows(InvalidCardSelectionException.class, () -> myHand.play(0, pg, p));
  }

  @Test
  public void iterationGoesOverAllCards() {
    CardInterface c1 = new SkipCard("red");
    CardInterface c2 = new SkipCard("blue");
    CardInterface c3 = new SkipCard("green");
    List<CardInterface> cards = List.of(c1, c2, c3);
    myHand.add(cards);
    int i = 0;
    for (CardInterface card : myHand) {
      assertTrue(cards.contains(card));
      i++;
    }
    assertEquals(3, i);
  }

  @Test
  public void flipIsCalledOnAllCards(){
    card1 = Mockito.mock(WildCard.class);
    card2 = Mockito.mock(WildCard.class);
    myHand.add(List.of(card1, card2));
    myHand.flip();
    verify(card1, times(1)).flip();
    verify(card2, times(1)).flip();
  }
}
