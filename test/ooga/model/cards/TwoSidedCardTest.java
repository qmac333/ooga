package ooga.model.cards;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import ooga.model.player.player.HumanPlayer;
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

  @Test
  void setColorDoesCorrectSide(){
    assertEquals("Red", tsc.getMyColor());
    tsc.flip();
    assertEquals("Green", tsc.getMyColor());
    tsc.setColor("Yellow");
    assertEquals("Yellow", tsc.getMyColor());
    tsc.flip();
    assertEquals("Red", tsc.getMyColor());
  }

  @Test
  void equalsWithItself(){
    assertTrue(tsc.equals(tsc));
  }

  @Test
  void equalsWithOneSidedCard(){
    OneSidedCard oc = new SkipCard("Red");
    assertFalse(tsc.equals(oc));
  }

  @Test
  void equalsWithIdenticalCard(){
    TwoSidedCard tsc2 = new TwoSidedCard(new SkipCard("Red"), new NumberCard("Green", 5));
    assertTrue(tsc.equals(tsc2));
  }

  @Test
  void equalsWithHalfIdenticalCard(){
    TwoSidedCard tsc2 = new TwoSidedCard(new SkipCard("Red"), new NumberCard("Blue", 5));
    assertFalse(tsc.equals(tsc2));
  }

  @Test
  void equalsWithOtherHalfIdenticalCard(){
    TwoSidedCard tsc2 = new TwoSidedCard(new SkipCard("Blue"), new NumberCard("Green", 5));
    assertFalse(tsc.equals(tsc2));
  }
}
