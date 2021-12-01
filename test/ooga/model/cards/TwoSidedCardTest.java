package ooga.model.cards;

import static org.mockito.Mockito.mock;

import ooga.model.player.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TwoSidedCardTest {
  TwoSidedCard tsc;

  @Mock
  HumanPlayer player;

  @BeforeEach
  void start(){
    tsc = new TwoSidedCard(new SkipCard("Red"), new NumberCard("Green", 5));
    player = mock(HumanPlayer.class);
  }

  @Test
  void startsOnCorrectSide(){
    assertEquals("Red", tsc.getMyColor());
    assertEquals(20, tsc.getNum());
    assertEquals("Skip", tsc.getType());
  }

  @Test
  void flippingItChangesSide(){
    tsc.flip();
    assertEquals("Green", tsc.getMyColor());
    assertEquals(5, tsc.getNum());
    assertEquals("Number", tsc.getType());
  }

  @Test
  void multipleFlipsMakesSense(){
    tsc.flip();
    tsc.flip();
    assertEquals("Red", tsc.getMyColor());
    assertEquals(20, tsc.getNum());
    assertEquals("Skip", tsc.getType());
  }

  @Test
  void callsCorrectAction(){
    tsc.executeAction(player);
    verify(player, times(1)).skipNextPlayer();
  }

  @Test
  void doesntGetCalledIfYouFlip(){
    tsc.flip();
    tsc.executeAction(player);
    verify(player, times(0)).skipNextPlayer();
  }

  @Test
  void twoFlipsCallsCorrectExecution(){
    tsc.flip();
    tsc.flip();
    tsc.executeAction(player);
    verify(player, times(1)).skipNextPlayer();
  }

}
