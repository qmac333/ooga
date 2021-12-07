package ooga.model.player;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import ooga.model.cards.NumberCard;
import ooga.model.cards.OneSidedCard;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.TwoSidedCard;
import ooga.model.cards.ViewCardInterface;
import ooga.model.cards.WildBlastCard;
import ooga.model.cards.WildCard;
import ooga.model.cards.WildDrawFourCard;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.player.ComputerPlayer;
import ooga.model.player.playerGroup.PlayerGroup;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {
  ComputerPlayer myPlayer;

  @Mock
  PlayerGroupPlayerInterface mockGroup;

  CardInterface cardToPlay;

  @BeforeEach
  public void setUp(){
    mockGroup = Mockito.mock(PlayerGroup.class);
    myPlayer = new ComputerPlayer("Paul", mockGroup);
  }

  @Test
  public void noCardResultsInDraw() throws ReflectionErrorException, IOException {
    // GIVEN a player is asked to play with an empty hand
    myPlayer.playCard();
    // THEN they draw
    verify(mockGroup, times(1)).noPlayDraw();
  }

  @Test
  public void whenCardCantBePlayedDraw() throws ReflectionErrorException, IOException {
    // GIVEN the player is asked to play when they don't have a rule abiding card
    cardToPlay = new SkipCard("Yellow");
    myPlayer.addCards(List.of(cardToPlay));
    when(mockGroup.canPlayCard(any(OneSidedCard.class))).thenReturn(false);
    myPlayer.playCard();
    // THEN they draw
    verify(mockGroup, times(1)).noPlayDraw();
  }

  @Test
  public void gameStateIsEffectedWhenCardCanBePlayed() throws ReflectionErrorException, IOException {
    // GIVEN the player has a card they can play
    cardToPlay = new SkipCard("Yellow");
    myPlayer.addCards(List.of(cardToPlay));
    when(mockGroup.canPlayCard(any(OneSidedCard.class))).thenReturn(true);
    myPlayer.playCard();
    // THEN the card's action is performed
    verify(mockGroup, times(1)).skipNextPlayer();
    // AND they don't draw
    verify(mockGroup, times(0)).noPlayDraw();
  }

  @Test
  public void getColorReturnsTheColorTheyHaveTheMost(){
    myPlayer.addCards(List.of(new NumberCard("Blue", 7), new SkipCard("Red"), new ReverseCard("Blue")));
    Assertions.assertEquals("Blue", myPlayer.getColor());
  }

  @Test
  public void getColorIgnoresBlackCards(){
    myPlayer.addCards(List.of(new NumberCard("Red", 7), new WildDrawFourCard("Black"), new WildCard("Black")));
    Assertions.assertEquals("Red", myPlayer.getColor());
  }

  @Test
  public void getColorDefaultsToRed(){
    myPlayer.addCards(List.of(new WildBlastCard("Black"), new WildDrawFourCard("Black"), new WildCard("Black")));
    Assertions.assertEquals("Red", myPlayer.getColor());
  }

  @Test
  public void awardingAndSettingPointsWorks(){
    myPlayer.awardPoints(100);
    Assertions.assertEquals(100, myPlayer.getPoints());
    myPlayer.setPoints(0);
    Assertions.assertEquals(0, myPlayer.getPoints());
    myPlayer.setPoints(50);
    Assertions.assertEquals(50, myPlayer.getPoints());
    myPlayer.awardPoints(50);
    Assertions.assertEquals(100, myPlayer.getPoints());
  }

  @Test
  public void dumpingCardsSetsHandSizeToZero(){
    myPlayer.addCards(List.of(new WildBlastCard("Black"), new WildDrawFourCard("Black"), new WildCard("Black")));
    Assertions.assertEquals(3, myPlayer.getHandSize());
    myPlayer.dumpCards();
    Assertions.assertEquals(0, myPlayer.getHandSize());
  }

  @Test
  public void addsHandPointsCorrectly(){
    myPlayer.addCards(List.of(new WildBlastCard("Black"), new WildDrawFourCard("Black"), new WildCard("Black")));
    Assertions.assertEquals(150, myPlayer.getNumPoints());
    myPlayer.addCards(List.of(new NumberCard("red", 2)));
    Assertions.assertEquals(152, myPlayer.getNumPoints());
  }

  @Test
  public void getValidIndexesWorks(){
    myPlayer.addCards(List.of(new WildBlastCard("Black"), new WildDrawFourCard("Black"), new WildCard("Black"), new SkipCard("Red")));
    when(mockGroup.canPlayCard(any())).thenReturn(true, true, false, true);
    Collection<Integer> indexes = myPlayer.getValidIndexes();
    Assertions.assertEquals(3, indexes.size());
    Assertions.assertTrue(indexes.contains(0));
    Assertions.assertTrue(indexes.contains(1));
    Assertions.assertTrue(indexes.contains(3));
  }

  @Test
  public void loadHandWorksAndGetViewCards(){
    Hand hand = new Hand();
    hand.add(List.of(new NumberCard("Red", 4), new SkipCard("Green")));
    myPlayer.loadHand(hand);
    Assertions.assertEquals(2, myPlayer.getHandSize());
    Assertions.assertEquals(hand, myPlayer.getMyHand());
    Assertions.assertEquals(2, myPlayer.getViewCards().size());
  }

  @Test
  public void flipHandWorks(){
    Hand hand = new Hand();
    hand.add(List.of(new TwoSidedCard(new NumberCard("Red", 4), new SkipCard("Green"))));
    myPlayer.loadHand(hand);
    myPlayer.flipHand();
    Collection<ViewCardInterface> vcis = myPlayer.getViewCards();
    Assertions.assertTrue(vcis.stream().anyMatch((ViewCardInterface v)-> v.getMyColor().equals("Green")));
  }
}
