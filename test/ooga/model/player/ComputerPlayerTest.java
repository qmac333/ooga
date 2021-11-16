package ooga.model.player;

import ooga.model.cards.Card;
import ooga.model.cards.SkipCard;
import ooga.model.gameState.GameState;
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
  GameState gameState;

  Card cardToPlay;

  @BeforeEach
  public void setUp(){
    gameState = Mockito.mock(GameState.class);
    myPlayer = new ComputerPlayer("Paul", gameState);
  }

  @Test
  public void noCardResultsInDraw(){
    // GIVEN a player is asked to play with an empty hand
    myPlayer.playCard();
    // THEN they draw
    verify(gameState, times(1)).getNextCard();
  }

  @Test
  public void whenCardCantBePlayedDraw(){
    // GIVEN the player is asked to play when they don't have a rule abiding card
    cardToPlay = new SkipCard(gameState, "Yellow");
    myPlayer.addCard(cardToPlay);
    when(gameState.canPlayCard(any(Card.class))).thenReturn(false);
    myPlayer.playCard();
    // THEN they draw
    verify(gameState, times(1)).getNextCard();
  }

  @Test
  public void gameStateIsEffectedWhenCardCanBePlayed(){
    // GIVEN the player has a card they can play
    cardToPlay = new SkipCard(gameState, "Yellow");
    myPlayer.addCard(cardToPlay);
    when(gameState.canPlayCard(any(Card.class))).thenReturn(true);
    myPlayer.playCard();
    // THEN the card's action is performed
    verify(gameState, times(1)).skipNextPlayer();
    // AND they don't draw
    verify(gameState, times(0)).getNextCard();
  }
}
