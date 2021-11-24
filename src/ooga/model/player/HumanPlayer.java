package ooga.model.player;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class HumanPlayer extends Player {

  public HumanPlayer(String name, GameStatePlayerInterface game, Supplier<Integer> supplier) {
    super(name, game, supplier);
  }

  @Override
  public void playCard() {
    int index = -1;
    index = super.getMyIntegerSupplier().get();
    try {
      getMyHand().play(index, super.getMyGame());
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}
