package ooga.model.player;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class HumanPlayer extends Player {

  public HumanPlayer(String name, GameStatePlayerInterface game) {
    super(name, game);
  }

  @Override
  public void playCard() {
    int index = -1;
    index = super.getMyIntegerSupplier().get();
    try {
      if (index < 0){
        super.addCards(getMyGame().noPlayDraw());
      } else {
        getMyHand().play(index, super.getMyGame(), this);
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public String getColor(){
    return super.getMyStringSupplier().get();
  }
}
