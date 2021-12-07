package ooga.model.player;

import java.util.List;
import ooga.model.cards.OneSidedCard;
import ooga.model.cards.CardInterface;
import ooga.model.cards.SkipCard;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.player.ComputerPlayer;
import ooga.model.player.playerGroup.PlayerGroup;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
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
  public void noCardResultsInDraw() throws ReflectionErrorException {
    // GIVEN a player is asked to play with an empty hand
    myPlayer.playCard();
    // THEN they draw
    verify(mockGroup, times(1)).noPlayDraw();
  }

  @Test
  public void whenCardCantBePlayedDraw() throws ReflectionErrorException {
    // GIVEN the player is asked to play when they don't have a rule abiding card
    cardToPlay = new SkipCard("Yellow");
    myPlayer.addCards(List.of(cardToPlay));
    when(mockGroup.canPlayCard(any(OneSidedCard.class))).thenReturn(false);
    myPlayer.playCard();
    // THEN they draw
    verify(mockGroup, times(1)).noPlayDraw();
  }

  @Test
  public void gameStateIsEffectedWhenCardCanBePlayed() throws ReflectionErrorException {
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
}
