package ooga.model.hand;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.model.cards.Card;
import ooga.model.cards.CardInterface;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.WildCard;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HandTest {

  Hand myHand;

  @Mock
  GameState myGame;

  @Mock
  WildCard card1;

  @Mock
  WildCard card2;

  @BeforeEach
  public void start() {
    myHand = new Hand();
    myGame = Mockito.mock(GameState.class);
  }

  @Test
  public void handSizeAdjustsCorrectly() {
    myHand.add(List.of(new SkipCard("red", null)));
    assertEquals(1, myHand.size());
    myHand.add(List.of(new ReverseCard("red", null), new NumberCard("red", 5)));
    assertEquals(3, myHand.size());
  }

  @Test
  public void handSizeDecreasesOnPlay() throws InvalidCardSelectionException {
    myHand.add(List.of(new ReverseCard("red", null), new NumberCard("red", 5)));
    myHand.play(1, myGame);
    assertEquals(1, myHand.size());
  }

  @Test
  public void correctMethodCalledOnTheGame() throws InvalidCardSelectionException {
    myHand.add(List.of(new SkipCard("red", null)));
    myHand.play(0, myGame);
    verify(myGame, times(1)).skipNextPlayer();
  }

  @Test
  public void throwsExceptionsWhenHandTooSmall() {
    assertThrows(InvalidCardSelectionException.class, () -> myHand.play(0, myGame));
  }

  @Test
  public void iterationGoesOverAllCards() {
    CardInterface c1 = new SkipCard("red", null);
    CardInterface c2 = new SkipCard("blue", null);
    CardInterface c3 = new SkipCard("green", null);
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