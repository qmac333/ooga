package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.player.player.HumanPlayer;
import ooga.model.player.player.Player;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WildCardTest {

  Player player;

  @Mock
  Supplier supplier;

  @BeforeEach
  public void start(){
    player = new HumanPlayer("Paul", null);
    supplier = mock(Supplier.class);
    when(supplier.get()).thenReturn("Blue");
    player.setSuppliers(null, supplier);
  }

  @Test
  public void wildCardTest(){
    WildCard wc = new WildCard("Black");
    wc.executeAction(player);
    Assertions.assertEquals("Blue", wc.getMyColor());
  }
}
