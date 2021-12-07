package ooga.model.player;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.DrawOneCard;
import ooga.model.gameState.GameState;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.player.HumanPlayer;
import ooga.model.player.player.Player;
import ooga.model.player.playerGroup.PlayerGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class HumanPlayerTest {

  @Mock
  Supplier intSupplier;

  @Mock
  Supplier stringSupplier;

  Player player;
  PlayerGroup pg;

  @Mock
  GameState game;

  @BeforeEach
  public void start() throws ReflectionErrorException {
    game = Mockito.mock(GameState.class);
    pg = new PlayerGroup(new HashMap<>(), game);
    player = new HumanPlayer("Paul", pg);
    intSupplier = Mockito.mock(Supplier.class);
    stringSupplier = Mockito.mock(Supplier.class);
    player.setSuppliers(intSupplier, stringSupplier);
  }

  @Test
  public void playsCardCorrectly() throws ReflectionErrorException, IOException {
    Mockito.when(intSupplier.get()).thenReturn(0);
    CardInterface card = new DrawOneCard("Blue");
    player.addCards(List.of(card));
    Mockito.when(game.canPlayCard(card)).thenReturn(true);
    player.playCard();
    Mockito.verify(game, Mockito.times(1)).addDraw(1);
  }

  @Test
  public void getColorReturnsWhatTheSupplierSays(){
    Mockito.when(stringSupplier.get()).thenReturn("Hi Paul");
    Assertions.assertEquals("Hi Paul", player.getColor());
    Mockito.when(stringSupplier.get()).thenReturn("Bye Paul");
    Assertions.assertEquals("Bye Paul", player.getColor());
  }

  @Test
  public void drawIsCalledWhenNeeded() throws ReflectionErrorException, IOException {
    Mockito.when(intSupplier.get()).thenReturn(-1);
    CardInterface card = new DrawOneCard("Blue");
    player.addCards(List.of(card));
    player.playCard();
    Mockito.verify(game, Mockito.times(1)).noPlayDraw();
  }

  @Test
  public void goesIntoCatchWhenRequired() throws ReflectionErrorException, IOException {
    Mockito.when(intSupplier.get()).thenReturn(2);
    CardInterface card = new DrawOneCard("Blue");
    player.addCards(List.of(card));
    player.playCard();
  }
}
